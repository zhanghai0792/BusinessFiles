package com.hy.annotation;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hy.model.Certificate;
import com.hy.model.EducationalResearch;
import com.hy.model.GuideStudent;
import com.hy.model.LearningExperience;
import com.hy.model.LearningGroup;
import com.hy.model.LearningMeeting;
import com.hy.model.MonographTextbook;
import com.hy.model.OtherWorks;
import com.hy.model.PaperResearch;
import com.hy.model.PaperTeaching;
import com.hy.model.Patent;
import com.hy.model.PracticeTeaching;
import com.hy.model.ScientificAchievement;
import com.hy.model.ScientificResearch;
import com.hy.model.Teacher;
import com.hy.model.TeachingAchievement;
import com.hy.model.TeachingMatch;
import com.hy.model.TeachingMaterial;
import com.hy.model.TeachingOtherPrize;
import com.hy.model.TeachingWorkload;
import com.hy.model.TrainingStudy;
import com.hy.model.WorkingExperience;
import com.hy.model.exportModel;

public class classToXLSSheet {
 public static Map<String, List<excelCell>> cxMap=new HashMap<String, List<excelCell>>();
 public static Map<String, String> sheetNames=new HashMap<String, String>();
 private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
 static{
	 cxMap.put("com.hy.model.Certificate", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.Certificate", "证书清单");
	 
	 cxMap.put("com.hy.model.EducationalResearch", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.EducationalResearch", "教改课题清单");
	 cxMap.put("com.hy.model.GuideStudent", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.GuideStudent", "指导学生清单");
	 cxMap.put("com.hy.model.LearningExperience", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.LearningExperience", "学习经历清单");
	 cxMap.put("com.hy.model.LearningGroup", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.LearningGroup", "学术团队清单");
	 cxMap.put("com.hy.model.LearningMeeting", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.LearningMeeting","学术会议清单");

	 cxMap.put("com.hy.model.MonographTextbook", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.MonographTextbook", "专著及教材清单");
	 cxMap.put("com.hy.model.OtherWorks", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.OtherWorks", "其他工作清单");
		
	 cxMap.put("com.hy.model.PaperResearch", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.PaperResearch", "学术论文清单");
		
	 cxMap.put("com.hy.model.PaperTeaching", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.PaperTeaching", "论文指导清单");
		
	 
	 cxMap.put("com.hy.model.Patent", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.Patent", "专利清单");
		
	 
	 cxMap.put("com.hy.model.PracticeTeaching", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.PracticeTeaching", "实践教学清单");
		
	 
	 cxMap.put("com.hy.model.ScientificAchievement", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.ScientificAchievement", "科研成果清单");
		
	 
	 cxMap.put("com.hy.model.ScientificResearch", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.ScientificResearch", "科研课题清单");
		
	 cxMap.put("com.hy.model.Teacher", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.Teacher", "教师详情");
		
	 cxMap.put("com.hy.model.TeachingAchievement", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.TeachingAchievement", "教学成果清单");
		
	 cxMap.put("com.hy.model.TeachingMatch", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.TeachingMatch", "教学比赛清单");
		
	 cxMap.put("com.hy.model.TeachingMaterial", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.TeachingMaterial", "教材清单");
		
	 cxMap.put("com.hy.model.TeachingOtherPrize", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.TeachingOtherPrize", "其他获奖清单");
	 cxMap.put("com.hy.model.TeachingWorkload", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.TeachingWorkload", "教学工作量清单");
		
	 cxMap.put("com.hy.model.TrainingStudy", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.TrainingStudy", "进修培训清单");
		
	 cxMap.put("com.hy.model.WorkingExperience", new ArrayList<excelCell>(0));
	 sheetNames.put("com.hy.model.WorkingExperience", "工作经历清单");
						
	 
	 init();
	 
	 
	 
	 //System.out.println("类解析完成");
	 
 }
 private static void init(){
	 for(Entry<String, List<excelCell>> entry:cxMap.entrySet()){
		 try {
			getFields(Class.forName(entry.getKey()),entry.getValue());
		} catch (ClassNotFoundException e) {
			System.out.println(entry.getKey()+" 不存在");
			e.printStackTrace();
		}
	 }
 }
 //分析类中指定注解的字段
 private static void getFields(Class claz,List<excelCell> list){
	 Field[] fields=claz.getDeclaredFields();
     for(Field f:fields){
     	if(f.isAnnotationPresent((excelExport.class))){
     		f.setAccessible(true);
     	excelExport e=f.getAnnotation(excelExport.class);
     	list.add(new excelCell(e.name(),f));
     	}
     }
     //System.out.println("加载"+claz.getName()+"完成");
 }
 
 public static String getObjectFieldValue(Object object,Field field){
 Class typeClaz=field.getType();
	try {
		Object value=field.get(object);
		//System.out.println("b"+value+"a");
		if(value==null){
			//System.out.println("value为null");
			return "null";}
		if("".equals(value)){
			//System.out.println("value为''");
			return "null";}
		if(typeClaz==Date.class){
			return sdf.format(value);
		}else{
			return value.toString();
		}
	} catch (Exception e) {
		e.printStackTrace();
		return "null";
	} 
 }
 
 
 public static List<String[]> getSheetContent(List models){
	//sheet表格的整个内容
			List<String[]> sheetContent=new ArrayList<String[]>(0);
			if(models!=null&&models.size()>0){
			String modelClassName=models.get(0).getClass().getName();
		    List<excelCell> sheetItems=classToXLSSheet.cxMap.get(modelClassName);
		    //String sheetName=classToXLSSheet.sheetNames.get(modelClassName);
		    if(sheetItems==null)
		    	return sheetContent;
		    int length=sheetItems.size()+1;
		    String[] values=new String[length];
		    //第一行的行标题
		    values[0]="序号";
		    for(int i=1;i<length;i++)
		    	values[i]=sheetItems.get(i-1).getName();
		    sheetContent.add(values);
		     int index=0;
			for(Object m:models)
				{
				index++;
				values=new String[length];
				  values[0]=index+"";
				for(int i=1;i<length;i++){
				 	values[i]=classToXLSSheet.getObjectFieldValue(m, sheetItems.get(i-1).getField());
				    // System.out.println(index+" "+i+" ="+values[i]);
				}
				sheetContent.add(values);
				}
			
 }
			return sheetContent;
}
 
 public static String getSheetTitle(List models){
	 String title=sheetNames.get(models.get(0).getClass().getName());
	 if(title==null)
	 return "未命名";
	 else
		 return title;
 }

}
