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

import com.hy.dao.ScientificResearchDao;
import com.hy.dao.impl.ScientificResearchImpl;
import com.hy.model.ScientificResearch;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.hy.util.FileUtil;
import com.opensymphony.xwork2.ModelDriven;

public class ScientificResearchAction extends BaseAction implements  ModelDriven<ScientificResearch>{
	
	private static final long serialVersionUID = 1L;
	private ScientificResearch scientificresearch  = new ScientificResearch();
	private List<ScientificResearch> scientificresearchs=new ArrayList<ScientificResearch>();;
	private List<Integer> ids = new ArrayList<Integer>();
	private ScientificResearchDao dao = new ScientificResearchImpl();
	private String cond="",tid="";
	private String condDate="";
	private String DateStart="";
	private String DateEnd="";
	private InputStream inputStream;
	private String fileDownloadName;
	private static String rootPath="/attachments_Img/ScientificResearch/";
	private static String zipPath="/ziptemp/ScientificResearch/";
    private String condition;
	
                    
	@Action(value = "getScientificResearchlist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		StringBuilder hql=null;
		if(condition!=null&&!"".equals(condition)){
			hql=new StringBuilder("FROM ScientificResearch as p where ");//
			condition=condition.replaceAll("@", "'");
			condition=condition.replaceAll("status = '-1'","status='0' or status='1'");
			hql.append(condition);
		}else{
		
		Teacher t= (Teacher) session.get("loginteacher");
		hql=new StringBuilder("select p  FROM ScientificResearch as p  where 1=1 ");
		if(!"3".equals(t.getType())){
			hql.append(" and p.author like '%"+t.getId()+"%'");
		}
		if ((!"".equals(tid.trim()))&&!"undefined".equals(tid.trim())) {// 同一用户,不同界面
			hql.append(" and p.author like '%" + tid + "%'");
		}
		if(!"".equals(cond.trim())){
			hql.append(" and ( p.author like '%"+cond+"%' or p.topic like '%"+cond+"%' or p.topicNum like '%"+cond+"%')");
		}
		if(!"".equals(condDate.trim())){
			if(!"".equals(DateStart.trim())){
				hql.append(" and p."+condDate+" >= '"+DateStart+"'");
			}
			if(!"".equals(DateEnd.trim())){
				hql.append(" and p."+condDate+" <= '"+DateEnd+"'");
			}
		}
		if(scientificresearch.getApprover()!=null&&!"".equals(scientificresearch.getApprover().trim())){
			hql.append(" and ( p.approver like'"+scientificresearch.getApprover()+"')");
		}

		if (scientificresearch.getStatus() != null && !"".equals(scientificresearch.getStatus().trim())) {
			if(("0".equals(scientificresearch.getStatus()) || ("1".equals(scientificresearch.getStatus())))){
			hql.append(" and ( p.status ='"+scientificresearch.getStatus()+"')");}
		}
		}
		
		if(page==0||rows==0){
			listobj=dao.getList(scientificresearch,hql.toString());
			jsoMap.put("total", 10);
			jsoMap.put("rows", listobj);
			}
			else{
				jsoMap.put("total",dao.getPages(hql));
				jsoMap.put("rows", dao.getPageObject(hql, page,rows));
			}
		
		return "success";
	}
	@Action(value = "addScientificResearch", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String add(){
		if(scientificresearch.getStatus()==null||"".equals(scientificresearch.getStatus()))
			scientificresearch.setStatus("0");
		jsoMap.put("msg",dao.add(scientificresearch));
		return "success";
		
	}
	
@Action(value = "deleteScientificResearch", results = {
					@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
			public String delete(){

		     int count=dao.delete(ids);
				if(count>0)
					deleteFoldByIds(rootPath, ids);
				
				if(count>0) jsoMap.put("success",true);
				else  jsoMap.put("success",false);
				jsoMap.put("msg", "共删除"+count+"条数据!");
				return "success";
			}
			
	
	@Action(value = "reviewScientificResearch", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String review() {
		System.out.println(ids.size());
		int count=dao.review(ids);
		if(count>0){
			jsoMap.put("success",true);
		}
		jsoMap.put("msg", "共审核通过"+count+"条数据!");
		return "success";
	}
	
	@Action(value = "exportScientificResearch", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
	public String export(){
		String name = "科研课题" + DateUtil.dateToYMDhms(new Date()) + ".zip";
		try {
			this.setFileDownloadName(new String(name.getBytes(), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/*String zip_temp =ServletActionContext.getServletContext().getRealPath(zipPath);
		File file=new File(zip_temp);
		if(!file.exists())
			file.mkdirs();
		File zip_tem=new File(zip_temp+"/"+name);
		try {
			ZipOutputStream zipOut=new ZipOutputStream(new FileOutputStream(zip_tem));
		   zip(zipOut, rootPath, ids);
		   inputStream=new FileInputStream(zip_tem);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		List<exportModel> models=dao.getByIds(ids);
		if(models!=null&&models.size()>0){
			inputStream=export(zipPath, rootPath, name,  models);
		}
		return "success";
	}
	

	@Action(value = "updateScientificResearch", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String update(){
		ScientificResearch temp=(ScientificResearch) dao.getByIdAndEvict(scientificresearch.getId());
		scientificresearch.setFileName(temp.getFileName());
		scientificresearch.setProjectCertificat(temp.getProjectCertificat());
		scientificresearch.setKnotCertificat(temp.getKnotCertificat());
		scientificresearch.setSfbook(temp.getSfbook());
		if(scientificresearch.getFile()!=null&&scientificresearch.getFile().size()>0){
			 deleteFiles(rootPath, scientificresearch.getFileName(), scientificresearch.getId());
		 }
		
		if(scientificresearch.getPc()!=null&&scientificresearch.getPc().size()>0){
			 deleteFiles(rootPath, scientificresearch.getProjectCertificat(), scientificresearch.getId());
		 }
		
		if(scientificresearch.getKc()!=null&&scientificresearch.getKc().size()>0){
			 deleteFiles(rootPath, scientificresearch.getKnotCertificat(), scientificresearch.getId());
		 }
		
		if(scientificresearch.getSf()!=null&&scientificresearch.getSf().size()>0){
			 deleteFiles(rootPath, scientificresearch.getSfbook(), scientificresearch.getId());
		 }
		
		boolean result=dao.update(scientificresearch);
		
		/*List<String> deletePath=new ArrayList<String>(0);
		   List<String> tempPaths=null;
		  tempPaths=super.copyNotNullUploadFiles(scientificresearch.getFileName(), temp.getFileName(), temp.getId());
		  if(tempPaths!=null&&scientificresearch.getFile()!=null&&scientificresearch.getFile().size()>0&&scientificresearch.getFileName()!=null&&!scientificresearch.getFileName().equals(temp.getFileName())){
					  deletePath.addAll(tempPaths);
		  }
		  
		  tempPaths=super.copyNotNullUploadFiles(scientificresearch.getProjectCertificat(), temp.getProjectCertificat(), temp.getId());
		  if(tempPaths!=null&&scientificresearch.getPc()!=null&&scientificresearch.getPc().size()>0&&scientificresearch.getProjectCertificat()!=null&&!scientificresearch.getProjectCertificat().equals(temp.getProjectCertificat())){
			  deletePath.addAll(tempPaths);
		  }
		  tempPaths=super.copyNotNullUploadFiles(scientificresearch.getKnotCertificat(), temp.getKnotCertificat(), temp.getId());
		  if(tempPaths!=null&&scientificresearch.getKc()!=null&&scientificresearch.getKc().size()>0&&scientificresearch.getKcFileName()!=null&&!scientificresearch.getKcFileName().equals(temp.getKcFileName())){
			  deletePath.addAll(tempPaths);
		  }
		  
		  tempPaths=super.copyNotNullUploadFiles(scientificresearch.getSfbook(), temp.getSfbook(), temp.getId());
		  if(tempPaths!=null&&scientificresearch.getSf()!=null&&scientificresearch.getSf().size()>0&&scientificresearch.getSfbook()!=null&&!scientificresearch.getSfbook().equals(temp.getSfbook())){
			  deletePath.addAll(tempPaths);
		  }*/
		  
		//jsoMap.put("msg",dao.update(scientificresearch));
		  
			jsoMap.put("msg", result);
			/*if(result==true&&deletePath!=null&&deletePath.size()>0){
				deleteFilePath(rootPath,deletePath);
			}*/
		return "success";
	}
	
	
	public ScientificResearch getScientificresearch() {
		return scientificresearch;
	}


	public void setScientificresearch(ScientificResearch scientificresearch) {
		this.scientificresearch = scientificresearch;
	}


	public List<ScientificResearch> getScientificresearchs() {
		return scientificresearchs;
	}


	public void setScientificresearchs(List<ScientificResearch> scientificresearchs) {
		this.scientificresearchs = scientificresearchs;
	}


	@Override
	public ScientificResearch getModel() {
		return scientificresearch;
	}


	public String getCond() {
		return cond;
	}


	public void setCond(String cond) {
		this.cond = cond;
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


	public String getCondDate() {
		return condDate;
	}


	public void setCondDate(String condDate) {
		this.condDate = condDate;
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
