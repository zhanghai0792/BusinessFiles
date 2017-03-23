package com.hy.dao.impl;

import java.io.ByteArrayOutputStream;
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
import org.hibernate.Session;

import com.hy.dao.TeachingWorkloadDao;
import com.hy.model.Teacher;
import com.hy.model.TeachingWorkload;
import com.hy.model.Teacher;
import com.hy.util.FileUtil;
import com.hy.util.PageUtil;

@SuppressWarnings("rawtypes")
public class TeachingWorkloadImpl extends  BaseDaoImpl implements TeachingWorkloadDao{
	
	//private UploadFilesService uploadFilesService=new UploadFilesServiceImpl();
	/*@SuppressWarnings("unchecked")
	@Override
	public List<TeachingWorkload> getList(TeachingWorkload ttw) {
		List<TeachingWorkload> list=super.search("FROM TeachingWorkload");
		return list;
	}*/
	
	
	
	@SuppressWarnings("unchecked")
	public PageUtil<Object> getList(String hql,PageUtil<Object> pr) {
		
		pr=super.searchMoreTablePage(hql,pr);
		List<Object> listobj=new ArrayList<Object>();
		List<Object> resultList=pr.getList();
		for (int i = 0; i < resultList.size(); i++) { 
			Map<String,Object> map=new HashMap<String, Object>();
		    Object[] obj = (Object[])resultList.get(i);
		    TeachingWorkload p=(TeachingWorkload)obj[0];
		    Teacher t=(Teacher)obj[1];
		    map.put("courseName", p.getCourseName());
		    map.put("courseType", p.getCourseType());
		    map.put("courseId", p.getCourseId());
		    map.put("courseTo", p.getCourseTo());
		    map.put("id", p.getId());
		    map.put("tclass", p.getTclass());
		    map.put("weekClassNum", p.getWeekClassNum());
		    map.put("periodNum", p.getPeriodNum());
		    map.put("teacherId", p.getTeacherId());
		    map.put("termDate", p.getTermDate());
		    map.put("teacherName", t.getName());
		    map.put("professTitle", t.getProfessTitle());
		    map.put("positiontype", t.getPositiontype());
		    listobj.add(map);
		}
		pr.setList(listobj);
		return pr;
	}
	
