package com.sgcc.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sgcc.enu.ExcelVersion;
import com.sgcc.po.ExcelSheetPO;

/**
 * 

* <p>Title: ExcelUtil</p>  

* <p>Description: excel工具类 提供读取功能</p>  

* @author mengjinyuan  

* @date 2019年3月14日
 */
public class ExcelUtil {

	/**
	 * 读取excel文件里面的内容 支持日期，数字，字符，函数公式，布尔类型
	 * 
	 * 
	 */

	private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	/**
	 * 读取excel文件内容，将表格数据封装到map中
	 * @param file excel文件
	 * @param startRow 从哪一行开始读，下标从0开始
	 * @param rowCount 要读取的行数
	 * @param columnCount 要读取的列数
	 * @return List<ExcelSheetPO>
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<ExcelSheetPO> readExcel(File file, Integer startRow,Integer rowCount, Integer columnCount)
			throws FileNotFoundException, IOException {
		logger.info("开始从文件" + file.getAbsolutePath() + "获取数据");
		// 根据后缀名称判断excel的版本
		String extName = FileUtil.getFileExtName(file);
		Workbook wb = null;
		if (ExcelVersion.V2003.getSuffix().equals(extName)) {
			logger.info("excel版本：v2003");
			wb = new HSSFWorkbook(new FileInputStream(file));
		} else if (ExcelVersion.V2007.getSuffix().equals(extName)) {
			logger.info("excel版本：v2007");
			wb = new XSSFWorkbook(new FileInputStream(file));
		} else {
			// 无效后缀名称，这里之能保证excel的后缀名称，不能保证文件类型正确，不过没关系，在创建Workbook的时候会校验文件格式
			throw new IllegalArgumentException("Invalid excel version");
		}
		// 开始读取数据
		List<ExcelSheetPO> sheetPOList = new ArrayList<>();

		// 解析sheet
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			ExcelSheetPO sheetPO = new ExcelSheetPO();
			// 行数据
			List<Map<String, Object>> rowList = new LinkedList<>();
			logger.info("开始读取sheet:" + sheet.getSheetName());
			sheetPO.setSheetName(sheet.getSheetName());
			sheetPO.setRowList(rowList);
			// 用于存放表头和数据索引的映射
			Map<Integer, String> headerMap = new HashMap<>();

			int readRowCount = 0;
			int realStartRow=0;
			if (rowCount == null || rowCount > sheet.getPhysicalNumberOfRows()) {
				readRowCount = sheet.getPhysicalNumberOfRows();
			} else {
				readRowCount = rowCount;
			}
			if (startRow == null || startRow > sheet.getPhysicalNumberOfRows()) {
				realStartRow = sheet.getFirstRowNum();
			} else {
				realStartRow = startRow;
			}

			// 解析sheet 的行
			for (int j = realStartRow; j < readRowCount; j++) {
				Row row = sheet.getRow(j);
				if (row == null) {
					continue;
				}
				if (row.getFirstCellNum() < 0) {
					continue;
				}
				int readColumnCount = 0;
				if (columnCount == null || columnCount > row.getLastCellNum()) {
					readColumnCount = (int) row.getLastCellNum();
				} else {
					readColumnCount = columnCount;
				}

				// 默认第一行为表头
				if (j == realStartRow) {
					for (int m = 0; m < readColumnCount; m++) {
						Cell cell = row.getCell(m);
						headerMap.put(m, cell.getStringCellValue());
					}
				} else {
					// 列数据
					Map<String, Object> columnMap = new HashMap<>();
					// 解析sheet 的列
					for (int k = 0; k < readColumnCount; k++) {
						Cell cell = row.getCell(k);
						columnMap.put(headerMap.get(k), getCellValue(wb, cell));
					}
					rowList.add(columnMap);
				}

			}
			sheetPOList.add(sheetPO);
			logger.info("sheet:" + sheet.getSheetName() + "读取完毕");
		}
		return sheetPOList;
	}

	/**
	 * 根据excel单元格的数据类型返回对应的java类型数据
	 * @param wb
	 * @param cell
	 * @return
	 */
	private static Object getCellValue(Workbook wb, Cell cell) {

		Object columnValue = null;
		if (cell != null) {
			DecimalFormat df = new DecimalFormat("0");// 格式化 number
			// String
			// 字符
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
			DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
			switch (cell.getCellType()) {
			case STRING:
				columnValue = cell.getStringCellValue();
				break;
			case NUMERIC:
				if ("@".equals(cell.getCellStyle().getDataFormatString())) {
					columnValue = df.format(cell.getNumericCellValue());
				} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
					columnValue = nf.format(cell.getNumericCellValue());
				} else {
					columnValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
				}
				break;
			case BOOLEAN:
				columnValue = cell.getBooleanCellValue();
				break;
			case BLANK:
				columnValue = "";
				break;
			case FORMULA:
				// 格式单元格
				FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
				evaluator.evaluateFormulaCell(cell);
				CellValue cellValue = evaluator.evaluate(cell);
				columnValue = cellValue.getNumberValue();
				break;
			default:
				columnValue = cell.toString();
			}
		}
		return columnValue;
	}
}