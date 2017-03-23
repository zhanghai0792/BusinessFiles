package com.hy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.hy.dao.ScientificAchievementDao;
import com.hy.model.ScientificAchievement;
import com.hy.service.UploadFilesService;
import com.hy.service.impl.UploadFilesServiceImpl;
import com.hy.util.FileUtil;
import com.hy.util.PY;

@SuppressWarnings("rawtypes")
public class ScientificAchievementImpl extends  BaseDaoImpl implements ScientificAchievementDao {
	private UploadFilesService uploadFilesService=new UploadFilesServiceImpl();
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(ScientificAchievement t, String hql) {
		List<Object> listobj=super.search(hql);
		return listobj;
	}

	
	
	
	public ScientificAchievementImpl() {
		super();
		claz=ScientificAchievement.class;
	}




	@SuppressWarnings("unchecked")
	@Override
	public boolean add(ScientificAchievement pr) {
		boolean flag=false;
		/*int maxId=super.getMaxId("t_scientificachievement");
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId+1+i)+pr.getPrize()+i, pr.getFileFileName().get(i));
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
			pr.setFileNewName(newNamelist);
			pr.setFileName(tempName);
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileNewName(),"attachments_Img\\ScientificAchievement");
		}*/
		if(super.add(pr)){
			flag=update(pr);
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delete(List<Integer> ids) {
		StringBuilder sql=new StringBuilder("delete  from t_scientificachievement  where id =?");
		return  super.updateORdeleteBatchByid(ids, sql.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(ScientificAchievement pr) {
		boolean flag=false;
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_"+(pr.getPrize()!=null?PY.getPinYinHeadChar(pr.getPrize()):"KYCG")+"_"+i, pr.getFileFileName().get(i));
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
			pr.setFileNewName(newNamelist);
			pr.setFileName(tempName);
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileNewName(),"attachments_Img\\ScientificAchievement\\"+pr.getId());
		}
		flag=super.update(pr);
		return flag;
	}

	@Override
	public int review(List<Integer> ids) {
		// TODO Auto-generated method stub
		return super.updateStateToOk(ids);
	}
	

}
