package com.hy.model;
// default package

import java.io.File;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.hy.annotation.excelExport;


/**
 * TeachingWorkload entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_teachingworkload"
    ,catalog="dpdatabase"
)
//教学工作量页面,理论教学页面
public class TeachingWorkload  implements java.io.Serializable,exportModel {


    // Fields    
     private Integer id;
     @excelExport(name="工号")
     private String teacherId;//教师id,name,positiontype

     @excelExport(name="教师")
	 private String teacherName;
	 @excelExport(name="职称")
	private String professTitle;
	 @excelExport(name="专/兼职")
	private String positiontype;
     
     @excelExport(name="课程代码")
     private String courseId;//课程代码
     @excelExport(name="课程名称")
     private String courseName;//课程名称
   //@excelExport(name="课程类型")
     private String courseType;//课程类型.理论课,实践课
     @excelExport(name="课程归属")
     private String courseTo;//课程归属
     @excelExport(name="班级")
     private String tclass;//班级,班级代码
     //private int tclassNum;//班级人数
     //private String material;//教材版别
     @excelExport(name="周课时")
     private String weekClassNum;//周课时
     @excelExport(name="总课时")
     private String periodNum;//总课时
@Transient   
public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
     
  
     
    private List<File> file;//图片文件
    private List<String>  fileFileName;//文件上传时的文件名
    private List<String> fileNewName;//新的文件名
	@Transient 
  	private String fileContentType;
    private String  fileName;//重新命名之后的字段名,以,分隔
    @excelExport(name="学期")
    private String termDate;


    // Constructors

    /** default constructor */
    public TeachingWorkload() {
    }

    
    /** full constructor */
    public TeachingWorkload(int id, String courseId, String courseName,
 			String courseType, String courseTo, String tclass,
 			 String weekClassNum, String periodNum,
 			String teacherId) {
 		super();
 		this.id = id;
 		this.courseId = courseId;
 		this.courseName = courseName;
 		this.courseType = courseType;
 		this.courseTo = courseTo;
 		this.tclass = tclass;
 		this.weekClassNum = weekClassNum;
 		this.periodNum = periodNum;
 		this.teacherId = teacherId;
 	}

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="id", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
 


	public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="courseId")

    public String getCourseId() {
        return this.courseId;
    }
    
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
    @Column(name="courseName")

    public String getCourseName() {
        return this.courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    @Column(name="tclass")

    public String getTclass() {
        return this.tclass;
    }
    
    public void setTclass(String tclass) {
        this.tclass = tclass;
    }
    
    // double-->precision=10, scale=2
    @Column(name="periodNum")

    public String getPeriodNum() {
        return this.periodNum;
    }
    
    public void setPeriodNum(String periodNum) {
        this.periodNum = periodNum;
    }
    
    @Column(name="teacherId")

    public String getTeacherId() {
        return this.teacherId;
    }
    
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Column(name="courseType")
	public String getCourseType() {
		return courseType;
	}


	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

    @Column(name="courseTo")
	public String getCourseTo() {
		return courseTo;
	}


	public void setCourseTo(String courseTo) {
		this.courseTo = courseTo;
	}

    @Column(name="weekClassNum")
	public String getWeekClassNum() {
		return weekClassNum;
	}


	public void setWeekClassNum(String weekClassNum) {
		this.weekClassNum = weekClassNum;
	}


	@Override
	public String toString() {
		return "TeachingWorkload [id=" + id + ", courseId=" + courseId
				+ ", courseName=" + courseName + ", courseType=" + courseType
				+ ", courseTo=" + courseTo + ", tclass=" + tclass
				+ ", weekClassNum=" + weekClassNum + ", periodNum=" + periodNum
				+ ", teacherId=" + teacherId + "]";
	}

	@Transient
	 @JSON(serialize=false) 
	public List<File> getFile() {
		return file;
	}


	public void setFile(List<File> file) {
		this.file = file;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getFileFileName() {
		return fileFileName;
	}


	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getFileNewName() {
		return fileNewName;
	}


	public void setFileNewName(List<String> fileNewName) {
		this.fileNewName = fileNewName;
	}

	@Column(name="fileName")
	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="termDate")
	public String getTermDate() {
		return termDate;
	}


	public void setTermDate(String termDate) {
		this.termDate = termDate;
	}

	@Transient
	 @JSON(serialize=false) 
	public String getFileContentType() {
		return fileContentType;
	}


	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	@Override
	@Transient
	 @JSON(serialize=false) 
	public String getFoldName() {
		// TODO Auto-generated method stub
		return "";
	}
	
	
    @Transient
	public String getProfessTitle() {
		return professTitle;
	}

	public void setProfessTitle(String professTitle) {
		this.professTitle = professTitle;
	}
     @Transient
	public String getPositiontype() {
		return positiontype;
	}

	public void setPositiontype(String positiontype) {
		this.positiontype = positiontype;
	}

	public TeachingWorkload(TeachingWorkload teachwork,Teacher teacher) {
		super(); 
		if(teachwork==null)
			 return;	
		this.id = teachwork.id;
		this.professTitle =teacher==null?"":teacher.getProfessTitle();
		this.positiontype =teacher==null?"":teacher.getPositiontype();
		this.courseId = teachwork.courseId;
		this.courseName = teachwork.courseName;
		this.courseType = teachwork.courseType;
		this.courseTo = teachwork.courseTo;
		this.tclass = teachwork.tclass;
		this.weekClassNum = teachwork.weekClassNum;
		this.periodNum = teachwork.periodNum;
		this.teacherName = teacher==null?"":teacher.getName();
		this.teacherId = teachwork.teacherId;
		this.termDate = teachwork.termDate;
	}


   








}