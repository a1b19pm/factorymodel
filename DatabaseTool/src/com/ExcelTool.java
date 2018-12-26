package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelTool {
	static Integer[] columnWidthArr = new Integer[DatabaseTableConstruction.showColumnNum];
	/**
	 * excel样式
	 */
	static HSSFCellStyle bgstyle;
	/**
	 * 表行数
	 */
	static int tableRowNum = 0;
	
	/**
	 * 设置excel数据
	 * @param dataList
	 * @param tableName
	 * @return
	 */
	public static HSSFWorkbook setExcelByDataList(HSSFWorkbook hwb, List<String[]> dataList, String tableName, int tableIndex){
		tableRowNum = 0; 
		HSSFSheet sheet = hwb.createSheet();
		setTableName(hwb, sheet, tableName, tableIndex);
		bgstyle = setTableStyleForBackgroundColor(hwb);
		setTableTitle(sheet);
		bgstyle = setTableStyleForBorder(hwb);
		for (int i = 0; i < dataList.size(); i++) {
			String[] dataArr = dataList.get(i);
			HSSFRow row = createRow(sheet, tableRowNum++);
			createCell(row, bgstyle, dataArr);
		}
		return hwb;
	}
	
	/**
	 * 生成Excel
	 * @param filePath
	 */
	public static void exportExcel(String filePath){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = new Date();
		System.out.println("任务启动" + format.format(startDate));
		
		List<String> tableList = DatabaseTableConstruction.getAllTableNames();
		HSSFWorkbook hwb = new HSSFWorkbook();// 開啟工作薄
		for (int i = 0; i < tableList.size(); i++) {
			String tableName = tableList.get(i);
			List<String[]> dataList = DatabaseTableConstruction.getDataForDatabase(tableName);
			ExcelTool.setExcelByDataList(hwb, dataList, tableName, i);
		}
		try {
			outFile(hwb, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			Date nowDate = new Date();
			System.out.println("任务结束" + format.format(nowDate));
			System.out.println("耗时" + (nowDate.getTime() - startDate.getTime())/1000+"秒");
		}
	}
	
	/**
	 * 创建表名行
	 * @param sheet
	 * @param tableName
	 */
	private static void setTableName(HSSFWorkbook hwb, HSSFSheet sheet, String tableName, int tableIndex) {
		hwb.setSheetName(tableIndex, tableName);
		HSSFRow row = createRow(sheet, tableRowNum++);
		String[] dataArr = new String[]{tableName};
		createCell(row, bgstyle, dataArr);
	}

	/**
	 * 创建行
	 * @param sheet
	 * @return
	 */
	private static HSSFRow createRow(HSSFSheet sheet, int rowNum){
		HSSFRow row = sheet.createRow(rowNum);
		row.setHeight((short) (2*256));
		return row;
	}
	
	/**
	 * 创建列元素
	 * @param row
	 * @param bgstyle
	 * @param dataArr
	 */
	private static void createCell(HSSFRow row, HSSFCellStyle bgstyle, String[] dataArr){
		for (int i = 0; i < dataArr.length; i++) {
			HSSFCell cell = row.createCell(i);
			String data = dataArr[i];
			cell = row.createCell(i);
			cell.setCellValue(data);
			if(bgstyle != null){
				cell.setCellStyle(bgstyle);
			}
		}
	}

	/**
	 * 设置表格样式
	 * @param hwb
	 * @return
	 */
	private static HSSFCellStyle setTableStyleForBackgroundColor(HSSFWorkbook hwb){
		setColumnWidthArr();//设置列宽
				
		HSSFCellStyle bgstyle = hwb.createCellStyle();//背景style(灰色)
		bgstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
		bgstyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		bgstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		return bgstyle;
	}
	
	/**
	 * 设置边框
	 * @param hwb
	 * @return
	 */
	private static HSSFCellStyle setTableStyleForBorder(HSSFWorkbook hwb){
		HSSFCellStyle bgstyle = hwb.createCellStyle();
		bgstyle.setBorderBottom(CellStyle.BORDER_THIN);
		bgstyle.setBorderLeft(CellStyle.BORDER_THIN);
		bgstyle.setBorderRight(CellStyle.BORDER_THIN);
		bgstyle.setBorderTop(CellStyle.BORDER_THIN);
		return bgstyle;
	}
	
	/**
	 * 设置表格标题
	 */
	private static void setTableTitle(HSSFSheet sheet){
		HSSFRow row = createRow(sheet, tableRowNum++);
		setColumnWidth(sheet, columnWidthArr);
		createCell(row, bgstyle, DatabaseTableConstruction.titleArr);
	}
	
	/**
	 * 输出文件
	 * @param hwb
	 * @param fileName
	 * @throws Exception
	 */
	private static void outFile(HSSFWorkbook hwb, String fileName) throws Exception{
		File file = new File(fileName); 
		OutputStream out = new  FileOutputStream(file);
		try {
			hwb.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 设置列宽
	 * @param sheet
	 * @param columnWidthArr
	 */
	private static void setColumnWidth(HSSFSheet sheet, Integer[] columnWidthArr){
		for (int i = 0; i < columnWidthArr.length; i++) {
			Integer width = columnWidthArr[i];
			sheet.setColumnWidth(i, 256*width+184);
		}
	}
	
	/**
	 * 设置列宽数组
	 */
	private static void setColumnWidthArr(){
		columnWidthArr[0] = 25;
		columnWidthArr[1] = 15;
		columnWidthArr[2] = 15;
		columnWidthArr[3] = 10;
		columnWidthArr[4] = 10;
//		columnWidthArr[5] = 15;

	}
}
