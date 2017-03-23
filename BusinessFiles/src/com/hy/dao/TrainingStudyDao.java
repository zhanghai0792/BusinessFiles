package com.hy.dao;

import java.util.List;

import com.hy.model.TrainingStudy;

public interface TrainingStudyDao extends baseDao2{
	public List<Object> getList(TrainingStudy trainingstudy,String hql);
	public boolean add(TrainingStudy trainingstudy);
	public boolean update(TrainingStudy trainingstudy);
	public boolean delete(TrainingStudy trainingstudy);
}
