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

import com.hy.dao.TeachingMatchDao;
import com.hy.dao.impl.TeachingMatchImpl;
import com.hy.model.Teacher;
import com.hy.model.TeachingMatch;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.opensymphony.xwork2.ModelDriven;

public class TeachingMatchAction extends BaseAction implements  ModelDriven<TeachingMatch>{

	
	private static final long serialVersionUID = 1L;
	private TeachingMatch teachingMatch  = new TeachingMatch();
	private List<TeachingMatch> teachingMatchs=new ArrayList<TeachingMatch>();
	private TeachingMatchDao dao = new TeachingMatchImpl();
	private List<Integer> ids=new ArrayList<Integer>();
	private String cond="",tid="";
	private String DateStart="";
	private String DateEnd="";
	private InputStream inputStream;
	private String fileDownloadName;
	private static String rootPath="/attachments_Img/TeachingMatch/";
	private static String zipPath="/ziptemp/TeachingMatch/";
	
	
	@Action(value = "getTeachingMatchlist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		StringBuilder hql=new StringBuilder("select new TeachingMatch(p,t) FROM TeachingMatch as p ,Teacher as t where p.teacherId=t.id");
		if(!"3".equals(t.getType())){//科教秘书=3
			hql.append(" and  p.teacherId like '%"+t.getId()+"%'");
		}
		if ((!"".equals(tid.trim()))&&(!"undefined".equals(tid.trim()))) {// 同一用户,不同界面
			hql.append(" and p.teacherId like '%" + tid + "%'");
		}
		if(!"".equals(cond.trim())){
			hql.append(" and ( p.teacherId like '%"+cond+"%' or p.prize like '%"+cond+"%')");
		}
		
		if(!"".equals(DateStart.trim())){
			hql.append(" and p.time >= '"+DateStart+"'");
		}
		if(!"".equals(DateEnd.trim())){
			hql.append(" and p.time <= '"+DateEnd+"'");
		}
		if(teachingMatch.getLevel()!=null&&!"".equals(teachingMatch.getLevel().trim())){
			hql.append(" and p.level = '"+teachingMatch.getLevel()+"'");
		}
		
		if (teachingMatch.getStatus() != null && !"".equals(teachingMatch.getStatus().trim())) {
			if(("0".equals(teachingMatch.getStatus()) || ("1".equals(teachingMatch.getStatus())))){
			hql.append(" and ( p.status ='"+teachingMatch.getStatus()+"')");}
		}
		
	/*	listobj=dao.getList(teachingMatch,hql.toString());
		jsoMap.put("total", 10);
		jsoMap.put("rows", listobj);*/
		
		if(page==0||rows==0){
			listobj=dao.getList(teachingMatch,hql.toString());
			jsoMap.put("total", 10);
			jsoMap.put("rows", listobj);
			}
			else{
				jsoMap.put("total",dao.getPages(hql));
				jsoMap.put("rows", dao.getPageObject(hql, page,rows));
			}	
		
		return "success";
	}
	@Action(value = "addTeachingMatch", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String add(){
		Teacher t= (Teacher) session.get("loginteacher");
		teachingMatch.setTeacherId(t.getId());
		teachingMatch.setTeacherName(t.getName());
		if(teachingMatch.getStatus()==null||"".equals(teachingMatch.getStatus()))
			teachingMatch.setStatus("0");
		jsoMap.put("msg",dao.add(teachingMatch));
		return "success";
		
	}
	//attachments_Img\\TeachingMatch
/*	private void deleteFile(Integer id){
		String FILEPATH = ServletActionContext.getServletContext().getRealPath("/attachments_Img/TeachingMatch");
		File fold=new File(FILEPATH);
		File[] files=fold.listFiles();
		String fileStart=id+"_";
		if(files!=null)
		for(File f:files){
			if(f.getName().startsWith(fileStart))
				f.delete();
		}
	}*/

	
	@Action(value = "updateTeachingMatch", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String update(){
		//System.out.println(teachingMatch);
		//deleteFile(teachingMatch.getId());
		Teacher t= (Teacher) session.get("loginteacher");
		
		teachingMatch.setTeacherName(t.getName());
		TeachingMatch temp=(TeachingMatch) dao.getByIdAndEvict(teachingMatch.getId());
		teachingMatch.setFileName(temp.getFileName());
		if(teachingMatch.getFile()!=null&&teachingMatch.getFile().size()>0){
			 deleteFiles(rootPath, teachingMatch.getFileName(), teachingMatch.getId());
		 }
		
		boolean result=dao.update(teachingMatch);
		
		/*List<String> deletTemp=null;
		if(teachingMatch.getFile()!=null && teachingMatch.getFile().size()>0 && teachingMatch.getFileName()!=null && !teachingMatch.getFileName().equals(temp.getFileName())){
			deletTemp=pathSplit(temp.getFileName(),teachingMatch.getId());	
		}*/
		jsoMap.put("msg",result);
		/*if(result==true&&deletTemp!=null&&deletTemp.size()>0){
			deleteFilePath(rootPath,deletTemp);
		}*/
		return "success";
	}
	@Action(value = "deleteTeachingMatch", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String delete(){
		//jsoMap.put("msg",dao.delete(teachingMatch));
		int count=dao.delete(ids);
		if(count>0)
			deleteFoldByIds(rootPath, ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共删除"+count+"条数据!");
		return "success";
	}
	@Action(value = "reviewTeachingMatch", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String review() {
		System.out.println(ids.size());
		int count=dao.review(ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共审核通过"+count+"条数据!");
		return "success";
	}
	
	@Action(value = "exportTeachingMatch", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
	public String export(){
		String name = "教学比赛" + DateUtil.dateToYMDhms(new Date()) + ".zip";
		try {
			this.setFileDownloadName(new String(name.getBytes(), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
/*
		String zip_temp =ServletActionContext.getServletContext().getRealPath(zipPath);
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
		
		List<exportModel> models=dao.getByIdsDeal(ids);
		if(models!=null&&models.size()>0){
			inputStream=export(zipPath, rootPath, name,  models);
		}
		return "success";
	}
	
	
	public TeachingMatch getTeachingMatch() {
		return teachingMatch;
	}

	public void setTeachingMatch(TeachingMatch teachingMatch) {
		this.teachingMatch = teachingMatch;
	}

	public List<TeachingMatch> getTeachingMatchs() {
		return teachingMatchs;
	}
	public void setTeachingMatchs(
			List<TeachingMatch> teachingMatchs) {
		this.teachingMatchs = teachingMatchs;
	}

	@Override
	public TeachingMatch getModel() {
		return teachingMatch;
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
