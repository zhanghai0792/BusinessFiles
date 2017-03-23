package com.hy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.hy.dao.GuideStudentDao;
import com.hy.model.GuideStudent;
import com.hy.service.UploadFilesService;
import com.hy.service.impl.UploadFilesServiceImpl;
import com.hy.util.FileUtil;
import com.hy.util.PY;

@SuppressWarnings("rawtypes")
public class GuideStudentImpl extends  BaseDaoImpl implements GuideStudentDao{
	private UploadFilesService uploadFilesService=new UploadFilesServiceImpl();
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(GuideStudent l,String hql) {
		List<Object> resultList=super.search(hql);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(GuideStudent pr) {
		boolean flag=false;
		/*int maxId=super.getMaxId("t_guidestudent");
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId+1)+pr.getPrize()+i, pr.getFileFileName().get(i));
				if(i<pr.getFileFileName().size()-1){
					if(newName!=null){
						tempName+=newName+",";
					}else{
						return false;//包含错误的文件类型
					}
				}else{
					tempName+=newName;
				}
				newNamelist.add(newName);
			}
			pr.setFileFileName(newNamelist);
			pr.setFileName(tempName);
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileFileName(),"attachments_Img\\GuideStudent");
		}
		flag=*/
		if(super.add(pr)){
			flag=update(pr);
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delete(List<Integer> ids) {
		/*boolean flag=false;
		flag=super.delete(pr);
		return flag;*/
		StringBuilder sql=new StringBuilder("delete  from t_guidestudent  where id =?");
		return  super.updateORdeleteBatchByid(ids, sql.toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public int review(List<Integer> ids) {
		/*StringBuilder sql=new StringBuilder("update t_guidestudent p set p.status='1' where p.id=?");
		return super.updateORdeleteBatchByid(ids, sql.toString());*/
		return super.updateStateToOk(ids);
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean update(GuideStudent pr) {
		boolean flag=false;
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_"+(pr.getPrize()!=null?PY.getPinYinHeadChar(pr.getPrize()):"ZDXSJS")+"_"+i, pr.getFileFileName().get(i));
				if(i<pr.getFileFileName().size()-1){
					if(newName!=null){
						tempName+=newName+",";
					}else{
						return false;//包含错误的文件类型
					}
				}else{
					tempName+=newName;
				}
				newNamelist.add(newName);
			}
			pr.setFileFileName(newNamelist);
			pr.setFileName(tempName);
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileFileName(),"attachments_Img\\GuideStudent\\"+pr.getId());
		}
		flag=super.update(pr);
		return flag;
	}

	public GuideStudentImpl() {
		super();
		super.claz=GuideStudent.class;
	}



}
