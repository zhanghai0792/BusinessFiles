package com.hy.dao;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.hy.model.BaseModel;
import com.hy.model.Certificate;
import com.hy.model.PracticeTeaching;
import com.hy.model.Teacher;
import com.hy.model.exportModel;

public interface TeacherDao extends baseDao2{
	public BaseModel login(Teacher teacher);
	public boolean update(Teacher teacher);
	public boolean delete(Teacher teacher);
	public int deletes(List<String> ids);
	public boolean add(Teacher teacher);
	public List<Object> getList(Teacher teacher,String hql);
	public Teacher getoldPwd(Teacher teacher);
	public boolean updatePwd(Teacher teacher, String newPwd);
	public List<Teacher> getByIdStrings(List<String> ids);
	public List<Certificate> getCertificateByTeacherId(List<String> teacherId);
	public List<Map<String, String>> importData(File file,String[] excelTemp) throws Exception;
	public Object getByIdAndEvict(String id); 
	public int insertBatch(final List<Map<String, String>> list) throws ParseException; 
	public int resetTeachersPwd(List<String> ids, String newPwd);
}
