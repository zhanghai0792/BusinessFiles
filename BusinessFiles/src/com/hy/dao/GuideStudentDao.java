package com.hy.dao;

import java.util.List;

import com.hy.model.GuideStudent;

public interface GuideStudentDao extends baseDao2{
	public List<Object> getList(GuideStudent t,String hql);
	public boolean add(GuideStudent pr);
	public int delete(List<Integer> ids);
	public boolean update(GuideStudent pr);
	public int review(List<Integer> ids);
	
}