	public TeachingWorkloadImpl() {
		super();
		claz=TeachingWorkload.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(TeachingWorkload le) {
		return super.add(le);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean update(TeachingWorkload le) {
		 return super.update(le);
	}
	@SuppressWarnings("unchecked")
	public boolean delete(TeachingWorkload le) {
		 return super.delete(le);
	}

	/*@Override
	public int importData(TeachingWorkload le) {
		int size=0;
			try {
				if(le.getFile().size()>0){
					List<Map<String, String>> listmap = FileUtil.analysisExcel(le.getFile().get(0));
					size=insertBatch(listmap,le.getTermDate());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return size;
	}*/
	
	@Override
	public int importData(TeachingWorkload le,String[] excelTemp) {
		int size=0;
			try {
				if(le.getFile().size()>0){
					List<Map<String, String>> listmap = FileUtil.analysisExcel(le.getFile().get(0),excelTemp);
					 if(listmap==null||listmap.size()==0)return 0;
					size=insertBatch(listmap,le.getTermDate());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return size;
	}
	
	@SuppressWarnings("deprecation")
	private int insertBatch(final List<Map<String, String>> list,String le){
		String sql="insert into t_teachingworkload(courseId,courseName,courseType,courseTo,tclass,weekClassNum,periodNum,teacherId,termDate) values(?,?,?,?,?,?,?,?,?)";
		//Session s=getSession();
		Connection conn =getSession().connection();
		try {
		long l1=System.currentTimeMillis();
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		conn.setAutoCommit(false);
		for(int i = 0;i<list.size(); i++) {
			Map<String, String> map=list.get(i);
				stmt.setString(1, map.get("courseId"));
				stmt.setString(2, map.get("courseName"));
				stmt.setString(3, map.get("courseType"));
				stmt.setString(4, map.get("courseTo"));
				stmt.setString(5, map.get("tclass"));
				stmt.setString(6, map.get("weekClassNum"));
				stmt.setString(7, map.get("periodNum"));
				stmt.setString(8, map.get("teacherId"));
				stmt.setString(9, le);
				//stmt.setString(10, map.get("teacherName"));
		    stmt.addBatch();
		    if (i % 5000 == 0) {
		        stmt.executeBatch();
		        conn.commit();
		    }
		}
		stmt.executeBatch();
		conn.commit();
		//closeSession();
		/*if(s!=null){
			s.close();
		}*/
		
		/*long l2=System.currentTimeMillis();
		System.out.println("导入l2-l1:"+(l2-l1));*/
		
		} catch (SQLException e) {
			e.printStackTrace();
			closeSession();
			return 0;
		}
		return list.size();
	}

	@SuppressWarnings({ "deprecation", "unused", "unchecked", })
	@Override
	public InputStream exportData(TeachingWorkload teachingworkload,String hql) {
		InputStream inputstream = null;
		try {
		String excelFile = ServletActionContext.getServletContext().getRealPath(
				"ExcelTemplete\\理论教学 - 导出模板.xls");
			InputStream is = new FileInputStream(excelFile);
			Workbook wb = WorkbookFactory.create(is);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			
			//List<Object> listmap =super.search(hql);//导入查询不要分页,直接查出所有的数据
			List<Object> listmap =getList(hql, null).getList();
			System.out.println(listmap.get(0));
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
					cc4.setCellValue((String)map.get("courseId"));
					cc4.setCellStyle(cs2);
					
					Cell cc5 = row.createCell(5);
					cc5.setCellType(Cell.CELL_TYPE_STRING);
					cc5.setCellValue((String)map.get("courseName"));
					cc5.setCellStyle(cs2);
					
					Cell cc6 = row.createCell(6);
					cc6.setCellType(Cell.CELL_TYPE_STRING);
					cc6.setCellValue((String)map.get("courseTo"));
					cc6.setCellStyle(cs2);
					
					Cell cc7 = row.createCell(7);
					cc7.setCellType(Cell.CELL_TYPE_STRING);
					cc7.setCellValue((String)map.get("courseType"));
					cc7.setCellStyle(cs2);
					
					Cell cc8 = row.createCell(8);
					cc8.setCellType(Cell.CELL_TYPE_STRING);
					cc8.setCellValue((String)map.get("tclass"));
					cc8.setCellStyle(cs2);
					
					Cell cc9 = row.createCell(9);
					cc9.setCellType(Cell.CELL_TYPE_STRING);
					cc9.setCellValue((String)map.get("weekClassNum"));
					cc9.setCellStyle(cs2);
					
					Cell cc10 = row.createCell(10);
					cc10.setCellType(Cell.CELL_TYPE_STRING);
					cc10.setCellValue((String)map.get("periodNum"));
					cc10.setCellStyle(cs2);
					}
				}
			inputstream=FileUtil.workbookInputStream(wb); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		return inputstream;
	}

	public int delete(List<Integer> ids) {
		String hql="delete from "+claz.getCanonicalName()+" where id in(:ids)";
		Query query=getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		
		return query.executeUpdate();
	}

	@Override
	public Map<String, List> getWorksTearm(String workHql, String practiceHql, String paperhql) {
		
		Map<String,List> map=new HashMap<String,List>(0);
		List list=new ArrayList(0);
		Query query;
		query=getSession().createQuery(workHql);
		list=query.list();
		map.put("teachings", list);
	   
		query=getSession().createQuery(practiceHql);
		list=query.list();
		map.put("practices", list);
	   
		query=getSession().createQuery(paperhql);
		list=query.list();
		map.put("papers", list);
	   
		return map;
	}
   

}
