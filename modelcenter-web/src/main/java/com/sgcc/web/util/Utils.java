package com.sgcc.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * 常用工具
 * 
 * <p>
 * Title: Utils
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author mengjinyuan
 * 
 * @date 2019年3月14日
 */
public class Utils {

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);

	/**
	 * 打印当前线程堆栈信息
	 * 
	 * @param prefix
	 */
	public static void printTrack(String prefix) {
		StackTraceElement[] st = Thread.currentThread().getStackTrace();

		if (null == st) {
			logger.info("invalid stack");
			return;
		}

		StringBuffer sbf = new StringBuffer();

		for (StackTraceElement e : st) {
			if (sbf.length() > 0) {
				sbf.append(" <- ");
				sbf.append(System.getProperty("line.separator"));
			}

			sbf.append(java.text.MessageFormat.format("{0}.{1}() {2}", e.getClassName(), e.getMethodName(),
					e.getLineNumber()));
		}

		logger.info(prefix + "\n************************************************************\n" + sbf.toString()
				+ "\n************************************************************");
	}
	


	/**
	 * 将一个对象中的值传到目标对象响应的属性中
	 * 使用场景：两个对象的字段不完全一样，null值不复制
	 * @param model 源对象
	 * @param target 目标对象
	 * @throws Exception
	 */
	public static void transValues(Object model,Object target) throws Exception {
        //获得实体类
        Class<?> clazz = model.getClass();
        Class<?> clazz1 = target.getClass();
        //查看有那些字段
        Field[] fields = clazz.getDeclaredFields();
        Field[] fields2=clazz1.getDeclaredFields();
        Map<String, Field> fieldMap=new HashMap<>();
        for(Field field:fields2){
        	fieldMap.put(field.getName(), field);
        }
        //遍历
        for (Field field : fields) {
            //属性的名字
            String fieldName = field.getName();
            //属性的类型
            //Class<?> type = field.getType();
            //再得到getter方法的名字
            String getMethodName = "get" + (fieldName.charAt(0)+"").toUpperCase() + fieldName.substring(1);
            Method getMethod = clazz.getMethod(getMethodName);
            //判断model的哪个属性是否为空,利用get方法
            Object object = getMethod.invoke(model);
            if(object!=null) {
            	if(fieldMap.get(fieldName)!=null){
            		 //不为空就进行赋值
                    // 通过属性名，来获取对应的setXXX的名字
                    String setMethodName = "set" + (fieldName.charAt(0)+"").toUpperCase() + fieldName.substring(1);
                    //这是set方法
                    Method setMethod = clazz1.getMethod(setMethodName, field.getType());
                    //这里需要进行类型的强制转换吗?
                    setMethod.invoke(target, object);
            	}
            }
        }
    }
	
	/**
	 * 将List里对象复制给目标对象并返回目标对象list
	 * @param source 原类型列表
	 * @param target 目标类型
	 * @param ignore 忽略转化的属性
	 * @return 目标类型列表
	 */
	public static <E, T> List<T> copyListProperties(List<E> source,Class<T> target,String ...ignore){
		List<T> tagetList=new ArrayList<>();
		if(source!=null){
			for(E e:source){
				try {
					T t = target.newInstance();
					BeanUtils.copyProperties(e,t,ignore);
					tagetList.add(t);
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		}
		return tagetList;
	}
	
	/**
	 * 生成uuid主键
	 * @return
	 */
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString(); 
		//去掉“-”符号 
		return uuid.replaceAll("-", "");
	}
	
	/**
	 * 获取两个时间内的间隔的秒数
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getBetWeenSeconds(Date start,Date end) {
		return (int) ((end.getTime() - start.getTime()) / 1000);
	}
	
	 /**
     * 把一个字符串的第一个字母大写
     */
    public static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        if(items[0]<='z'&&items[0]>='a') {
            items[0] = (byte) ((char) items[0] - 'a' + 'A');
        }
        return new String(items);
    }
}