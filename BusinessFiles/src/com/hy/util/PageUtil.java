package com.hy.util;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: PageUtil 
 * @Description: TODO
 * @author wwh
 * @date 2016-10-27
 * @param <E>
 * http://bbs.csdn.net/topics/330268969
 */
public class PageUtil<E> {
	private String orderBy = "";
	private String sort = "asc";
	private List<E> list = new ArrayList<E>(); //查询结果
	private int pageNo = 1;//默认是第一页,实际页号
	private int pageSize = 20; //每页默认20条,每页记录数,使用时根据实际参数值确定
	private int recTotal = 0; //默认的总记录数
	     
		@SuppressWarnings("rawtypes")
		public List getList() {
	        return list;
	    }
	    public void setList(List<E> list) {
	        this.list = list;
	    }
	    public int getPageNo() {
	        return pageNo;
	    }
	    public void setPageNo(int pageNo) {
	        this.pageNo = pageNo;
	    }
	    public int getPageSize() {
	        return (0==pageSize)?20:pageSize;
	    }
	    public void setPageSize(int pageSize) {
	        this.pageSize = pageSize;
	    }
	    public int getRecTotal() {
	        return recTotal;
	    }
	    public void setRecTotal(int recTotal) {
	        this.recTotal = recTotal;
	    }
	     
	    public int getPageTotal() {
	        int ret = (this.getRecTotal() - 1) / this.getPageSize() + 1;
	        ret = (ret<1)?1:ret;
	        return ret;
	    }
	     
	    public int getFirstRec()
	    {
	        int ret = (this.getPageNo()-1) * this.getPageSize();// + 1;
	        ret = (ret < 1)?0:ret;
	        return ret;
	    }
	    public String getOrderBy() {
	        return orderBy;
	    }
	    public void setOrderBy(String orderBy) {
	        this.orderBy = orderBy;
	    }
	    public String getSort() {
	        return sort;
	    }
	    public void setSort(String sort) {
	        this.sort = sort;
	    }
}
