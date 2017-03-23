package com.hy.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tools.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.hy.annotation.classToXLSSheet;
import com.hy.dao.BaseDao;
import com.hy.model.Certificate;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.FileUtil;
import com.hy.util.ZipUtil;
import com.hy.util.fileDeleteThread;
import com.hy.util.myPOIExcelUtil;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("BusinessFiles")//使用convention-plugin插件提供的@Action注解将一个普通java类标注为一个可以处理用户请求的Action
@Namespace("/")//使用convention-plugin插件提供的@Namespace注解为这个Action指定一个命名空间
//@InterceptorRefs( {@InterceptorRef("defaultStack"),@InterceptorRef(value="paramsPrepareParamsStack",params = {"modelDriven.refreshModelBeforeResult", "true" })}) @InterceptorRef(value="paramsPrepareParamsStack",params={"modelDriven.refreshModelBeforeResult","true"})
public class BaseAction extends ActionSupport  implements SessionAware,RequestAware  {
	/**
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, Object> session;//登录
	protected Map<String, Object> request;//利用Map传值,暂时没用到
	protected HttpServletRequest servletrequest;//原生request传值
	protected List<Object> listobj=new ArrayList<Object>();//protected当前类及其子类可直接访问
	protected Map<String, Object> jsoMap=new HashMap<String, Object>();//返回JSONObject的Map
    protected int rows;
    protected int page;
	/**
	 * 
	 * @Description 借助struts2框架的拦截器得到request对象
	 * @param request
	 */
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	
	/**
	 * 
	 * @Description 借助struts2框架的拦截器得到session对象
	 * @param arg0
	 */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	public void setServletRequest(HttpServletRequest servletrequest) {
		this.servletrequest = servletrequest;
	}

	public List<Object> getListobj() {
		return listobj;
	}

	public void setListobj(List<Object> listobj) {
		this.listobj = listobj;
	}

	public Map<String, Object> getJsoMap() {
		return jsoMap;
	}

	public void setJsoMap(Map<String, Object> jsoMap) {
		this.jsoMap = jsoMap;
	}



	

	public HttpServletRequest getServletrequest() {
		return ServletActionContext.getRequest();//返回原生的Request
		//return servletrequest;
	}
	
