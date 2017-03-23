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

import com.hy.dao.GuideStudentDao;
import com.hy.dao.impl.GuideStudentImpl;
import com.hy.model.GuideStudent;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.opensymphony.xwork2.ModelDriven;

public class GuideStudentAction extends BaseAction implements  ModelDriven<GuideStudent>{

	private static String rootPath="/attachments_Img/GuideStudent/";
	private static String zipPath="/ziptemp/GuideStudent/";
	
	private static final long serialVersionUID = 1L;
	private GuideStudent guidestudent  = new GuideStudent();
	private List<GuideStudent> guidestudents=new ArrayList<GuideStudent>();
	private GuideStudentDao dao = new GuideStudentImpl();
	private String cond="",tid="";
	private String DateStart="";
	private String DateEnd="";
	private List<Integer> ids=new ArrayList<Integer>();
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




	@Action(value = "getGuideStudentlist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		StringBuilder hql=new StringBuilder("select p FROM GuideStudent as p where 1=1");
		if(!"3".equals(t.getType())){//科教秘书=3
			hql.append(" and  p.author like '%"+t.getId()+"%'");
		}
		if ((!"".equals(tid.trim()))&&(!"undefined".equals(tid.trim()))) {// 同一用户,不同界面
					hql.append(" and p.author like '%" + tid + "%'");
		}
		if(!"".equals(cond.trim())){
			hql.append(" and (  p.author like '%"+cond+"%' or p.prize like '%"+cond+"%')");
		}
		
		if(!"".equals(DateStart.trim())){
			hql.append(" and p.time >= '"+DateStart+"'");
		}
		if(!"".equals(DateEnd.trim())){
			hql.append(" and p.time <= '"+DateEnd+"'");
		}
		if(guidestudent.getLevel()!=null&&"".equals(guidestudent.getLevel().trim())){
			hql.append(" and p.level ='"+guidestudent.getLevel()+"'");
		}
		if (guidestudent.getStatus() != null && !"".equals(guidestudent.getStatus().trim())) {
			if(("0".equals(guidestudent.getStatus()) || ("1".equals(guidestudent.getStatus())))){
			hql.append(" and ( p.status ='"+guidestudent.getStatus()+"')");}
		}
		/*listobj=dao.getList(guidestudent,hql.toString());
		jsoMap.put("total", 10);
		jsoMap.put("rows", listobj);*/
		
		if(page==0||rows==0){
			listobj=dao.getList(guidestudent,hql.toString());
			jsoMap.put("total", 10);
			jsoMap.put("rows", listobj);
			}
			else{
				jsoMap.put("total",dao.getPages(hql));
				jsoMap.put("rows", dao.getPageObject(hql, page,rows));
			}
		return "success";
	}
	


	
	@Action(value = "addGuideStudent", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String add(){
		if(guidestudent.getStatus()==null||"".equals(guidestudent.getStatus()))
			guidestudent.setStatus("0");
		jsoMap.put("msg",dao.add(guidestudent));
		return "success";
		
	}
	@Action(value = "updateGuideStudent", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String update(){
		//System.out.println(guidestudent);
		//deleteFile(guidestudent.getId());
		GuideStudent guidTemp=(GuideStudent) dao.getByIdAndEvict(guidestudent.getId());
		guidestudent.setFileName(guidTemp.getFileName());
		
		
		 if(guidestudent.getFile()!=null&&guidestudent.getFile().size()>0){
			 deleteFiles(rootPath, guidestudent.getFileName(), guidestudent.getId());
		 }
		 boolean result=dao.update(guidestudent);
		jsoMap.put("msg",result);
		/*if(result==true&&deletTemp!=null&&deletTemp.size()>0){
			deleteFilePath(rootPath,deletTemp);
		}*/
		return "success";
	}
	@Action(value = "deleteGuideStudent", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String delete(){
		//jsoMap.put("msg",dao.delete(guidestudent));
		int count=dao.delete(ids);
		if(count>0)
			deleteFoldByIds(rootPath, ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共删除"+count+"条数据!");
		return "success";
	}
	@Action(value = "reviewGuideStudent", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String review() {
		System.out.println(ids.size());
		int count=dao.review(ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共审核通过"+count+"条数据!");
		return "success";
	}
	public GuideStudent getGuidestudent() {
		return guidestudent;
	}
	
	
	@Action(value = "exportGuideStudent", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
	public String export(){
		String name = "指导学生竞赛" + DateUtil.dateToYMDhms(new Date()) + ".zip";
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

	public void setGuidestudent(GuideStudent guidestudent) {
		this.guidestudent = guidestudent;
	}

	public List<GuideStudent> getGuidestudents() {
		return guidestudents;
	}

	public void setGuidestudents(List<GuideStudent> guidestudents) {
		this.guidestudents = guidestudents;
	}

	@Override
	public GuideStudent getModel() {
		return guidestudent;
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
