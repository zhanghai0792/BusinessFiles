package com.hy.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
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

import com.hy.action.BaseAction;
import com.hy.dao.TeachingMaterialDao;
import com.hy.model.Teacher;
import com.hy.model.TeachingMaterial;
import com.hy.util.FileUtil;
import com.hy.util.PageUtil;

@SuppressWarnings("rawtypes")
public class TeachingMaterialImpl extends  BaseDaoImpl implements TeachingMaterialDao {

	@SuppressWarnings("unchecked")
	@Override
	public PageUtil<Object> getList(String hql,PageUtil<Object> pr) {
		pr=super.searchMoreTablePage(hql, pr);
		List<Object> resultList=pr.getList();
		List<Object> listobj=new ArrayList<Object>();
		for (int i = 0; i < resultList.size(); i++) { 
			Map<String,Object> map=new HashMap<String, Object>();
		    Object[] obj = (Object[])resultList.get(i);
		    TeachingMaterial p=(TeachingMaterial)obj[0];
		    Teacher t=(Teacher)obj[1];
		    map.put("courseName", p.getCourseName());
		    map.put("guideBook", p.getGuideBook());
		    map.put("author", p.getAuthor());
		    map.put("publishing", p.getPublishing());
		    map.put("id", p.getId());
		    map.put("publishingDate", p.getPublishingDate());
		    map.put("editionNum", p.getEditionNum());
		    map.put("bookNum", p.getBookNum());
		    map.put("bookPrize", p.getBookPrize());
		    map.put("tclass", p.getTclass());
		    map.put("dept", p.getDept());
		    map.put("teacherId", p.getTeacherId());
		  /*  String termDate=p.getTermDate();
		    if(termDate!=null){
		    	if(termDate.length()==11)
		            termDate=BaseAction.termChangeToYear(termDate);		
		     }
		    	*/
		    map.put("termDate", p.getTermDate());
		    map.put("teacherName", t.getName());
		    map.put("professTitle", t.getProfessTitle());
		    map.put("positiontype", t.getPositiontype());
		    listobj.add(map);
		}
		pr.setList(listobj);
		return pr;
	}
	
	
	
	

	public TeachingMaterialImpl() {
		super();
		claz=TeachingMaterial.class;
	}





	@SuppressWarnings("unchecked")
	@Override
	public boolean add(TeachingMaterial pr) {
		return super.add(pr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(TeachingMaterial pr) {
		return super.update(pr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delete(List<Integer> ids) {
		StringBuilder sql=new StringBuilder("delete  from t_teachingmaterial  where id =?");
		return  super.updateORdeleteBatchByid(ids, sql.toString());
		//return super.delete(pr);
	}

	@Override
	public boolean importData(TeachingMaterial pr) {
		return false;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public InputStream exportData(TeachingMaterial teachingmaterial,
			String hql) {
		InputStream inputstream = null;
		try {
		String excelFile = ServletActionContext.getServletContext().getRealPath(
				"ExcelTemplete\\教材管理-导出模板.xls");
			InputStream is = new FileInputStream(excelFile);
			Workbook wb = WorkbookFactory.create(is);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			List<Object> listmap =getList(hql, null).getList();
			Sheet sheet = wb.getSheetAt(0);
			CellStyle cs2 = wb.createCellStyle();
			// 创建两种字体
			Font f = wb.createFont();
			// 创建第一种字体样式（用于列名）
			f.setFontHeightInPoints((short) 12);
			f.setColor(IndexedColors.BLACK.getIndex());
			System.out.println(listmap.get(0));
			//List<Object> listmap =super.search(hql);//导入查询不要分页,直接查出所有的数据
			cs2.setFont(f);
			cs2.setBorderLeft(CellStyle.BORDER_MEDIUM);
			cs2.setBorderRight(CellStyle.BORDER_MEDIUM);
			cs2.setBorderTop(CellStyle.BORDER_MEDIUM);
			cs2.setBorderBottom(CellStyle.BORDER_MEDIUM);
			cs2.setAlignment(CellStyle.ALIGN_CENTER);
			for (int i = 0; i < listmap.size(); i++) {
				
				Map<String, Object> map= (Map<String, Object>) listmap.get(i);
				Row row = sheet.createRow((short) (3+ i));
				for (int j = 0; j < 11; j++) {
					
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
					cc2.setCellValue((String)map.get("courseName"));
					cc2.setCellStyle(cs2);
					
					Cell cc3 = row.createCell(3);
					cc3.setCellType(Cell.CELL_TYPE_STRING);
					cc3.setCellValue((String)map.get("guideBook"));
					cc3.setCellStyle(cs2);
					
					Cell cc4 = row.createCell(4);
					cc4.setCellType(Cell.CELL_TYPE_STRING);
					cc4.setCellValue((String)map.get("author"));
					cc4.setCellStyle(cs2);
					
					Cell cc5 = row.createCell(5);
					cc5.setCellType(Cell.CELL_TYPE_STRING);
					cc5.setCellValue((String)map.get("publishing"));
					cc5.setCellStyle(cs2);
					
					Cell cc6 = row.createCell(6);
					cc6.setCellType(Cell.CELL_TYPE_STRING);
					cc6.setCellValue((String)map.get("publishingDate"));
					cc6.setCellStyle(cs2);
					
					Cell cc7 = row.createCell(7);
					cc7.setCellType(Cell.CELL_TYPE_STRING);
					cc7.setCellValue((String)map.get("editionNum"));
					cc7.setCellStyle(cs2);
					
					Cell cc8 = row.createCell(8);
					cc8.setCellType(Cell.CELL_TYPE_STRING);
					cc8.setCellValue((String)map.get("bookNum"));
					cc8.setCellStyle(cs2);
					
					Cell cc9 = row.createCell(9);
					cc9.setCellType(Cell.CELL_TYPE_STRING);
					cc9.setCellValue((String)map.get("bookPrize"));
					cc9.setCellStyle(cs2);
					
					Cell cc10 = row.createCell(10);
					cc10.setCellType(Cell.CELL_TYPE_STRING);
					cc10.setCellValue((String)map.get("tclass"));
					cc10.setCellStyle(cs2);
					
					Cell cc11 = row.createCell(11);
					cc11.setCellType(Cell.CELL_TYPE_STRING);
					cc11.setCellValue((String)map.get("dept"));
					cc11.setCellStyle(cs2);
					
					}
				}
			inputstream=FileUtil.workbookInputStream(wb); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		return inputstream;
	}

}
