package com.hy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.hy.annotation.excelExport;


/**
 * TeachingMaterial entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_teachingmaterial"
    ,catalog="dpdatabase"
)
//教参
public class TeachingMaterial  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;
     @excelExport(name="工号")
     private String teacherId;
     @excelExport(name="授课教师")
	 private String teacherName;
     @excelExport(name="课程名称")
     private String courseName;//课程名称
     @excelExport(name="教参名称")
     private String guideBook;//教参名称
     @excelExport(name="作者")
     private String author;//作者
     @excelExport(name="出版社")
     private String publishing;//出版社(全名)
     @excelExport(name="出版时间")
     private String publishingDate;//(出版时间 2015  2010/8/1 2014年6月1日 2013年11月)
     @excelExport(name="版次")
     private String editionNum;//版次
     @excelExport(name="书号")
     private String bookNum;//书号(ISBN)
     
 	@Transient
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
     

     //private String teacherName;//教师用数(龚方青熊洁)
     @excelExport(name="教材获奖情况")
     private String bookPrize;//(教材获奖情况)
     @excelExport(name="使用年级班级")
     private String tclass;//使用专业年级或班级
     @excelExport(name="教研室")
     private String dept;//教研室
     @excelExport(name="学期")
     private String termDate;//使用学期

    // Constructors
     @Column(name="termDate")
    public String getTermDate() {
		return termDate;
	}


	public void setTermDate(String termDate) {
		this.termDate = termDate;
	}


	/** default constructor */
    public TeachingMaterial() {
    }

    
    /** full constructor */
    public TeachingMaterial(String courseName, String guideBook, String author, String publishing, String publishingDate, String editionNum, String bookNum, String teacherName, String bookPrize, String tclass, String dept) {
        this.courseName = courseName;
        this.guideBook = guideBook;
        this.author = author;
        this.publishing = publishing;
        this.publishingDate = publishingDate;
        this.editionNum = editionNum;
        this.bookNum = bookNum;
        this.teacherId = teacherName;
        this.bookPrize = bookPrize;
        this.tclass = tclass;
        this.dept = dept;
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
    
    @Column(name="guideBook")

    public String getGuideBook() {
        return this.guideBook;
    }
    
    public void setGuideBook(String guideBook) {
        this.guideBook = guideBook;
    }
    
    @Column(name="author")

    public String getAuthor() {
        return this.author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    @Column(name="publishing")

    public String getPublishing() {
        return this.publishing;
    }
    
    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }
    
    @Column(name="publishingDate")

    public String getPublishingDate() {
        return this.publishingDate;
    }
    
    public void setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
    }
    
    @Column(name="editionNum")

    public String getEditionNum() {
        return this.editionNum;
    }
    
    public void setEditionNum(String editionNum) {
        this.editionNum = editionNum;
    }
    
    @Column(name="bookNum")

    public String getBookNum() {
        return this.bookNum;
    }
    
    public void setBookNum(String bookNum) {
        this.bookNum = bookNum;
    }
    
    @Column(name="teacherId")

    public String getTeacherId() {
        return this.teacherId;
    }
    
    public void setTeacherId(String teacherName) {
        this.teacherId = teacherName;
    }
    
    @Column(name="bookPrize")

    public String getBookPrize() {
        return this.bookPrize;
    }
    
    public void setBookPrize(String bookPrize) {
        this.bookPrize = bookPrize;
    }
    
    @Column(name="tclass")

    public String getTclass() {
        return this.tclass;
    }
    
    public void setTclass(String tclass) {
        this.tclass = tclass;
    }
    
    @Column(name="dept")

    public String getDept() {
        return this.dept;
    }
    
    public void setDept(String dept) {
        this.dept = dept;
    }


	@Override
	@Transient
	 @JSON(serialize=false) 
	public String getFoldName() {
		// TODO Auto-generated method stub
		return "";
	}

	public TeachingMaterial(TeachingMaterial t1,Teacher t2) {
		super();
		this.id = t1.id;
		this.teacherId = t1.teacherId;
		this.teacherName = t2.getName();
		this.courseName = t1.courseName;
		this.guideBook = t1.guideBook;
		this.author = t1.author;
		this.publishing = t1.publishing;
		this.publishingDate = t1.publishingDate;
		this.editionNum = t1.editionNum;
		this.bookNum = t1.bookNum;
		this.bookPrize = t1.bookPrize;
		this.tclass = t1.tclass;
		this.dept = t1.dept;
		this.termDate = t1.termDate;
	}
   








}