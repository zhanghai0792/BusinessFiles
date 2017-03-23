package com.hy.dao;

import java.util.List;

import com.hy.model.WorkingExperience;

public interface WorkingExperienceDao extends baseDao2{
	public List<WorkingExperience> getList(WorkingExperience we, String hql);
	public boolean add(WorkingExperience le);

	public boolean update(WorkingExperience le);

	public boolean delete(WorkingExperience le);
}
