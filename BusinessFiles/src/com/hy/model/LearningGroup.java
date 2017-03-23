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
 * LearningGroup entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_learninggroup"
    ,catalog="dpdatabase"
)

public class LearningGroup  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;
     @excelExport(name="工号")
     private String teacherId;
     
     @excelExport(name="教师")
	 private String teacherName;
     
     @excelExport(name="团队名称")
     private String name;
     @excelExport(name="团队级别")
     private String level;
     @excelExport(name="方向")
     private String major;
     @excelExport(name="担任职务")
     private String job;
     

 	@Transient
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
   


    // Constructors

    /** default constructor */
    public LearningGroup() {
    }

    
    /** full constructor */
    public LearningGroup(String name, String level, String major, String job, String teacherId) {
        this.name = name;
        this.level = level;
        this.major = major;
        this.job = job;
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
    
    @Column(name="name")

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="level")

    public String getLevel() {
        return this.level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    @Column(name="major")

    public String getMajor() {
        return this.major;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    
    @Column(name="job")

    public String getJob() {
        return this.job;
    }
    
    public void setJob(String job) {
        this.job = job;
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
		return "LearningGroup [id=" + id + ", name=" + name + ", level="
				+ level + ", major=" + major + ", job=" + job + ", teacherId="
				+ teacherId + "]";
	}


	@Override
	@Transient
	 @JSON(serialize=false) 
	public String getFoldName() {
		// TODO Auto-generated method stub
		return "";
	}

	public LearningGroup(LearningGroup l,Teacher t) {
		super();
		this.id = l.id;
		this.teacherId = l.teacherId;
		this.teacherName = t.getName();
		this.name = l.name;
		this.level = l.level;
		this.major = l.major;
		this.job = l.job;
	}
   
    







}