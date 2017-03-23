package com.hy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.hy.dao.PatentDao;
import com.hy.model.Patent;
import com.hy.service.UploadFilesService;
import com.hy.service.impl.UploadFilesServiceImpl;
import com.hy.util.FileUtil;

@SuppressWarnings("rawtypes")
public class PatentImpl extends BaseDaoImpl implements PatentDao{
	private UploadFilesService uploadFilesService=new UploadFilesServiceImpl();
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(Patent pr, String hql) {
		List<Object> listobj=super.search(hql);
		return listobj;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(Patent pr) {
		boolean flag=false;
		/*int maxId=super.getMaxId("t_patent");
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		//证书复印件
		if(pr.getCert()!=null&&pr.getCert().size()>0){
			for(int i=0;i<pr.getCertFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId+1)+pr.getPatentNum()+"_证书"+i, pr.getCertFileName().get(i));
				if(i<pr.getCertFileName().size()-1){
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
			pr.setCertFileName(newNamelist);
			pr.setCertName(tempName);
			uploadFilesService.uploadFile(pr.getCert(),pr.getCertFileName(),"attachments_Img\\Patent\\"+(maxId+1)+pr.getPatentNum());
			tempName="";newName="";newNamelist.clear();
		}
		//转化鉴定
		if(pr.getTrans()!=null&&pr.getTrans().size()>0){
			for(int i=0;i<pr.getTransFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId+1)+pr.getPatentNum()+"_转化鉴定"+i, pr.getTransFileName().get(i));
				if(i<pr.getTransFileName().size()-1){
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
			uploadFilesService.uploadFile(pr.getTrans(),pr.getTransFileName(),"attachments_Img\\Patent\\"+(maxId+1)+pr.getPatentNum());
			tempName="";newName="";newNamelist.clear();
		}
		pr.setCertFileName(newNamelist);
		pr.setCertName(tempName);*/
		if(super.add(pr)){
			flag=update(pr);
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delete(List<Integer> ids) {
		StringBuilder sql=new StringBuilder("delete  from t_patent  where id =?");
		return  super.updateORdeleteBatchByid(ids, sql.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(Patent pr) {
		boolean flag=false;String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		//证书复印件
		if(pr.getCert()!=null&&pr.getCert().size()>0){
			for(int i=0;i<pr.getCertFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_certificate_"+i, pr.getCertFileName().get(i));
				if(i<pr.getCertFileName().size()-1){
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
			pr.setCertFileName(newNamelist);
			pr.setCertName(tempName);
			uploadFilesService.uploadFile(pr.getCert(),pr.getCertFileName(),"attachments_Img\\Patent\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
			
		}
		//转化鉴定
		if(pr.getTrans()!=null&&pr.getTrans().size()>0){
			for(int i=0;i<pr.getTransFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_transFile_"+i, pr.getTransFileName().get(i));
				if(i<pr.getTransFileName().size()-1){
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
			pr.setTransFileName(newNamelist);
			pr.setTransName(tempName);
			uploadFilesService.uploadFile(pr.getTrans(),pr.getTransFileName(),"attachments_Img\\Patent\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}

		flag=super.update(pr);
		return flag;
	}

	@Override
	public int review(List<Integer> ids) {
		// TODO Auto-generated method stub
		return super.updateStateToOk(ids);
	}
	public PatentImpl() {
		super();
		claz=Patent.class;
	}

}
