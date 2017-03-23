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

import com.hy.dao.ScientificAchievementDao;
import com.hy.dao.impl.ScientificAchievementImpl;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.hy.model.ScientificAchievement;
import com.opensymphony.xwork2.ModelDriven;

public class ScientificAchievementAction extends BaseAction implements  ModelDriven<ScientificAchievement>{

	private static String rootPath="/attachments_Img/ScientificAchievement/";
	private static String zipPath="/ziptemp/ScientificAchievement/";
	private InputStream inputStream;
	private String fileDownloadName;
	private static final long serialVersionUID = 1L;
	private ScientificAchievement scientificachievement  = new ScientificAchievement();
	private List<ScientificAchievement> scientificachievements=new ArrayList<ScientificAchievement>();
	private ScientificAchievementDao dao = new ScientificAchievementImpl();
	private List<Integer> ids=new ArrayList<Integer>();
	private String cond="",tid="";
	private String DateStart="";
	private String DateEnd="";
	
	@Action(value = "getScientificAchievementlist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		StringBuilder hql=new StringBuilder("select distinct p FROM ScientificAchievement as p where 1=1");
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
		if(scientificachievement.getLevel()!=null&&!"".equals(scientificachievement.getLevel().trim())){
			hql.append(" and p.level = '"+scientificachievement.getLevel()+"'");
		}
		if (scientificachievement.getStatus() != null && !"".equals(scientificachievement.getStatus().trim())) {
			if(("0".equals(scientificachievement.getStatus()) || ("1".equals(scientificachievement.getStatus())))){
			hql.append(" and ( p.status ='"+scientificachievement.getStatus()+"')");}
		}
		
		
	/*	listobj=dao.getList(scientificachievement,hql.toString());
		jsoMap.put("total", 10);
		jsoMap.put("rows", listobj);*/
		
		if(page==0||rows==0){
			listobj=dao.getList(scientificachievement,hql.toString());
			jsoMap.put("total", 10);
			jsoMap.put("rows", listobj);
			}
			else{
				jsoMap.put("total",dao.getPages(hql));
				jsoMap.put("rows", dao.getPageObject(hql, page,rows));
			}
		return "success";
	}
	@Action(value = "addScientificAchievement", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String add(){
		//System.out.println(scientificachievement);
		if(scientificachievement.getStatus()==null||"".equals(scientificachievement.getStatus()))
			scientificachievement.setStatus("0");
		jsoMap.put("msg",dao.add(scientificachievement));
		return "success";
		
	}
/*	//"attachments_Img\\ScientificAchievement"
	private void deleteFile(Integer id){
		String FILEPATH = ServletActionContext.getServletContext().getRealPath("/attachments_Img/ScientificAchievement");
		File fold=new File(FILEPATH);
		File[] files=fold.listFiles();
		String fileStart=id+"_";
		if(files!=null)
		for(File f:files){
			if(f.getName().startsWith(fileStart))
				f.delete();
		}
	}*/
	@Action(value = "updateScientificAchievement", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String update(){
		
		ScientificAchievement temp=(ScientificAchievement) dao.getByIdAndEvict(scientificachievement.getId());
		scientificachievement.setFileName(temp.getFileName());
		if(scientificachievement.getFile()!=null&&scientificachievement.getFile().size()>0){
			 deleteFiles(rootPath, scientificachievement.getFileName(), scientificachievement.getId());
		 }
		
		boolean result=dao.update(scientificachievement);
		/*List<String> deletTemp=null;
		if(scientificachievement.getFileName()!=null && !scientificachievement.getFileName().equals(temp.getFileName())){
			deletTemp=pathSplit(temp.getFileName(),scientificachievement.getId());
			}*/
		
		jsoMap.put("msg",result);
		/*if(result==true&&deletTemp!=null&&deletTemp.size()>0){
			deleteFilePath(rootPath,deletTemp);
		}*/
		return "success";
	}
	@Action(value = "deleteScientificAchievement", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String delete(){
		//jsoMap.put("msg",dao.delete(scientificachievement));
		int count=dao.delete(ids);
		/*for(Integer id:ids)
			deleteFile(id);*/
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		if(count>0)
			deleteFoldByIds(rootPath, ids);
		
		jsoMap.put("msg", "共删除"+count+"条数据!");
		return "success";
	}
	
	@Action(value = "exportScientificAchievement", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
	public String export(){
		String name = "科研成果" + DateUtil.dateToYMDhms(new Date()) + ".zip";
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
	
	
	@Action(value = "reviewScientificAchievement", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String review() {
		System.out.println(ids.size());
		int count=dao.review(ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共审核通过"+count+"条数据!");
		return "success";
	}
	public ScientificAchievement getScientificachievement() {
		return scientificachievement;
	}

	public void setScientificachievement(ScientificAchievement Scientificachievement) {
		this.scientificachievement = Scientificachievement;
	}

	public List<ScientificAchievement> getScientificachievements() {
		return scientificachievements;
	}
	public void setScientificachievements(
			List<ScientificAchievement> Scientificachievements) {
		this.scientificachievements = Scientificachievements;
	}

	@Override
	public ScientificAchievement getModel() {
		return scientificachievement;
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
