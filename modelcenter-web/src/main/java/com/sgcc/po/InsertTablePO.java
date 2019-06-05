package com.sgcc.po;

import java.util.List;

public class InsertTablePO {

	private String tableName;
	private List<InsertDataPO> insertData;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<InsertDataPO> getInsertData() {
		return insertData;
	}
	public void setInsertData(List<InsertDataPO> insertData) {
		this.insertData = insertData;
	}
	@Override
	public String toString() {
		return "InsertTableVO [tableName=" + tableName + ", insertData=" + insertData + "]";
	}
}