	public void setServletrequest(HttpServletRequest servletrequest) {
		this.servletrequest = servletrequest;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	

	
	

	/**
	 * 
	 * @Description 注销，释放session
	 * @return
	 * @throws Exception
	 */
	/*@Action(value = "loginOut", results = { @Result(name = "success", type = "json") })
	public String loginOut() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.invalidate();
		return SUCCESS;
	}*/
//rootPath：该类型的文件的根目录
//fileRelativePaths：表示rootPath的相对目录如：/1/1-oooo.jpg
protected void deleteFilePath(String rootPath,List<String> fileRelativePaths){
	String FILEPATH = ServletActionContext.getServletContext().getRealPath(rootPath);
	for(String file:fileRelativePaths)
		if(!file.startsWith("/"))
	FileUtil.deleteDiskFile(FILEPATH+"/"+file);
		else
			FileUtil.deleteDiskFile(FILEPATH+file);
}
protected void deleteFiles(String rootPath,String fileNames,Integer id){
	if(fileNames!=null&&!"".equals(fileNames)){
		String[] fileNS=fileNames.split(",");
		if(fileNS!=null&&fileNS.length>0){
			List<String> deleteFiles=new ArrayList<String>(0);
			for(String s:fileNS){
				deleteFiles.add("/"+id+"/"+s);
			}
			deleteFilePath(rootPath,deleteFiles);
		}
	}
}

protected void deleteFiles(String rootPath,String fileNames){
	if(fileNames!=null&&!"".equals(fileNames)){
		String[] fileNS=fileNames.split(",");
		if(fileNS!=null&&fileNS.length>0){
			List<String> deleteFiles=new ArrayList<String>(0);
			for(String s:fileNS){
				deleteFiles.add("/"+s);
			}
			deleteFilePath(rootPath,deleteFiles);
		}
	}
}
protected void deleteFoldByIds(String rootPath,List<Integer> ids){
	for(Integer id:ids)
		deleteIdFold(rootPath, id);
}


protected void deleteIdFold(String rootPath,Integer id){
	if(id!=null){
		String FILEPATH = ServletActionContext.getServletContext().getRealPath(rootPath);
		FILEPATH=FILEPATH+"/"+id;
		File fold=new File(FILEPATH);
		if(fold.exists()&&fold.isDirectory()){
			File[] files=fold.listFiles();
			if(files!=null && files.length>0){
				for(File f:files)
					f.delete();
			}
			fold.delete();
		}
	}
}


//传入的id如果为空，这返回的路径中不含id信息，否则添加id信息
protected List<String> copyNotNullUploadFiles(List<String> files,String oldFilePaths,Integer id){
	if(files!=null&&files.size()>0){
		return pathSplit(oldFilePaths, id);	
	}
	//System.out.println("删除记录为空");
	return null;
}

//传入的id如果为空，这返回的路径中不含id信息，否则添加id信息
protected List<String> copyNotNullUploadFiles(String newFilePath,String oldFilePaths,Integer id){
	if(newFilePath!=null && !newFilePath.equals(oldFilePaths)){
		return pathSplit(oldFilePaths, id);	
	}
	System.out.println("删除记录为空");
	return null;
}


public List<String> pathSplit(String oldFilePaths,Integer id){
	if(oldFilePaths!=null&&!"".equals(oldFilePaths)){
		String[] paths=oldFilePaths.split(",");
		  if(paths!=null&&paths.length>0){
			  List<String> r_paths=new ArrayList<String>(0);
			   for(String p:paths){
				   String dep=(id==null? ("/"+p): ("/"+id+"/"+p));
				   System.out.println(dep);
			    r_paths.add(dep);
			    }
			   return r_paths;
		  }
		}
	return null;
}
//将2017-1的学期格式转换为 2016-2017-2
//2017-2 转换为2017-2018-1
protected String yearChangeToTerm(String v){
	if(v!=null&&v.length()==6){
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
	return yearCond;}
	else{
		return null;
	}
}	



//将 2016-2017-2转换为2017-1
//2017-2018-1转换为2017-2
	public static String termChangeToYear(String v){
		if(v==null||v.length()!=11){
			return null;
		}else{
			String x=v.substring(10);
			String year_s=v.substring(0,4);
			int year=Integer.parseInt(year_s);
			int term=Integer.parseInt(x);
			if(term==1)
				return year+"-2";
			else
				return (year+1)+"-1";
		}
}

	
	
	/*public InputStream exportData(String photopath,BaseDao dao,List<Integer> idss) {
		String path_temp =ServletActionContext.getServletContext().getRealPath("/temp");
		String zip_temp =ServletActionContext.getServletContext().getRealPath("/ziptemp");//将压缩文件暂存到临时目录
	//	String photopath = ServletActionContext.getServletContext().getRealPath("/attachments_Img/Certificate/");
		String zippath_zip = String.format("%s\\%s.zip", zip_temp,"temp");//重复生成则覆盖
		//文件复制
		for(int i=0;i<idss.size();i++){
			//System.out.println(idss[i]);
			Certificate pr=getIdObject(idss[i]);
			//System.out.println(pr.getFileName());
			//String teacherId=pr.getTeacherId();
			//String Name=pr.getName();
			String fileName=pr.getFileName();
			if(fileName!=null&&!"".equals(fileName.trim())){
				String [] fm=fileName.split(",");
				for(int k=0;k<fm.length;k++){
					File fmt=new File(photopath+"\\"+idss[i]+"\\"+fm[k]);
					//System.out.println(photopath+"\\"+fm[k]);
					if(fmt.exists()){
						//System.out.println(path_temp+"/"+fm[k]);
						//File destf=new File(path_temp+"/"+fm[k]);if(!destf.exists()) destf.mkdirs();
						//System.out.println(photopath+"\\"+fm[k]);
						//System.out.println(path_temp+"\\"+fm[k]);
						FileUtil.copyFile(photopath+"\\"+idss[i]+"\\"+fm[k],path_temp+"\\"+idss[i]+"\\"+fm[k]);
					}
				}
			}
		}
		
		try {
			ZipUtil.zip(path_temp, zippath_zip);
			File fileLoad = new File(zippath_zip);//
			FileInputStream in = new FileInputStream(fileLoad);//转成输入流返回
			//fileLoad.deleteOnExit();//删除产生的临时文件
			//xlsfile.deleteOnExit();
			return in;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
*/
	
	
	
public boolean copyFiles(String photopath,Integer id,String fileItems){
	boolean flag=false;
	String path_temp =ServletActionContext.getServletContext().getRealPath("/temp");
	/*String zip_temp =ServletActionContext.getServletContext().getRealPath("/ziptemp");//将压缩文件暂存到临时目录
	String zippath_zip = String.format("%s\\%s.zip", zip_temp,("temp-"+(new Date()).getTime()));//重复生成则覆盖
	*/
	if(fileItems!=null&&!"".equals(fileItems)){
	String [] fm=fileItems.split(",");
	 if(fm!=null&&fm.length>0){
		for(int k=0;k<fm.length;k++){
				File fmt=new File(photopath+"\\"+id+"\\"+fm[k]);
				if(fmt.exists()){
					FileUtil.copyFile(photopath+"\\"+id+"\\"+fm[k],path_temp+"\\"+id+"\\"+fm[k]);
				}
				}
		return true;
	 }
	}
	return flag;
}
	
public InputStream zip(String path_temp,String zippath_zip){
	try {
		ZipUtil.zip(path_temp, zippath_zip);
		File fileLoad = new File(zippath_zip);//
		FileInputStream in = new FileInputStream(fileLoad);//转成输入流返回
		return in;
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;
}	


public void zip(ZipOutputStream zip,String rootPath,List<Integer> ids){
	String path="";
	String rootPath_a=ServletActionContext.getServletContext().getRealPath(rootPath);
  for(Integer id:ids){
	  try {
		ZipUtil.zipFileOrDirectory(zip,new File(rootPath_a+"/"+id),"");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}

public void zipModel(ZipOutputStream zip,String rootPath,List<exportModel> models){
	String path="";
	String rootPath_a=ServletActionContext.getServletContext().getRealPath(rootPath);
   int index=0;
	for(exportModel model:models){
		 index++;
	  try {
		ZipUtil.zipFileOrDirectory(zip,new File(rootPath_a+"/"+model.getId()),index+"-"+model.getFoldName()+"-"+model.getId());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}

public void zipTeacherCertModel(ZipOutputStream zip,String rootPath,List<Certificate> models){
	String path="";
	String rootPath_a=ServletActionContext.getServletContext().getRealPath(rootPath);
   int index=0;
	for(Certificate model:models){
		 index++;
	  try {
		ZipUtil.zipFileOrDirectory(zip,new File(rootPath_a+"/"+model.getId()),model.getTeacherName()+"["+model.getTeacherId()+"]/"+model.getFoldName()+"_"+model.getId());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}



public InputStream export(String zipPath,String rootPath,String name,List<exportModel> models){
	String zip_temp =ServletActionContext.getServletContext().getRealPath(zipPath);
	File file=new File(zip_temp);
	if(!file.exists())
		file.mkdirs();
	File zip_tem=new File(zip_temp+"/"+name);
	ZipOutputStream zipOut=null;
	long times=0;
	if(models!=null)
		times=models.size();
	try {
		zipOut=new ZipOutputStream(new FileOutputStream(zip_tem));
		zipOut.setEncoding(System.getProperty("sun.jnu.encoding"));
		zipExcel(zipOut,models);
		if(rootPath!=null)
		zipModel(zipOut, rootPath, models);
	 
	 
	   return new FileInputStream(zip_tem);
	   
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		if(zipOut!=null)
			try {
				zipOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		  fileDeleteThread.delete(zip_tem,times);
	}
	return null;
}

public InputStream exportTeachers(String zipPath,String name,List<Teacher> models,List<Certificate> cers){
	String zip_temp =ServletActionContext.getServletContext().getRealPath(zipPath);
	File file=new File(zip_temp);
	if(!file.exists())
		file.mkdirs();
	File zip_tem=new File(zip_temp+"/"+name);
	ZipOutputStream zipOut=null;
	try {
		zipOut=new ZipOutputStream(new FileOutputStream(zip_tem));
		zipOut.setEncoding(System.getProperty("sun.jnu.encoding"));
		zipExcel(zipOut,models);//导出教师的基本信息
		//导出教师的所有证件信息
		zipTeacherCertModel(zipOut, CertificateAction.rootPath, cers);
	   return new FileInputStream(zip_tem);
	   
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		if(zipOut!=null)
			try {
				zipOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		  fileDeleteThread.delete(zip_tem);
	}
	return null;
}


private void zipExcel(ZipOutputStream zipOut, List models) {
	if(models==null||models.size()==0)
		return;
	String title=classToXLSSheet.getSheetTitle(models);
	List<String[]> sheetContents=classToXLSSheet.getSheetContent(models);
	if(sheetContents!=null&&sheetContents.size()>0){
		ZipUtil.zipAddExcel(zipOut, title);
		try {
			myPOIExcelUtil.export(zipOut, title, sheetContents);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("压缩excel出错");
		}
	}
	
	
}

public int getRows() {
	return rows;
}

public void setRows(int rows) {
	this.rows = rows;
}

public int getPage() {
	return page;
}

public void setPage(int page) {
	this.page = page;
}





}
