package com.sgcc.exception;

/**
 * 

* <p>Title: ConsumerException</p>  

* <p>Description:Consumer自定义异常类 </p>  

* @author mengjinyuan  

* @date 2019年3月14日
 */
public class ConsumerException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ConsumerException() {
		// TODO Auto-generated constructor stub
	}

	public ConsumerException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ConsumerException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ConsumerException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ConsumerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
}
