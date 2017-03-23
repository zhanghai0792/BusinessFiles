package com.hy.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.LearningGroupDao;
import com.hy.dao.impl.LearningGroupImpl;
import com.hy.model.LearningGroup;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.opensymphony.xwork2.ModelDriven;

public class LearningGroupAction extends BaseAction implements  ModelDriven<LearningGroup>{

	
	private static final long serialVersionUID = 1L;
	private LearningGroup learninggroup  = new LearningGroup();
	private List<LearningGroup> learninggroups=new ArrayList<LearningGroup>();
	private LearningGroupDao dao = new LearningGroupImpl();
	private String cond="",tid="";
	private static String zipPath="/ziptemp/LearningGroup/";
	private InputStream inputStream;
	private String fileDownloadName;

private List<Integer> ids = new ArrayList<Integer>();
	
	@Action(value = "getLearningGrouplist", results = {@Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		learninggroup.setTeacherId(t.getId());
		StringBuilder hql=new StringBuilder("select p,t FROM LearningGroup as p,Teacher as t where p.teacherId=t.id");
		if(!"3".equals(t.getType())){//科教秘书=3
			hql.append(" and  p.teacherId='"+t.getId()+"'");
		}
		if ((!"".equals(tid.trim()))&&(!"undefined".equals(tid.trim()))) {// 同一用户,不同界面
			hql.append(" and p.teacherId='" + tid + "'");
		}
		if(!"".equals(cond.trim())){
			hql.append(" and ( t.name like'%"+cond+"%' or p.teacherId like '%"+cond+"%' or p.name like '%"+cond+"%' or p.major like '%"+cond+"%' or p.job like '%"+cond+"%')");
		}
		
		listobj=dao.getList(learninggroup,hql.toString());
		//jsoMap.put("total", 10);
		jsoMap.put("rows", listobj);
		return "success";
	}
	@Action(value = "addLearningGroup", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String add() {
		Teacher t= (Teacher) session.get("loginteacher");
		learninggroup.setTeacherId(t.getId());
		//learninggroup.setTeacherName(t.getName());
		jsoMap=new HashMap<String, Object>();
		jsoMap.put("msg", dao.add(learninggroup));
		return "success";
	}
	@Action(value = "updateLearningGroup", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String update() {
		Teacher t= (Teacher) session.get("loginteacher");
		learninggroup.setTeacherId(t.getId());
		//learninggroup.setTeacherName(t.getName());
		jsoMap.put("msg", dao.update(learninggroup));
		return "success";
	}
	@Action(value = "deleteLearningGroup", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String delete() {
		jsoMap.put("msg", dao.delete(learninggroup));
		return "success";
	}
	@Action(value = "exportLearningGroup", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
			public String export() {
				String name = "学术团队" + DateUtil.dateToYMDhms(new Date()) + ".zip";
				try {
					this.setFileDownloadName(new String(name.getBytes(), "ISO-8859-1"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				List<exportModel> models=dao.getByIdsDeal(ids);
				if(models!=null&&models.size()>0){
					inputStream=export(zipPath, null, name,  models);
				}
				
				return "success";
			}

	
	
	public LearningGroup getLg() {
		return learninggroup;
	}

	public void setLg(LearningGroup lg) {
		this.learninggroup = lg;
	}

	public List<LearningGroup> getLgs() {
		return learninggroups;
	}

	public void setLgs(List<LearningGroup> lgs) {
		this.learninggroups = lgs;
	}


	public String getCond() {
		return cond;
	}
	public void setCond(String cond) {
		this.cond = cond;
	}
	@Override
	public LearningGroup getModel() {
		return learninggroup;
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
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

}
