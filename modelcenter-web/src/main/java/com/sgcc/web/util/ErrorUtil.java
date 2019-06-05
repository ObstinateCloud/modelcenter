package com.sgcc.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 

* <p>Title: ErrorUtil</p>  

* <p>Description:获取错误码工具 </p>  

* @author mengjinyuan  

* @date 2019年3月14日
 */
public class ErrorUtil {
	
	private static volatile Properties properties;
	private static Logger logger=LoggerFactory.getLogger(ErrorUtil.class);
	
	static{
		if (properties==null) {
			synchronized (ErrorUtil.class) {
				if (properties==null) {
					properties=new Properties();
					logger.info("加载错误码配置文件");
					System.err.println("加载错误码配置文件");
					InputStream in = ErrorUtil.class.getClassLoader().getResourceAsStream("config/errorCode.properties");
					BufferedReader bf = new BufferedReader(new InputStreamReader(in));  
					try {
						properties.load(bf);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private ErrorUtil(){
		
	}
	
	public static String getMsg(String code){
		return properties.getProperty(code);
	}
}
