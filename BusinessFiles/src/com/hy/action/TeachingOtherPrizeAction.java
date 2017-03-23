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

import com.hy.dao.TeachingOtherPrizeDao;
import com.hy.dao.impl.TeachingOtherPrizeImpl;
import com.hy.model.Teacher;
import com.hy.model.TeachingOtherPrize;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.opensymphony.xwork2.ModelDriven;

public class TeachingOtherPrizeAction extends BaseAction implements  ModelDriven<TeachingOtherPrize>{

	
	private static final long serialVersionUID = 1L;
	private TeachingOtherPrize teachingotherPrize  = new TeachingOtherPrize();
	private List<TeachingOtherPrize> teachingotherPrizes=new ArrayList<TeachingOtherPrize>();
	private TeachingOtherPrizeDao dao = new TeachingOtherPrizeImpl();
	private List<Integer> ids=new ArrayList<Integer>();
	private String cond="",tid="";
	private String DateStart="";
	private String DateEnd="";
	private InputStream inputStream;
	private String fileDownloadName;
	private static String rootPath="/attachments_Img/TeachingOtherPrize/";
	private static String zipPath="/ziptemp/TeachingOtherPrize/";
	
	@Action(value = "getTeachingOtherPrizelist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		teachingotherPrize.setTeacherId(t.getId());
		StringBuilder hql=new StringBuilder("select new TeachingOtherPrize(p,t) FROM TeachingOtherPrize as p,Teacher as t where p.teacherId=t.id");
		if(!"3".equals(t.getType())){//科教秘书=3
			hql.append(" and  p.teacherId='"+t.getId()+"'");
		}
		if ((!"".equals(tid.trim()))&&(!"undefined".equals(tid.trim()))) {// 同一用户,不同界面
			hql.append(" and p.teacherId='" + tid + "'");
		}
		if(!"".equals(cond.trim())){
			hql.append(" and ( t.name like'%"+cond+"%' or p.teacherId like '%"+cond+"%' or p.prize like '%"+cond+"%')");
		}
		
		if(!"".equals(DateStart.trim())){
			hql.append(" and p.time >= '"+DateStart+"'");
		}
		if(!"".equals(DateEnd.trim())){
			hql.append(" and p.time <= '"+DateEnd+"'");
		}
		if(teachingotherPrize.getLevel()!=null&&!"".equals(teachingotherPrize.getLevel().trim())){
			hql.append(" and p.level = '"+teachingotherPrize.getLevel()+"'");
		}
		if (teachingotherPrize.getStatus() != null && !"".equals(teachingotherPrize.getStatus().trim())) {
			if(("0".equals(teachingotherPrize.getStatus()) || ("1".equals(teachingotherPrize.getStatus())))){
			hql.append(" and ( p.status ='"+teachingotherPrize.getStatus()+"')");}
		}
		
	/*	listobj=dao.getList(teachingotherPrize,hql.toString());
		jsoMap.put("total", 10);
		jsoMap.put("rows", listobj);*/
		
		if(page==0||rows==0){
			//listobj=dao.getList(teachingotherPrize,hql.toString());
			//jsoMap.put("total", 10);
			//jsoMap.put("rows", listobj);
			jsoMap.put("rows", dao.getPageObject(hql, 1,Integer.parseInt(dao.getPages(hql)+"")));
			}
			else{
				jsoMap.put("total",dao.getPages(hql));
				jsoMap.put("rows", dao.getPageObject(hql, page,rows));
			}	
		return "success";
	}
	

	
	
	@Action(value = "addTeachingOtherPrize", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String add(){
		Teacher t= (Teacher) session.get("loginteacher");
		teachingotherPrize.setTeacherId(t.getId());
		//teachingotherPrize.setTeacherName(t.getName());
		System.out.println("status="+teachingotherPrize.getStatus()+";");
		if(teachingotherPrize.getStatus()==null||"".equals(teachingotherPrize.getStatus()))
			teachingotherPrize.setStatus("0");
		jsoMap.put("msg",dao.add(teachingotherPrize));
		return "success";
		
	}
	@Action(value = "updateTeachingOtherPrize", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String update(){
		//deleteFile(teachingotherPrize.getId());
		TeachingOtherPrize temp=(TeachingOtherPrize) dao.getByIdAndEvict(teachingotherPrize.getId());
		teachingotherPrize.setFileName(temp.getFileName());
		//teachingotherPrize.setTeacherName(temp.getTeacherName());
		if(teachingotherPrize.getFile()!=null&&teachingotherPrize.getFile().size()>0){
			 deleteFiles(rootPath, teachingotherPrize.getFileName(), teachingotherPrize.getId());
		 }
		
		boolean result=dao.update(teachingotherPrize);
		/*List<String> deletTemp=null;
		if(teachingotherPrize.getFile()!=null&&teachingotherPrize.getFile().size()>0&&teachingotherPrize.getFileName()!=null&&!teachingotherPrize.getFileName().equals(temp.getFileName())){
			deletTemp=pathSplit(temp.getFileName(),teachingotherPrize.getId());	
		}*/
		//jsoMap.put("msg",dao.update(teachingotherPrize));
		
		jsoMap.put("msg",result);
		/*if(result==true&&deletTemp!=null&&deletTemp.size()>0){
			deleteFilePath(rootPath,deletTemp);
		}*/
		return "success";
	}
	@Action(value = "deleteTeachingOtherPrize", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"})})
	public String delete(){
		//jsoMap.put("msg",dao.delete(teachingotherPrize));
		int count=dao.delete(ids);
		if(count>0)
			deleteFoldByIds(rootPath, ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共删除 "+count+" 条数据!");
		return "success";
	}
	@Action(value = "reviewTeachingOtherPrize", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String review() {
		int count=dao.review(ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共审核通过"+count+"条数据!");
		return "success";
	}
	
	@Action(value = "exportTeachingOtherPrize", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
	public String export(){
		String name = "其他奖励" + DateUtil.dateToYMDhms(new Date()) + ".zip";
		try {
			this.setFileDownloadName(new String(name.getBytes(), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<exportModel> models=dao.getByIdsDeal(ids);
		if(models!=null&&models.size()>0){
			inputStream=export(zipPath, rootPath, name,  models);
		}
		return "success";
	}
	
	
	public TeachingOtherPrize getTo() {
		return teachingotherPrize;
	}


	public void setTo(TeachingOtherPrize to) {
		this.teachingotherPrize = to;
	}


	public List<TeachingOtherPrize> getTos() {
		return teachingotherPrizes;
	}


	public void setTos(List<TeachingOtherPrize> tos) {
		this.teachingotherPrizes = tos;
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
	@Override
	public TeachingOtherPrize getModel() {
		return teachingotherPrize;
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
