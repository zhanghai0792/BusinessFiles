package com.hy.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.OtherWorksDao;
import com.hy.dao.impl.OtherWorksImpl;
import com.hy.model.OtherWorks;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.opensymphony.xwork2.ModelDriven;

public class OtherWorksAction extends BaseAction implements  ModelDriven<OtherWorks>{

	
	private static final long serialVersionUID = 1L;
	private OtherWorks otherworks  = new OtherWorks();
	private List<OtherWorks> otherworkss=new ArrayList<OtherWorks>();;
	private OtherWorksDao dao = new OtherWorksImpl();
	private Date DateEnd;
	private Date DateStart;
	private static String zipPath="/ziptemp/OtherWorks/";
	private InputStream inputStream;
	private String fileDownloadName;

private List<Integer> ids = new ArrayList<Integer>();
	
	public Date getDateEnd() {
		return DateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		DateEnd = dateEnd;
	}
	public Date getDateStart() {
		return DateStart;
	}
	public void setDateStart(Date dateStart) {
		DateStart = dateStart;
	}
	@Action(value = "getOtherWorkslist", results = {@Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		
		Teacher t= (Teacher) session.get("loginteacher");
		
		otherworkss=dao.getListParams(t.getId(),DateStart,DateEnd);
		jsoMap.put("total", 10);
		jsoMap.put("rows", otherworkss);
		return "success";
	}
	@Action(value = "addOtherWorks", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String add() {
		Teacher t= (Teacher) session.get("loginteacher");
		otherworks.setTeacherId(t.getId());
		//otherworks.setTeacherName(t.getName());
		jsoMap.put("msg", dao.add(otherworks));
		return "success";
	}
	@Action(value = "updateOtherWorks", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String update() {
		Teacher t= (Teacher) session.get("loginteacher");
		otherworks.setTeacherId(t.getId());
		//otherworks.setTeacherName(t.getName());
		jsoMap.put("msg", dao.update(otherworks));
		return "success";
	}
	@Action(value = "deleteOtherWorks", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String delete() {
		jsoMap.put("msg", dao.delete(otherworks));
		return "success";
	}
	
	@Action(value = "exportOtherWorks", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
			public String export() {
				String name = "其他工作" + DateUtil.dateToYMDhms(new Date()) + ".zip";
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
	
	public OtherWorks getOw() {
		return otherworks;
	}

	public void setOw(OtherWorks ow) {
		this.otherworks = ow;
	}

	public List<OtherWorks> getOws() {
		return otherworkss;
	}

	public void setOws(List<OtherWorks> ows) {
		this.otherworkss = ows;
	}


	@Override
	public OtherWorks getModel() {
		return otherworks;
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
