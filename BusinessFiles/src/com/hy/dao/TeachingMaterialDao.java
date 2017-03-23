package com.hy.dao;

import java.io.InputStream;
import java.util.List;

import com.hy.model.TeachingMaterial;
import com.hy.util.PageUtil;

public interface TeachingMaterialDao extends baseDao2{
	public PageUtil<Object> getList(String hql,PageUtil<Object> pr);
	public boolean add(TeachingMaterial pr);
	public boolean update(TeachingMaterial pr);
	public int delete(List<Integer> ids);
	public boolean importData(TeachingMaterial pr);
	public InputStream exportData(TeachingMaterial teachingmaterial,String string);
}
