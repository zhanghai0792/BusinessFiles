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

import javax.naming.ldap.PagedResultsResponseControl;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.tools.zip.ZipOutputStream;

import com.hy.dao.PaperResearchDao;
import com.hy.dao.impl.PaperResearchImpl;
import com.hy.model.BaseModel;
import com.hy.model.PaperResearch;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.hy.util.FileUtil;
import com.opensymphony.xwork2.ModelDriven;

public class PaperResearchAction extends BaseAction implements ModelDriven<PaperResearch> {
	private static String rootPath="/attachments_Img/PaperResearch/";
	private static String zipPath="/ziptemp/PaperResearch/";
	
	private static final long serialVersionUID = 1L;
	private PaperResearch paperresearch = new PaperResearch();
	private List<Integer> ids = new ArrayList<Integer>();
	private PaperResearchDao dao = new PaperResearchImpl();
	private BaseModel baseModel = new BaseModel();
    private String condition;
	
	private String cond = "", tid = "";// 查询条件
	private String publishDateStart = "";// 发表时间
	private String publishDateEnd = "";
	private InputStream inputStream;
	private String fileDownloadName;
	@Action(value = "getPaperResearchlist", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String getList() {
		StringBuilder hql=null;
		if(condition!=null&&!"".equals(condition)){
			hql=new StringBuilder("FROM PaperResearch as p where ");//
			condition=condition.replaceAll("@", "'");
			condition=condition.replaceAll("status = '-1'","status='0' or status='1'");
			hql.append(condition);
		}else{
			Teacher t = (Teacher) session.get("loginteacher");
			hql=new StringBuilder("select p FROM PaperResearch as p where 1=1");//
			if (!"3".equals(t.getType())) {
				hql.append(" and p.author like '%" + t.getId() + "%'");
			}
			if ((!"".equals(tid.trim()))&&(!"undefined".equals(tid.trim()))){// 同一用户,不同界面
				hql.append(" and p.author like '%" + tid + "%'");
			}
			if (!"".equals(cond.trim())) {
				hql.append( " and (  p.author like '%" + cond
						+ "%' or p.paperTitle like '%" + cond + "%' or p.publication like '%" + cond
						+ "%' or p.publishNum like '%" + cond + "%')");
			}
			if (!"".equals(publishDateStart.trim())) {
				hql.append(" and ( p.publishDate >='" + publishDateStart + "')");
			}
			if (!"".equals(publishDateEnd.trim())) {
				hql.append(" and ( p.publishDate <='" + publishDateEnd + "')");
			}
			if (paperresearch.getStatus() != null && !"".equals(paperresearch.getStatus().trim())) {
				if(("0".equals(paperresearch.getStatus()) || ("1".equals(paperresearch.getStatus())))){
				hql.append(" and ( p.status ='"+paperresearch.getStatus()+"')");}
			}
			
			if (paperresearch.getLevel() != null && !"".equals(paperresearch.getLevel().trim())) {
				hql.append( " and ( p.level like'" + paperresearch.getLevel() + "')");
			}
		}
		
		if(page==0||rows==0){
			listobj=dao.getList(paperresearch,hql.toString());
			jsoMap.put("total", 10);
			jsoMap.put("rows", listobj);
			}
			else{
				jsoMap.put("total",dao.getPages(hql));
				jsoMap.put("rows", dao.getPageObject(hql, page,rows));
			}
		return "success";
	}

	@Action(value = "addPaperResearch", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String add() {
		/*Teacher t = (Teacher) session.get("loginteacher");
		paperresearch.setCreateId(t.getId());*/
		/*
		 * for(int i=0;i<paperresearch.getFmFileName().size();i++){
		 * System.out.println("封面:"+paperresearch.getFmFileName().get(i)); }
		 */
		/*String author=paperresearch.getAuthor();
		String [] ss=author.split(",");String str="";
		for(int i=0;i<ss.length;i++){
			int start=ss[i].indexOf("[");
			int end=ss[i].indexOf("]");
			str+=ss[i].substring(start+1, end-1)+",";
		}
		str=str.substring(0, str.length()-1);
		paperresearch.setAuthor(str);System.out.println(str);*/
		if(paperresearch.getStatus()==null||"".equals(paperresearch.getStatus()))
			paperresearch.setStatus("0");
		jsoMap.put("msg", dao.add(paperresearch));
		return "success";

	}

	@Action(value = "updatePaperResearch", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String update() {
		PaperResearch temp=(PaperResearch) dao.getByIdAndEvict(paperresearch.getId());
		paperresearch.setFmName(temp.getFmName());
		paperresearch.setBqyName(temp.getBqyName());
		paperresearch.setMlName(temp.getMlName());
		paperresearch.setZwName(temp.getZwName());
		paperresearch.setFdName(temp.getFdName());
		paperresearch.setJsName(temp.getJsName());
		  /*
		   如果各部分有记录，先删除相关记录再保存新的记录
		   * */
		 if(paperresearch.getFm()!=null&&paperresearch.getFm().size()>0){
			 deleteFiles(rootPath, paperresearch.getFmName(), paperresearch.getId());
		    System.out.println("删除[封面]文件");
		 }
		
		 if(paperresearch.getBqy()!=null&&paperresearch.getBqy().size()>0){
			 deleteFiles(rootPath, paperresearch.getBqyName(), paperresearch.getId());
		    System.out.println("删除[版权页]文件");
		 }
		
		 if(paperresearch.getMl()!=null&&paperresearch.getMl().size()>0){
			 deleteFiles(rootPath, paperresearch.getMlName(), paperresearch.getId());
		    System.out.println("删除[目录]文件");
		 }
		 

		 if(paperresearch.getZw()!=null&&paperresearch.getZw().size()>0){
			 deleteFiles(rootPath, paperresearch.getZwName(), paperresearch.getId());
		    System.out.println("删除[正文]文件");
		 }
		 
		 if(paperresearch.getFd()!=null&&paperresearch.getFd().size()>0){
			 deleteFiles(rootPath, paperresearch.getFdName(), paperresearch.getId());
		    System.out.println("删除[封底]文件");
		 }
		 
		 if(paperresearch.getJs()!=null&&paperresearch.getJs().size()>0){
			 deleteFiles(rootPath, paperresearch.getJsName(), paperresearch.getId());
		    System.out.println("删除[检索页]文件");
		 }
		 
		  boolean result=dao.update(paperresearch);
		
		 /*List<String> deletePath=new ArrayList<String>(0);
		   List<String> tempPaths=null;
		  tempPaths=super.copyNotNullUploadFiles(paperresearch.getFmName(), temp.getFmName(), temp.getId());
		  if(tempPaths!=null&&paperresearch.getFmName()!=null  && !paperresearch.getFmName().equals(temp.getFmName()))
			  deletePath.addAll(tempPaths);
		
		  
		  tempPaths=super.copyNotNullUploadFiles(paperresearch.getBqyName(), temp.getBqyName(), temp.getId());
		  if(tempPaths!=null&&paperresearch.getBqyName()!=null && !paperresearch.getBqyName().equals(temp.getBqyName()))
			  deletePath.addAll(tempPaths);
		  
		  
		  tempPaths=super.copyNotNullUploadFiles(paperresearch.getMlName(), temp.getMlName(), temp.getId());
		  if(tempPaths!=null&&paperresearch.getMlName()!=null&& !paperresearch.getMlName().equals(temp.getMlName()))
			  deletePath.addAll(tempPaths);
		 
		  
		  tempPaths=super.copyNotNullUploadFiles(paperresearch.getZwName(), temp.getZwName(), temp.getId());
		  if(tempPaths!=null&&paperresearch.getZwName()!=null&& !paperresearch.getZwName().equals(temp.getZwName()))
				  deletePath.addAll(tempPaths);
		  
		  
		  
		  tempPaths=super.copyNotNullUploadFiles(paperresearch.getFdName(), temp.getFdName(), temp.getId());
		  if(tempPaths!=null&&paperresearch.getFdName()!=null&& !paperresearch.getFdName().equals(temp.getFdName()))
                      deletePath.addAll(tempPaths);
		
		 
		  
		  tempPaths=super.copyNotNullUploadFiles(paperresearch.getJsName(), temp.getJsName(), temp.getId());
		  if(tempPaths!=null&&paperresearch.getJsName()!=null&& !paperresearch.getJsName().equals(temp.getJsName()))
                      deletePath.addAll(tempPaths);
		  
		 */
		  
		  
		jsoMap.put("msg", result);
		/*if(result==true&&deletePath!=null&&deletePath.size()>0){
			deleteFilePath(rootPath,deletePath);
		}*/
		return "success";
	}

	@Action(value = "deletePaperResearch", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String delete() {
		//System.out.println(ids.size());
		int count=dao.delete(ids);
		        
		
		deleteFoldByIds(rootPath, ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共删除"+count+"条数据!");
		return "success";
	}
	@Action(value = "reviewPaperResearch", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String review() {
		System.out.println(ids.size());
		int count=dao.review(ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共审核通过"+count+"条数据!");
		return "success";
	}
	@Action(value = "exportPaperResearch", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
	public String export(){
		String name = "学术论文" + DateUtil.dateToYMDhms(new Date()) + ".zip";
		try {
			this.setFileDownloadName(new String(name.getBytes(), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	/*	String zip_temp =ServletActionContext.getServletContext().getRealPath(zipPath);
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
	@Override
	public PaperResearch getModel() {
		return paperresearch;
	}

	public PaperResearch getPaperresearch() {
		return paperresearch;
	}

	public void setPaperresearch(PaperResearch paperresearch) {
		this.paperresearch = paperresearch;
	}

	

	public BaseModel getBaseModel() {
		return baseModel;
	}

	public void setBaseModel(BaseModel baseModel) {
		this.baseModel = baseModel;
	}

	public String getCond() {
		return cond;
	}

	public void setCond(String cond) {
		this.cond = cond;
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

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}


	

	

	

	

	
}
