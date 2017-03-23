package com.hy.dao;

import java.util.List;

import com.hy.model.LearningMeeting;

public interface LearningMeetingDao extends baseDao2{
	public List<Object> getList(LearningMeeting learningmeeting,String hql);
	public boolean add(LearningMeeting learningmeeting);
	public boolean update(LearningMeeting learningmeeting);
	public boolean delete(LearningMeeting learningmeeting);
}
