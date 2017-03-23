package com.hy.service.impl;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.hy.model.BaseModel;
import com.hy.service.UploadFilesService;
import com.hy.util.FileUtil;


public class UploadFilesServiceImpl implements UploadFilesService {

	@Override
	public BaseModel uploadFile(BaseModel baseModel) {
		
		List<File> filelist = baseModel.getExcel();// 获得文件
		String message="";
		/*System.out.println("+++++++UploadFileServiceImpl++++++++");
		System.out.println(filelist.size());
		System.out.println(baseModel.getNewexcelFileName().size());
*/		
		String FILEPATH = ServletActionContext.getServletContext().getRealPath(
				baseModel.getCondition());//附件目录
		String realPath = FILEPATH + "\\";// 设置文件保存目录
		//System.out.println(realPath);
		for(int i=0;i<filelist.size();i++){
			
			if (filelist.get(i) != null) {// 如果有文件在上传
				File saveDir = new File(realPath);// 创建目录对象
				if (!saveDir.exists()) {// 判断是否是目录
					saveDir.mkdirs();// 创建目录
				}
				File saveFile = new File(realPath, baseModel.getNewexcelFileName().get(i));// 设置目标文件
				try {
					FileUtils.copyFile(filelist.get(i), saveFile);
					//message+="上传成功";
					System.out.println("保存文件成功"+saveFile.getCanonicalPath());
					baseModel.setResultFlag(true);
				} catch (IOException e) {
					System.out.println(e.getMessage());
					baseModel.setResultFlag(false);
					//message+="上传失败";
					e.printStackTrace();
				}
			}
		}
		baseModel.setMessage(message);
		return baseModel;
	}
	
	//上传一组文件
	public String uploadFile(List<File> file,List<String> newFileName,String Condition) {
		BaseModel baseModel = new BaseModel();
		baseModel.setExcel(file);
		baseModel.setNewexcelFileName(newFileName);
		baseModel.setCondition(Condition);
		baseModel=uploadFile(baseModel);
		return baseModel.getMessage();
	}
	public String importexcelFile(File file) throws IOException {
		//InputStream inputStream = file.getInputStream();
		//POIFSFileSystem fs = new POIFSFileSystem(inputStream);
		
		
		 FileInputStream fis=new FileInputStream(file);
		 // 相对路径创建文件流  
        //FileInputStream fis = new FileInputStream(fileName);  
        //POI解析该流  
        XSSFWorkbook workbook = new XSSFWorkbook(fis);  
        // 只解析第一页表格的数据  
        XSSFSheet sheet = workbook.getSheetAt(0);  
        // 获取表格行数  
        int rows = sheet.getLastRowNum();  
        // 逐行解析（具体文件从第三行才是所需数据）  
        for (int rowIndex = 2; rowIndex < rows; rowIndex++) {  
            XSSFRow row = sheet.getRow(rowIndex);  
            // 获取该行的列数  
            int columns = row.getLastCellNum();  
            // 对该行逐列解析  
            for (int columnIndex = 0; columnIndex < columns; columnIndex++) {  
                // 获取该行列的单元格对象  
                XSSFCell cell = row.getCell(columnIndex);  
                // 字符串形式解析单元格数据（具体类型具体分析）  
                System.out.println(cell.getRichStringCellValue().toString());
                System.out.println(cell.getDateCellValue().toString());
                
            }  
        }  
		return null;
		
	}
	
	public String importExcelFile(File file) throws Exception {
		List<Map<String, String>> listmap=FileUtil.analysisExcel(file);
		System.out.println(listmap.size());
		if (listmap.size() > 0) {
			for (int i = 0; i < listmap.size(); i++) {
				
			}
		}	
		return null;
		
	}


	


}
