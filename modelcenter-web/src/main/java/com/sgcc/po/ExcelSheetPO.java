package com.sgcc.po;

import java.util.List;
import java.util.Map;

/**
 * 定义表格的行数据对象
 */
public class ExcelSheetPO {

    /**
     * sheet的名称
     */
    private String sheetName;

	/**
     * 表格行数据
     */
    private List<Map<String, Object>> rowList;
    
    public String getSheetName() {
		return sheetName;
	}


	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	
	public List<Map<String, Object>> getRowList() {
		return rowList;
	}


	public void setRowList(List<Map<String, Object>> rowList) {
		this.rowList = rowList;
	}


	@Override
	public String toString() {
		return "ExcelSheetPO [sheetName=" + sheetName + ", rowList=" + rowList + "]";
	}
}