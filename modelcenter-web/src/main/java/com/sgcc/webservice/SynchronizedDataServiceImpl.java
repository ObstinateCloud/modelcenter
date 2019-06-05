package com.sgcc.webservice;

public class SynchronizedDataServiceImpl implements SynchronizedDataService {

	@Override
	public String synchronizedData(String tableName, String data) {
		
		//TODO 从数据库中查询出同步数据相对应的字段
		return "数据：table="+tableName+"data="+data;
	
	}
   
}