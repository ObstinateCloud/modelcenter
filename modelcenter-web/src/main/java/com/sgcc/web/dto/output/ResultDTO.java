package com.sgcc.web.dto.output;

import com.sgcc.enu.ResultEnum;

/**
 * 
 * 
 * <p>
 * Title: ResultDTO
 * </p>
 * 
 * <p>
 * Description: 返回结果数据规范
 * </p>
 * 
 * @author mengjinyuan
 * 
 * @date 2019年3月14日
 */
public class ResultDTO {
	private String code;
	private String msg;
	private Object object = new Object();
	
	public ResultDTO() {

	}

	public ResultDTO(String code, String msg, Object object) {
		this.code = code;
		this.msg = msg;
		this.object = object;
	}

	public ResultDTO(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static ResultDTO success() {
		return new ResultDTO(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
	}

	public static ResultDTO success(String msg) {
		return new ResultDTO(ResultEnum.SUCCESS.getCode(), msg);
	}

	public static ResultDTO success(String msg, Object object) {
		return new ResultDTO(ResultEnum.SUCCESS.getCode(), msg, object);
	}

	public static ResultDTO fail() {
		return new ResultDTO(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
	}

	public static ResultDTO fail(String msg) {
		return new ResultDTO(ResultEnum.FAIL.getCode(), msg);
	}

	public static ResultDTO error() {
		return new ResultDTO(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
	}

	public static ResultDTO error(String msg) {
		return new ResultDTO(ResultEnum.ERROR.getCode(), msg);
	}

	public static ResultDTO error(String code, String msg) {
		return new ResultDTO(code, msg);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
