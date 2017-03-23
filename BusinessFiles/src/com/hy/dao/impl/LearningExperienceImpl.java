package com.hy.dao.impl;

import com.hy.dao.LearningExperienceDao;
import com.hy.model.LearningExperience;
import com.hy.util.PageUtil;

@SuppressWarnings("rawtypes")
public class LearningExperienceImpl extends  BaseDaoImpl implements LearningExperienceDao{

	@SuppressWarnings("unchecked")
	@Override
	public PageUtil<Object> getList(PageUtil<Object> pr,String hql) {
		//List<Teacher> list =super.search
		//customerList = session.createQuery("from Customer").list();
		//List<LearningExperience> list=super.search(hql);
		
		//" limit " + (p - 1) * r + "," + r
		//return list;
		pr=super.searchMoreTablePage(hql, pr);
		pr.setList(pr.getList());
		return pr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(LearningExperience le) {
		return super.add(le);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(LearningExperience le) {
		 return super.update(le);
	}
	@SuppressWarnings("unchecked")
	public boolean delete(LearningExperience le) {
		 return super.delete(le);
	}

	public LearningExperienceImpl() {
		super();
		claz=LearningExperience.class;
	}
	
	
	
}
