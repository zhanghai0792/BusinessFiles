package com.hy.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.tools.zip.ZipOutputStream;
import org.hibernate.service.jdbc.connections.internal.DatasourceConnectionProviderImpl;

import com.hy.dao.EducationalResearchDao;
import com.hy.dao.impl.BaseDaoImpl;
import com.hy.dao.impl.EducationalResearchImpl;
import com.hy.model.EducationalResearch;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.hy.util.FileUtil;
import com.opensymphony.xwork2.ModelDriven;

public class EducationalResearchAction extends BaseAction implements  ModelDriven<EducationalResearch>{
	private static String rootPath="/attachments_Img/EducationalResearch/";
	private static String zipPath="/ziptemp/EducationalResearch/";
	
	private static final long serialVersionUID = 1L;
	private EducationalResearch educationalresearch  = new EducationalResearch();
	private List<EducationalResearch> educationalresearchs=new ArrayList<EducationalResearch>();
	private EducationalResearchDao dao = new EducationalResearchImpl();
	private List<Integer> ids=new ArrayList<Integer>();
	private String cond="",tid="";
	private String condDate="";
	private String DateStart="";
	private String DateEnd="";
    private String condition;
	private InputStream inputStream;
	private String fileDownloadName;
	@Action(value = "getEducationalResearchlist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		StringBuilder hql=null;
		if(condition!=null&&!"".equals(condition)){
			//hql=new StringBuilder("select distinct p FROM EducationalResearch as p,Teacher as t where p.author like CONCAT('%[',t.id,'%]') and ");//
			hql=new StringBuilder("select distinct p FROM EducationalResearch as p  where ");//
			
			condition=condition.replaceAll("@", "'");
			condition=condition.replaceAll("status = '-1'","status='0' or status='1'");
			hql.append(condition);
		}else{
		
		Teacher t= (Teacher) session.get("loginteacher");
		/*StringBuilder hql=new StringBuilder("select p,t FROM EducationalResearch as p,Teacher as t where p.teacherId=t.id");
		if(!"3".equals(t.getType())){//科教秘书=3
			hql.append(" and  p.teacherId='"+t.getId()+"'");
		}
		if ((!"".equals(tid.trim()))&&(!"undefined".equals(tid.trim()))) {// 同一用户,不同界面
			hql.append(" and p.teacherId='" + tid + "'");
		}*/
		hql=new StringBuilder("select distinct p FROM EducationalResearch as p,Teacher as t where p.author like CONCAT('%[',t.id,'%]')");
		if(!"3".equals(t.getType())){//科教秘书=3
			hql.append(" and  t.id='"+t.getId()+"'");
		}
		if ((!"".equals(tid.trim()))&&(!"undefined".equals(tid.trim()))) {// 同一用户,不同界面
			hql.append(" and t.id='" + tid + "'");
		}
		
		if(!"".equals(cond.trim())){
			hql.append(" and ( t.name like'%"+cond+"%' or t.id like '%"+cond+"%' or p.topic like '%"+cond+"%' or p.topicNum like '%"+cond+"%')");
		}
		if(!"".equals(condDate.trim())){
			if(!"".equals(DateStart.trim())){
				hql.append(" and p."+condDate+" >= '"+DateStart+"'");
			}
			if(!"".equals(DateEnd.trim())){
				hql.append(" and p."+condDate+" <= '"+DateEnd+"'");
			}
		}
		if(educationalresearch.getApprover()!=null&&!"".equals(educationalresearch.getApprover().trim())){
			hql.append(" and ( p.approver like'"+educationalresearch.getApprover()+"')");
		}
		if (educationalresearch.getStatus() != null && !"".equals(educationalresearch.getStatus().trim())) {
			if(("0".equals(educationalresearch.getStatus()) || ("1".equals(educationalresearch.getStatus())))){
			hql.append(" and ( p.status ='"+educationalresearch.getStatus()+"')");}
		}
		//System.out.println("page="+page+";rows="+rows);
		}
		if(page==0||rows==0){
		listobj=dao.getList(educationalresearch,hql.toString());
		jsoMap.put("total", 10);
		jsoMap.put("rows", listobj);
		}
		else{
			jsoMap.put("total",dao.getPages(hql));
			jsoMap.put("rows", dao.getPageObject(hql, page,rows));
		}
		return "success";
	}

