package com.hy.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.LearningExperienceDao;
import com.hy.dao.impl.LearningExperienceImpl;
import com.hy.model.LearningExperience;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.hy.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;
public class LearningExperienceAction extends BaseAction implements  ModelDriven<LearningExperience>{

	
	private static final long serialVersionUID = 1L;
	private LearningExperience learningexperience  = new LearningExperience();
	private List<LearningExperience> learningexperiences=new ArrayList<LearningExperience>();;
	private LearningExperienceDao dao = new LearningExperienceImpl();
	private static String zipPath="/ziptemp/LearningExperience/";
	private InputStream inputStream;
	private String fileDownloadName;
    private List<Integer> ids = new ArrayList<Integer>();
	
	/**ignoreHierarchy 忽略父类的属性
	 * @return
	 */
	
	//返回json数据类型,一定要存在get,set 方法
	//将list存入是返回JSONArray形式[{"graduatDate":"2016-09-20","id":1,"level":"1","major":"教育技术学","school":"华东师范大学","teacherId":"14181"},{"graduatDate":"2016-09-12","id":2,"level":"1","major":"教育技术学","school":"华东师范大学","teacherId":"admin"}]
	//@Action(value = "getLElist", results = { @Result(name = "success", type = "json",params = {"root","learningexperiences","ignoreHierarchy","false"}) })
	@Action(value = "getLearningExperiencelist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap","ignoreHierarchy","false"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		LearningExperience tle  = new LearningExperience();
		tle.setTeacherId(t.getId());
		StringBuilder hql =new StringBuilder("FROM LearningExperience p  where teacherId = ");
		if (t!=null) {
			hql.append("  '" + t.getId() + "'");
		}
		hql.append(" order by graduatDate desc");
		/*//this.learningexperiences=dao.getList(tle,hql.toString());
		int rows=Integer.parseInt(servletrequest.getParameter("rows"));
		int page=Integer.parseInt(servletrequest.getParameter("page"));
		PageUtil<Object> pr=new PageUtil<Object>();
		pr.setPageNo(page);pr.setPageSize(rows);
		pr= dao.getList(pr,hql.toString());
		jsoMap.put("total", pr.getRecTotal());
		jsoMap.put("rows", pr.getList());
		//jsoMap.put("rows", learningexperiences);
*/		
		if(page==0||rows==0){
			int rows=Integer.parseInt(servletrequest.getParameter("rows"));
			int page=Integer.parseInt(servletrequest.getParameter("page"));
			PageUtil<Object> pr=new PageUtil<Object>();
			pr.setPageNo(page);pr.setPageSize(rows);
			pr= dao.getList(pr,hql.toString());
			jsoMap.put("total", pr.getRecTotal());
			jsoMap.put("rows", pr.getList());
			}
			else{
				jsoMap.put("total",dao.getPages(hql));
				jsoMap.put("rows", dao.getPageObject(hql, page,rows));
			}
		return "success";
	}
	
	@Action(value = "addLE", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String add() {
		Teacher t= (Teacher) session.get("loginteacher");
		learningexperience.setTeacherId(t.getId());
		
		//learningexperience.setTeacherName(t.getName());
		//System.out.println("登录信息"+t.getId());
		jsoMap.put("msg", dao.add(learningexperience));
		return "success";
	}
	@Action(value = "updateLE", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String update() {
		Teacher t= (Teacher) session.get("loginteacher");
		learningexperience.setTeacherId(t.getId());
		//learningexperience.setTeacherName(t.getName());
		jsoMap.put("msg", dao.update(learningexperience));
		return "success";
	}
	@Action(value = "deleteLE", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String delete() {
		jsoMap.put("msg", dao.delete(learningexperience));
		return "success";
	}
	
	@Action(value = "exportLearningExperience", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/x-tar;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
			public String export() {
				String name = "学习经历" + DateUtil.dateToYMDhms(new Date()) + ".zip";
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

	public List<LearningExperience> getLes() {
		return learningexperiences;
	}

	public void setLes(List<LearningExperience> les) {
		this.learningexperiences = les;
	}

	@Override
	public LearningExperience getModel() {
		return learningexperience;
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
