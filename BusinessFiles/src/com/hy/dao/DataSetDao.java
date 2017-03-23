package com.hy.dao;

import java.util.List;

import com.hy.model.DataSet;

public interface DataSetDao {
	public List<Object> getList(DataSet dataset,String hql);
	public boolean add(DataSet dataset);
	public boolean update(DataSet dataset);
	public int delete(List<Integer> ids);
}
