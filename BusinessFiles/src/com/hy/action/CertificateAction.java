package com.hy.action;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tools.zip.ZipOutputStream;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.CertificateDao;
import com.hy.dao.impl.CertificateImpl;
import com.hy.model.Certificate;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.util.DateUtil;
import com.hy.util.FileUtil;
import com.opensymphony.xwork2.ModelDriven;

public class CertificateAction extends BaseAction implements  ModelDriven<Certificate>{

	/**
	 * @Fields serialVersionUID:TODO
	 */
	public static String rootPath="/attachments_Img/Certificate/";
	private static String zipPath="/ziptemp/Certificate/";
	private static final long serialVersionUID = 1L;
	private Certificate certificate  = new Certificate();
	private List<Certificate> cms=new ArrayList<Certificate>();
	private List<Integer> ids = new ArrayList<Integer>();
	private CertificateDao dao = new CertificateImpl();
	private String cond="";
	private InputStream inputStream;
	private String fileDownloadName;
	@Action(value = "getCMlist", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String getList() {
		Teacher t= (Teacher) session.get("loginteacher");
		certificate.setTeacherId(t.getId());
		//certificate.setTeacherName(t.getName());
		StringBuilder hql=new StringBuilder("select distinct p FROM Certificate as p,Teacher as t where p.teacherId=t.id");
		hql.append(" and  p.teacherId='"+t.getId()+"'");
		cms=dao.getList(certificate,hql.toString());
		if(!"".equals(cond.trim())){
			hql.append(" and ( t.name like'%" + cond + "%' or p.teacherId like '%" + cond
					+ "%' or p.name like '%" + cond + "%'");
		}
		//jsoMap.put("total", 10);
		jsoMap.put("rows", cms);
		return "success";
	}
	@Action(value = "addCM", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String add() {
		Teacher t= (Teacher) session.get("loginteacher");
		certificate.setTeacherId(t.getId());
		//certificate.setTeacherName(t.getName());
		jsoMap.put("msg", dao.add(certificate));
		return "success";
	}
	@Action(value = "updateCM", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String update() {
		Teacher t= (Teacher) session.get("loginteacher");
		certificate.setTeacherId(t.getId());
		//certificate.setTeacherName(t.getName());
		/*deleteFile(certificate.getId());*/
		Certificate cerTemp=(Certificate) dao.getByIdAndEvict(certificate.getId());
		certificate.setFileName(cerTemp.getFileName());
		 if(certificate.getFile()!=null&&certificate.getFile().size()>0){
			 deleteFiles(rootPath,cerTemp.getFileName(),certificate.getId());
		    System.out.println("删除[检索页]文件");
		 }
		boolean result=dao.update(certificate);

		/*for(String s:deletTemp)
			System.out.println("要删除:"+s);*/
		
		jsoMap.put("msg",result);
		/*if(result==true&&deletTemp!=null&&deletTemp.size()>0){
			deleteFilePath(rootPath, deletTemp);
		}*/
		return "success";
	}
	
	/*private void deleteFile(Integer id){
		String FILEPATH = ServletActionContext.getServletContext().getRealPath("/attachments_Img/Certificate");
		Certificate certificateTemp=dao.getCertificateById(id);
		dao.evict(certificateTemp);
		String fileNames=certificateTemp.getFileName();
		if(fileNames!=null){
			String[] diskFiles=fileNames.split(";");
			for(String fileName:diskFiles){
				FileUtil.deleteDiskFile(FILEPATH+"/"+fileName);
			}
		}
		
	}*/
	
	@Action(value = "deleteCM", results = { @Result(name = "success", type = "json",params = {"root","jsoMap"}) })
	public String delete() {
		/*if(ids.size()>0){
			List<String> deletTemp=null;
			List<String> delets=new ArrayList<String>(0);
		for(int i=0;i<ids.size();i++){
			Certificate cerTemp=(Certificate) dao.getByIdAndEvict(ids.get(i));
			deletTemp=pathSplit(cerTemp.getFileName(),null);
		    if(deletTemp!=null&&deletTemp.size()>0)
		    	delets.addAll(deletTemp);
		}*/
		if(ids.size()>0){
		int count=dao.delete(ids);
		if(count>0)
			deleteFoldByIds(rootPath, ids);
	System.out.println("证书删除成功");	
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共删除"+count+"条数据!");
		return "success";}
		else{
			System.out.println("没有id");
			jsoMap.put("success",false);
			jsoMap.put("msg", "没有传入id参数");
			return "success";
		}
	}
	
	@Action(value = "exportCertificateManage", results = { @Result(name = "success", 
	type = "stream", params = {
	"contentType", "application/x-tar;", 
	"inputName", "inputStream", "contentDisposition",
	"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
	public String export() {
		//System.out.println(cond);
		String name = "证书" + DateUtil.dateToYMDhms(new Date()) + ".zip";
		try {
			this.setFileDownloadName(new String(name.getBytes(), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<exportModel> models=dao.getByIdsDeal(ids);
		if(models!=null&&models.size()>0){
			inputStream=export(zipPath, rootPath, name,  models);
		}
		
		return "success";
	}
	
	
	/*@Action(value = "dowloadTemplate", results = { @Result(name = "success", 
			type = "stream", params = {
			"contentType", "application/octet-stream;", 
			"inputName", "inputStream", "contentDisposition",
			"attachment;filename=\"${fileDownloadName}\"", "bufferSize", "40960" }) })
			public String export1() {
		           String name ="";
		           String file=null;
		         if("TeachingWorkload".equals(fileDownloadName)){
		        	name="理论教学上传模板.xls"; 
		        	file="TeachingWorkload.xls";
		         }
		         if("PaperTeaching".equals(fileDownloadName)){
			        	name="指导论文上传模板.xls"; 
			        	file="PaperTeaching.xls";
			         }
		         if("PracticeTeaching".equals(fileDownloadName)){
			        	name="实践教学上传模板.xls"; 
			        	file="PracticeTeaching.xls";
			         }
		         try {
					this.setFileDownloadName(new String(name.getBytes(), "ISO-8859-1"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         if(file!=null){
		        	String filePath=ServletActionContext.getServletContext().getRealPath("/WEB-INF/classes/template/"+file);
		        	File FILE=new File(filePath);
		        	try {
						inputStream=new FileInputStream(FILE);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         }
				return "success";
			}
	*/
	
	@Override
	public Certificate getModel() {
		return certificate;
	}

	public Certificate getCm() {
		return certificate;
	}

	public void setCm(Certificate cm) {
		this.certificate = cm;
	}

	public List<Certificate> getCms() {
		return cms;
	}

	public void setCms(List<Certificate> cms) {
		this.cms = cms;
	}

	public CertificateDao getCmdao() {
		return dao;
	}

	public void setCmdao(CertificateDao cmdao) {
		this.dao = cmdao;
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
	public String getCond() {
		return cond;
	}
	public void setCond(String cond) {
		this.cond = cond;
	}
	
	
	
}
