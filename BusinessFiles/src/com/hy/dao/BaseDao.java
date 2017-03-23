package com.hy.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

public interface  BaseDao<T>{
	public Session getSession();
	public void closeSession();
	public List<T> search(String hql);
	public List<?> searchMoreTable(String hql);
	//public List<Object> searchMoreTable1(String hql);
	//public List<Object> searchMoreTablePage(String hql);
	public Transaction getTransaction();
	public int updateORdeleteBatchByid(List<Integer> ids,String sql);
	public List<T> searchX(String hql,Map<String,Object> map);
	public T getById(Integer id);
	public T getById(String id);
	public Object getByIdAndEvict(Integer id);
	public Object getByIdAndEvict(String id);
	public int updateStateToOk(List<Integer> ids);
   public Long getPages(StringBuilder hql);
   public List<T> getPageObject(StringBuilder hql,int indexPage,int pageSize);
}
