package com.hy.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 
 * @ClassName AnalysisFile
 * @Description 解析文件工具类,兼容excel2003,2007.和xls格式
 * @author 王文华
 * @date 2016-7-2
 */
public class AnalysisFile {
	/**
	 * 
	 * @Description 解析excel的内容放入list
	 * @param file
	 * @return
	 * @throws Exception
	 * @author 王文华
	 * @date 2016-7-2
	 */
	/*@SuppressWarnings("rawtypes")
	public static List analysisExcel(MultipartFile file)throws Exception{
		InputStream inputStream = file.getInputStream();
		POIFSFileSystem fs = new POIFSFileSystem(inputStream);
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		Sheet sheet = workbook.getSheetAt(0);// 获得工作簿对象
		Row row = null;
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();// 定义map对象用于保存读取到的excel文件
		Row rowField = sheet.getRow(2);// 获得表字段
		for (int i=4 ; i <=sheet.getLastRowNum(); i++) {// 循环单元格
			HashMap<String, String> map = new HashMap<String, String>();
			row = sheet.getRow(i);
			int k = 0;
			if(row!=null){
				for (int j = 0; j < rowField.getLastCellNum(); j++) {// 循环列
					String val = getValue(row.getCell(j));
					if(val == null || "".equals(val))
						k++;
					map.put(rowField.getCell(j).getStringCellValue(),val);
				}
			}
			if(k != rowField.getLastCellNum())
				list.add(map);
		}
		return list;
	}*/
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 
	 * @Description 获取excel单元格内容,将其转换为String类型
	 * @param cell
	 * @return
	 * @author 王文华
	 * @date 2016-7-2
	 */
	public static String getValue(Cell cell) {
		String value = "";
		if (null == cell) {
			return value;
		}
		switch (cell.getCellType()) {
		// 数值型
		case Cell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
				value = format.format(date);
			} else {// 纯数字
				BigDecimal big = new BigDecimal(cell.getNumericCellValue());
				value = big.toString();
				if (null != value && !"".equals(value.trim())) {
					String[] item = value.split("[.]");
					if (1 < item.length && item[1].trim().length() > 3) {// 如果小数点后面大于三位，则保留三位小数
						value = item[0] + "." + item[1].trim().substring(0, 3);
					}
				}
			}
			break;
		// 字符串类型
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue().toString();
			break;
		// 公式类型
		case Cell.CELL_TYPE_FORMULA:
			// 读公式计算值
			try {
				value = String.valueOf(cell.getNumericCellValue());
			} catch (Exception e) {
				//e.printStackTrace();
				value = cell.getStringCellValue().toString();
			}
			
			if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
				value = cell.getStringCellValue().toString();
			}
			break;
		// 布尔类型
		case Cell.CELL_TYPE_BOOLEAN:
			value = " " + cell.getBooleanCellValue();
			break;
		// 空值
		case Cell.CELL_TYPE_BLANK:
			value = null;
			// LogUtil.getLogger().error("excel出现空值");
			break;
		// 故障
		case Cell.CELL_TYPE_ERROR:
			value = null;
			// LogUtil.getLogger().error("excel出现故障");
			break;
		default:
			value = cell.getStringCellValue().toString();
		}
		if (value != null && "null".endsWith(value.trim())) {
			value = null;
		}
		return value;
	}
}
