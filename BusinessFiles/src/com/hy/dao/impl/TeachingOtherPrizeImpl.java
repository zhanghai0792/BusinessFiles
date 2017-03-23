package com.hy.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.dao.TeachingOtherPrizeDao;
import com.hy.model.Teacher;
import com.hy.model.TeachingOtherPrize;
import com.hy.service.UploadFilesService;
import com.hy.service.impl.UploadFilesServiceImpl;
import com.hy.util.DateUtil;
import com.hy.util.FileUtil;
import com.hy.util.PY;

@SuppressWarnings("rawtypes")
public class TeachingOtherPrizeImpl extends  BaseDaoImpl implements TeachingOtherPrizeDao{
	private UploadFilesService uploadFilesService=new UploadFilesServiceImpl();
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(TeachingOtherPrize l,String hql) {
		List<Object> resultList=super.searchMoreTable(hql);
		List<Object> listobj=new ArrayList<Object>();
		for (int i = 0; i < resultList.size(); i++) { 
			Map<String,Object> map=new HashMap<String, Object>();
		    Object[] obj = (Object[])resultList.get(i);
		    TeachingOtherPrize p=(TeachingOtherPrize)obj[0];
		    Teacher t=(Teacher)obj[1];
		    map.put("prize", p.getPrize());
		    map.put("attachment", p.getAttachment());
		    map.put("time", DateUtil.dateToYMD(p.getTime()));
		    map.put("torder", p.getTorder());
		    map.put("level", p.getLevel());
		    map.put("score", p.getScore());
		    map.put("status", p.getStatus());
		    map.put("id", p.getId());
		    map.put("teacherId", p.getTeacherId());
		    map.put("teacherName", t.getName());
		    map.put("fileName", p.getFileName());
		    listobj.add(map);
		}
		return listobj;
	}

	
	
	
	public TeachingOtherPrizeImpl() {
		super();
		claz=TeachingOtherPrize.class;
	}




	@SuppressWarnings("unchecked")
	@Override
	public int delete(List<Integer> ids) {
		/*boolean flag=false;
		flag=super.delete(pr);
		return flag;*/
		StringBuilder sql=new StringBuilder("delete  from t_teachingotherprize  where id =?");
		return  super.updateORdeleteBatchByid(ids, sql.toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public int review(List<Integer> ids) {
		/*StringBuilder sql=new StringBuilder("update t_teachingotherprize p set p.status='1' where p.id=?");
		return super.updateORdeleteBatchByid(ids, sql.toString());*/
		return super.updateStateToOk(ids);
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean update(TeachingOtherPrize pr) {
		boolean flag=false;
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_"+(pr.getPrize()!=null?PY.getPinYinHeadChar(pr.getPrize()):"JXBS")+"_"+i, pr.getFileFileName().get(i));
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
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileNewName(),"attachments_Img\\TeachingOtherPrize\\"+pr.getId());
		}
		flag=super.update(pr);
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(TeachingOtherPrize pr) {
		boolean flag=false;
		/*int maxId=super.getMaxId("t_teachingotherprize");
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getTeacherId()+"_"+(maxId+1+i)+"_To_"+i, pr.getFileFileName().get(i));
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
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileNewName(),"attachments_Img\\TeachingOtherPrize");
		}
		flag=*/
		
		if(super.add(pr)){
			flag=update(pr);
		}
		return flag;
	}

}
