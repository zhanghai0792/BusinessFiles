package com.hy.model;

import java.io.File;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class BaseModel {
	
	private boolean resultFlag = false ;//正确为1
	private String message;
	private int rows;//总记录数
	private int page;//
	private Object data;// 存放查询结果集,字段非常重要，是前后台传递数据的重要桥梁
	
	private List<File> excel;// 用于文件导入时存放文件
	private List<String> excelFileName;// 用于文件导入时存放文件名
	private List<String>  newexcelFileName;
//	private String fileNameList;// 用于存放文件上传的路径
	private String condition = "";// 用于存放查询条件,文件存放时的上级根目录
	
	
	
	/*private int cpage = 1;// 当前页码
	private int pageSize = 10;// 每页显示的行数
	private int totalPages = 1;// 总共多少页
	private int total = 0;// 总共多少条
	private int indexCount;// 起始记录*/
	
	@JSON(serialize=false)//该属性不被序列化到json数据中
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	/*@JSON(serialize=false)
	public int getCpage() {
		return cpage;
	}
	public void setCpage(int cpage) {
		this.cpage = cpage;
	}
	@JSON(serialize=false)
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@JSON(serialize=false)
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	@JSON(serialize=false)
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	@JSON(serialize=false)
	public int getIndexCount() {
		return indexCount;
	}
	public void setIndexCount(int indexCount) {
		this.indexCount = indexCount;
	}
	
	*//**
	 * 
	 * @Description 设置分页信息
	 * @param baseModel
	 * @return
	 *//*
	public BaseModel setPageInfo(BaseModel baseModel) {
		// 设置分页信息
		int cpage = baseModel.getCpage();// 当前页面
		int pageSize = baseModel.getPageSize();// 每页显示的记录数
		int count = baseModel.getTotal();// 记录总数
		int totalPages = count / pageSize;
		if (count > 0 && count % pageSize > 0) {// 如果尾页存在记录，则页面加1
			totalPages += 1;
		}
		baseModel.setTotalPages(totalPages);// 设置总页面数
		int indexCount = (cpage - 1) * pageSize;// 起始记录
		baseModel.setIndexCount(indexCount);// 设置起始记录
		return baseModel;
	}*/
	@JSON(serialize=false)
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	@JSON(serialize=false)
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public List<File> getExcel() {
		return excel;
	}
	public void setExcel(List<File> excel) {
		this.excel = excel;
	}
	public List<String> getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(List<String> excelFileName) {
		this.excelFileName = excelFileName;
	}
	public List<String> getNewexcelFileName() {
		return newexcelFileName;
	}
	public void setNewexcelFileName(List<String> newexcelFileName) {
		this.newexcelFileName = newexcelFileName;
	}

   
	
	
	

}
