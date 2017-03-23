package com.hy.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.TeacherDao;
import com.hy.dao.impl.TeacherDaoImpl;
import com.hy.model.BaseModel;
import com.hy.model.Certificate;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.Convert;
import com.hy.util.DateUtil;
import com.hy.util.PY;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class TeacherAction  extends BaseAction implements ModelDriven<Teacher>{
	private static String[] excelTemp={"id","name","sex","cardId","phone","political","school","major","finalEducat","finalEducatDate","finalDegree","finalDegreeDate","professTitle","professDate","employTitle","employDate","dept","导入职工信息"};
	private String rootPath="attachments_Img\\Teacher\\photo";
	private Teacher teacher=new Teacher();
	@SuppressWarnings("unused")
	private List<Teacher> teachers = new ArrayList<Teacher>();
	private String message;
	private TeacherDao dao = new TeacherDaoImpl();
	private BaseModel baseModel = new BaseModel();// 消息状态变量定义
    private String realInfo;
    private File file;//照片
   	private String fileName;//文件上传时的文件名
   	private String fileContentType;
    
	private String condText="";//输入框的值
	private String condCom="";//下拉框的条件
	private String valCom="";//下拉框的值
	private String condDate="";//日期框的条件
	private String DateStart="";//日期框的条件
	private String DateEnd="";//日期框的条件
	private String isPage="0";
	private String tid="";
	private static String zipPath="/ziptemp/Teacher/";
	private InputStream inputStream;
	private String fileDownloadName;
    private String condition;
private List<String> ids = new ArrayList<String>();
	
	
	@Action(value = "login", results = {
				@Result(name = "Teacher", type = "redirect", location = "Jsps/Teacher/BaseInfo.jsp"),
				@Result(name = "TeachingSecretary", type = "redirect", location = "Jsps/TeachingSecretary/TheoryTeaching.jsp"),
				@Result(name = "AcademicSecretary", type = "redirect", location = "Jsps/AcademicSecretary/PaperResearch.jsp"),
				@Result(name = "Admin", type = "redirect", location = "Jsps/Admin/Admin.jsp"),
				@Result(name = "error" ,location = "/login.jsp") })
	public String login() {
		
		baseModel=dao.login(teacher);
		if(!baseModel.isResultFlag()){
			//addActionError("用户名或密码错误");
			this.clearErrorsAndMessages();//清空错误信息
			message="用户名或密码错误";
			//this.addFieldError("userinfo", "用户名或密码错误");
    		return "error";
    	}else{
    		//session.put("login", baseModel);
    		Teacher t=(Teacher) baseModel.getData();
    		String type=t.getType();
    		session.put("loginteacher",t);
    		if("1".equals(type)){
    			return "Teacher";
    		}else if("2".equals(type)){
    			return "TeachingSecretary";
    		}else if("3".equals(type)){
    			return "AcademicSecretary";
    		}else if("0".equals(type)){
    			return "Admin";
    		}
    		return "error";
    	}
}
	/* type = "dispatcher",*/
	@Action(value = "logout", results = {
			@Result(name = "success",type = "redirect",location = "/login.jsp"),
			@Result(name = "error" ,location = "/login.jsp") })
public String loginout() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session1 = request.getSession(true);
		if(!session1.isNew()){
			session.clear();
			session1.invalidate();
			super.execute();
		}
		
	return "success";

}

	
//@Interceptors(value = { null })
//params = {"modelDriven.refreshModelBeforeResult","true"}
//@InterceptorRef(value = "paramsPrepareParamsStack", params = {"modelDriven.refreshModelBeforeResult", "true" })
@Action(value = "updateTeacher", results = {
		@Result(name = "success",  type = "json",params = {"root","jsoMap"}),
		@Result(name = "error" ,type = "json")})
public String updateTeacher() {
	
	//baseModel=dao.update(teacher);
	//Teacher t= (Teacher) session.get("loginteacher");
	//http://www.2cto.com/kf/201409/331282.html
	String isupdate=servletrequest.getParameter("isupdate");
	session.put("loginteacher", teacher);
	 if(!"-1".equals(isupdate)){
		session.put("loginteacher", teacher);
	 }
	//System.out.println(teacher);
	 if(!PY.getPinYinHeadChar(teacher.getName()).equals(teacher.getPy()))
	    teacher.setPy(PY.getPinYinHeadChar(teacher.getName()));
	  Teacher t=(Teacher) dao.getByIdAndEvict(teacher.getId());
	    teacher.setPassword(t.getPassword());
	   teacher.setPhotoName(t.getPhotoName());
	   if(teacher.getBirthDate()==null||"".equals(teacher.getBirthDate())){
		   teacher.setBirthDate(t.getBirthDate());
	   }
	   if(teacher.getPy()==null||"".equals(teacher.getPy())){
		   teacher.setPy(t.getPy());
	   }
	   if(teacher.getNation()==null||"".equals(teacher.getNation())){
		   teacher.setNation(t.getNation());
	   }
	   if(teacher.getDept()==null||"".equals(teacher.getDept())){
		   teacher.setDept(t.getDept());
	   }
	   if(teacher.getGrade()==null||"".equals(teacher.getGrade())){
		   teacher.setGrade(t.getGrade());
	   }
	   if(teacher.getIsDelete()!=t.getIsDelete()){
		   teacher.setIsDelete(t.getIsDelete());
	   }
	   
	   if(teacher.getType()==null||"".equals(teacher.getType()))
		   teacher.setType(t.getType());
	   if(teacher.getPhoto()!=null){
		deleteFiles(rootPath, t.getPhotoName());	  
	   }
	 boolean success=dao.update(teacher);
	 jsoMap.put("success",success );
	 if(success)
	jsoMap.put("msg", "更新成功");
	 else
		 jsoMap.put("msg", "更新失败"); 
	//个人和管理员返回的信息不相同
	return "success";
	
}
@Action(value = "addTeacher", results = {
		@Result(name = "success",  type = "json",params = {"root","jsoMap"}),
		@Result(name = "error" ,type = "json")})
