package com.hy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.hy.annotation.excelExport;


/**
 * OtherWorks entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_otherworks"
    ,catalog="dpdatabase"
)

public class OtherWorks  implements java.io.Serializable,exportModel {
/*description	
	eeeeeeee
	time	
	2017-01-01
	work	
	学科建设
*/
    // Fields    

     private Integer id;
     @excelExport(name="工号")
     private String teacherId;
     @excelExport(name="教师")
	 private String teacherName;
     @excelExport(name="工作性质")
     private String work;
     @excelExport(name="工作描述")
     private String description;
     @excelExport(name="时间")
     private Date time;

 	@Transient
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
     
    


    // Constructors

    /** default constructor */
    public OtherWorks() {
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
    
    @Column(name="work")

    public String getWork() {
        return this.work;
    }
    
    public void setWork(String work) {
        this.work = work;
    }
    
    @Column(name="description")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="time")
    @JSON(format="yyyy-MM-dd")
    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
    
    @Column(name="teacherId")

    public String getTeacherId() {
        return this.teacherId;
    }
    
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }


	@Override
	@Transient
	 @JSON(serialize=false) 
	public String getFoldName() {
		// TODO Auto-generated method stub
		return "";
	}

	public OtherWorks(OtherWorks o,Teacher t) {
		super();
		this.id = o.id;
		this.teacherId = o.teacherId;
		this.teacherName = t.getName();
		this.work = o.work;
		this.description = o.description;
		this.time = o.time;
	}
   








}