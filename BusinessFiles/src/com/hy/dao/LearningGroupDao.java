package com.hy.dao;

import java.util.List;

import com.hy.model.LearningGroup;

public interface LearningGroupDao extends baseDao2{
	public List<Object> getList(LearningGroup l,String hql);
	public boolean add(LearningGroup le);
	public boolean update(LearningGroup le);
	public boolean delete(LearningGroup le);
}
