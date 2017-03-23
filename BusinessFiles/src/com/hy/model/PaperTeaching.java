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
 * PaperTeaching entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_paperteaching"
    ,catalog="dpdatabase"
)

public class PaperTeaching  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;
     
     @excelExport(name="工号")
     private String teacherId;
     @excelExport(name="授课教师")
	 private String teacherName;
     
     @excelExport(name="职称")
 	private String professTitle;
 	

    @Transient
 	public String getProfessTitle() {
 		return professTitle;
 	}

 	public void setProfessTitle(String professTitle) {
 		this.professTitle = professTitle;
 	}
     
     
      @excelExport(name="学生数")
     private String tclassNum;
     @excelExport(name="折算课时")
     private String discountClassNum;
  
     @excelExport(name="学期")
     private String termDate;


 	@Transient
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
     
     
     private List<File> file;//图片文件
 
  	private String fileContentType;
     private List<String>  fileFileName;//文件上传时的文件名
     private List<String> fileNewName;//新的文件名
     private String  fileName;//重新命名之后的字段名,以,分隔

    // Constructors

    /** default constructor */
    public PaperTeaching() {
    }

    
    /** full constructor */
    public PaperTeaching( String courseName, String courseType, String tclass, String tclassNum, String weekNum, String discountClassNum, String teacherId) {
        this.tclassNum = tclassNum;
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
    
    /*@Column(name="grade")

    public String getGrade() {
        return this.grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }*/
    
   
    
    @Column(name="tclassNum")

    public String getTclassNum() {
        return this.tclassNum;
    }
    
    public void setTclassNum(String tclassNum) {
        this.tclassNum = tclassNum;
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

	@Transient
	 @JSON(serialize=false) 
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
		this.termDate =termDate;
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

	public PaperTeaching(PaperTeaching p,Teacher teacher) {
		super();
		this.id = p.id;
		this.teacherId = p.teacherId;
		this.teacherName = teacher.getName();
		this.professTitle = teacher.getProfessTitle();
		
		this.tclassNum = p.tclassNum;
		
		this.discountClassNum = p.discountClassNum;
		this.termDate = p.termDate;
	}







}