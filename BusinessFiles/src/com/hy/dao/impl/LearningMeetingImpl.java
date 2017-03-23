package com.hy.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.dao.LearningMeetingDao;
import com.hy.model.LearningMeeting;
import com.hy.model.Teacher;
import com.hy.util.DateUtil;

@SuppressWarnings("rawtypes")
public class LearningMeetingImpl extends  BaseDaoImpl implements LearningMeetingDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(LearningMeeting l,String hql) {
		List<Object> resultList=super.searchMoreTable(hql);
		List<Object> listobj=new ArrayList<Object>();
		for (int i = 0; i < resultList.size(); i++) { 
			Map<String,Object> map=new HashMap<String, Object>();
		    Object[] obj = (Object[])resultList.get(i);
		    LearningMeeting p=(LearningMeeting)obj[0];
		    Teacher t=(Teacher)obj[1];
		    map.put("name", p.getName());
		    map.put("major", p.getMajor());
		    map.put("organization", p.getOrganization());
		    map.put("time", DateUtil.dateToYMD(p.getTime()));
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
	public boolean add(LearningMeeting learningmeeting) {
		return super.add(learningmeeting);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(LearningMeeting learningmeeting) {
		return super.update(learningmeeting);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean delete(LearningMeeting learningmeeting) {
		return super.delete(learningmeeting);
	}

	public LearningMeetingImpl() {
		super();
		claz=LearningMeeting.class;
	}

}
