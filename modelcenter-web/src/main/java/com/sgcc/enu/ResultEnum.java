package com.sgcc.enu;

/**
 * 

* <p>Title: ResultEnum</p>  

* <p>Description:返回结果码枚举 </p>  

* @author mengjinyuan  

* @date 2019年3月14日
 */
public enum ResultEnum {
	
	SUCCESS("0","成功"),FAIL("1","失败"),ERROR("2","错误"),NOLOGIN("3","未登录");
	private String code;
	private String msg;
	
	ResultEnum(String code,String msg){
		this.code=code;
		this.msg=msg;
	}
	
	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
