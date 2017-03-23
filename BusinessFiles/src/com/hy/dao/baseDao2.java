package com.hy.dao;

import java.util.List;

public interface baseDao2 {
public Object getByIdAndEvict(Integer id);
public List getByIds(List<Integer> ids);
public List getByIdsDeal(List<Integer> ids);
public Long getPages(StringBuilder hql);
public List getPageObject(StringBuilder hql, int indexPage, int pageSize);
}
