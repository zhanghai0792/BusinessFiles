package com.hy.dao.impl;

import java.util.List;

import com.hy.dao.DataSetDao;
import com.hy.model.DataSet;

@SuppressWarnings("rawtypes")
public class DataSetImpl extends  BaseDaoImpl implements DataSetDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(DataSet dataset,String hql) {
		
		
		//hql+="order by text";
		List<Object> list=super.search(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(DataSet dataset) {
		boolean flag=false;
		 flag=super.add(dataset);
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(DataSet dataset) {
		boolean flag=false;
		 flag=super.update(dataset);
		return flag;
	}


	@SuppressWarnings("unchecked")
	@Override
	public int delete(List<Integer> ids) {
		StringBuilder sql=new StringBuilder("delete  from t_dataset  where id =?");
		return  super.updateORdeleteBatchByid(ids, sql.toString());
	}

}
