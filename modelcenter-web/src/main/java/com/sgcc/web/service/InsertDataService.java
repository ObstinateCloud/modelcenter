package com.sgcc.web.service;

import com.alibaba.fastjson.JSONObject;
import com.sgcc.po.VariableJSONObjectPO;

/**
 * 万能插入数据

* <p>Title: InsertDataService</p>  

* <p>Description: </p>  

* @author mengjinyuan  

* @date 2019年3月14日
 */
public interface InsertDataService {

	/**
	 * 插入数据
	 * @param tableName 表名
	 * @param json json字符串
	 */
	public void insertData(String tableName,String json);
	
	/**
	 * 插入数据
	 * @param tableName 表名
	 * @param jsonObject json对象
	 */
	public void insertData(String tableName,JSONObject jsonObject);
	
	/**
	 * 插入数据
	 * @param tableName
	 * @param variableJSONObject 该对象可以修改json的key @See VariableJSONObject
	 */
	public void insertData(String tableName,VariableJSONObjectPO variableJSONObject);
}
