package com.hy.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.dao.LearningGroupDao;
import com.hy.model.LearningGroup;
import com.hy.model.Teacher;

@SuppressWarnings("rawtypes")
public class LearningGroupImpl extends  BaseDaoImpl implements LearningGroupDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(LearningGroup l,String hql) {
		List<Object> resultList=super.searchMoreTable(hql);
		List<Object> listobj=new ArrayList<Object>();
		for (int i = 0; i < resultList.size(); i++) { 
			Map<String,Object> map=new HashMap<String, Object>();
		    Object[] obj = (Object[])resultList.get(i);
		    LearningGroup p=(LearningGroup)obj[0];
		    Teacher t=(Teacher)obj[1];
		    map.put("name", p.getName());
		    map.put("major", p.getMajor());
		    map.put("level", p.getLevel());
		    map.put("job", p.getJob());
		    map.put("id", p.getId());
		    map.put("teacherId", p.getTeacherId());
		    map.put("teacherName", t.getName());
		    listobj.add(map);
		}
		return listobj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(LearningGroup le) {
		return super.add(le);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(LearningGroup le) {
		return super.update(le);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean delete(LearningGroup le) {
		return super.delete(le);
	}

	public LearningGroupImpl() {
		super();
		claz=LearningGroup.class;
	}

}
