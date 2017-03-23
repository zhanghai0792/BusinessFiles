package com.hy.dao;

import java.util.List;

import com.hy.model.ScientificAchievement;

public interface ScientificAchievementDao extends baseDao2{
	public List<Object> getList(ScientificAchievement t,String hql);
	public boolean add(ScientificAchievement pr);
	public int delete(List<Integer> ids);
	public boolean update(ScientificAchievement pr);
	public int review(List<Integer> ids);
}
