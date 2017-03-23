package com.hy.dao.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import org.hibernate.Query;

import com.hy.dao.PaperTeachingDao;
import com.hy.model.PaperTeaching;
import com.hy.model.Teacher;
import com.hy.util.FileUtil;
import com.hy.util.PageUtil;

@SuppressWarnings("rawtypes")
public class PaperTeachingImpl extends  BaseDaoImpl implements PaperTeachingDao{

	/*@SuppressWarnings("unchecked")
	public List<PaperTeaching> getList(PaperTeaching l) {
		List<PaperTeaching> list=super.search("FROM PaperTeaching");
		return list;
	}*/
	@SuppressWarnings("unchecked")
	public PageUtil<Object> getList(String hql,PageUtil<Object> pr) {
		
		pr=super.searchMoreTablePage(hql,pr);
		List<Object> resultList=pr.getList();
		List<Object> listobj=new ArrayList<Object>();
		for (int i = 0; i < resultList.size(); i++) { 
			Map<String,Object> map=new HashMap<String, Object>();
		    Object[] obj = (Object[])resultList.get(i);
		    PaperTeaching p=(PaperTeaching)obj[0];
		    Teacher t=(Teacher)obj[1];
		    //map.put("grade", p.getGrade());
		    map.put("id", p.getId());
		    map.put("termDate", p.getTermDate());
		     map.put("discountClassNum", p.getDiscountClassNum());
		     map.put("tclassNum", p.getTclassNum());
		    map.put("teacherId", p.getTeacherId());
		    map.put("teacherName", t.getName());
		    map.put("professTitle", t.getProfessTitle());
		   
		    listobj.add(map);
		}
		pr.setList(listobj);
		return pr;
	}
	@SuppressWarnings("unchecked")
	public boolean add(PaperTeaching le) {
		return super.add(le);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(PaperTeaching le) {
		 return super.update(le);
	}
	@SuppressWarnings("unchecked")
	public boolean delete(PaperTeaching le) {
		 return super.delete(le);
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public InputStream exportData(PaperTeaching pr, String hql) {
		InputStream inputstream = null;
		try {
		String excelFile = ServletActionContext.getServletContext().getRealPath(
				"ExcelTemplete\\论文指导-导出模板.xls");
			InputStream is = new FileInputStream(excelFile);
			Workbook wb = WorkbookFactory.create(is);
			
			List<Object> listmap =super.search(hql);
			System.out.println("teachingworkload:"+hql);
			
			Sheet sheet = wb.getSheetAt(0);
			CellStyle cs2 = wb.createCellStyle();
			// 创建两种字体
			Font f = wb.createFont();
			// 创建第一种字体样式（用于列名）
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
					cc2.setCellValue((String)map.get("professTitle"));
					cc2.setCellStyle(cs2);
					
					Cell cc3 = row.createCell(3);
					cc3.setCellType(Cell.CELL_TYPE_STRING);
					cc3.setCellValue((String)map.get("positiontype"));
					cc3.setCellStyle(cs2);
					
					Cell cc4 = row.createCell(4);
					cc4.setCellType(Cell.CELL_TYPE_STRING);
					cc4.setCellValue((String)map.get("courseName"));
					cc4.setCellStyle(cs2);
					
					Cell cc5 = row.createCell(5);
					cc5.setCellType(Cell.CELL_TYPE_STRING);
					cc5.setCellValue((String)map.get("courseType"));
					cc5.setCellStyle(cs2);
					
					Cell cc6 = row.createCell(6);
					cc6.setCellType(Cell.CELL_TYPE_STRING);
					cc6.setCellValue((String)map.get("tclass"));
					cc6.setCellStyle(cs2);
					
					
					
					Cell cc7 = row.createCell(7);
					cc7.setCellType(Cell.CELL_TYPE_STRING);
					cc7.setCellValue((String)map.get("tclassNum"));
					cc7.setCellStyle(cs2);
					
					Cell cc8 = row.createCell(8);
					cc8.setCellType(Cell.CELL_TYPE_STRING);
					cc8.setCellValue((String)map.get("weekNum"));
					cc8.setCellStyle(cs2);
					
					Cell cc9 = row.createCell(9);
					cc9.setCellType(Cell.CELL_TYPE_STRING);
					cc9.setCellValue((String)map.get("discountClassNum"));
					cc9.setCellStyle(cs2);
					
					}
				}
			inputstream=FileUtil.workbookInputStream(wb); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		return inputstream;
	}

	@Override
	public int importData(PaperTeaching pr,String[] excelTemp) {
		int size=0;
		try {
		List<Map<String, String>> listmap = FileUtil.analysisExcel(pr.getFile().get(0),excelTemp);
		 if(listmap==null||listmap.size()==0)return 0;	
		size=insertBatch(listmap,pr.getTermDate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	@SuppressWarnings("deprecation")
	private int insertBatch(List<Map<String, String>> listmap,String termDate) {
		String sql="insert into t_paperteaching(tclassNum,discountClassNum,teacherId,termDate) values(?,?,?,?)";
		try {
		Connection conn = getSession().connection();
		long l1=System.currentTimeMillis();
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		conn.setAutoCommit(false);
		String classNum;
		Map<String, String> map;
		for(int i = 0;i<listmap.size(); i++) {
			map=listmap.get(i);
			   classNum=map.get("discountClassNum");
				stmt.setString(1, map.get("tclassNum"));
				if(classNum==null||"".equals(classNum)){
					classNum="0";
				}
				stmt.setString(2, classNum);
				stmt.setString(3, map.get("teacherId"));
				stmt.setString(4, termDate);
		    stmt.addBatch();
		}
		stmt.executeBatch();
		conn.commit();
		//closeSession();
		/*long l2=System.currentTimeMillis();
		System.out.println("导入l2-l1:"+(l2-l1));*/
		
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return listmap.size();
	}
	public PaperTeachingImpl() {
		super();
		claz=PaperTeaching.class;
	}
	@Override
	public int delete(List<Integer> ids) {
		String hql="delete "+claz.getCanonicalName()+" where id in(:ids)";
		Query query=getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		
		return query.executeUpdate();
	}
   
	
}
