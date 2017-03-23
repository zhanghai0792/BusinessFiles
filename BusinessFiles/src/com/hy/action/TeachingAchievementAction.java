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

import com.hy.dao.TeachingAchievementDao;
import com.hy.dao.impl.TeachingAchievementImpl;
import com.hy.model.Patent;
import com.hy.model.Teacher;
import com.hy.model.TeachingAchievement;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.opensymphony.xwork2.ModelDriven;

public class TeachingAchievementAction extends BaseAction implements  ModelDriven<TeachingAchievement>{

	
	private static final long serialVersionUID = 1L;
	private TeachingAchievement teachingachievement  = new TeachingAchievement();
	private List<TeachingAchievement> teachingachievements=new ArrayList<TeachingAchievement>();
	private TeachingAchievementDao dao = new TeachingAchievementImpl();
	private List<Integer> ids=new ArrayList<Integer>();
	private String cond="",tid="";
	private String DateStart="";
	private String DateEnd="";
	private InputStream inputStream;
	private String fileDownloadName;
	private static String rootPath="/attachments_Img/TeachingAchievement/";
	private static String zipPath="/ziptemp/TeachingAchievement/";
	
	
	@Action(value = "getTeachingAchievementlist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		StringBuilder hql=new StringBuilder("select distinct p FROM TeachingAchievement as p where 1=1");
		if(!"3".equals(t.getType())){//科教秘书=3
			hql.append(" and  p.author like '%"+t.getId()+"%'");
		}
		if ((!"".equals(tid.trim()))&&(!"undefined".equals(tid.trim()))) {// 同一用户,不同界面
			hql.append(" and p.author like '%" + tid + "%'");
		}
		if(!"".equals(cond.trim())){
			hql.append(" and ( p.author like '%"+cond+"%' or p.prize like '%"+cond+"%')");
		}
		
		if(!"".equals(DateStart.trim())){
			hql.append(" and p.time >= '"+DateStart+"'");
		}
		if(!"".equals(DateEnd.trim())){
			hql.append(" and p.time <= '"+DateEnd+"'");
		}
		if(teachingachievement.getLevel()!=null&&!"".equals(teachingachievement.getLevel().trim())){
			hql.append(" and p.level = '"+teachingachievement.getLevel()+"'");
		}
		if (teachingachievement.getStatus() != null && !"".equals(teachingachievement.getStatus().trim())) {
			if(("0".equals(teachingachievement.getStatus()) || ("1".equals(teachingachievement.getStatus())))){
			hql.append(" and ( p.status ='"+teachingachievement.getStatus()+"')");}
		}
		
	/*	listobj=dao.getList(teachingachievement,hql.toString());
		jsoMap.put("total", 10);
		jsoMap.put("rows", listobj);*/
		if(page==0||rows==0){
			listobj=dao.getList(teachingachievement,hql.toString());
			jsoMap.put("total", 10);
			jsoMap.put("rows", listobj);
			}
			else{
				jsoMap.put("total",dao.getPages(hql));
				jsoMap.put("rows", dao.getPageObject(hql, page,rows));
			}		
		
		return "success";
	}
	@Action(value = "addTeachingAchievement", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String add(){
		if(teachingachievement.getStatus()==null||"".equals(teachingachievement.getStatus()))
			teachingachievement.setStatus("0");
		jsoMap.put("msg",dao.add(teachingachievement));
		return "success";
		
	}
	
	@Action(value = "updateTeachingAchievement", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String update(){
		TeachingAchievement temp= (TeachingAchievement) dao.getByIdAndEvict(teachingachievement.getId());
		teachingachievement.setFileName(temp.getFileName());
		if(teachingachievement.getFile()!=null&&teachingachievement.getFile().size()>0){
			 deleteFiles(rootPath, teachingachievement.getFileName(), teachingachievement.getId());
		 }
		
		boolean result=dao.update(teachingachievement);
		/*List<String> deletePath=new ArrayList<String>(0);
		   List<String> tempPaths=null;
		  tempPaths=super.copyNotNullUploadFiles(teachingachievement.getFileName(), temp.getFileName(), temp.getId());
		  if(tempPaths!=null && teachingachievement.getFileName()!=null && !teachingachievement.getFileName().equals(temp.getFileName()))
					  deletePath.addAll(tempPaths);	
		  */
		jsoMap.put("msg",result);
		/*if(result==true&&deletePath!=null&&deletePath.size()>0){
			deleteFilePath(rootPath,deletePath);
		}*/
		return "success";
	}
	@Action(value = "deleteTeachingAchievement", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String delete(){
		//jsoMap.put("msg",dao.delete(teachingachievement));
		int count=dao.delete(ids);
		if(count>0)
			deleteFoldByIds(rootPath, ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共删除"+count+"条数据!");
		return "success";
	}
	@Action(value = "reviewTeachingAchievement", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String review() {
		System.out.println(ids.size());
		int count=dao.review(ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共审核通过"+count+"条数据!");
		return "success";
	}
	
	@Action(value = "exportTeachingAchievement", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
	public String export(){
		String name = "教学成果及奖励" + DateUtil.dateToYMDhms(new Date()) + ".zip";
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
	
	
	public TeachingAchievement getTeachingachievement() {
		return teachingachievement;
	}

	public void setTeachingachievement(TeachingAchievement teachingachievement) {
		this.teachingachievement = teachingachievement;
	}

	public List<TeachingAchievement> getTeachingachievements() {
		return teachingachievements;
	}
	public void setTeachingachievements(
			List<TeachingAchievement> teachingachievements) {
		this.teachingachievements = teachingachievements;
	}

	@Override
	public TeachingAchievement getModel() {
		return teachingachievement;
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
	
	

}
