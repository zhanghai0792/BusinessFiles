package com.hy.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.TrainingStudyDao;
import com.hy.dao.impl.TrainingStudyImpl;
import com.hy.model.Teacher;
import com.hy.model.TrainingStudy;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.opensymphony.xwork2.ModelDriven;

public class TrainingStudyAction extends BaseAction implements  ModelDriven<TrainingStudy>{

	
	private static final long serialVersionUID = 1L;
	private TrainingStudy trainingstudy  = new TrainingStudy();
	private List<TrainingStudy> trainingstudys=new ArrayList<TrainingStudy>();;
	private TrainingStudyDao dao = new TrainingStudyImpl();
	private String tid="";
	private static String zipPath="/ziptemp/TrainingStudy/";
	private InputStream inputStream;
	private String fileDownloadName;
	private String DateStart="";
	private String DateEnd="";
private List<Integer> ids = new ArrayList<Integer>();
	
	
	
	@Action(value = "getTrainingStudylist", results = {@Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		trainingstudy.setTeacherId(t.getId());
		StringBuilder hql=new StringBuilder("select p,t FROM TrainingStudy as p,Teacher t where p.teacherId=t.id");
		if(!"3".equals(t.getType())){//科教秘书=3
			hql.append(" and  p.teacherId='"+t.getId()+"'");
		}
		if ((!"".equals(tid.trim()))&&(!"undefined".equals(tid.trim()))) {// 同一用户,不同界面
				hql.append(" and p.teacherId='" + tid + "'");
		}
		
		if(!"".equals(DateStart.trim())){
			hql.append(" and p.startDate >= '"+DateStart+"'");
		}
		if(!"".equals(DateEnd.trim())){
			hql.append(" and p.startDate <= '"+DateEnd+"'");
		}
		
		listobj=dao.getList(trainingstudy,hql.toString());
		//jsoMap.put("total", 10);
		jsoMap.put("rows", listobj);
		return "success";
	}
	@Action(value = "addTrainingStudy", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String add() {
		Teacher t= (Teacher) session.get("loginteacher");
		trainingstudy.setTeacherId(t.getId());
		trainingstudy.setTeacherName(t.getName());
		jsoMap.put("msg", dao.add(trainingstudy));
		return "success";
	}
	@Action(value = "updateTrainingStudy", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String update() {
		Teacher t= (Teacher) session.get("loginteacher");
		trainingstudy.setTeacherId(t.getId());
		trainingstudy.setTeacherName(t.getName());
		jsoMap=new HashMap<String, Object>();
		jsoMap.put("msg", dao.update(trainingstudy));
		return "success";
	}
	@Action(value = "deleteTrainingStudy", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String delete() {
		jsoMap.put("msg", dao.delete(trainingstudy));
		return "success";
	}
	
	@Action(value = "exportTrainingStudy", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
			public String export() {
				String name = "进修培训" + DateUtil.dateToYMDhms(new Date()) + ".zip";
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
	
	public TrainingStudy getTs() {
		return trainingstudy;
	}

	public void setTs(TrainingStudy ts) {
		this.trainingstudy = ts;
	}

	public List<TrainingStudy> getTss() {
		return trainingstudys;
	}

	public void setTss(List<TrainingStudy> tss) {
		this.trainingstudys = tss;
	}

	@Override
	public TrainingStudy getModel() {
		return trainingstudy;
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

}
