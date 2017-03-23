package com.hy.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.TeachingWorkloadDao;
import com.hy.dao.impl.TeachingWorkloadImpl;

import com.hy.model.*;

import com.hy.util.DateUtil;
import com.hy.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;

public class TeachingWorkloadAction extends BaseAction implements ModelDriven<TeachingWorkload> {
	private static String[] excelTemp={"teacherId","teacherName","unDeal1","unDeal2","courseId","courseName","courseTo","courseType","tclass","weekClassNum","periodNum","理论教学"};
	
	private static final long serialVersionUID = 1L;

	private TeachingWorkload teachingworkload = new TeachingWorkload();
	private List<TeachingWorkload> teachingworkloads = new ArrayList<TeachingWorkload>();
	private TeachingWorkloadDao dao = new TeachingWorkloadImpl();
	private String cond = "", tid = "";
	private static String zipPath="/ziptemp/TeachingWorkload/";
	private InputStream inputStream;
	private String fileDownloadName;

private List<Integer> ids = new ArrayList<Integer>(0);


	@Action(value = "getTeachingWorkloadlist", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String getList() {
		if("".equals(tid)||tid==null){
		
		Teacher t = (Teacher) session.get("loginteacher");
		teachingworkload.setTeacherId(t.getId());
		StringBuilder hql =new StringBuilder("select p,t FROM TeachingWorkload as p,Teacher as t where p.teacherId=t.id");
		if (!"2".equals(t.getType())) {
			hql.append(" and p.teacherId='" + t.getId() + "'");
		}
		if (!"".equals(tid.trim())) {// 同一用户,不同界面
			hql.append(" and p.teacherId='" + tid + "'");
		}
		if (!"".equals(cond.trim())) {
			hql.append(" and ( t.name like'%" + cond + "%' or p.teacherId like '%" + cond
					+ "%' or p.courseId like '%" + cond + "%' or p.courseName like '%" + cond
					+ "%' or p.courseTo like '%" + cond + "%' or p.tclass like '%" + cond + "%' )");
		}
		if (teachingworkload.getTermDate()!=null&&!"".equals(teachingworkload.getTermDate())) {
			/*String v=teachingworkload.getTermDate();
			if(v.length()==11){
			String s=v.substring(0, 10);
			String s1=v.substring(10, v.length());
			if("3".equals(s1.trim())){
				String str=s+"1";
				hql.append(" and p.termDate >= '"+str+"' and p.termDate <= '"+v+"'" );
			}else{
				hql.append(" and p.termDate='"+teachingworkload.getTermDate()+"'");
			}
			}else if(v.length()==6){
				String x=v.substring(5);
				String year_s=v.substring(0,4);
				int year=Integer.parseInt(year_s);
				int term=Integer.parseInt(x);
				String yearCond="";
				if(term==1){
					yearCond=(year-1)+"-"+year+"-2";
				}else{
					yearCond=year+"-"+(year+1)+"-1";
				}
				hql.append(" and p.termDate='"+yearCond+"'");
			}*/
			hql.append(" and p.termDate='"+teachingworkload.getTermDate()+"'");
			
		}
		int rows=Integer.parseInt(servletrequest.getParameter("rows"));//要查询多少条
		int page=Integer.parseInt(servletrequest.getParameter("page"));//页码
		PageUtil<Object> pr=new PageUtil<Object>();
		pr.setPageNo(page);pr.setPageSize(rows);
		pr= dao.getList(hql.toString(),pr);
		jsoMap.put("total", pr.getRecTotal());
		jsoMap.put("rows", pr.getList());
		return "success";}
		else{
			//普通老师查询界面看工作总量
			//理论教学工作量
			String tearm="";
			
			if (teachingworkload.getTermDate()!=null&&!"".equals(teachingworkload.getTermDate())) {
				tearm=" and p.termDate='"+teachingworkload.getTermDate()+"'";
				
			}
			StringBuffer TeachingWorkloadHQL=new StringBuffer("select new TeachingWorkload(p,t) from TeachingWorkload p,Teacher t where p.teacherId=t.id and t.id='"+tid+"'");
			TeachingWorkloadHQL.append(tearm);
			
			StringBuffer PracticeTeachingHQL=new StringBuffer("select new PracticeTeaching(p,t) from PracticeTeaching p,Teacher t where p.teacherId=t.id and t.id='"+tid+"'");
			PracticeTeachingHQL.append(tearm);
			
			StringBuffer PaperTeachingHQL=new StringBuffer("select new PaperTeaching(p,t) from PaperTeaching p,Teacher t where p.teacherId=t.id and t.id='"+tid+"'");
			PaperTeachingHQL.append(tearm);
			Map<String,List> map=dao.getWorksTearm(TeachingWorkloadHQL.toString(), PracticeTeachingHQL.toString(), PaperTeachingHQL.toString());
			jsoMap.put("success",true);
			jsoMap.put("msg",tid+":"+teachingworkload.getTermDate());
			jsoMap.put("teachings", map.get("teachings"));
			jsoMap.put("practices", map.get("practices"));
			jsoMap.put("papers", map.get("papers"));
		  return "success";	
		}
		
		
		
		
	}

	@Action(value = "addTeachingWorkload", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String add() {
		Teacher t= (Teacher) session.get("loginteacher");
		String type=t.getType();
		if("1".equals(t.getType()))
		teachingworkload.setTeacherName(t.getName());
		jsoMap.put("msg", dao.add(teachingworkload));
		return "success";
	}

	@Action(value = "updateTeachingWorkload", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String update() {
		Teacher t= (Teacher) session.get("loginteacher");
		String type=t.getType();
		if("1".equals(t.getType()))
		teachingworkload.setTeacherName(t.getName());
		
		jsoMap.put("msg", dao.update(teachingworkload));
		return "success";
	}

	@Action(value = "deleteTeachingWorkload", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String delete() {
		System.out.println(ids);
		
		jsoMap.put("msg", dao.delete(ids)>0);
		return "success";
	}

	@Action(value = "importTeachingWorkload", results = {
			@Result(name = "success", type = "json", params = { "root", "jsoMap" }) })
	public String importData() {
		int size = dao.importData(teachingworkload,excelTemp);
		if (size > 0) {
			jsoMap.put("success", true);
			jsoMap.put("msg", "共 " + size + " 条数据导入成功");
		}else{
			jsoMap.put("success", false);
			jsoMap.put("msg", "请选择正确的文件模板!!!");
		}
		return "success";
	}
	@Action(value = "exportTeachingWorkload", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
			public String export() {
				String name = "理论教学" + DateUtil.dateToYMDhms(new Date()) + ".zip";
				try {
					this.setFileDownloadName(new String(name.getBytes(), "ISO-8859-1"));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				//System.out.println(ids.size());
				List<exportModel> models=dao.getByIdsDeal(ids);
				if(models!=null&&models.size()>0){
					inputStream=export(zipPath, null, name,  models);
				}
				
				return "success";
			}

	
	
	
	public TeachingWorkload getTeachingworkload() {
		return teachingworkload;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public void setTeachingworkload(TeachingWorkload teachingworkload) {
		this.teachingworkload = teachingworkload;
	}

	public List<TeachingWorkload> getTeachingworkloads() {
		return teachingworkloads;
	}

	public void setTeachingworkloads(List<TeachingWorkload> teachingworkloads) {
		this.teachingworkloads = teachingworkloads;
	}

	@Override
	public TeachingWorkload getModel() {
		return teachingworkload;
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
	/*
	 * public String getAttachName() { return attachName; } public void
	 * setAttachName(String attachName) throws UnsupportedEncodingException {
	 * this.attachName = new String(attachName.getBytes(),"ISO-8859-1"); }
	 */

	/*
	 * try { this.fileName = new String(fileName.getBytes(),"ISO-8859-1"); }
	 * catch (UnsupportedEncodingException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); }
	 */

	/*
	 * private void workbookInputStream(Workbook workbook, String fileName) {
	 * try { //this.setFileName(URLEncoder.encode(fileName, "UTF-8"));
	 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 * workbook.write(baos); inputStream = new
	 * ByteArrayInputStream(baos.toByteArray()); baos.close(); } catch
	 * (Exception e) { } }
	 */

	public String getFileDownloadName() {
		return fileDownloadName;
	}

	public void setFileDownloadName(String fileDownloadName) {
		this.fileDownloadName = fileDownloadName;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

}
