package com.hy.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.PracticeTeachingDao;
import com.hy.dao.impl.PracticeTeachingImpl;
import com.hy.model.PracticeTeaching;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.hy.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;

public class PracticeTeachingAction extends BaseAction implements  ModelDriven<PracticeTeaching>{

	private static String[] excelTemp={"teacherId","teacherName","unDeal1","unDeal2","courseName","courseType","tclass","tclassNum","weekNum","discountClassNum","实践教学"};
	
	private static final long serialVersionUID = 1L;
	private PracticeTeaching practiceteaching  = new PracticeTeaching();
	private List<PracticeTeaching> practiceteachings=new ArrayList<PracticeTeaching>();
	private PracticeTeachingDao dao = new PracticeTeachingImpl();
	private String cond="";
	private static String zipPath="/ziptemp/PracticeTeaching/";
	private InputStream inputStream;
	private String fileDownloadName;

private List<Integer> ids = new ArrayList<Integer>();
	
	

	@Action(value = "getPracticeTeachinglist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		PracticeTeaching tle  = new PracticeTeaching();
		tle.setTeacherId(t.getId());
		StringBuilder hql=new StringBuilder("select p,t FROM PracticeTeaching as p,Teacher as t where p.teacherId=t.id");
		if(!"".equals(cond.trim())){
			hql.append(" and ( t.name like'%"+cond+"%' or p.teacherId like '%"+cond+"%'  or p.courseName like '%"+cond+"%'  or p.tclass like '%"+cond+"%' )");
		}
		if (practiceteaching.getTermDate()!=null&&!"".equals(practiceteaching.getTermDate())) {
			/*String v=practiceteaching.getTermDate();
			String s=v.substring(0, 10);
			String s1=v.substring(10, v.length());
			if("3".equals(s1.trim())){
				String str=s+"1";
				hql.append(" and p.termDate >= '"+str+"' and p.termDate <= '"+v+"'" );
			}else{
				hql.append(" and p.termDate='"+practiceteaching.getTermDate()+"'");
			}*/
			hql.append(" and p.termDate='"+practiceteaching.getTermDate()+"'");
		}
		int rows=Integer.parseInt(servletrequest.getParameter("rows"));
		int page=Integer.parseInt(servletrequest.getParameter("page"));
		PageUtil<Object> pr=new PageUtil<Object>();
		pr.setPageNo(page);pr.setPageSize(rows);
		pr= dao.getList(hql.toString(),pr);
		jsoMap.put("total", pr.getRecTotal());
		jsoMap.put("rows", pr.getList());
		return "success";
	}
	@Action(value = "addPracticeTeaching", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String add() {
		Teacher t= (Teacher) session.get("loginteacher");
		String type=t.getType();
		if("1".equals(t.getType()))
	  practiceteaching.setTeacherName(t.getName());
		jsoMap.put("msg", dao.add(practiceteaching));
		
		return "success";
	}
	@Action(value = "updatePracticeTeaching", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String update() {
		Teacher t= (Teacher) session.get("loginteacher");
		String type=t.getType();
		if("1".equals(t.getType()))
		practiceteaching.setTeacherName(t.getName());
		jsoMap.put("msg", dao.update(practiceteaching));
		return "success";
	}
	@Action(value = "deletePracticeTeaching", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String delete() {
		jsoMap.put("msg", dao.delete(ids)>0);
		return "success";
	}
	@Action(value = "importPracticeTeaching", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String importData() {
		int size=dao.importData(practiceteaching,excelTemp);
		if(size>0){
			jsoMap.put("success",true);
			jsoMap.put("msg","共 "+size+" 条数据导入成功");
		}else{
			jsoMap.put("success",false);
			jsoMap.put("msg","导入失败");
		}
		return "success";
	}
	/*@Action(value = "exportPracticeTeaching", results = { @Result(name = "success", type = "stream",params = { "contentType",
			"application/octet-stream;", "inputName", "inputStream",
			"contentDisposition","attachment;filename=\"${fileDownloadName}\"", "bufferSize","40960" }) })
	public String exportData() {
			try {
				String name="实践教学"+DateUtil.dateToYMDhms(new Date())+".xls";
				this.setFileDownloadName(URLEncoder.encode(name, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Teacher t= (Teacher) session.get("loginteacher");
			practiceteaching.setTeacherId(t.getId());
			String hql="select p,t FROM PracticeTeaching as p,Teacher as t where p.teacherId=t.id";
			if(!"".equals(cond.trim())){
				hql+=" and ( t.name like'%"+cond+"%' or p.teacherId like '%"+cond+"%'  or p.courseName like '%"+cond+"%'  or p.tclass like '%"+cond+"%' )";
			}
			inputStream =dao.exportData(practiceteaching,hql);
			jsoMap.put("success",true);
			jsoMap.put("msg","共 条数据导出成功");
			return "success";
	}*/
	@Action(value = "exportPracticeTeaching", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
			public String export() {
				String name = "实践教学" + DateUtil.dateToYMDhms(new Date()) + ".zip";
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
	
	
	@Override
	public PracticeTeaching getModel() {
		return practiceteaching;
	}

	public PracticeTeaching getPracticeteaching() {
		return practiceteaching;
	}

	public void setPracticeteaching(PracticeTeaching practiceteaching) {
		this.practiceteaching = practiceteaching;
	}

	public List<PracticeTeaching> getPracticeteachings() {
		return practiceteachings;
	}

	public void setPracticeteachings(List<PracticeTeaching> practiceteachings) {
		this.practiceteachings = practiceteachings;
	}

	public String getCond() {
		return cond;
	}

	public void setCond(String cond) {
		this.cond = cond;
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
