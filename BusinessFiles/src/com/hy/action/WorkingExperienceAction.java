package com.hy.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.WorkingExperienceDao;
import com.hy.dao.impl.WorkingExperienceImpl;
import com.hy.model.Teacher;
import com.hy.model.WorkingExperience;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.opensymphony.xwork2.ModelDriven;

public class WorkingExperienceAction extends BaseAction implements  ModelDriven<WorkingExperience>{
	private static final long serialVersionUID = 1L;
	private WorkingExperience workingexperience  = new WorkingExperience();
	private List<WorkingExperience> workingexperiences=new ArrayList<WorkingExperience>();;
	private WorkingExperienceDao dao = new WorkingExperienceImpl();
	private static String zipPath="/ziptemp/WorkingExperience/";
	private InputStream inputStream;
	private String fileDownloadName;

private List<Integer> ids = new ArrayList<Integer>();
	
	
	@Action(value = "getWorkingExperiencelist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		WorkingExperience twe  = new WorkingExperience();
		twe.setTeacherId(t.getId());
		StringBuilder hql =new StringBuilder("FROM WorkingExperience  where teacherId = ");
		if (t!=null) {
			hql.append("  '" + t.getId() + "'");
		}
		hql.append(" order by startDate desc");
/*		workingexperiences=dao.getList(twe,hql.toString());
		//String rows = req.getParameter("rows");
		//String page = req.getParameter("page");
		jsoMap.put("total", 10);
		jsoMap.put("rows", workingexperiences);*/
		
		if(page==0||rows==0){
			workingexperiences=dao.getList(twe,hql.toString());
			jsoMap.put("total", 10);
			jsoMap.put("rows", workingexperiences);
			}
			else{
				jsoMap.put("total",dao.getPages(hql));
				jsoMap.put("rows", dao.getPageObject(hql, page,rows));
			}
		return "success";
	}
	
	@Action(value = "addWE", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String add() {
		jsoMap=new HashMap<String, Object>();
		Teacher t= (Teacher) session.get("loginteacher");
		workingexperience.setTeacherId(t.getId());
		workingexperience.setTeacherName(t.getName());
		jsoMap.put("msg", dao.add(workingexperience));
		return "success";
	}
	@Action(value = "updateWE", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String update() {
		jsoMap=new HashMap<String, Object>();
		Teacher t= (Teacher) session.get("loginteacher");
		workingexperience.setTeacherId(t.getId());
		workingexperience.setTeacherName(t.getName());
		jsoMap.put("msg", dao.update(workingexperience));
		return "success";
	}
	@Action(value = "deleteWE", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String delete() {
		jsoMap=new HashMap<String, Object>();
		jsoMap.put("msg", dao.delete(workingexperience));
		return "success";
	}
	@Action(value = "exportWorkingExperience", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
			public String export() {
				String name = "工作经历" + DateUtil.dateToYMDhms(new Date()) + ".zip";
				try {
					this.setFileDownloadName(new String(name.getBytes(), "ISO-8859-1"));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				List<exportModel> models=dao.getByIdsDeal(ids);
				if(models!=null&&models.size()>0){
					inputStream=export(zipPath, null, name,  models);
				}
				
				return "success";
			}
	
	
	public WorkingExperience getWe() {
		return workingexperience;
	}

	public void setWe(WorkingExperience we) {
		this.workingexperience = we;
	}

	public List<WorkingExperience> getWes() {
		return workingexperiences;
	}

	public void setWes(List<WorkingExperience> wes) {
		this.workingexperiences = wes;
	}

	public Map<String, Object> getJsoMap() {
		return jsoMap;
	}

	public void setJsoMap(Map<String, Object> jsoMap) {
		this.jsoMap = jsoMap;
	}

	@Override
	public WorkingExperience getModel() {
		return workingexperience;
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

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	
	
}