public String add() {
	//teacher.setPassword(CommonUtil.md5("123123"));//默认密码123123
	teacher.setPy(PY.getPinYinHeadChar(teacher.getName()));
	teacher.setPassword("123123");//默认密码123123
	teacher.setPositiontype("专职");
	if(dao.getByIdAndEvict(teacher.getId())!=null){
		jsoMap.put("success",false);
		jsoMap.put("msg", "["+teacher.getId()+"]已经存在不能添加");
		return "success";	
	}
	//teacher.setType("1");//默认为普通教师
	
	boolean success= dao.add(teacher);
			jsoMap.put("success",success );
			 if(success)
			jsoMap.put("msg", "添加成功");
			 else
		       jsoMap.put("msg", "添加失败");
		
	 
	return "success";	
}
@Action(value = "deleteTeacher", results = {
		@Result(name = "success",  type = "json",params = {"root","jsoMap"}),
		@Result(name = "error" ,type = "json")})
public String delete() {
	
	
	int count=dao.deletes(ids);
	 jsoMap.put("success",count>0 );
	 if(count>0)
	jsoMap.put("msg", "删除"+count+"记录");
	 else
      jsoMap.put("msg", "删除失败");
	return "success";
}
/*管理员初始化教师密码，初始化值为123123*/
@Action(value = "resetTeachersPwd", results = {
		@Result(name = "success",  type = "json",params = {"root","jsoMap"}),
		@Result(name = "error" ,type = "json")})
public String adminResetPwd() {
	Teacher user=(Teacher)session.get("loginteacher");
	if(!"0".equals(user.getType())){
		jsoMap.put("success",false);
		 jsoMap.put("msg", "权限不足");
		return "success";
	}
	if(ids!=null&&ids.size()>0){
	int count=dao.resetTeachersPwd(ids,"123123");
	 jsoMap.put("success",count>0 );
	 if(count>0)
	jsoMap.put("msg", "重置"+count+"条记录密码");
	 else
      jsoMap.put("msg", "重置密码失败");
	 }else{
		 jsoMap.put("success",false ); 
		 jsoMap.put("msg", "重置失败，请选择待重置密码的记录");
	 }
	return "success";
}

@Action(value = "getTeacherlist", results = {
		@Result(name = "success",  type = "json",params = {"root","jsoMap"}),
		@Result(name = "error" ,type = "json")})
public String getList(){
	StringBuilder hql=new StringBuilder("FROM Teacher p where isDelete ='0' ");
	if(condition!=null&&!"".equals(condition)){
		
		condition=condition.replaceAll("@", "'");
		hql.append("and "+condition);
	}else{
	
	
	
	if(!"".equals(condText.trim())){
		hql.append(" and ( id like '%"+condText+"%' or name like '%"+condText+"%' or cardId like '"+condText+"')");
	}
	if(!"".equals(condCom.trim())&&!"".equals(valCom.trim())){
		hql.append(" and (  "+condCom+"  like '%"+valCom+"%' )");
	}
	if(!"".equals(condDate.trim())){
		if(!"".equals(DateStart.trim()))
			hql.append(" and  "+ condDate +"    >= '"+DateStart+"' ");
		if(!"".equals(DateEnd.trim()))
			hql.append(" and  "+ condDate +"    <= '"+DateEnd+"' ");
	}
	if(!"".equals(tid.trim())){
		hql.append(" and id ='"+tid+"'");//检查指定工号的用户是否存在的需要
	}
	}
	//hql.append(" order by name asc");
	if(page==0||rows==0){
	
	listobj=dao.getList(teacher,hql.toString());
	if(listobj.size()==0){
		jsoMap.put("msg", false);
	}else{
			jsoMap.put("msg", true);
			//jsoMap.put("total", 10);
			if(realInfo==null||"".equals(realInfo)){
				//非教务人员查询
			Teacher temp=new Teacher();
			temp.setId("U0001");
			temp.setName("非学院人员");
			listobj.add(temp);}
			jsoMap.put("rows", listobj);
	}
	}else{
		
		jsoMap.put("total",dao.getPages(hql));
		jsoMap.put("rows", dao.getPageObject(hql, page,rows));		
	}
	return "success";
	
}
@Action(value = "editPwd", results = {
		@Result(name = "success",  type = "json",params = {"root","jsoMap"}),
		@Result(name = "error" ,type = "json",params = {"root","jsoMap"})})
