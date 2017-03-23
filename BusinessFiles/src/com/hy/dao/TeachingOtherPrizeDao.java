package com.hy.dao;

import java.util.List;

import com.hy.model.TeachingOtherPrize;

public interface TeachingOtherPrizeDao extends baseDao2{
	public List<Object> getList(TeachingOtherPrize t,String hql);
	public int delete(List<Integer> ids);
	public boolean update(TeachingOtherPrize pr);
	public boolean add(TeachingOtherPrize pr);
	public int review(List<Integer> ids);
}
