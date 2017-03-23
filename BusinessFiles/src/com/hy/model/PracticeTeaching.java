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
 * PracticeTeaching entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_practiceteaching"
    ,catalog="dpdatabase"
)

public class PracticeTeaching  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;
     @excelExport(name="工号")
     private String teacherId;
     
     @excelExport(name="教师")
	 private String teacherName;
	 @excelExport(name="职称")
	private String professTitle;
	 @excelExport(name="专/兼职")
	private String positiontype;
	 
     
     @excelExport(name="课程名称")
     private String courseName;
     @excelExport(name="课程性质")
     private String courseType;
     @excelExport(name="班级代码")
     private String tclass;
     @excelExport(name="学生数")
     private String tclassNum;
     @excelExport(name="实践周数")
     private String weekNum;
     @excelExport(name="总课时")
     private String discountClassNum;
     
     
     private List<File> file;//图片文件
 	@Transient 
  	private String fileContentType;
     private List<String>  fileFileName;//文件上传时的文件名
     private List<String> fileNewName;//新的文件名
     private String  fileName;//重新命名之后的字段名,以,分隔
     @excelExport(name="学期")
     private String termDate;

 	@Transient
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
     
    // Constructors

    /** default constructor */
    public PracticeTeaching() {
    }

    
    /** full constructor */
    public PracticeTeaching(String courseName, String courseType, String tclass, String tclassNum, String weekNum, String discountClassNum, String teacherId) {
        this.courseName = courseName;
        this.courseType = courseType;
        this.tclass = tclass;
        this.tclassNum = tclassNum;
        this.weekNum = weekNum;
        this.discountClassNum = discountClassNum;
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
    
    @Column(name="courseName")

    public String getCourseName() {
        return this.courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    @Column(name="courseType")

    public String getCourseType() {
        return this.courseType;
    }
    
    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
    
    @Column(name="tclass")

    public String getTclass() {
        return this.tclass;
    }
    
    public void setTclass(String tclass) {
        this.tclass = tclass;
    }
    
    @Column(name="tclassNum")

    public String getTclassNum() {
        return this.tclassNum;
    }
    
    public void setTclassNum(String tclassNum) {
        this.tclassNum = tclassNum;
    }
    
    @Column(name="weekNum")

    public String getWeekNum() {
        return this.weekNum;
    }
    
    public void setWeekNum(String weekNum) {
        this.weekNum = weekNum;
    }
    
    @Column(name="discountClassNum")

    public String getDiscountClassNum() {
        return this.discountClassNum;
    }
    
    public void setDiscountClassNum(String discountClassNum) {
        this.discountClassNum = discountClassNum;
    }
    
    @Column(name="teacherId")

    public String getTeacherId() {
        return this.teacherId;
    }
    
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }


	@Override
	public String toString() {
		return "PracticeTeaching [id=" + id + ", courseName=" + courseName
				+ ", courseType=" + courseType + ", tclass=" + tclass
				+ ", tclassNum=" + tclassNum + ", weekNum=" + weekNum
				+ ", discountClassNum=" + discountClassNum + ", teacherId="
				+ teacherId + "]";
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

		public PracticeTeaching(PracticeTeaching p,Teacher teacher) {
			super();
			this.teacherId = p.teacherId;
			this.teacherName =teacher.getName();
			this.professTitle = teacher.getProfessTitle();
			this.positiontype = teacher.getPositiontype();
			this.courseName = p.courseName;
			this.courseType = p.courseType;
			this.tclass = p.tclass;
			this.tclassNum = p.tclassNum;
			this.weekNum = p.weekNum;
			this.discountClassNum = p.discountClassNum;
			this.termDate = p.termDate;
		}




}