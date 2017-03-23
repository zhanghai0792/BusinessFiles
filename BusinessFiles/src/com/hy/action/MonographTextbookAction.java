package com.hy.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.tools.zip.ZipOutputStream;

import com.hy.dao.MonographTextbookDao;
import com.hy.dao.impl.MonographTextbookImpl;
import com.hy.model.MonographTextbook;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.hy.util.FileUtil;
import com.opensymphony.xwork2.ModelDriven;

public class MonographTextbookAction extends BaseAction implements  ModelDriven<MonographTextbook>{
	private static String rootPath="/attachments_Img/MonographTextbook/";
	private static String zipPath="/ziptemp/MonographTextbook/";
	
	private static final long serialVersionUID = 1L;
	private MonographTextbook monographtextbook  = new MonographTextbook();
	private List<MonographTextbook> monographtextbooks=new ArrayList<MonographTextbook>();;
	private MonographTextbookDao dao = new MonographTextbookImpl();
	private List<Integer> ids=new ArrayList<Integer>();
	private String publishDateStart="";
	private String publishDateEnd="";
	private String cond="",tid="";
	private InputStream inputStream;
	private String fileDownloadName;
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getFileDownloadName() {
		return fileDownloadName;
	}
	public void setFileDownloadName(String fileDownloadName) {
		this.fileDownloadName = fileDownloadName;
	}
	@Action(value = "getMonographTextbooklist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		StringBuilder hql=new StringBuilder("select p  FROM MonographTextbook as p  where 1=1");
		if(!"3".equals(t.getType())){//科教秘书=3
			hql.append(" and  p.author like '%"+t.getId()+"%'");
		}
		if ((!"".equals(tid.trim()))&&(!"undefined".equals(tid.trim()))) {// 同一用户,不同界面
			hql.append(" and p.author like '%" + tid + "%'");
		}
		if(!"".equals(cond.trim())){
			hql.append(" and (  p.author like '%"+cond+"%' or p.bookName like '%"+cond+"%' or p.publication like '%"+cond+"%')");
		}
		if(!"".equals(publishDateStart.trim())){
			hql.append(" and p.publishDate >= '"+publishDateStart+"'");
		}
		if(!"".equals(publishDateEnd.trim())){
			hql.append(" and p.publishDate <= '"+publishDateEnd+"'");
		}
		if (monographtextbook.getStatus() != null && !"".equals(monographtextbook.getStatus().trim())) {
			if(("0".equals(monographtextbook.getStatus()) || ("1".equals(monographtextbook.getStatus())))){
			hql.append(" and ( p.status ='"+monographtextbook.getStatus()+"')");}
		}
		
	/*	listobj=dao.getList(monographtextbook,hql.toString());
		jsoMap.put("total", 10);
		jsoMap.put("rows", listobj);*/
		if(page==0||rows==0){
			listobj=dao.getList(monographtextbook,hql.toString());
			jsoMap.put("total", 10);
			jsoMap.put("rows", listobj);
			}
			else{
				jsoMap.put("total",dao.getPages(hql));
				jsoMap.put("rows", dao.getPageObject(hql, page,rows));
			}	
		
		return "success";
	}
	@Action(value = "addMonographTextbook", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String add(){
		if(monographtextbook.getStatus()==null||"".equals(monographtextbook.getStatus()))
			monographtextbook.setStatus("0");
		jsoMap.put("msg",dao.add(monographtextbook));
		return "success";
		
	}
	
	/*private void deleteOldUploadFile(Integer id){
		String FILEPATH = ServletActionContext.getServletContext().getRealPath("/attachments_Img/MonographTextbook");
		MonographTextbook mt=dao.getById(id);
		if(mt!=null){
			String fileNames=mt.getFileName();
			dao.evict(mt);
			if(fileNames!=null&&!"".equals(fileNames)){
				String[] fileTemps=fileNames.split(",");
				if(fileTemps!=null)
					for(String fn:fileTemps)
						FileUtil.deleteDiskFile(FILEPATH+"/"+fn);
			}
		}
	}
	*/
	@Action(value = "updateMonographTextbook", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String update(){
		MonographTextbook monTemp=(MonographTextbook) dao.getByIdAndEvict(monographtextbook.getId());
		monographtextbook.setFileName(monTemp.getFileName());
		List<String> deletTemp=null;
		
		
		/*if(monographtextbook.getFile()!=null && monographtextbook.getFile().size()>0 && monographtextbook.getFileName()!=null && !monographtextbook.getFileName().equals(monTemp.getFileName())){
			deletTemp=pathSplit(monTemp.getFileName(),monographtextbook.getId());
			
		}*/
		if(monographtextbook.getFile()!=null&&monographtextbook.getFile().size()>0){
			 deleteFiles(rootPath, monographtextbook.getFileName(), monographtextbook.getId());
		 }
		boolean result=dao.update(monographtextbook);
		//deleteOldUploadFile(monographtextbook.getId());
		//jsoMap.put("msg",dao.update(monographtextbook));
		jsoMap.put("msg",result);
		/*if(result==true&&deletTemp!=null&&deletTemp.size()>0){
			deleteFilePath(rootPath,deletTemp);
		}*/
		return "success";
	}
	@Action(value = "deleteMonographTextbook", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String delete(){
		
		int count=dao.delete(ids);
		deleteFoldByIds(rootPath, ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共删除"+count+"条数据!");
		return "success";
	}
	@Action(value = "reviewMonographTextbook", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String review() {
		System.out.println(ids.size());
		int count=dao.review(ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共审核通过"+count+"条数据!");
		return "success";
	}

	
	@Action(value = "exportMonographTextbook", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
	public String export(){
		String name = "专著及教材" + DateUtil.dateToYMDhms(new Date()) + ".zip";
		try {
			this.setFileDownloadName(new String(name.getBytes(), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		List<exportModel> models=dao.getByIds(ids);
		if(models!=null&&models.size()>0){
			inputStream=export(zipPath, rootPath, name,  models);
		}
		return "success";
	}
	
	
	public MonographTextbook getMonographtextbook() {
		return monographtextbook;
	}



	public void setMonographtextbook(MonographTextbook monographtextbook) {
		this.monographtextbook = monographtextbook;
	}



	public List<MonographTextbook> getMonographtextbooks() {
		return monographtextbooks;
	}



	public void setMonographtextbooks(List<MonographTextbook> monographtextbooks) {
		this.monographtextbooks = monographtextbooks;
	}



	@Override
	public MonographTextbook getModel() {
		return monographtextbook;
	}



	public String getPublishDateStart() {
		return publishDateStart;
	}



	public void setPublishDateStart(String publishDateStart) {
		this.publishDateStart = publishDateStart;
	}



	public String getPublishDateEnd() {
		return publishDateEnd;
	}



	public void setPublishDateEnd(String publishDateEnd) {
		this.publishDateEnd = publishDateEnd;
	}



	public String getCond() {
		return cond;
	}



	public void setCond(String cond) {
		this.cond = cond;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	
}
