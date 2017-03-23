package com.hy.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.hy.dao.OtherWorksDao;
import com.hy.model.OtherWorks;

@SuppressWarnings("rawtypes")
public class OtherWorksImpl extends  BaseDaoImpl implements OtherWorksDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<OtherWorks> getList(OtherWorks mt) {
		List<OtherWorks> list=super.search("FROM OtherWorks o where o.teacherId='"+mt.getTeacherId()+"' order by o.time desc");
		return list;
	}
	
	public List<OtherWorks> getListParams(String teacherId,Date start,Date end){
		String hql="FROM OtherWorks o where o.teacherId=:teacherId and o.time>=:startDate and o.time<=:endDate order by o.time desc";
	    Map<String,Object> map=new HashMap<String,Object>();
	    map.put("teacherId",teacherId);
	    map.put("startDate", start);
	    map.put("endDate", end);
	    return super.searchX(hql, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(OtherWorks otherworks) {
		return super.add(otherworks);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(OtherWorks otherworks) {
		return super.update(otherworks);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean delete(OtherWorks otherworks) {
		return super.delete(otherworks);
	}

	public OtherWorksImpl() {
		super();
		claz=OtherWorks.class;
	}

}
