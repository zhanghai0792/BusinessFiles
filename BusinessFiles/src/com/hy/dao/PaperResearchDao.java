package com.hy.dao;

import java.io.InputStream;
import java.util.List;

import com.hy.model.PaperResearch;

public interface PaperResearchDao extends baseDao2{
	public List<Object> getList(PaperResearch pr,String hql);
	public boolean add(PaperResearch pr);
	public int delete(List<Integer> ids);
	public boolean update(PaperResearch pr);
	public int review(List<Integer> ids);
	public InputStream exportData(PaperResearch paperresearch, String string);
	
}
