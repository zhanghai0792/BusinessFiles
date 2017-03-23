package com.hy.dao;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.hy.model.TeachingWorkload;
import com.hy.util.PageUtil;

public interface TeachingWorkloadDao extends baseDao2{
	public Map<String, List> getWorksTearm(String workHql, String practiceHql, String paperhql);	
	public PageUtil<Object> getList(String hql,PageUtil<Object> pr);
	public boolean add(TeachingWorkload le);
	public boolean update(TeachingWorkload le);
	public boolean delete(TeachingWorkload le);
	public int delete(List<Integer> ids);
	public int importData(TeachingWorkload le,String[] excelTemp);
	public InputStream exportData(TeachingWorkload teachingworkload,String cond);
}
