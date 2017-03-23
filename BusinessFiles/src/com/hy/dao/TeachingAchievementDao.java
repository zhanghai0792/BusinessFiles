package com.hy.dao;

import java.util.List;

import com.hy.model.TeachingAchievement;

public interface TeachingAchievementDao extends baseDao2{
	public List<Object> getList(TeachingAchievement t,String hql);
	public boolean add(TeachingAchievement pr);
	public int delete(List<Integer> ids);
	public boolean update(TeachingAchievement pr);
	public int review(List<Integer> ids);
}
