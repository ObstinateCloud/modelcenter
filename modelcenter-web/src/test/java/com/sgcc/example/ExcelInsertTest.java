package com.sgcc.example;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sgcc.web.service.InsertDataService;
import com.sgcc.web.util.ExcelUtil;
import com.sgcc.JUnit4ClassRunner;
import com.sgcc.po.ExcelSheetPO;
import com.sgcc.po.VariableJSONObjectPO;

/**
 * 测试插入数据

* <p>Title: InsertDataTest</p>  

* <p>Description: </p>  

* @author mengjinyuan  

* @date 2019年3月16日
 */
@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring-beans.xml" })
public class ExcelInsertTest {

	@Autowired
	private InsertDataService insertDataService;
	
	public void testInsertTableDataFromJSON() {
		/**
		 * 从json数据中插入
		 */
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", "10003");
		jsonObject.put("classPath", "bbbbb");
		jsonObject.put("cronExpression", "0/10 * * * *?");

		VariableJSONObjectPO variableJSONObject = new VariableJSONObjectPO(jsonObject);
		variableJSONObject.setUnderline(true);
		insertDataService.insertData("cron_task", variableJSONObject);

	}
	

	public void testInsertTableDataFromEXCEL() {
		/**
		 * 测试从excel插入数据
		 */
		File file = new File("D:\\test.xls");
		try {
			List<ExcelSheetPO> list = ExcelUtil.readExcel(file,1,null, null);
			for (ExcelSheetPO excelSheetPO : list) {
				List<Map<String, Object>> rowList = excelSheetPO.getRowList();
				JSONObject jsonObject = new JSONObject();
				for (Map<String, Object> columnMap : rowList) {
					jsonObject = JSONObject.parseObject(JSON.toJSONString(columnMap));
					insertDataService.insertData(excelSheetPO.getSheetName(), jsonObject);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void print(){
		System.err.println("");
	}

}
