package com.hy.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.TeachingMaterialDao;
import com.hy.dao.impl.TeachingMaterialImpl;
import com.hy.model.Teacher;
import com.hy.model.TeachingMaterial;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.hy.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;

public class TeachingMaterialAction  extends BaseAction implements  ModelDriven<TeachingMaterial>{

	private static final long serialVersionUID = 1L;
	private TeachingMaterial teachingmaterial  = new TeachingMaterial();
	//private List<TeachingMaterial> teachingmaterials=new ArrayList<TeachingMaterial>();
	private TeachingMaterialDao dao = new TeachingMaterialImpl();
	private List<Integer> ids = new ArrayList<Integer>();
	private String cond="",tid="";
	private InputStream inputStream;
	private String fileDownloadName;
	private static String zipPath="/ziptemp/TeachingMaterial/";
	
	@Action(value = "getTeachingMateriallist", results = {@Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		teachingmaterial.setTeacherId(t.getId());
		StringBuilder hql=new StringBuilder("select p,t FROM TeachingMaterial as p,Teacher as t where p.teacherId=t.id ");
		if(!"2".equals(t.getType())){//教务秘书
			hql.append(" and  p.teacherId='"+t.getId()+"'");
		}
		if ((!"".equals(tid.trim()))&&(!"undefined".equals(tid.trim()))) {// 同一用户,不同界面
			hql.append(" and p.teacherId='" + tid + "'");
		}
		if(!"".equals(cond.trim())){
			//  
			hql.append(" and ( t.name like '%"+cond+"%' or p.teacherId like '%"+cond+"%' or p.courseName like '%"+cond+"%' or p.guideBook like '%"+cond+"%' " +
					"or p.bookNum like '%"+cond+"%' or p.tclass like '%"+cond+"%' )");
		}
		if(teachingmaterial.getTermDate()!=null && !"".equals(teachingmaterial.getTermDate())){
			 String term=teachingmaterial.getTermDate();
			/* if(term.length()==6)
				 term=yearChangeToTerm(term);*/
			 hql.append(" and termDate='"+term+"'");
		}
		
		System.out.println(hql.toString());
		int rows=Integer.parseInt(servletrequest.getParameter("rows"));
		int page=Integer.parseInt(servletrequest.getParameter("page"));
		PageUtil<Object> pr=new PageUtil<Object>();
		pr.setPageNo(page);pr.setPageSize(rows);
		pr= dao.getList(hql.toString(),pr);
		jsoMap.put("total", pr.getRecTotal());
		jsoMap.put("rows", pr.getList());
		return "success";
	}
	@Action(value = "addTeachingMaterial", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String add() {
		jsoMap=new HashMap<String, Object>();
		if(teachingmaterial.getTeacherId()==null||"".equals(teachingmaterial.getTeacherId())){
		Teacher t= (Teacher) session.get("loginteacher");
		teachingmaterial.setTeacherId(t.getId());
		}
		//teachingmaterial.setTeacherName(t.getName());
		/*if(teachingmaterial.getTermDate()!=null&&teachingmaterial.getTermDate().length()==6){
			teachingmaterial.setTermDate(yearChangeToTerm(teachingmaterial.getTermDate()));
		}*/
		jsoMap.put("msg", dao.add(teachingmaterial));
		return "success";
	}
	@Action(value = "updateTeachingMaterial", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String update() {
		Teacher t= (Teacher) session.get("loginteacher");
		if(teachingmaterial.getTeacherId()==null||"".equals(teachingmaterial.getTeacherId())){
			
		teachingmaterial.setTeacherId(t.getId());
		}
		//teachingmaterial.setTeacherName(t.getName());
		/*if(teachingmaterial.getTermDate()!=null&&teachingmaterial.getTermDate().length()==6){
			teachingmaterial.setTermDate(yearChangeToTerm(teachingmaterial.getTermDate()));
		}*/
		jsoMap.put("msg", dao.update(teachingmaterial));
		return "success";
	}
	@Action(value = "deleteTeachingMaterial", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String delete() {
		//jsoMap.put("msg", dao.delete(teachingmaterial));
		ids.add(teachingmaterial.getId());
		int count=dao.delete(ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共删除"+count+"条数据!");
		return "success";
	}
	
	/*@Action(value = "exportTeachingMaterial", results = { @Result(name = "success", type = "stream", params = {
			"contentType", "application/octet-stream;", "inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
	public String exportData() {
		try {
			String name = "教材管理" + DateUtil.dateToYMDhms(new Date()) + ".xls";
			this.setFileDownloadName(new String(name.getBytes(), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Teacher t = (Teacher) session.get("loginteacher");
		teachingmaterial.setTeacherId(t.getId());
		
		StringBuilder hql=new StringBuilder("select p,t FROM TeachingMaterial as p,Teacher as t where p.teacherId=t.id ");
		if(!"2".equals(t.getType())){//教务秘书
			hql.append(" and  p.teacherId='"+t.getId()+"'");
		}
		if (!"".equals(tid.trim())) {// 同一用户,不同界面
			hql.append(" and p.teacherId='" + tid + "'");
		}
		if(!"".equals(cond.trim())){
			//  
			hql.append(" and ( t.name like '%"+cond+"%' or p.teacherId like '%"+cond+"%' or p.courseName like '%"+cond+"%' or p.guideBook like '%"+cond+"%' " +
					"or p.bookNum like '%"+cond+"%' or p.tclass like '%"+cond+"%' )");
		}
		
		inputStream = dao.exportData(teachingmaterial, hql.toString());
		jsoMap.put("success", true);
		jsoMap.put("msg", "共 条数据导出成功");
		return "success";
	}*/
	@Action(value = "exportTeachingMaterial", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
			public String export() {
				String name = "教材使用" + DateUtil.dateToYMDhms(new Date()) + ".zip";
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
	public TeachingMaterial getModel() {
		return teachingmaterial;
	}
	

	public TeachingMaterial getTeachingmaterial() {
		return teachingmaterial;
	}

	public void setTeachingmaterial(TeachingMaterial teachingmaterial) {
		this.teachingmaterial = teachingmaterial;
	}

	public String getCond() {
		return cond;
	}

	public void setCond(String cond) {
		this.cond = cond;
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
