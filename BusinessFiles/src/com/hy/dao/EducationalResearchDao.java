package com.hy.dao;

import java.util.List;

import com.hy.model.EducationalResearch;

public interface EducationalResearchDao  extends baseDao2{
	public List<Object> getList(EducationalResearch l,String hql);
	public boolean add(EducationalResearch pr);
	public int delete(List<Integer> ids);
	public int review(List<Integer> ids);
	public boolean update(EducationalResearch pr);
	public EducationalResearch getById(Integer id);
}
