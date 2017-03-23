package com.hy.dao;

import java.util.List;

import com.hy.model.Patent;

public interface PatentDao extends baseDao2{
	public List<Object> getList(Patent pr,String hql);
	public boolean add(Patent pr);
	public int delete(List<Integer> ids);
	public boolean update(Patent pr);
	public int review(List<Integer> ids);
}
