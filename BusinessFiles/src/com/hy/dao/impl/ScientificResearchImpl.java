package com.hy.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.hibernate.Transaction;
import org.hibernate.Session;

import com.hy.dao.ScientificResearchDao;
import com.hy.model.ScientificResearch;
import com.hy.model.Teacher;
import com.hy.service.UploadFilesService;
import com.hy.service.impl.UploadFilesServiceImpl;
import com.hy.util.DateUtil;
import com.hy.util.FileUtil;
import com.hy.util.ZipUtil;

@SuppressWarnings("rawtypes")
public class ScientificResearchImpl extends  BaseDaoImpl implements ScientificResearchDao{
	private UploadFilesService uploadFilesService=new UploadFilesServiceImpl();
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(ScientificResearch s,String hql) {
		List<Object> resultList=super.search(hql);
		return resultList;
	}

	
	
	public ScientificResearchImpl() {
		super();
		claz=ScientificResearch.class;
	}



	@SuppressWarnings("unchecked")
	@Override
	public boolean add(ScientificResearch pr) {
		boolean flag=false;
		flag=super.add(pr);
		update(pr);
		/*boolean flag=false;int maxId=super.getMaxId("t_scientificresearch");
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
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileFileName(),"attachments_Img\\ScientificResearch\\"+(maxId+1)+pr.getTopic());
			tempName="";newName="";newNamelist.clear();
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
			uploadFilesService.uploadFile(pr.getPc(),pr.getPcFileName(),"attachments_Img\\ScientificResearch\\"+(maxId+1)+pr.getTopic());
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
			uploadFilesService.uploadFile(pr.getKc(),pr.getKcFileName(),"attachments_Img\\ScientificResearch\\"+(maxId+1)+pr.getTopic());
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
			uploadFilesService.uploadFile(pr.getSf(),pr.getSfFileName(),"attachments_Img\\ScientificResearch\\"+(maxId+1)+pr.getTopic());
			tempName="";newName="";newNamelist.clear();
		}
		flag=super.add(pr);*/
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delete(List<Integer> ids) {
		/*boolean flag=super.delete(pr);
		return flag;*/
		StringBuilder sql=new StringBuilder("delete  from t_scientificresearch  where id =?");
		return  super.updateORdeleteBatchByid(ids, sql.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(ScientificResearch pr) {
		boolean flag=false;
		String tempName="";String newName="";
		List<String> newNamelist=new ArrayList<String>();
		//附件
		if(pr.getFile()!=null&&pr.getFile().size()>0){
			for(int i=0;i<pr.getFileFileName().size();i++){
				//attachment 附件
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
			pr.setFileFileName(newNamelist);//将新文件名重命名覆盖上传时的文件名
			pr.setFileName(tempName);
			uploadFilesService.uploadFile(pr.getFile(),pr.getFileFileName(),"attachments_Img\\ScientificResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		//立项PDF
		if(pr.getPc()!=null&&pr.getPc().size()>0){
			for(int i=0;i<pr.getPcFileName().size();i++){
				//projectApproval
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
			uploadFilesService.uploadFile(pr.getPc(),pr.getPcFileName(),"attachments_Img\\ScientificResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		//结题PDF
		if(pr.getKc()!=null&&pr.getKc().size()>0){
			for(int i=0;i<pr.getKcFileName().size();i++){
				//conclusion 结题
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
			uploadFilesService.uploadFile(pr.getKc(),pr.getKcFileName(),"attachments_Img\\ScientificResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		//申报书
		if(pr.getSf()!=null&&pr.getSf().size()>0){
			for(int i=0;i<pr.getSfFileName().size();i++){
				//declaration 申报书
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
			uploadFilesService.uploadFile(pr.getSf(),pr.getSfFileName(),"attachments_Img\\ScientificResearch\\"+pr.getId());
			tempName="";newName="";newNamelist.clear();
		}
		flag=super.update(pr);
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int review(List<Integer> ids) {
		/*StringBuilder sql=new StringBuilder("update t_scientificresearch p set p.status='1' where p.id=?");
		return super.updateORdeleteBatchByid(ids, sql.toString());*/
		return super.updateStateToOk(ids);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public InputStream exportData(ScientificResearch scientificresearch,
			String hql) {
		OutputStream os=null;
		String path_temp =ServletActionContext.getServletContext().getRealPath("/temp");
		String zip_temp =ServletActionContext.getServletContext().getRealPath("/ziptemp");//将压缩文件暂存到临时目录
		List<Object> listmap =getList(null, hql);
		String photopath = ServletActionContext.getServletContext().getRealPath("/attachments_Img/ScientificResearch");
		try {
		String excelFile = ServletActionContext.getServletContext().getRealPath(
				"ExcelTemplete\\科研课题.xls");//取出模板
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
	}

	private void FileHandle(Map<String, Object> map, String photopath,
			String path_temp) {
		String paperTitle=(String)map.get("topic");
		System.out.println(paperTitle);
		String teacherId=(String)map.get("teacherId");
		String fmName=(String) map.get("fileName");
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
		String projectCertificat=(String) map.get("projectCertificat");
		if(projectCertificat!=null&&!"".equals(projectCertificat.trim())){
			String [] fm=projectCertificat.split(",");
			for(int k=0;k<fm.length;k++){
				File fmt=new File(photopath+"/"+teacherId+paperTitle+"/"+fm[k]);
				if(fmt.exists()){
					File destf=new File(path_temp+"/"+teacherId+paperTitle);if(!destf.exists()) destf.mkdirs();
					FileUtil.copyFile(photopath+"/"+teacherId+paperTitle+"/"+fm[k],path_temp+"/"+teacherId+paperTitle+"/"+fm[k]);
				}
			}
		}
		String knotCertificat=(String) map.get("knotCertificat");
		if(knotCertificat!=null&&!"".equals(knotCertificat.trim())){
			String [] fm=knotCertificat.split(",");
			for(int k=0;k<fm.length;k++){
				File fmt=new File(photopath+"/"+teacherId+paperTitle+"/"+fm[k]);
				if(fmt.exists()){
					File destf=new File(path_temp+"/"+teacherId+paperTitle);if(!destf.exists()) destf.mkdirs();
					FileUtil.copyFile(photopath+"/"+teacherId+paperTitle+"/"+fm[k],path_temp+"/"+teacherId+paperTitle+"/"+fm[k]);
				}
			}
		}
	
		
	}


}
