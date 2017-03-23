package com.hy.dao;

import java.util.Date;
import java.util.List;

import com.hy.model.OtherWorks;

public interface OtherWorksDao extends baseDao2{
	public List<OtherWorks> getList(OtherWorks otherworks);
	public List<OtherWorks> getListParams(String teacherId,Date start,Date end);
	public boolean add(OtherWorks otherworks);
	public boolean update(OtherWorks otherworks);
	public boolean delete(OtherWorks otherworks);
}
