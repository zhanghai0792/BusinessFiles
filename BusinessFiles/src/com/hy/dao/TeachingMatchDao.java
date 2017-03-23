package com.hy.dao;

import java.util.List;

import com.hy.model.TeachingMatch;

public interface TeachingMatchDao extends baseDao2{

	public List<Object> getList(TeachingMatch t,String hql);
	public boolean add(TeachingMatch pr);
	public int delete(List<Integer> ids);
	public boolean update(TeachingMatch pr);
	public int review(List<Integer> ids);
}
