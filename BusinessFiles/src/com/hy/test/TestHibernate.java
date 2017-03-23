package com.hy.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.hy.action.BaseAction;
import com.hy.annotation.classToXLSSheet;
import com.hy.annotation.excelCell;
import com.hy.dao.BaseDao;
import com.hy.dao.CertificateDao;
import com.hy.dao.TeacherDao;
import com.hy.dao.impl.BaseDaoImpl;
import com.hy.dao.impl.CertificateImpl;
import com.hy.dao.impl.CertificateImpl2;
import com.hy.dao.impl.TeacherDaoImpl;
import com.hy.model.exportModel;
import com.hy.util.StringUtil;
import com.hy.util.fileDeleteThread;
import com.hy.util.myPOIExcelUtil;

public class TestHibernate {
	
	TeacherDao dao = new TeacherDaoImpl();
	@SuppressWarnings("rawtypes")
	BaseDao basedao = new BaseDaoImpl();
	
	/**
	 * @param 测试Hibernate
	 */
	public static void main(String[] args) {
		/*String s="2016-2017-3";
		System.out.println(s.substring(0, 10));*/
		//System.out.println(UUID.randomUUID().toString());
	/*  String v="2016-2";
	  String x=v.substring(5);
		String year=v.substring(0,4);
	   System.out.println(x);
	   System.out.println(year);*/
		/*String[] paths="123,333".split(",");
		for(String s:paths)
			System.out.println(s);*/
		
		/*CertificateDao dao = new CertificateImpl2();
		
		List<Integer> ids=new ArrayList<Integer>();
		ids.add(19);
		ids.add(20);
		ids.add(21);
		List<exportModel> models=dao.getByIds(ids);*/
		
		/*String trem= BaseAction.termChangeToYear("2015-2016-2");
		System.out.println(trem);*/
		/*//sheet表格的整个内容
		List<String[]> sheetContent=new ArrayList<String[]>(0);
		if(models!=null&&models.size()>0){
		String modelClassName=models.get(0).getClass().getName();
	    List<excelCell> sheetItems=classToXLSSheet.cxMap.get(modelClassName);
	    String sheetName=classToXLSSheet.sheetNames.get(modelClassName);
	    if(sheetItems==null)
	    	return;
	    int length=sheetItems.size();
	    String[] values=new String[length];
	    //第一行的行标题
	    for(int i=0;i<length;i++)
	    	values[i]=sheetItems.get(i).getName();
	    sheetContent.add(values);
		for(exportModel m:models)
			{
			values=new String[length];
			for(int i=0;i<length;i++){
				values[i]=classToXLSSheet.getObjectFieldValue(m, sheetItems.get(i).getField());
			}
			sheetContent.add(values);
			}
		if(sheetContent.size()>0){
			for(String[] row:sheetContent){
				for(int i=0;i<row.length;i++)
					System.out.print(row[i]+"   ");
				System.out.println();
			}
		}
		}*/
	/*	List<String[]> sheetContent=classToXLSSheet.getSheetContent(models);
		if(sheetContent!=null&&sheetContent.size()>0){
			File file=new File("E:\\aa.xls");
			try {
				FileOutputStream fos=new FileOutputStream(file);
				myPOIExcelUtil.export(fos, "测试", sheetContent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("ok");
		}*/

		//System.out.println(System.getProperty("sun.jnu.encoding"));
/* String hql="publishDate>=@2014-01-01@ and status=@1@ and level=@SCI,A&HCI@";
 
  String from=hql.replaceAll("@", "'");
  System.out.println(from);*/
	/*	String content="张海|123123|主编,周小雄|14181|编委,邵小兵|123|副主编,杨杰|6050001|编委,周小雄|14181|编委";
		System.out.println(StringUtil.bookAuthorNameDeal(content));*/
		String condition="author like 'a' and status='-1' or sex=1";
		condition=condition.replaceAll("status='-1'","status='0' or status='1'");
		System.out.println(condition);
	}
	@Test
	public  void  save(){
		//Teacher t=new Teacher("123", "123", new Date(), "汉", "1", "1", "1", new Date(), "1", new Date(), new Date(), "1", "1", "1", "1");
		/*t.setId("123");
		t.setName("王文华");
		t.setPassword("123");
		t.setNation("汉");
		t.setCardId("362421199412204113");
		t.setDept("1");
		t.setEmployDate(new Date());
		t.setBirthDate(new Date());
		t.setEmployTitle("qw");
		t.setFinalDegree("qw");*/
		//System.out.println(dao.update(t));

	//测试查询返回List<Map<>>
		
		 
		
	}
	@SuppressWarnings("unused")
	@Test
	public void test() throws IOException{
		//String root = File.listRoots()[0].toString();
		//File file = new File(root+"tr/tst.txt");
		//file.createNewFile();
		//FileOutputStream fis = new FileOutputStream(root+"\\tr\\tst.txt");  
		//file.mkdirs();
	}
	

}
