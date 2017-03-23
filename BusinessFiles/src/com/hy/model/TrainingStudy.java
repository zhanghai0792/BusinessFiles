package com.hy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.hy.annotation.excelExport;


/**
 * TrainingStudy entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_trainingstudy"
    ,catalog="dpdatabase"
)

public class TrainingStudy  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;
     @excelExport(name="工号")
     private String teacherId;
 @excelExport(name="教师")
	 private String teacherName;
     @excelExport(name="进修类型")
     private String type;
     @excelExport(name="进修方向")
     private String direction;
     @excelExport(name="时间")
     private Date startDate;
     @excelExport(name="天数")
     private String totalDays;
     @excelExport(name="地点")
     private String address;
  
 
	@Transient
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
     
   


    // Constructors

    /** default constructor */
    public TrainingStudy() {
    }

    
    /** full constructor */
    public TrainingStudy(String type, String direction, Date startDate, String totalDays, String address, String teacherId) {
        this.type = type;
        this.direction = direction;
        this.startDate = startDate;
        this.totalDays = totalDays;
        this.address = address;
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
    
    @Column(name="type")

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="direction")

    public String getDirection() {
        return this.direction;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="startDate", length=10)
    @JSON(format="yyyy-MM-dd")
    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    @Column(name="totalDays")

    public String getTotalDays() {
        return this.totalDays;
    }
    
    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }
    
    @Column(name="address")

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
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
		return "TrainingStudy [id=" + id + ", type=" + type + ", direction="
				+ direction + ", startDate=" + startDate + ", totalDays="
				+ totalDays + ", address=" + address + ", teacherId="
				+ teacherId + "]";
	}


	@Override
	@Transient
	 @JSON(serialize=false) 
	public String getFoldName() {
		// TODO Auto-generated method stub
		return "";
	}

	public TrainingStudy(TrainingStudy t1,Teacher t2) {
		super();
		this.id = t1.id;
		this.teacherId = t1.teacherId;
		this.teacherName = t2.getName();
		this.type = t1.type;
		this.direction = t1.direction;
		this.startDate = t1.startDate;
		this.totalDays = t1.totalDays;
		this.address = t1.address;
	}
   








}