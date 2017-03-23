package com.hy.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.PaperTeachingDao;
import com.hy.dao.impl.PaperTeachingImpl;
import com.hy.model.PaperTeaching;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.hy.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;

public class PaperTeachingAction extends BaseAction implements  ModelDriven<PaperTeaching>{
	private static String[] excelTemp={"teacherId","teacherName","unDeal1","tclassNum","discountClassNum","指导论文"};
	
	private static String zipPath="/ziptemp/PaperTeaching/";
	private static final long serialVersionUID = 1L;
	private PaperTeaching paperteaching  = new PaperTeaching();
	private List<PaperTeaching> paperteachings=new ArrayList<PaperTeaching>();;
	private PaperTeachingDao dao = new PaperTeachingImpl();
	private String cond="";
	private String fileDownloadName;
	private InputStream inputStream;
	private List<Integer> ids = new ArrayList<Integer>();
	
	@Action(value = "getPaperTeachinglist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		PaperTeaching tle  = new PaperTeaching();
		tle.setTeacherId(t.getId());
		StringBuilder hql=new StringBuilder("select p,t FROM PaperTeaching as p,Teacher as t where p.teacherId=t.id");
		if(!"".equals(cond.trim())){
			hql.append(" and ( t.name like'%"+cond+"%' or p.teacherId like '%"+cond+"%'  )");
		}
		if (paperteaching.getTermDate()!=null&&!"".equals(paperteaching.getTermDate())) {
			/*String v=paperteaching.getTermDate();
			String s=v.substring(0, 10);
			String s1=v.substring(10, v.length());
			if("3".equals(s1.trim())){
				String str=s+"1";
				hql.append(" and p.termDate >= '"+str+"' and p.termDate <= '"+v+"'" );
			}else{
				hql.append(" and p.termDate='"+paperteaching.getTermDate()+"'");
			}*/
			hql.append(" and p.termDate='"+paperteaching.getTermDate()+"'");
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
	
	@Action(value = "addPaperTeaching", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String add() {
		jsoMap.put("msg", dao.add(paperteaching));
		return "success";
	}
	@Action(value = "updatePaperTeaching", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String update() {
	
		jsoMap.put("msg", dao.update(paperteaching));
		return "success";
	}
	@Action(value = "deletePaperTeaching", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String delete() {
		jsoMap.put("msg", dao.delete(ids)>0);
		return "success";
	}
	@Action(value = "importPaperTeaching", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String importData() {
		int size=dao.importData(paperteaching,excelTemp);
		if(size>0){
			jsoMap.put("success",true);
			jsoMap.put("msg","共 "+size+" 条数据导入成功");
		}
		return "success";
	}
	@Action(value = "exportPaperTeaching", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
			public String export() {
				String name = "论文指导" + DateUtil.dateToYMDhms(new Date()) + ".zip";
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
	/*@Action(value = "exportPaperTeaching", results = { @Result(name = "success", type = "stream",params = { "contentType",
			"application/octet-stream;", "inputName", "inputStream",
			"contentDisposition","attachment;filename=\"${fileDownloadName}\"", "bufferSize","40960" }) })
	public String exportData() {
			try {
				String name="教学期刊"+DateUtil.dateToYMDhms(new Date())+".xls";
				this.setFileDownloadName(URLEncoder.encode(name, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Teacher t= (Teacher) session.get("loginteacher");
			paperteaching.setTeacherId(t.getId());
			String hql="select p,t FROM PaperTeaching as p,Teacher as t where p.teacherId=t.id";
			if(!"".equals(cond.trim())){
				hql+=" and ( t.name like'%"+cond+"%' or p.teacherId like '%"+cond+"%'  or p.courseName like '%"+cond+"%'  or p.tclass like '%"+cond+"%' )";
			}
			inputStream =dao.exportData(paperteaching,hql);
			jsoMap.put("success",true);
			jsoMap.put("msg","共 条数据导出成功");
			return "success";
	}*/
	
	
	
	@Override
	public PaperTeaching getModel() {
		return paperteaching;
	}

	public PaperTeaching getPaperteaching() {
		return paperteaching;
	}

	public void setPaperteaching(PaperTeaching paperteaching) {
		this.paperteaching = paperteaching;
	}

	public List<PaperTeaching> getPaperteachings() {
		return paperteachings;
	}

	public void setPaperteachings(List<PaperTeaching> paperteachings) {
		this.paperteachings = paperteachings;
	}

	public String getCond() {
		return cond;
	}

	public void setCond(String cond) {
		this.cond = cond;
	}

	public String getFileDownloadName() {
		return fileDownloadName;
	}

	public void setFileDownloadName(String fileDownloadName) {
		this.fileDownloadName = fileDownloadName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	

}
