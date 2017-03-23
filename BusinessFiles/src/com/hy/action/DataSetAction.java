package com.hy.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.hy.dao.DataSetDao;
import com.hy.dao.impl.DataSetImpl;
import com.hy.model.DataSet;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class DataSetAction extends BaseAction implements  ModelDriven<DataSet>{
	
	private DataSet dataSet  = new DataSet();
	private DataSetDao dao  = new DataSetImpl();
	private List<Integer> ids=new ArrayList<Integer>();
	@Override
	public DataSet getModel() {
		return dataSet;
	}
	@Action(value = "getDataSet", results = { @Result(name = "success", type = "json",params = {"root","listobj"}) })
	public String getData(){
		//System.out.println(servletrequest.getParameter("classFiled"));
		
		//String classFiled=ServletActionContext.getRequest().getParameter("classFiled");
		/*for (Map.Entry<String, String> entry : map.entrySet()) {
			   System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			  }*/
        
		/*for (Entry<String, Object> entry : request.entrySet()) {
			   System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			  }*/
		System.out.println(dataSet);
		StringBuilder hql=new StringBuilder("FROM DataSet ");
		if(dataSet!=null){
			hql.append(" where classFiled='"+dataSet.getClassFiled()+"'");
			if(dataSet.getType()!=null){
				hql.append(" and type ='"+dataSet.getType()+"'");
			}
		}
		listobj=dao.getList(dataSet,hql.toString());
		/*jsoMap.put("total", 10);
		jsoMap.put("rows", listobj);*/
		return "success";
	}
	@Action(value = "addDataSet", results = { @Result(name = "success", type = "json",params = {"root","listobj"}) })
	public String add(){
		jsoMap.put("success", dao.add(dataSet));
		return "success";
	}
	@Action(value = "updateDataSet", results = { @Result(name = "success", type = "json",params = {"root","listobj"}) })
	public String update(){
		jsoMap.put("success", dao.update(dataSet));
		return "success";
	}
	@Action(value = "deleteDataSet", results = { @Result(name = "success", type = "json",params = {"root","listobj"}) })
	public String delete(){
		int count=dao.delete(ids);
		if(count>0) jsoMap.put("success",true);
		else  jsoMap.put("success",false);
		jsoMap.put("msg", "共删除"+count+"条数据!");
		return "success";
	}
	public DataSet getDataset() {
		return dataSet;
	}
	public void setDataset(DataSet dataset) {
		this.dataSet = dataset;
	}
	
	
}
