package com.hy.dao.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.hy.dao.TeacherDao;
import com.hy.model.BaseModel;
import com.hy.model.Certificate;
import com.hy.model.Teacher;
import com.hy.model.exportModel;
import com.hy.service.UploadFilesService;
import com.hy.service.impl.UploadFilesServiceImpl;
import com.hy.util.FileUtil;
import com.hy.util.PY;


@SuppressWarnings("rawtypes")
public class TeacherDaoImpl extends  BaseDaoImpl implements TeacherDao{
	private UploadFilesService uploadFilesService=new UploadFilesServiceImpl();
	@SuppressWarnings("unchecked")
	@Override
	public BaseModel login(Teacher teacher) {
		BaseModel baseModel = new BaseModel();
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", teacher.getId());
		params.put("password", teacher.getPassword());
		List<Teacher> list =super.searchX("FROM Teacher WHERE id=:id AND password = :password",params);
		if(list!=null && list.size()>0){
			//Map<String, Object> map = new HashMap<String, Object>();
			//map.put("teacher", list.get(0));
			baseModel.setData(list.get(0));
			//baseModel.setData(list.get(0));
			baseModel.setResultFlag(true);
			
		}
		return baseModel;
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(Teacher pr) {
		boolean flag=false;
		//上传图片
		String newName="";
		List<File> f=new ArrayList<File>();List<String> s=new ArrayList<String>();
		if(pr.getPhoto()!=null){
				newName=FileUtil.getNewFileName(pr.getId(), pr.getPhotoFileName());
				pr.setPhotoFileName(newName);
				pr.setPhotoName(newName);
				f.add(pr.getPhoto());
				s.add(pr.getPhotoFileName());
				uploadFilesService.uploadFile(f,s,"attachments_Img\\Teacher\\photo\\");
		}
		
		flag=super.update(pr);
		System.out.println(pr);
		return flag;
		
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(Teacher teacher,String hql) {
		List<Object> list=super.searchMoreTable(hql);
		return list;
	}



	@SuppressWarnings("unchecked")
	@Override
	public boolean add(Teacher pr){
		boolean flag=false;String newName="";
		List<File> f=new ArrayList<File>();List<String> s=new ArrayList<String>();
		if(pr.getPhoto()!=null){
				newName=FileUtil.getNewFileName(pr.getId(), pr.getPhotoFileName());
				pr.setPhotoFileName(newName);
				pr.setPhotoName(newName);
				f.add(pr.getPhoto());
				s.add(pr.getPhotoFileName());
      uploadFilesService.uploadFile(f,s,"attachments_Img\\Teacher\\photo");
		}
		
		 flag=super.add(pr);
		return flag;
	}



	@Override
	public boolean delete(Teacher teacher) {
		boolean flag=false;
		String hql="update Teacher set isDelete='1' where id= '"+teacher.getId()+"'";
		int c=super.updateDelete(hql);
		System.out.println(c);
		if(c==1){
			flag=true;
		}
		return flag;
	}



	@Override
	public Teacher getoldPwd(Teacher teacher) {
		StringBuilder hql=new StringBuilder("from Teacher where id='"+teacher.getId()+"'");
		List l=super.search(hql.toString());
		if(l.size()>0)
			return (Teacher)(l.get(0));
		else return null;
	}



	@Override
	public boolean updatePwd(Teacher teacher,String newPwd) {
		boolean flag=false;
		String hql="update Teacher set password='"+newPwd+"' where id= '"+teacher.getId()+"'";
		//int c=super.getSession().c;
		return false;
	}



	public TeacherDaoImpl() {
		super();
		claz=Teacher.class;
	}

	
	@SuppressWarnings("unchecked")
	public List<Teacher> getByIdStrings(List<String> ids){
		String hql="from "+claz.getSimpleName()+"  where id in(:ids)";
		Query query=getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		return query.list();
		
	}



	@Override
	public int deletes(List<String> ids) {
		boolean flag=false;
		String hql="update Teacher set isDelete='1' where id in(:ids)";
		Query query=getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		return query.executeUpdate();
	}



	@Override
	public List<Map<String, String>> importData(File file, String[] excelTemp) throws Exception{
		int size=0;
		try {
			if(file!=null){
				List<Map<String, String>> listmap = FileUtil.analysisExcel(file,excelTemp);
				 return listmap; 
				/*if(listmap==null||listmap.size()==0)return 0;
				 List<String> ids=new ArrayList<String>(0);
				 for(Map<String,String> m:listmap){
					 ids.add(m.get("id"));
				 }
				  List<Teacher> hasTeachers=getByIdStrings(ids);
				 if(hasTeachers!=null&&hasTeachers.size()>0){
					 StringBuffer sb=new StringBuffer();
					 for(Teacher t:hasTeachers)
						 sb.append(t.getId()+";");
					 throw new Exception("[ "+sb.toString()+"]已经存在,不可以添加");
				 }
					 
				size=insertBatch(listmap);
			}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	@SuppressWarnings("deprecation")
	public int insertBatch(final List<Map<String, String>> list) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		Date dateTemp=null;
		int total=0;
		int[] successCount;
		String sql="insert into dpdatabase.tb_manager (id,name,sex,cardId,phone,political,school,major,finalEducat,finalEducatDate,finalDegree,finalDegreeDate,professTitle,professDate,employTitle,employDate,dept,type,password,py,isDelete,positiontype) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'0','专职')";
		//Session s=getSession();
		Connection conn =getSession().connection();
		try {
		
		int size=1;
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		conn.setAutoCommit(false);
		for(int i = 0;i<list.size(); i++) {
			Map<String, String> map=list.get(i);
				stmt.setString(1, map.get("id"));
				stmt.setString(2, map.get("name"));
				if(map.get("sex")!=null&&!"".equals(map.get("sex"))){
					 if("0".equals(map.get("sex"))||"女".equals(map.get("sex")))
						stmt.setBoolean(3,false);
					 else
						 stmt.setBoolean(3,true); 
					}else{
						stmt.setBoolean(3, false);
					}
				//stmt.setString(3, map.get("sex"));
				stmt.setString(4, map.get("cardId"));
				stmt.setString(5, map.get("phone"));
				stmt.setString(6, map.get("political"));
				stmt.setString(7, map.get("school"));
				stmt.setString(8, map.get("major"));
				stmt.setString(9, map.get("finalEducat"));
				if(map.get("finalEducatDate")!=null&&!"".equals(map.get("finalEducatDate"))){
				  dateTemp=sdf.parse(map.get("finalEducatDate").replaceAll("-", "/"));
					stmt.setDate(10, new java.sql.Date(dateTemp.getTime()));
				}else{
					stmt.setDate(10,null);
				}
				stmt.setString(11, map.get("finalDegree"));
				if(map.get("finalDegreeDate")!=null&&!"".equals(map.get("finalDegreeDate"))){
					  dateTemp=sdf.parse(map.get("finalDegreeDate").replaceAll("-", "/"));
						stmt.setDate(12, new java.sql.Date(dateTemp.getTime()));
					}else{
						stmt.setDate(12,null);
					}
				//stmt.setString(12, map.get("finalDegreeDate"));
				stmt.setString(13, map.get("professTitle"));
				if(map.get("professDate")!=null&&!"".equals(map.get("professDate"))){
					  dateTemp=sdf.parse(map.get("professDate").replaceAll("-", "/"));
						stmt.setDate(14, new java.sql.Date(dateTemp.getTime()));
					}else{
						stmt.setDate(14, null);
					}
				//stmt.setString(14, map.get("professDate"));
				stmt.setString(15, map.get("employTitle"));
				if(map.get("employDate")!=null&&!"".equals(map.get("employDate"))){
					  dateTemp=sdf.parse(map.get("employDate").replaceAll("-", "/"));
						stmt.setDate(16, new java.sql.Date(dateTemp.getTime()));
					}else{
						stmt.setDate(16,null);
					}
				//stmt.setString(16, map.get("employDate"));
				stmt.setString(17, map.get("dept"));
				stmt.setString(18, "1");
				stmt.setString(19, "123123");//默认密码
				if(map.get("name")!=null&&!"".equals(map.get("name"))){
					  
						stmt.setString(20, PY.getPinYinHeadChar(map.get("name")));
					}else{
						stmt.setString(20,null);
					}
		    stmt.addBatch();
		    if (i / 100 == size) {
		    	size++;
		    	successCount=stmt.executeBatch();
		        conn.commit();
		        for(int x:successCount)
		        total=total+x;
		    }
		}
		successCount=stmt.executeBatch();
		conn.commit();
		for(int x:successCount)
	        total=total+x;
		//closeSession();
		/*if(s!=null){
			s.close();
		}*/
		
		/*long l2=System.currentTimeMillis();
		System.out.println("导入l2-l1:"+(l2-l1));*/
		
		} catch (SQLException e) {
			e.printStackTrace();
			//closeSession();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return total;
		}
		return total;
	}



	@Override
	public int resetTeachersPwd(List<String> ids, String newPwd) {
	  String updateHql="update "+claz.getName()+" t set password=:newPwd where id in(:ids)";
	   Query query=getSession().createQuery(updateHql);
	   query.setString("newPwd", newPwd);
	   query.setParameterList("ids", ids);
	   
		return query.executeUpdate();
	}



	@Override
	public List<Certificate> getCertificateByTeacherId(List<String> teacherIds) {
		String hql="select new Certificate(c,t) from Certificate c,Teacher t where c.teacherId=t.id and c.teacherId in(:ids)";
		Query query=getSession().createQuery(hql);
		query.setParameterList("ids", teacherIds);
		return query.list();
	}





}
