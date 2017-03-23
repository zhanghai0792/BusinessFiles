package com.hy.dao;

import java.io.InputStream;
import java.util.List;

import com.hy.model.PracticeTeaching;
import com.hy.model.TeachingWorkload;
import com.hy.util.PageUtil;

public interface PracticeTeachingDao extends baseDao2{
	//public List<PracticeTeaching> getList(PracticeTeaching practiceteaching);
	public boolean add(PracticeTeaching practiceteaching);
	public boolean update(PracticeTeaching practiceteaching);
	public boolean delete(PracticeTeaching practiceteaching);
	public int delete(List<Integer> ids);
	//public List<Object> getList1(PracticeTeaching l);
	public PageUtil<Object> getList(String hql,PageUtil<Object> pr);
	public InputStream exportData(PracticeTeaching pr, String hql);
	public List getByIdsDeal(List<Integer> ids);
	public int importData(PracticeTeaching le,String[] excelTemp);

}
