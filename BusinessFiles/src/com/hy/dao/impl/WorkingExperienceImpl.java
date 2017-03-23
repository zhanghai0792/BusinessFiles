package com.hy.dao.impl;

import java.util.List;

import com.hy.dao.WorkingExperienceDao;
import com.hy.model.WorkingExperience;

@SuppressWarnings("rawtypes")
public class WorkingExperienceImpl extends  BaseDaoImpl implements WorkingExperienceDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkingExperience> getList(WorkingExperience we,String hql) {
		List<WorkingExperience> list=super.search(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(WorkingExperience we) {
		return super.add(we);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(WorkingExperience we) {
		return super.update(we);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean delete(WorkingExperience we) {
		return super.delete(we);
	}

	public WorkingExperienceImpl() {
		super();
		claz=WorkingExperience.class;
	}

}