public String editPwd() {
	String oldPwd=servletrequest.getParameter("oldPwd");
	String newPwd=servletrequest.getParameter("newPwd");
	Teacher t = (Teacher) session.get("loginteacher");
	Teacher t1=dao.getoldPwd(t);
	System.out.println(t1);
	if(t1!=null){
		System.out.println(oldPwd);
		System.out.println(newPwd);
		if(!"".equals(oldPwd.trim())&&oldPwd.equals(t1.getPassword())){
			t1.setPassword(newPwd);
			jsoMap.put("success", dao.update(t1));
			jsoMap.put("msg", "修改成功");
			return "success";
		}else{
			jsoMap.put("success", false);
			jsoMap.put("msg", "原密码错误!");
			return "error";
		}
	}
	jsoMap.put("success", false);
	return "error";
}
 
@Action(value = "exportTeacher", results = { @Result(name = "success", 
type = "stream", params = {
"contentType", "application/x-tar;", 
"inputName", "inputStream", "contentDisposition",
"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
public String export() {
	String name = "教师信息" + DateUtil.dateToYMDhms(new Date()) + ".zip";
	try {
		this.setFileDownloadName(new String(name.getBytes(), "ISO-8859-1"));
	} catch (UnsupportedEncodingException e1) {
		e1.printStackTrace();
	}
	List<Teacher> teachers=dao.getByIdStrings(ids);
	List<Certificate> cers=dao.getCertificateByTeacherId(ids);
	if(teachers!=null&&teachers.size()>0){
		inputStream=exportTeachers(zipPath, name, teachers,cers);
	}
	
	return "success";
}

@Action(value = "importTeacher", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
public String importData() {
	
	int size=0;
	try {
		List<Map<String, String>> listmap=dao.importData(file,excelTemp);
		if(listmap==null||listmap.size()==0){
			jsoMap.put("success",false);
			jsoMap.put("msg","没有读到记录，导入失败");
			return "success";
		}
		 List<String> ids=new ArrayList<String>(0);
		 for(Map<String,String> m:listmap){
			 ids.add(m.get("id"));
		 }
		  List<Teacher> hasTeachers=dao.getByIdStrings(ids);
		 if(hasTeachers!=null&&hasTeachers.size()>0){
			 StringBuffer sb=new StringBuffer();
			 for(Teacher t:hasTeachers)
				 sb.append(t.getId()+";");
			 jsoMap.put("success",false);
				jsoMap.put("msg","导入错误<br>已经存在工号为 [ "+sb.toString()+" ]的记录");
				return "success"; 
		 }
		 size=dao.insertBatch(listmap);
		if(size>0){
			jsoMap.put("success",true);
			jsoMap.put("msg","共 "+size+" 条数据导入成功");
		}else{
			jsoMap.put("success",false);
			jsoMap.put("msg","批量导入失败");
		}
	} catch (Exception e) {
		jsoMap.put("success",false);
		jsoMap.put("msg",e.getMessage());
	}
	
	return "success";
}


		   public Teacher getTeacher() {
		      return teacher;
		   }

		   public void setTeacher(Teacher teacher) {
		      this.teacher = teacher;
		   }


		public String getMessage() {
			return message;
		}


		public void setMessage(String message) {
			this.message = message;
		}

		public BaseModel getBaseModel() {
			return baseModel;
		}

		public void setBaseModel(BaseModel baseModel) {
			this.baseModel = baseModel;
		}
		@Override
		public Teacher getModel() {
			return teacher;
		}
		public String getCondText() {
			return condText;
		}
		public void setCondText(String condText) {
			this.condText = condText;
		}
		public String getCondCom() {
			return condCom;
		}
		public void setCondCom(String condCom) {
			this.condCom = condCom;
		}
		public String getValCom() {
			return valCom;
		}
		public void setValCom(String valCom) {
			this.valCom = valCom;
		}
		public String getCondDate() {
			return condDate;
		}
		public void setCondDate(String condDate) {
			this.condDate = condDate;
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
		public String getTid() {
			return tid;
		}
		public void setTid(String tid) {
			this.tid = tid;
		}
		public String getIsPage() {
			return isPage;
		}
		public void setIsPage(String isPage) {
			this.isPage = isPage;
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
		public List<String> getIds() {
			return ids;
		}
		public void setIds(List<String> ids) {
			this.ids = ids;
		}
		public String getRealInfo() {
			return realInfo;
		}
		public void setRealInfo(String realInfo) {
			this.realInfo = realInfo;
		}
		public File getFile() {
			return file;
		}
		public void setFile(File file) {
			this.file = file;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getFileContentType() {
			return fileContentType;
		}
		public void setFileContentType(String fileContentType) {
			this.fileContentType = fileContentType;
		}
		public String getCondition() {
			return condition;
		}
		public void setCondition(String condition) {
			this.condition = condition;
		}
		


	
}
