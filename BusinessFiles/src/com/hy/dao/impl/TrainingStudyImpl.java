package com.hy.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.dao.TrainingStudyDao;
import com.hy.model.Teacher;
import com.hy.model.TrainingStudy;
import com.hy.util.DateUtil;

@SuppressWarnings("rawtypes")
public class TrainingStudyImpl extends  BaseDaoImpl implements TrainingStudyDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(TrainingStudy l,String hql) {
		List<Object> resultList=super.searchMoreTable(hql);
		List<Object> listobj=new ArrayList<Object>();
		for (int i = 0; i < resultList.size(); i++) { 
			Map<String,Object> map=new HashMap<String, Object>();
		    Object[] obj = (Object[])resultList.get(i);
		    TrainingStudy p=(TrainingStudy)obj[0];
		    Teacher t=(Teacher)obj[1];
		    map.put("type", p.getType());
		    map.put("direction", p.getDirection());
		    map.put("startDate", DateUtil.dateToYMD(p.getStartDate()));
		    map.put("totalDays", p.getTotalDays());
		    map.put("address", p.getAddress());
		    map.put("id", p.getId());
		    map.put("teacherId", p.getTeacherId());
		    map.put("teacherName", t.getName());
		    listobj.add(map);
		}
		return listobj;	
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(TrainingStudy trainingstudy) {
		return super.add(trainingstudy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(TrainingStudy trainingstudy) {
		return super.update(trainingstudy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean delete(TrainingStudy trainingstudy) {
		return super.delete(trainingstudy);
	}

	public TrainingStudyImpl() {
		super();
		claz=TrainingStudy.class;
	}

}
