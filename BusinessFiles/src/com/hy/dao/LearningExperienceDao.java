package com.hy.dao;

import com.hy.model.LearningExperience;
import com.hy.util.PageUtil;


public interface LearningExperienceDao extends baseDao2{
	
	public PageUtil<Object> getList(PageUtil<Object> pr,String hql);
	public boolean add(LearningExperience le);
	public boolean update(LearningExperience le);
	public boolean delete(LearningExperience le);
}
