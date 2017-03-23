package com.hy.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.hy.model.exportModel;
import com.hy.util.DateUtil;

@Namespace("/uploadspace")
public class UploadFilesAction  extends BaseAction{

	private static final long serialVersionUID = 1L;
	private File file;// 文件
	private String fileFileName;// 文件名称
    private String fileContentType;//提交过来的file的MIME类型
    
    @Action(value = "uploadFiles", results = { @Result(name = "success", type = "json") })
	public String uploadFilesAction() throws Exception {
    	
    	String path = ServletActionContext.getServletContext().getRealPath(
				"attachments_Img");
    	
    	System.out.println(path);
		String realPath = path + "\\";// 设置文件保存目录
		if (file != null) {// 如果有文件在上传
			File saveDir = new File(realPath);// 创建目录对象
			if (!saveDir.exists()) {// 判断是否是目录
				saveDir.mkdirs();// 创建目录
			}
			String fileName = fileFileName.substring(0,
					fileFileName.lastIndexOf("."));// 获得文件名
			String type = fileFileName.substring(fileFileName.lastIndexOf("."),
					fileFileName.length()).toLowerCase();// 获得文件类型
			// 如果文件名带空格则删除空格，将英文符号的,替换成中文符号的，将英文符号的:替换为中文符号
			fileName = fileName.replace(" ", "").replace(",", "，")
					.replace(":", "：").replace("/", "").replace("\\", "");
			fileFileName = fileName + type;
			if (".jpg".equals(type) || ".jpeg".equals(type)
					|| ".png".equals(type) || ".bmp".equals(type)) {// 如果是图片
				
			} else {
				
			}
			File saveFile = new File(realPath, fileFileName);// 设置目标文件
			FileUtils.copyFile(file, saveFile);
			
		}
		return "success";
    	
    }
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

}