	@Action(value = "addEducationalResearch", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String add(){
		if(educationalresearch.getStatus()==null||"".equals(educationalresearch.getStatus()))
			educationalresearch.setStatus("0");
		jsoMap.put("msg",dao.add(educationalresearch));
		return "success";
		
	}
	@Action(value = "updateEducationalResearch", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String update(){
		Integer idPath=educationalresearch.getId();
		boolean flag=false;
		/*String FILEPATH = ServletActionContext.getServletContext().getRealPath("/attachments_Img/EducationalResearch/"+idPath);
		FileUtil.deleteDiskFile(FILEPATH);*/
		EducationalResearch eduTemp=dao.getById(educationalresearch.getId());
		
		  educationalresearch.setFileName(eduTemp.getFileName());
		  educationalresearch.setProjectCertificat(eduTemp.getProjectCertificat());
		  educationalresearch.setKnotCertificat(eduTemp.getKnotCertificat());
		  educationalresearch.setSfbook(eduTemp.getSfbook());
			
		  if( educationalresearch.getFile()!=null&& educationalresearch.getFile().size()>0){
				 deleteFiles(rootPath,  educationalresearch.getFileName(),educationalresearch.getId());
			 }  
		  if( educationalresearch.getPc()!=null&& educationalresearch.getPc().size()>0){
				 deleteFiles(rootPath,  educationalresearch.getProjectCertificat(),educationalresearch.getId());
			 } 
		  if( educationalresearch.getKc()!=null&& educationalresearch.getKc().size()>0){
				 deleteFiles(rootPath,  educationalresearch.getKnotCertificat(),educationalresearch.getId());
			 } 
		  if( educationalresearch.getSf()!=null&& educationalresearch.getSf().size()>0){
				 deleteFiles(rootPath,  educationalresearch.getSfbook(),educationalresearch.getId());
			 } 
		  
		flag=dao.update(educationalresearch);
		
	  /*List<String> deletePath=new ArrayList<String>(0);
	   List<String> tempPaths=null;
	  
	   if(educationalresearch.getFileName()!=null && !educationalresearch.getFileName().equals(eduTemp.getFileName()))
	     tempPaths=super.copyNotNullUploadFiles(educationalresearch.getFileName(), eduTemp.getFileName(), eduTemp.getId());
	  if(tempPaths!=null&&educationalresearch.getFileName()!=null&&!educationalresearch.getFileName().equals(eduTemp.getFileName())){
		  deletePath.addAll(tempPaths);
	  }
	  tempPaths=super.copyNotNullUploadFiles(educationalresearch.getProjectCertificat(), eduTemp.getProjectCertificat(), eduTemp.getId());
	  if(tempPaths!=null&&educationalresearch.getProjectCertificat()!=null&&!educationalresearch.getProjectCertificat().equals(eduTemp.getProjectCertificat()))
		  deletePath.addAll(tempPaths);
	  
	  tempPaths=super.copyNotNullUploadFiles(educationalresearch.getKnotCertificat(), eduTemp.getKnotCertificat(), eduTemp.getId());
	  if(tempPaths!=null&&educationalresearch.getKnotCertificat()!=null&&!educationalresearch.getKnotCertificat().equals(eduTemp.getKnotCertificat()))
				  deletePath.addAll(tempPaths);
	  
	  tempPaths=super.copyNotNullUploadFiles(educationalresearch.getSfbook(), eduTemp.getSfbook(), eduTemp.getId());
	  if(tempPaths!=null&&educationalresearch.getSfbook()!=null&&!educationalresearch.getSfbook().equals(eduTemp.getSfbook()))
				  deletePath.addAll(tempPaths);*/
	 
	  jsoMap.put("msg",flag);
		 /*if(flag==true){
			 deleteFilePath(rootPath,deletePath); 
		 }*/
		return "success";
	}
	@Action(value = "deleteEducationalResearch", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String delete(){
		/*jsoMap.put("msg",dao.delete(educationalresearch));
		return "success";*/
		int count=dao.delete(ids);
		
		
		if(count>0)
		deleteFoldByIds(rootPath, ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共删除"+count+"条数据!");
		return "success";
	}
	@Action(value = "reviewEducationalResearch", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String review() {
		System.out.println(ids.size());
		int count=dao.review(ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共审核通过"+count+"条数据!");
		return "success";
	}
	@Action(value = "exportEducationalResearch", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
	public String export(){
		String name = "教改课题" + DateUtil.dateToYMDhms(new Date()) + ".zip";
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
	public EducationalResearch getEducationalresearch() {
		return educationalresearch;
	}


	public void setEducationalresearch(EducationalResearch educationalresearch) {
		this.educationalresearch = educationalresearch;
	}


	public List<EducationalResearch> getEducationalresearchs() {
		return educationalresearchs;
	}


	public void setEducationalresearchs(
			List<EducationalResearch> educationalresearchs) {
		this.educationalresearchs = educationalresearchs;
	}


	@Override
	public EducationalResearch getModel() {
		return educationalresearch;
	}

	public String getCond() {
		return cond;
	}

	public void setCond(String cond) {
		this.cond = cond;
	}

	public String getCondDate() {
		return condDate;
	}

	public void setCondDate(String condDate) {
		this.condDate = condDate;
	}

	public String getDateStart() {
		return DateStart;
	}

	public void setDateStart(String dateStart) {
		DateStart = dateStart;
	}

	public String getDateEnd() {
		return DateEnd;
	}

	public void setDateEnd(String dateEnd) {
		DateEnd = dateEnd;
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

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}




	
}
