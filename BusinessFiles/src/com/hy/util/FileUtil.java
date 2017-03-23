package com.hy.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

public class FileUtil {
	public static void deleteDiskFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			if (!file.isDirectory()) {
				try {
					System.out.println("删除文件 ["+file.getCanonicalPath()+"]");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				file.delete();
				
			} else {
				File[] files = file.listFiles();
				for (File f : files)
					f.delete();
				try {
					System.out.println("删除目录 ["+file.getCanonicalPath()+"]");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				file.delete();
				
			}
		}
	}

	public static String getNewFileName(String params, String fileFileName) {

		String fileName = fileFileName.substring(0, fileFileName.lastIndexOf("."));// 获得文件名
		String type = fileFileName.substring(fileFileName.lastIndexOf("."), fileFileName.length()).toLowerCase();// 获得文件类型
		// 如果文件名带空格则删除空格，将英文符号的,替换成中文符号的，将英文符号的:替换为中文符号
		fileName = fileName.replace(" ", "").replace(",", "，").replace(":", "：").replace("/", "").replace("\\", "");
		fileFileName = fileName + type;
		if (".jpg".equals(type) || ".jpeg".equals(type) || ".gif".equals(type) || ".png".equals(type)
				|| ".bmp".equals(type) || ".pdf".equals(type) || ".xls".equals(type) || ".xlsx".equals(type)
				|| ".zip".equals(type) || ".rar".equals(type)||".kdh".equals(type)) {
			// fileFileName = time + type;
			fileFileName = params + type;// 不作解析的文件类型

		} else {
			// fileFileName = time + "_" + fileName + type;
			// fileFileName = params + type;

			return null;
		}

		return fileFileName;
	}
	/*
	 * public void inputstreamtofile(InputStream ins,File file){ OutputStream os
	 * = new FileOutputStream(file); int bytesRead = 0; byte[] buffer = new
	 * byte[8192]; while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
	 * os.write(buffer, 0, bytesRead); } os.close(); ins.close(); }
	 */
	// 判断文件类型
	/*
	 * public static int getFileContentType(String fileFileName){
	 * if(!"".equals(fileFileName.trim())){ String type =
	 * fileFileName.substring(fileFileName.lastIndexOf("."),
	 * fileFileName.length()).toLowerCase();// 获得文件类型 if (".jpg".equals(type) ||
	 * ".jpeg".equals(type) || ".png".equals(type) || ".bmp".equals(type)) {//
	 * 如果是图片 return 1;//图片类型 } else { return 2; } } return -1; }
	 */

	public static List<Map<String, String>> analysisExcel(File file) throws Exception {
		Workbook workbook = WorkbookFactory.create(file);// ---统一实现
		Sheet sheet = workbook.getSheetAt(0);// 获得第一个工作簿对象
		Row row = null;
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();// 定义map对象用于保存读取到的excel文件
		Row rowField = sheet.getRow(2);// 获得表格字段
		for (int i = 3; i <= sheet.getLastRowNum(); i++) {// 循环单元格
			HashMap<String, String> map = new HashMap<String, String>();
			row = sheet.getRow(i);
			int k = 0;
			if (row != null) {
				for (int j = 0; j < rowField.getLastCellNum(); j++) {// 循环列
					if (rowField.getCell(j) != null) {// 此列的字段值不为空
						String val = getValue(row.getCell(j));
						if (val == null || "".equals(val))
							k++;
						map.put(rowField.getCell(j).getStringCellValue(), val);
					}

				}
			}
			if (k != rowField.getLastCellNum())
				list.add(map);
		}
		workbook.close();
		return list;
	}

	
	public static List<Map<String, String>> analysisExcel(File file,String[] excelTemp) throws Exception {
		Workbook workbook = WorkbookFactory.create(file);// ---统一实现
		Sheet sheet = workbook.getSheetAt(0);// 获得第一个工作簿对象
		Row row = null;
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>(0);// 定义map对象用于保存读取到的excel文件
		Row rowField1 = sheet.getRow(0);
               if(!excelTemp[excelTemp.length-1].equals(getValue(rowField1.getCell(0)))){
            		System.out.println("导入excel的字段长度不匹配");   
            	   return null;
            		   }
               System.out.println("开始读取excel的数据");  
		Row rowField = sheet.getRow(2);// 获得表格字段
		for (int i = 2; i <= sheet.getLastRowNum(); i++) {// 循环单元格
			HashMap<String, String> map = new HashMap<String, String>();
			row = sheet.getRow(i);
			int k = 0;
			if (row != null) {
				for (int j = 0; j < rowField.getLastCellNum(); j++) {// 循环列
					if (rowField.getCell(j) != null) {// 此列的字段值不为空
						String val = getValue(row.getCell(j));
						if (val == null || "".equals(val))
							k++;
						map.put(excelTemp[j], val);
					}

				}
			}
			if (k != rowField.getLastCellNum())
				list.add(map);
		}
		workbook.close();
		return list;
	}
	
	
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// FileInputStream fis=new FileInputStream(file);
	// POIFSFileSystem fs = new POIFSFileSystem(fis);
	// http://www.oschina.net/question/166633_82410
	// Workbook workbook = new HSSFWorkbook(fs);
	/**
	 * 
	 * @Description 获取excel单元格内容,将其转换为String类型 wwy
	 */
	@SuppressWarnings("deprecation")
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
				// e.printStackTrace();
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

