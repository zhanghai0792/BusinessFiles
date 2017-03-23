package com.hy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.hy.dao.MonographTextbookDao;
import com.hy.model.MonographTextbook;
import com.hy.service.UploadFilesService;
import com.hy.service.impl.UploadFilesServiceImpl;
import com.hy.util.FileUtil;

@SuppressWarnings("rawtypes")
public class MonographTextbookImpl extends  BaseDaoImpl implements MonographTextbookDao{
	private UploadFilesService uploadFilesService=new UploadFilesServiceImpl();
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(MonographTextbook mt,String hql) {
		List<Object> resultList=super.search(hql);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(MonographTextbook pr) {
		boolean flag=false;
		
		/*int maxId=super.getMaxId("t_monographtextbook");
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId+1+i)+pr.getBookName()+i, pr.getFileFileName().get(i));
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
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileNewName(),"attachments_Img\\MonographTextbook");
		}*/
		if(super.add(pr))
			flag=update(pr);
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delete(List<Integer> ids) {
		/*boolean flag=false;
		flag=super.delete(pr);
		return flag;*/
		StringBuilder sql=new StringBuilder("delete  from t_monographtextbook  where id =?");
		return  super.updateORdeleteBatchByid(ids, sql.toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public int review(List<Integer> ids) {
		/*StringBuilder sql=new StringBuilder("update t_monographtextbook p set p.status='1' where p.id=?");
		return super.updateORdeleteBatchByid(ids, sql.toString());*/
		return super.updateStateToOk(ids);
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean update(MonographTextbook pr) {
		boolean flag=false;
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_book_"+i, pr.getFileFileName().get(i));
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
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileNewName(),"attachments_Img\\MonographTextbook\\"+pr.getId());
		}
		flag=super.update(pr);
		return flag;
	}
	
	
	public MonographTextbookImpl() {
		claz=MonographTextbook.class;
	}

	public MonographTextbook getById(Integer id){
		return (MonographTextbook)super.getById(id);
	}
	public void evict(MonographTextbook pr){
		super.evit(pr);
	}
}
