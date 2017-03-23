package com.hy.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.hy.model.BaseModel;


/**
 * ClassName: UploadFilesService 
 * @Description: 用于文件上传,服务层
 */
public interface UploadFilesService {
	public BaseModel uploadFile(BaseModel baseModel);
	public String importexcelFile(File file) throws IOException;
	public String importExcelFile(File file) throws Exception;
	public String uploadFile(List<File> fm, List<String> fmNewName, String string);
}