	public static File inputstreamtofile(InputStream ins, File file) {
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		try {
			OutputStream os = new FileOutputStream(file);
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*** 将workbook转换为InputStream */
	public static InputStream workbookInputStream(Workbook workbook) {
		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// this.setFileName(URLEncoder.encode(fileName, "UTF-8"));
			workbook.write(baos);
			/*
			 * baos.flush(); 第二种方法 byte[] aa = baos.toByteArray(); is = new
			 * ByteArrayInputStream(aa, 0, aa.length); baos.close();
			 */
			is = new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return is;
	}

	// 复制文件
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	@SuppressWarnings("unused")
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					// System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}

	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	// 遍历搜索文件夹
	public static void fun(File file, String ext) {
		int n = 0;
		File f[] = file.listFiles();
		if (f != null) {
			for (int i = 0; i < f.length; i++) {
				fun(f[i], ext);
			}
		} else {
			String filename = file.getName();
			if (filename.length() > ext.length()) {
				filename = filename.substring(filename.length() - ext.length());
				if (filename.equals(ext)) {
					n++;
					System.out.println(file.getName());
				}
			}
		}
	}

	public void traverseFolder1(String path) {
		int fileNum = 0, folderNum = 0;
		File file = new File(path);
		if (file.exists()) {
			LinkedList<File> list = new LinkedList<File>();
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					System.out.println("文件夹:" + file2.getAbsolutePath());
					list.add(file2);
					fileNum++;
				} else {
					System.out.println("文件:" + file2.getAbsolutePath());
					folderNum++;
				}
			}
			File temp_file;
			while (!list.isEmpty()) {
				temp_file = list.removeFirst();
				files = temp_file.listFiles();
				for (File file2 : files) {
					if (file2.isDirectory()) {
						System.out.println("文件夹:" + file2.getAbsolutePath());
						list.add(file2);
						fileNum++;
					} else {
						System.out.println("文件:" + file2.getAbsolutePath());
						folderNum++;
					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
		System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);

	}

	public static void traverseFolder2(String path) {

		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						System.out.println("文件夹:" + file2.getAbsolutePath());
						traverseFolder2(file2.getAbsolutePath());
					} else {
						System.out.println("文件:" + file2.getAbsolutePath());
					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
	}

	public static List<File> getFileList(String strPath, String ext) {
		List<File> filelist = new ArrayList<File>();
		File dir = new File(strPath);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				if (files[i].isDirectory()) { // 判断是文件还是文件夹
					getFileList(files[i].getAbsolutePath(), ext); // 获取文件绝对路径
				} else if (fileName.endsWith(ext)) { // 判断文件名是否以.avi结尾
					String strFileName = files[i].getAbsolutePath();
					System.out.println("---" + strFileName);
					filelist.add(files[i]);
				} else {
					continue;
				}
			}

		}
		return filelist;
	}

	public static List<File> getFileListALL(String strPath) {
		List<File> fileNamelist = new ArrayList<File>();
		File dir = new File(strPath);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) { // 是文件夹
					getFileListALL(files[i].getAbsolutePath());// 获取文件绝对路径
				} else {// 是文件
					// String fileName = files[i].getName();
					// String strFileName = files[i].getAbsolutePath();//
					// 获取文件绝对路径
					/*
					 * if(strFileName.contains("Fm")){ System.out.println("---"
					 * + strFileName); }
					 */
					fileNamelist.add(files[i]);
				}
			}
		}
		return fileNamelist;

	}

	@Test
	public void test2() {
		FileUtil.copyFile(
				"G:\\JavaWeb\\apache-tomcat-7.0.42\\webapps\\BusinessFiles\\attachments_Img\\Certificate\\14181聘书11.jpg",
				"G:\\JavaWeb\\apache-tomcat-7.0.42\\webapps\\BusinessFiles\\temp\\14181聘书11.jpg");
		// getFileListALL("G:\\JavaWeb\\apache-tomcat-7.0.42\\webapps\\BusinessFiles\\ziptemp\\temp\\mj采用视点校正的视点可分级编码");
	}
}
