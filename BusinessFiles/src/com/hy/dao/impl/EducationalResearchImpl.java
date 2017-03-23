package com.hy.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.dao.EducationalResearchDao;
import com.hy.model.EducationalResearch;
import com.hy.model.Teacher;
import com.hy.service.UploadFilesService;
import com.hy.service.impl.UploadFilesServiceImpl;
import com.hy.util.DateUtil;
import com.hy.util.FileUtil;

@SuppressWarnings("rawtypes")
public class EducationalResearchImpl extends  BaseDaoImpl implements EducationalResearchDao{
	private UploadFilesService uploadFilesService=new UploadFilesServiceImpl();
	/**
	 *@Description:只显示自己的数据列表
	 *@throws
	 */
	@SuppressWarnings("unchecked")
	@Override 
	public List<Object> getList(EducationalResearch l,String hql) {
		List<Object> resultList=super.search(hql);
		return resultList;
	}

	/**
	 *@Description:显示所有人的数据列表
	 *@throws
	 */
	/*@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllList(EducationalResearch l,String hql) {
		List<Object> list=super.search(hql);
		return list;
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(EducationalResearch pr) {
		boolean flag=false;
		/*int maxId=super.getMaxId("t_educationalresearch");
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		
		//附件
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId+1)+pr.getTopic()+"_附件"+i, pr.getFileFileName().get(i));
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
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileFileName(),"attachments_Img\\EducationalResearch\\"+(maxId+1)+pr.getTopic());
		}
		//立项PDF
		if(pr.getPc()!=null&&pr.getPc().size()>0){
			for(int i=0;i<pr.getPcFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId+1)+pr.getTopic()+"_立项"+i, pr.getPcFileName().get(i));
				if(i<pr.getPcFileName().size()-1){
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
			pr.setPcFileName(newNamelist);
			pr.setProjectCertificat(tempName);
			uploadFilesService.uploadFile(pr.getPc(),pr.getPcFileName(),"attachments_Img\\EducationalResearch\\"+(maxId+1)+pr.getTopic());
			tempName="";newName="";newNamelist.clear();
		}
		//结题PDF
		if(pr.getKc()!=null&&pr.getKc().size()>0){
			for(int i=0;i<pr.getKcFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId+1)+pr.getTopic()+"_结题"+i, pr.getKcFileName().get(i));
				if(i<pr.getKcFileName().size()-1){
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
			pr.setKcFileName(newNamelist);
			pr.setKnotCertificat(tempName);
			uploadFilesService.uploadFile(pr.getKc(),pr.getKcFileName(),"attachments_Img\\EducationalResearch\\"+(maxId+1)+pr.getTopic());
			tempName="";newName="";newNamelist.clear();
		}
		//申报书
		if(pr.getSf()!=null&&pr.getSf().size()>0){
			for(int i=0;i<pr.getSfFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId+1)+pr.getTopic()+"_申报书"+i, pr.getSfFileName().get(i));
				if(i<pr.getSfFileName().size()-1){
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
			pr.setSfFileName(newNamelist);
			pr.setSfbook(tempName);
			uploadFilesService.uploadFile(pr.getSf(),pr.getSfFileName(),"attachments_Img\\EducationalResearch\\"+(maxId+1)+pr.getTopic());
			tempName="";newName="";newNamelist.clear();
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
		StringBuilder sql=new StringBuilder("delete  from t_educationalresearch  where id =?");
		return  super.updateORdeleteBatchByid(ids, sql.toString());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean update(EducationalResearch pr) {
		boolean flag=false;
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		//附件
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_attachment_"+i, pr.getFileFileName().get(i));
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
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileFileName(),"attachments_Img\\EducationalResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		//立项PDF
		if(pr.getPc()!=null&&pr.getPc().size()>0){
			for(int i=0;i<pr.getPcFileName().size();i++){
				
				newName=FileUtil.getNewFileName(pr.getId()+"_projectApproval_"+i, pr.getPcFileName().get(i));
				if(i<pr.getPcFileName().size()-1){
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
			pr.setPcFileName(newNamelist);
			pr.setProjectCertificat(tempName);
			uploadFilesService.uploadFile(pr.getPc(),pr.getPcFileName(),"attachments_Img\\EducationalResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		//结题PDF
		if(pr.getKc()!=null&&pr.getKc().size()>0){
			for(int i=0;i<pr.getKcFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_conclusion_"+i, pr.getKcFileName().get(i));
				if(i<pr.getKcFileName().size()-1){
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
			pr.setKcFileName(newNamelist);
			pr.setKnotCertificat(tempName);
			uploadFilesService.uploadFile(pr.getKc(),pr.getKcFileName(),"attachments_Img\\EducationalResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		//申报书
		if(pr.getSf()!=null&&pr.getSf().size()>0){
			for(int i=0;i<pr.getSfFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_declaration_"+i, pr.getSfFileName().get(i));
				if(i<pr.getSfFileName().size()-1){
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
			pr.setSfFileName(newNamelist);
			pr.setSfbook(tempName);
			uploadFilesService.uploadFile(pr.getSf(),pr.getSfFileName(),"attachments_Img\\EducationalResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		flag=super.update(pr);
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int review(List<Integer> ids) {
		/*StringBuilder sql=new StringBuilder("update t_educationalresearch p set p.status='1' where p.id=?");
		return super.updateORdeleteBatchByid(ids, sql.toString());*/
		return super.updateStateToOk(ids);
	}

	public EducationalResearchImpl() {
		super();
		super.claz=EducationalResearch.class;
	}
	public EducationalResearch getById(Integer id){
		EducationalResearch temp=(EducationalResearch) super.getById(id);
	    super.evit(temp);
	    return temp;
	}
}
