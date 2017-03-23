package com.hy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ClassName: POIExcelUtil 
 * @Description: 读取excel工具类
 * @author wwh
 * @date 2016-12-12
 */
public class POIExcelUtil {

	private static final String EXTENSION_XLS = "xls";
	private static final String EXTENSION_XLSX = "xlsx";
	public static void readExcel(String filename) throws FileNotFoundException{
		//获取workbook对象
		Workbook workbook = null;
		InputStream inputStream = new FileInputStream(filename);
		try {
			workbook = getWorkbook(inputStream,filename);
			Sheet sheet =  workbook.getSheetAt(0); //默认一个工作表
			
			//循环行Row,默认有列名，rowNum从1开始
			for(int rowNum=1; rowNum<= sheet.getPhysicalNumberOfRows(); rowNum++){
				Row row = sheet.getRow(rowNum);
				if(row == null){
					continue;
				}
				//循环列Cell
				for(int cellNum=0; cellNum<=row.getLastCellNum(); cellNum++){
					Cell cell = row.getCell(cellNum);
					if(cell == null){
						continue;
					}
					System.out.println("  "+getsValue(cell));
				}
				System.out.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private static Workbook getWorkbook(InputStream inputStream, String filename) throws IOException {
		Workbook wb = null;
		if(filename.endsWith(EXTENSION_XLS)){
			wb = new HSSFWorkbook(inputStream);//Excel2003
		}else if(filename.endsWith(EXTENSION_XLSX)){
			wb = new XSSFWorkbook(inputStream);//Excel2007及以上
		}
		return wb;
	}
	private static String getsValue(Cell cell) {
		if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
			return String.valueOf(cell.getBooleanCellValue());
		}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			return String.valueOf(cell.getNumericCellValue());
		}else{
		return String.valueOf(cell.getStringCellValue());
		}
	}

	
	private Workbook workbook;//excel对象
	private Map<String,String> properties;//key:excel标题，value:对象属性
	private Map<Integer,String> header;//标题
	private String date_format = "yyyy-MM-dd";//默认日期格式
	private SimpleDateFormat format;
	
	/**
	 * 构造函数
	 * @param properties
	 */
	public POIExcelUtil(Map<String,String> properties){
		this.properties = properties;
		format = new SimpleDateFormat(date_format);
	}
	
	//自定义日期格式
	public POIExcelUtil(Map<String,String> properties,String date_format){
		this.properties = properties;
		this.date_format = date_format;
		format = new SimpleDateFormat(date_format);
	}
	
	/**
	 * 初始化工作薄
	 * @param filename
	 * @throws IOException
	 */
	public void init(String filename) throws IOException{
		if(filename.endsWith(EXTENSION_XLS)){
			workbook = new HSSFWorkbook(new FileInputStream(filename));//Excel2003
		}else if(filename.endsWith(EXTENSION_XLSX)){
			workbook = new XSSFWorkbook(new FileInputStream(filename));//Excel2007及以上
		}
	}
	/**
	 * 生成对象
	 * @param clazz
	 * @param required
	 * @return
	 */
	public <T> List<T> bindToModels(Class clazz,boolean required){
		Sheet sheet = workbook.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		if(rowNum < 1){
			return null;
		}
		
		this.loadHeader(sheet);//标题栏
		List<T> beanList = new ArrayList<T>();
		for(int i=1; i<=rowNum; i++){
			Row row = sheet.getRow(i);
			int cellNum = row.getLastCellNum();
			try {
				T instance = (T) clazz.newInstance();
				for(int columns=0; columns<cellNum; columns++){
					Cell cell = row.getCell(columns);
					//判断单元格是数据类型
					String value = localCellType(cell);
					//判断单元格的值是否为空
					if(null == value){
						
					}else{
						String key = header.get(columns);
						//加载实际的值
						this.loadValue(clazz,instance,this.properties.get(key),value);
					}
					
				}
				beanList.add(instance);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return beanList;
		
	}
	
	/**
	 * 注入属性值
	 * @param clazz
	 * @param instance
	 * @param pro
	 * @param value
	 * @throws Exception
	 */
	private <T> void loadValue(Class clazz, T instance, String pro, String value) throws Exception{
		String getMethod = this.initGetMethod(pro);
		Class type = clazz.getDeclaredMethod(getMethod, null).getReturnType();
		Method method = clazz.getMethod(this.initSetMethod(pro), type);
		if (type == String.class)
		{
		method.invoke(instance, value);
		} else if (type == int.class || type == Integer.class)
		{
		method.invoke(instance, Integer.parseInt(value));
		} else if (type == long.class || type == Long.class)
		{
		method.invoke(instance, Long.parseLong(value));
		} else if (type == float.class || type == Float.class)
		{
		method.invoke(instance, Float.parseFloat(value));
		} else if (type == double.class || type == Double.class)
		{
		method.invoke(instance, Double.parseDouble(value));
		} else if (type == Date.class)
		{
		method.invoke(instance, this.parseDate(value));
		}
		
	}
	
	private String initSetMethod(String field) {
		return "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}
	private String initGetMethod(String field) {
		return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}
	private Date parseDate(String value) throws ParseException {
		
		return format.parse(value);
	}
	/**
	 * 将单元格数据转换成string类型
	 * @param cell
	 * @return
	 */
	private String localCellType(Cell cell) {
		String value = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if(DateUtil.isCellDateFormatted(cell)){
				value = this.formateDate(cell.getDateCellValue());
			}else{
			value = String.valueOf((long)cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			System.out.println("不支持函数！");
			break;
		default:
			break;
		}
		return value;
	}
	
	private String formateDate(Date date) {
		return formateDate(date);
	}
	/**
	 * 获取第一行标题栏数据
	 * @param sheet
	 */
	private void loadHeader(Sheet sheet) {
		this.header = new HashMap<Integer,String>();
		Row row = sheet.getRow(0);
		int columns = row.getLastCellNum();
		for(int i=0; i<columns; i++){
			String value = row.getCell(i).getStringCellValue();
			if(null == value){
				throw new RuntimeException("标题栏不能为空！");
			}
			header.put(i, value);
		}
	}
	
	
}
