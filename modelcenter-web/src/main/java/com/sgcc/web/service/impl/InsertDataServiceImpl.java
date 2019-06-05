package com.sgcc.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sgcc.po.InsertDataPO;
import com.sgcc.po.InsertTablePO;
import com.sgcc.po.VariableJSONObjectPO;
import com.sgcc.web.mapper.extend.CommonDao;
import com.sgcc.web.service.InsertDataService;

@Service
public class InsertDataServiceImpl implements InsertDataService{

	@Autowired
	private CommonDao commonDao;
	
	@Override
	public void insertData(String tableName,String json) {
		JSONObject jsonObject=JSONObject.parseObject(json);
		insertData(tableName,jsonObject);
	}

	@Override
	public void insertData(String tableName,VariableJSONObjectPO variableJSONObject) {
		InsertTablePO insertTableVO=new InsertTablePO();
		List<InsertDataPO> insertData=new ArrayList<>();
		JSONObject jsonObject=variableJSONObject.getJsonObject();
		Set<String> keySet = jsonObject.keySet(); 
		for(String key:keySet){
			insertData.add(new InsertDataPO(key, jsonObject.getString(key)));
		}
		insertTableVO.setTableName(tableName);
		insertTableVO.setInsertData(insertData);
		commonDao.insertData(insertTableVO);
	
	}

	@Override
	public void insertData(String tableName, JSONObject jsonObject) {
		VariableJSONObjectPO variableJSONObject=new VariableJSONObjectPO(jsonObject);
		insertData(tableName,variableJSONObject);
	}
}
