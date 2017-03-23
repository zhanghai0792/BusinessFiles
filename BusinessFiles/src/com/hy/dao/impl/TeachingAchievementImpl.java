package com.hy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.hy.dao.TeachingAchievementDao;
import com.hy.model.TeachingAchievement;
import com.hy.service.UploadFilesService;
import com.hy.service.impl.UploadFilesServiceImpl;
import com.hy.util.FileUtil;

@SuppressWarnings("rawtypes")
public class TeachingAchievementImpl extends  BaseDaoImpl implements TeachingAchievementDao{
	private UploadFilesService uploadFilesService=new UploadFilesServiceImpl();
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(TeachingAchievement l,String hql) {
		List<Object> resultList=super.search(hql);
		return resultList;
	}

	
	
	public TeachingAchievementImpl() {
		super();
		claz=TeachingAchievement.class;
	}



	@SuppressWarnings("unchecked")
	@Override
	public boolean add(TeachingAchievement pr) {
		boolean flag=false;
		/*int maxId=super.getMaxId("t_teachingachievement");
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_"+(maxId+1+i)+"_Ta_"+i, pr.getFileFileName().get(i));
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
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileFileName(),"attachments_Img\\TeachingAchievement");
		}*/
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
		StringBuilder sql=new StringBuilder("delete  from t_teachingachievement  where id =?");
		return  super.updateORdeleteBatchByid(ids, sql.toString());

	}
	@SuppressWarnings("unchecked")
	@Override
	public int review(List<Integer> ids) {
		/*StringBuilder sql=new StringBuilder("update t_teachingachievement p set p.status='1' where p.id=?");
		return super.updateORdeleteBatchByid(ids, sql.toString());*/
		return super.updateStateToOk(ids);
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean update(TeachingAchievement pr) {
		boolean flag=false;
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_JXCG_"+i, pr.getFileFileName().get(i));
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
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileFileName(),"attachments_Img\\TeachingAchievement\\"+pr.getId());
		}
		flag=super.update(pr);
		return flag;
	}

}
