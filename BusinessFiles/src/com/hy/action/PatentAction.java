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

import com.hy.dao.PatentDao;
import com.hy.dao.impl.PatentImpl;
import com.hy.model.Patent;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.hy.util.FileUtil;
import com.opensymphony.xwork2.ModelDriven;

public class PatentAction extends BaseAction implements ModelDriven<Patent>{
	
	private static String rootPath="/attachments_Img/Patent/";
	private static String zipPath="/ziptemp/Patent/";
	
	
	private static final long serialVersionUID = 1L;
	private Patent patent = new Patent();
	private List<Integer> ids = new ArrayList<Integer>();
	private PatentDao dao = new PatentImpl();

	private String cond = "", tid = "";// 查询条件
	private String dateStart = "";// 发表时间
	private String dateEnd = "";
	private InputStream inputStream;
	private String fileDownloadName;
	@Action(value = "getPatentlist", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String getList() {
		Teacher t = (Teacher) session.get("loginteacher");
		StringBuilder hql =new StringBuilder("select p FROM Patent as p where 1=1");//
		if (!"3".equals(t.getType())) {
			hql.append(" and p.author like '%" + t.getId() + "%'");
		}
		if ((!"".equals(tid.trim()))&&!"undefined".equals(tid.trim())) {// 同一用户,不同界面
			hql.append(" and p.author like '%" + tid + "%'");
		}
		if(!"".equals(dateStart.trim())){
			hql.append(" and p.time >= '"+dateStart+"'");
		}
		if(!"".equals(dateEnd.trim())){
			hql.append(" and p.time <= '"+dateEnd+"'");
		}
		if (patent.getStatus() != null && !"".equals(patent.getStatus().trim())) {
			if(("0".equals(patent.getStatus()) || ("1".equals(patent.getStatus())))){
			hql.append(" and ( p.status ='"+patent.getStatus()+"')");}
		}
	/*	listobj = dao.getList(patent, hql.toString());
		
		jsoMap.put("total", 10);
		jsoMap.put("rows", listobj);*/
		
		if(page==0||rows==0){
			listobj=dao.getList(patent,hql.toString());
			jsoMap.put("total", 10);
			jsoMap.put("rows", listobj);
			}
			else{
				jsoMap.put("total",dao.getPages(hql));
				jsoMap.put("rows", dao.getPageObject(hql, page,rows));
			}
		return "success";
	}

	@Action(value = "addPatent", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String add() {
		if(patent.getStatus()==null||"".equals(patent.getStatus()))
			patent.setStatus("0");
		jsoMap.put("msg", dao.add(patent));
		return "success";

	}

	@Action(value = "updatePatent", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String update() {
		/*String FILEPATH = ServletActionContext.getServletContext().getRealPath("/attachments_Img/Patent");
		FileUtil.deleteDiskFile(FILEPATH+"/"+patent.getId());
		*/
		Patent temp=(Patent) dao.getByIdAndEvict(patent.getId());
		patent.setCertName(temp.getCertName());
		patent.setTransName(temp.getTransName());
		
		if(patent.getCert()!=null&&patent.getCert().size()>0){
			 deleteFiles(rootPath, patent.getCertName(), patent.getId());
		 }
		if(patent.getTrans()!=null&&patent.getTrans().size()>0){
			 deleteFiles(rootPath, patent.getTransName(), patent.getId());
		 }
		
		boolean result=dao.update(patent);
		/*List<String> deletePath=new ArrayList<String>(0);
		   List<String> tempPaths=null;
		  tempPaths=super.copyNotNullUploadFiles(patent.getCertName(), temp.getCertName(), temp.getId());
		  if(tempPaths!=null && patent.getCertName()!=null && !patent.getCertName().equals(temp.getCertName()))
					  deletePath.addAll(tempPaths);	
		  
		  tempPaths=super.copyNotNullUploadFiles(patent.getTransName(), temp.getTransName(), temp.getId());
		  if(tempPaths!=null && patent.getTransName()!=null && !patent.getTransName().equals(temp.getTransName()))
					  deletePath.addAll(tempPaths);	
		*/
		jsoMap.put("msg", result);
		/*if(result==true&&deletePath!=null&&deletePath.size()>0){
			deleteFilePath(rootPath,deletePath);
		}*/
		return "success";
	}

	@Action(value = "deletePatent", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String delete() {
		
		int count=dao.delete(ids);
		if(count>0)
		deleteFoldByIds(rootPath, ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共删除"+count+"条数据!");
		return "success";
	}
	@Action(value = "reviewPatent", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String review() {
		System.out.println(ids.size());
		int count=dao.review(ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共审核通过"+count+"条数据!");
		return "success";
	}
	
	
	
	
	@Action(value = "exportPatent", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
	public String export(){
		String name = "专利" + DateUtil.dateToYMDhms(new Date()) + ".zip";
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
		}
		*/
		
		List<exportModel> models=dao.getByIds(ids);
		if(models!=null&&models.size()>0){
			inputStream=export(zipPath, rootPath, name,  models);
		}
		return "success";
	}
	
	
	public InputStream getInputStream() {
		return inputStream;
	}
	@Override
	public Patent getModel() {
		return patent;
	}

	public Patent getPatent() {
		return patent;
	}

	public void setPatent(Patent patent) {
		this.patent = patent;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public String getCond() {
		return cond;
	}

	public void setCond(String cond) {
		this.cond = cond;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}



	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
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

}
