package com.hy.dao;

import java.io.InputStream;
import java.util.List;

import com.hy.model.PaperTeaching;
import com.hy.util.PageUtil;

public interface PaperTeachingDao extends baseDao2{
	public PageUtil<Object> getList(String hql,PageUtil<Object> pr);
	public boolean add(PaperTeaching paperteaching);
	public boolean update(PaperTeaching paperteaching);
	public boolean delete(PaperTeaching paperteaching);
	public int importData(PaperTeaching pr,String[] excelTemp);
	public InputStream exportData(PaperTeaching pr, String hql);
	public int delete(List<Integer> ids);
}
