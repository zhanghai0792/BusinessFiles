package com.hy.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;

import com.hy.dao.CertificateDao;
import com.hy.model.Certificate;
import com.hy.service.UploadFilesService;
import com.hy.service.impl.UploadFilesServiceImpl;
import com.hy.util.FileUtil;
import com.hy.util.HibernateSessionFactory;
import com.hy.util.PY;
import com.hy.util.ZipUtil;

@SuppressWarnings("rawtypes")
public class CertificateImpl extends  BaseDaoImpl implements CertificateDao {
	
	
	public CertificateImpl() {
		super();
		super.claz=Certificate.class;
	}
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
     	private UploadFilesService uploadFilesService=new UploadFilesServiceImpl();
	@SuppressWarnings("unchecked")
	@Override
	public List<Certificate> getList(Certificate c,String hql) {
		List<Certificate> list=super.search(hql);
		
		//" limit " + (p - 1) * r + "," + r
		return list;
	}

	public Certificate getCertificateById(Integer id){
		return (Certificate) super.getById(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(Certificate pr) {
		//int maxId=getMaxId("t_certificate");bpp;eam
		boolean flag=false;
		/*
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
			//	newName=FileUtil.getNewFileName(pr.getTeacherId()+pr.getName()+i, pr.getFileFileName().get(i));
			newName=FileUtil.getNewFileName(pr.getTeacherId()+"-"+PY.getPinYinHeadChar(pr.getType()).toUpperCase()+"-"+i, pr.getFileFileName().get(i));
				
				if(i<pr.getFileFileName().size()-1){
					if(newName!=null){
						tempName+=newName+",";
					}else{
						return false;//包含错误的文件类型
					}
				}else{
					tempName+=newName;
				}
				newNamelist.add(newName);
			}
			pr.setFileFileName(newNamelist);
			pr.setFileName(tempName);
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileFileName(),"attachments_Img\\Certificate");
		}
		return super.add(pr);*/
		if(super.add(pr)){
			flag=update(pr);
		}
		return flag;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(Certificate pr) {
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				//newName=FileUtil.getNewFileName(pr.getTeacherId()+pr.getName()+i, pr.getFileFileName().get(i));
				newName=FileUtil.getNewFileName(pr.getTeacherId()+"-"+PY.getPinYinHeadChar(pr.getType()).toUpperCase()+"-"+i, pr.getFileFileName().get(i));
				
				if(i<pr.getFileFileName().size()-1){
					if(newName!=null){
						tempName+=newName+",";
					}else{
						return false;//包含错误的文件类型
					}
				}else{
					tempName+=newName;
				}
				newNamelist.add(newName);
			}
			pr.setFileFileName(newNamelist);
			pr.setFileName(tempName);
			String message=uploadFilesService.uploadFile(pr.getFile(),pr.getFileFileName(),"attachments_Img\\Certificate\\"+pr.getId());
		     System.out.println(message);
		}
		return super.update(pr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delete(List<Integer> ids) {
		//return super.delete(le);
		StringBuilder sql=new StringBuilder("delete  from t_certificate  where id =?");
		return  super.updateORdeleteBatchByid(ids, sql.toString());
	}
	//根据id主键查询到指定对象
	public Certificate getIdObject(String id){
		Certificate t=null;
		List l=getSession().createSQLQuery("select * from t_certificate where id ='"+id+"'").addEntity(Certificate.class).list();
		if(l.size()>0){
			t= (Certificate) l.get(0);
		}
		return t;
	}
	public InputStream exportData(String [] idss) {
		String path_temp =ServletActionContext.getServletContext().getRealPath("/temp");
		String zip_temp =ServletActionContext.getServletContext().getRealPath("/ziptemp/Certificate/");//将压缩文件暂存到临时目录
		String photopath = ServletActionContext.getServletContext().getRealPath("/attachments_Img/Certificate/");
		String zippath_zip = String.format("%s\\%s.zip", zip_temp,"temp");//重复生成则覆盖
		//文件复制
		for(int i=0;i<idss.length;i++){
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

	
	@Override
	public void evict(Certificate c) {
		super.evit(c);
		
	}
	
/*	public Session getSession() {
		return HibernateSessionFactory.getSession();
}*/
}
