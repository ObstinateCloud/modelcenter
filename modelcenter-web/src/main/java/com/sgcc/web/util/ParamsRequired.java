package com.sgcc.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.sgcc.annotation.Validate;
import com.sgcc.exception.IllegalParamException;

public class ParamsRequired {

	public static <T>boolean validate(T pojo) throws IllegalParamException {
		Class<T> clazz = (Class<T>) getSuperClassGenricType(pojo.getClass());
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		int count = 0;
		try {

			for (Field field : fields) {
				Method method = null;
				// 判断null
				if (field.isAnnotationPresent(Validate.class)) {
					Validate validate = field.getAnnotation(Validate.class);
					switch (validate.type()) {
					case NOT_NULL:
						method = clazz.getMethod("get" + getMethodName(field.getName()));
						Object obj = method.invoke(pojo);
						if (obj == null) {
							sb.append(++count + ".)").append(validate.message()).append("\n");
						}
						break;
					case NOT_EMPTY:
						method = clazz.getMethod("get" + getMethodName(field.getName()));
						if (field.getGenericType().toString().equals("class java.lang.String")) {
							String str = (String) method.invoke(pojo);
							if (str == null || str.equals("") || str.equals(" ")) {
								sb.append(++count + ".)").append(validate.message()).append("\n");
							}
						}
						break;
					case DATE:
						method = clazz.getMethod("get" + getMethodName(field.getName()));
						String str=null==method.invoke(pojo)?"":method.invoke(pojo).toString();
						if(!ValidateUtils.Date(str)){
							sb.append(++count + ".)").append(validate.message()).append("\n");
						}
						break;
					case MAX_LEN:
						method = clazz.getMethod("get" + getMethodName(field.getName()));
						if (field.getGenericType().toString().equals("class java.lang.String")) {
							String str1 = (String) method.invoke(pojo);
							int maxLen=(int) validate.max();
							if (str1!=null&&str1.length()>maxLen) {
								sb.append(++count + ".)").append(validate.message()).append("\n");
							}
						}
						break;
					default:
						break;
					}
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sb.toString().length() > 0) {
			throw new IllegalParamException(sb.toString());
		}
		return true;
	}

	/**
	 * 将第一个字母变大写
	 * @param fildeName
	 * @return
	 * @throws Exception
	 */
	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
	
	  /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型
     * @param clazz 
     * @return 返回第一个类型
     */
    public static Class<?> getSuperClassGenricType(Class<?> clazz) {
        return getSuperClassGenricType(clazz, 0);
    }
 
    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型
     * @param clazz
     * @param 返回某下标的类型
     */
    public static Class getSuperClassGenricType(Class clazz, int index)
            throws IndexOutOfBoundsException {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}