package com.hy.dao;

import java.io.InputStream;
import java.util.List;

import com.hy.model.ScientificResearch;

public interface ScientificResearchDao extends baseDao2{
	public List<Object> getList(ScientificResearch sr,String hql);
	public boolean add(ScientificResearch pr);
	public int delete(List<Integer> ids);
	public boolean update(ScientificResearch pr);
	public int review(List<Integer> ids);
	public InputStream exportData(ScientificResearch scientificresearch,String string);
}
