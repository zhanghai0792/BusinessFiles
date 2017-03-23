package com.hy.dao;

import java.util.List;

import com.hy.model.MonographTextbook;

public interface MonographTextbookDao  extends baseDao2{
	public List<Object> getList(MonographTextbook mt,String hql);
	public boolean add(MonographTextbook pr);
	public int delete(List<Integer> ids);
	public int review(List<Integer> ids);
	public boolean update(MonographTextbook pr);
	public MonographTextbook getById(Integer id);
	public void evict(MonographTextbook pr);
}
