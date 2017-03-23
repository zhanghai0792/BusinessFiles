package com.hy.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;

import com.hy.dao.PaperResearchDao;
import com.hy.model.PaperResearch;
import com.hy.service.UploadFilesService;
import com.hy.service.impl.UploadFilesServiceImpl;
import com.hy.util.FileUtil;
import com.hy.util.ZipUtil;

@SuppressWarnings("rawtypes")
public class PaperResearchImpl extends  BaseDaoImpl implements PaperResearchDao{
	
	private UploadFilesService uploadFilesService=new UploadFilesServiceImpl();
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(PaperResearch pr,String hql) {
		List<Object> resultList=super.search(hql);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(PaperResearch pr) {
		boolean flag=false;
		//判断文件类型,将新的文件名设置到数据库
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		//封面
		if(pr.getFm()!=null&&pr.getFm().size()>0){
			
			for(int i=0;i<pr.getFmFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_covers_"+i, pr.getFmFileName().get(i));
				if(i<pr.getFmFileName().size()-1){
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
			pr.setFmNewName(newNamelist);
			pr.setFmName(tempName);
			uploadFilesService.uploadFile(pr.getFm(),pr.getFmNewName(),"attachments_Img\\PaperResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		//版权页		
		if(pr.getBqy()!=null&&pr.getBqy().size()>0){
					for(int i=0;i<pr.getBqyFileName().size();i++){
						newName=FileUtil.getNewFileName(pr.getId()+"_copyright_"+i, pr.getBqyFileName().get(i));
						if(i<pr.getBqyFileName().size()-1){
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
					pr.setBqyNewName(newNamelist);
					pr.setBqyName(tempName);
					uploadFilesService.uploadFile(pr.getBqy(),pr.getBqyNewName(),"attachments_Img\\PaperResearch\\"+pr.getId());
					tempName="";newName="";newNamelist.clear();
			}
		//目录
		if(pr.getMl()!=null&&pr.getMl().size()>0){
			for(int i=0;i<pr.getMlFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_catalog_"+i, pr.getMlFileName().get(i));
				if(i<pr.getMlFileName().size()-1){
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
			pr.setMlNewName(newNamelist);
			pr.setMlName(tempName);
			uploadFilesService.uploadFile(pr.getMl(),pr.getMlNewName(),"attachments_Img\\PaperResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		//正文
		if(pr.getZw()!=null&&pr.getZw().size()>0){
			for(int i=0;i<pr.getZwFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_text_"+i, pr.getZwFileName().get(i));
				if(i<pr.getZwFileName().size()-1){
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
			pr.setZwNewName(newNamelist);
			pr.setZwName(tempName);
			uploadFilesService.uploadFile(pr.getZw(),pr.getZwNewName(),"attachments_Img\\PaperResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		//封底
		if(pr.getFd()!=null&&pr.getFd().size()>0){
			for(int i=0;i<pr.getFdFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_backCover_"+i, pr.getFdFileName().get(i));
				if(i<pr.getFdFileName().size()-1){
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
			pr.setFdNewName(newNamelist);
			pr.setFdName(tempName);
			uploadFilesService.uploadFile(pr.getFd(),pr.getFdNewName(),"attachments_Img\\PaperResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		//检索页
		if(pr.getJs()!=null&&pr.getJs().size()>0){
			for(int i=0;i<pr.getJsFileName().size();i++){
				newName=FileUtil.getNewFileName(pr.getId()+"_retrieve_"+i, pr.getJsFileName().get(i));
				if(i<pr.getJsFileName().size()-1){
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
			pr.setJsNewName(newNamelist);
			pr.setJsName(tempName);
			uploadFilesService.uploadFile(pr.getJs(),pr.getJsNewName(),"attachments_Img\\PaperResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		flag=super.update(pr);
		return flag;
}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(PaperResearch pr) {
		
		boolean flag=false;
		if(super.add(pr)){
		return update(pr);}
		/*//判断文件类型,将新的文件名设置到数据库
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		//封面
		if(pr.getFm()!=null&&pr.getFm().size()>0){
			
			for(int i=0;i<pr.getFmFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId)+"_covers_"+i, pr.getFmFileName().get(i));
				if(i<pr.getFmFileName().size()-1){
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
			pr.setFmNewName(newNamelist);
			pr.setFmName(tempName);
			uploadFilesService.uploadFile(pr.getFm(),pr.getFmNewName(),"attachments_Img\\PaperResearch\\"+(maxId+1));
			tempName="";newName="";newNamelist.clear();
		}
		//版权页		
		if(pr.getBqy()!=null&&pr.getBqy().size()>0){
					for(int i=0;i<pr.getBqyFileName().size();i++){
						newName=FileUtil.getNewFileName((maxId)+"_copyright_"+i, pr.getBqyFileName().get(i));
						if(i<pr.getBqyFileName().size()-1){
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
					pr.setBqyNewName(newNamelist);
					pr.setBqyName(tempName);
					uploadFilesService.uploadFile(pr.getBqy(),pr.getBqyNewName(),"attachments_Img\\PaperResearch\\"+(maxId+1));
					tempName="";newName="";newNamelist.clear();
			}
		//目录
		if(pr.getMl()!=null&&pr.getMl().size()>0){
			for(int i=0;i<pr.getMlFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId)+"_catalog_"+i, pr.getMlFileName().get(i));
				if(i<pr.getMlFileName().size()-1){
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
			pr.setMlNewName(newNamelist);
			pr.setMlName(tempName);
			uploadFilesService.uploadFile(pr.getMl(),pr.getMlNewName(),"attachments_Img\\PaperResearch\\"+(maxId+1));
			tempName="";newName="";newNamelist.clear();
		}
		//正文
		if(pr.getZw()!=null&&pr.getZw().size()>0){
			for(int i=0;i<pr.getZwFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId)+"_text_"+i, pr.getZwFileName().get(i));
				if(i<pr.getZwFileName().size()-1){
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
			pr.setZwNewName(newNamelist);
			pr.setZwName(tempName);
			uploadFilesService.uploadFile(pr.getZw(),pr.getZwNewName(),"attachments_Img\\PaperResearch\\"+(maxId+1));
			tempName="";newName="";newNamelist.clear();
		}
		//封底
		if(pr.getFd()!=null&&pr.getFd().size()>0){
			for(int i=0;i<pr.getFdFileName().size();i++){
				newName=FileUtil.getNewFileName((maxId)+"_backCover_"+i, pr.getFdFileName().get(i));
				if(i<pr.getFdFileName().size()-1){
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
			pr.setFdNewName(newNamelist);
			pr.setFdName(tempName);
			uploadFilesService.uploadFile(pr.getFd(),pr.getFdNewName(),"attachments_Img\\PaperResearch\\"+(maxId+1));
			tempName="";newName="";newNamelist.clear();
		}
		//检索页
				if(pr.getJs()!=null&&pr.getJs().size()>0){
					for(int i=0;i<pr.getJsFileName().size();i++){
						newName=FileUtil.getNewFileName((maxId)+"_retrieve_"+i, pr.getJsFileName().get(i));
						if(i<pr.getJsFileName().size()-1){
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
					pr.setJsNewName(newNamelist);
					pr.setJsName(tempName);
					uploadFilesService.uploadFile(pr.getJs(),pr.getJsNewName(),"attachments_Img\\PaperResearch\\"+pr.getId());
					tempName="";newName="";newNamelist.clear();
				}*/
		
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delete(List<Integer> ids) {
		//delete p from t_paperresearch   p  where p.id =?
		/*StringBuilder sql=new StringBuilder("delete  from t_paperresearch  where id =?");
		return  super.updateORdeleteBatchByid(ids, sql.toString());*/
		String hql="delete from "+claz.getSimpleName()+" where id in(:ids)";
		Query query=getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		return query.executeUpdate();
	}

	@SuppressWarnings({ "unchecked", })
	@Override
	public int review(List<Integer> ids) {
		/*Session s= getSession();
		Transaction tx = s.beginTransaction();
		StringBuilder hql=new StringBuilder("update PaperResearch p set p.status='1' where p.id = :id");
		int count=0;
		for(int i=0;i<ids.size();i++){
	    count =s.createQuery(hql.toString()).setInteger("id",ids.get(i)).executeUpdate();
		if(count>0)count++;
		}
	    tx.commit();	
		s.close();*/
		
		//StringBuilder sql=new StringBuilder("update t_paperresearch p set p.status='1' where p.id=?");
		return super.updateStateToOk(ids);
	}

	/**
	 *@Description:学术论文 可选择  压缩包形式下载
	 *@throws
	 *@author wwh 
	 *@date 2016-12-12
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public InputStream exportData(PaperResearch paperresearch, String hql) {
		OutputStream os=null;
		String path_temp =ServletActionContext.getServletContext().getRealPath("/temp");
		String zip_temp =ServletActionContext.getServletContext().getRealPath("/ziptemp");//将压缩文件暂存到临时目录
		List<Object> listmap =getList(null, hql);
		String photopath = ServletActionContext.getServletContext().getRealPath("/attachments_Img/PaperResearch");
		try {
		String excelFile = ServletActionContext.getServletContext().getRealPath(
				"ExcelTemplete\\学术论文.xls");//取出模板
			InputStream iS = new FileInputStream(excelFile);
			Workbook wb = WorkbookFactory.create(iS);
			
			Sheet sheet = wb.getSheetAt(0);
			CellStyle cs2 = wb.createCellStyle();
			Font f = wb.createFont();
			f.setFontHeightInPoints((short) 12);
			f.setColor(IndexedColors.BLACK.getIndex());
			cs2.setFont(f);
			cs2.setBorderLeft(CellStyle.BORDER_MEDIUM);
			cs2.setBorderRight(CellStyle.BORDER_MEDIUM);
			cs2.setBorderTop(CellStyle.BORDER_MEDIUM);
			cs2.setBorderBottom(CellStyle.BORDER_MEDIUM);
			cs2.setAlignment(CellStyle.ALIGN_CENTER);
			for (int i = 0; i < listmap.size(); i++) {
				Map<String, Object> map= (Map<String, Object>) listmap.get(i);
				FileHandle(map,photopath, path_temp);
				/*List<File> fileNamelist=FileUtil.getFileListALL(photopath+"/"+teacherId+paperTitle);
					for(int k=0;k<fileNamelist.size();k++){
						if("Fm".equals(fileNamelist.get(k).getName())){
							File destf=new File(path_temp+"/"+teacherId+paperTitle);
							if(!destf.exists()) destf.mkdirs();
						}
					File tf=new File(photopath+"/"+teacherId+paperTitle);
					if(tf.exists()){
						File destf=new File(path_temp+"/"+teacherId+paperTitle);
						if(!destf.exists()) destf.mkdirs();
						FileUtil.copyFolder(photopath+"/"+teacherId+paperTitle,path_temp+"/"+teacherId+paperTitle);	
					}*/
				
				Row row = sheet.createRow((short) (3+ i));
				for (int j = 0; j < 3; j++) {
					Cell cc0 = row.createCell(0);
					cc0.setCellType(Cell.CELL_TYPE_STRING);
					cc0.setCellValue((String)map.get("teacherId"));
					cc0.setCellStyle(cs2);
					
					Cell cc1 = row.createCell(1);
					cc1.setCellType(Cell.CELL_TYPE_STRING);
					cc1.setCellValue((String)map.get("teacherName"));
					cc1.setCellStyle(cs2);
					
					Cell cc2 = row.createCell(2);
					cc2.setCellType(Cell.CELL_TYPE_STRING);
					cc2.setCellValue((String)map.get("teacherName"));
					cc2.setCellStyle(cs2);
					
					Cell cc3 = row.createCell(3);
					cc3.setCellType(Cell.CELL_TYPE_STRING);
					cc3.setCellValue((String)map.get("teacherName"));
					cc3.setCellStyle(cs2);
					
					Cell cc4 = row.createCell(4);
					cc4.setCellType(Cell.CELL_TYPE_STRING);
					cc4.setCellValue((String)map.get("teacherName"));
					cc4.setCellStyle(cs2);
					}
				}
			File xlsfile=new File(path_temp+"/temp.xls");
			os=new FileOutputStream(xlsfile);
            wb.write(os);
            os.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		String zippath_zip = String.format("%s\\%s.zip", zip_temp,"temp");//重复生成则覆盖
		try {
			ZipUtil.zip(path_temp, zippath_zip);
			File fileLoad = new File(zippath_zip);//
			FileInputStream in = new FileInputStream(fileLoad);//转成输入流返回
			//fileLoad.deleteOnExit();//删除产生的临时文件
			//xlsfile.deleteOnExit();
			return in;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
		/*try{
			ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path_dest_zip));
		byte bt[] = new byte[10*1024];
		for(int j = 0 ; j < inFiles.size() ; j++){
			if(!inFiles.get(j).exists()) continue;
			FileInputStream is = new FileInputStream(inFiles.get(j));
			ZipEntry ze = new ZipEntry(URLEncoder.encode(System.currentTimeMillis()+outFiles.get(j).getName() , "UTF-8"));
				zout.putNextEntry(ze);
			int len = -1;
			while((len = is.read(bt)) != -1){
				if(bt!=null){
					zout.write(bt, 0, len);//写入到临时压缩包文件
				}
			}
			zout.closeEntry();
			is.close();
		}
		zout.close();
		File fileLoad = new File(path_dest_zip);//
		FileInputStream in = new FileInputStream(fileLoad);//转成输入流返回
		//in.close();
		fileLoad.deleteOnExit();//删除产生的临时文件
		xlsfile.deleteOnExit();
		
		return in;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;*/
		/*File fileLoad = new File(path_dest_zip);;
		response.setHeader("Content-disposition", "attachment;filename="+ java.net.URLEncoder.encode(name_zip, "UTF-8"));	
		response.setContentType("application/x-tar");
		long fileLength = fileLoad.length();
		String length = String.valueOf(fileLength);
		response.setHeader("Content_Length", length);
		FileInputStream in = new FileInputStream(fileLoad);*/		
		
	}
	
	//文件操作
	private void FileHandle(Map map,String photopath,String path_temp){
		String paperTitle=(String)map.get("paperTitle");
		String teacherId=(String)map.get("teacherId");
		String fmName=(String) map.get("fmName");
		if(fmName!=null&&!"".equals(fmName.trim())){
			String [] fm=fmName.split(",");
			for(int k=0;k<fm.length;k++){
				File fmt=new File(photopath+"/"+teacherId+paperTitle+"/"+fm[k]);
				if(fmt.exists()){
					File destf=new File(path_temp+"/"+teacherId+paperTitle);if(!destf.exists()) destf.mkdirs();
					FileUtil.copyFile(photopath+"/"+teacherId+paperTitle+"/"+fm[k],path_temp+"/"+teacherId+paperTitle+"/"+fm[k]);
				}
			}
		}
		String bqyName=(String) map.get("bqyName");
		if(bqyName!=null&&!"".equals(bqyName.trim())){
			String [] fm=bqyName.split(",");
			for(int k=0;k<fm.length;k++){
				File fmt=new File(photopath+"/"+teacherId+paperTitle+"/"+fm[k]);
				if(fmt.exists()){
					File destf=new File(path_temp+"/"+teacherId+paperTitle);if(!destf.exists()) destf.mkdirs();
					FileUtil.copyFile(photopath+"/"+teacherId+paperTitle+"/"+fm[k],path_temp+"/"+teacherId+paperTitle+"/"+fm[k]);
				}
			}
		}
		String mlName=(String) map.get("mlName");
		if(mlName!=null&&!"".equals(mlName.trim())){
			String [] fm=mlName.split(",");
			for(int k=0;k<fm.length;k++){
				File fmt=new File(photopath+"/"+teacherId+paperTitle+"/"+fm[k]);
				if(fmt.exists()){
					File destf=new File(path_temp+"/"+teacherId+paperTitle);if(!destf.exists()) destf.mkdirs();
					FileUtil.copyFile(photopath+"/"+teacherId+paperTitle+"/"+fm[k],path_temp+"/"+teacherId+paperTitle+"/"+fm[k]);
				}
			}
		}
		String zwName=(String) map.get("zwName");
		if(zwName!=null&&!"".equals(zwName.trim())){
			String [] fm=zwName.split(",");
			for(int k=0;k<fm.length;k++){
				File fmt=new File(photopath+"/"+teacherId+paperTitle+"/"+fm[k]);
				if(fmt.exists()){
					File destf=new File(path_temp+"/"+teacherId+paperTitle);if(!destf.exists()) destf.mkdirs();
					FileUtil.copyFile(photopath+"/"+teacherId+paperTitle+"/"+fm[k],path_temp+"/"+teacherId+paperTitle+"/"+fm[k]);
				}
			}
		}
		String fdName=(String) map.get("fdName");
		if(fdName!=null&&!"".equals(fdName.trim())){
			String [] fm=fdName.split(",");
			for(int k=0;k<fm.length;k++){
				File fmt=new File(photopath+"/"+teacherId+paperTitle+"/"+fm[k]);
				if(fmt.exists()){
					File destf=new File(path_temp+"/"+teacherId+paperTitle);if(!destf.exists()) destf.mkdirs();
					FileUtil.copyFile(photopath+"/"+teacherId+paperTitle+"/"+fm[k],path_temp+"/"+teacherId+paperTitle+"/"+fm[k]);
				}
			}
		}
	}

	public PaperResearchImpl() {
		super();
		claz=PaperResearch.class;
	}
	
	
	
}
