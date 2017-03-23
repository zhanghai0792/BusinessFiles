package com.hy.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.LearningMeetingDao;
import com.hy.dao.impl.LearningMeetingImpl;
import com.hy.model.LearningMeeting;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.opensymphony.xwork2.ModelDriven;

public class LearningMeetingAction extends BaseAction implements  ModelDriven<LearningMeeting>{

	
	private static final long serialVersionUID = 1L;
	private LearningMeeting learningmeeting  = new LearningMeeting();
	private List<LearningMeeting> learningmeetings=new ArrayList<LearningMeeting>();;
	private LearningMeetingDao dao = new LearningMeetingImpl();
	private String cond="",tid="";
	private String DateStart="";
	private String DateEnd="";
	private static String zipPath="/ziptemp/LearningMeeting/";
	private InputStream inputStream;
	private String fileDownloadName;

private List<Integer> ids = new ArrayList<Integer>();
	
	
	@Action(value = "getLearningMeetinglist", results = {@Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		learningmeeting.setTeacherId(t.getId());
		StringBuilder hql=new StringBuilder("select p,t FROM LearningMeeting as p,Teacher as t where p.teacherId=t.id");
		if(!"3".equals(t.getType())){//科教秘书=3
			hql.append(" and  p.teacherId='"+t.getId()+"'");
		}
		if ((!"".equals(tid.trim()))&&(!"undefined".equals(tid.trim()))) {// 同一用户,不同界面
			hql.append(" and p.teacherId='" + tid + "'");
		}
		if(!"".equals(cond.trim())){
			hql.append(" and ( t.name like'%"+cond+"%' or p.teacherId like '%"+cond+"%' or p.name like '%"+cond+"%' or p.major like '%"+cond+"%' or p.organization like '%"+cond+"%' or p.address like '%"+cond+"%')");
		}
		
		if(!"".equals(DateStart.trim())){
			hql.append(" and p.time >= '"+DateStart+"'");
		}
		if(!"".equals(DateEnd.trim())){
			hql.append(" and p.time <= '"+DateEnd+"'");
		}
		listobj=dao.getList(learningmeeting,hql.toString());
		//jsoMap.put("total", 10);
		jsoMap.put("rows", listobj);
		return "success";
	}
	@Action(value = "addLearningMeeting", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String add() {
		Teacher t= (Teacher) session.get("loginteacher");
		learningmeeting.setTeacherId(t.getId());
		//learningmeeting.setTeacherName(t.getName());
		jsoMap.put("msg", dao.add(learningmeeting));
		return "success";
	}
	@Action(value = "updateLearningMeeting", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String update() {
		//Teacher t= (Teacher) session.get("loginteacher");
		//learningmeeting.setTeacherName(t.getName());
		jsoMap.put("msg", dao.update(learningmeeting));
		return "success";
	}
	@Action(value = "deleteLearningMeeting", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String delete() {
		jsoMap.put("msg", dao.delete(learningmeeting));
		return "success";
	}

	@Action(value = "exportLearningMeeting", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
			public String export() {
				String name = "参加会议情况" + DateUtil.dateToYMDhms(new Date()) + ".zip";
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
	
	public LearningMeeting getLearningmeeting() {
		return learningmeeting;
	}
	public void setLearningmeeting(LearningMeeting learningmeeting) {
		this.learningmeeting = learningmeeting;
	}
	public List<LearningMeeting> getLearningmeetings() {
		return learningmeetings;
	}
	public void setLearningmeetings(List<LearningMeeting> learningmeetings) {
		this.learningmeetings = learningmeetings;
	}
	@Override
	public LearningMeeting getModel() {
		return learningmeeting;
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
