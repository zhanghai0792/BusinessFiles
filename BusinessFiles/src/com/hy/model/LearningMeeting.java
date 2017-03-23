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
 * LearningMeeting entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_learningmeeting"
    ,catalog="dpdatabase"
)

public class LearningMeeting  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;
     @excelExport(name="工号")
     private String teacherId;
     @excelExport(name="教师")
	 private String teacherName;
     @excelExport(name="会议名称")
     private String name;
     @excelExport(name="专业方向")
     private String major;
     @excelExport(name="组委会")
     private String organization;
     @excelExport(name="会议时间")
     private Date time;
     @excelExport(name="会议地点")
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
    public LearningMeeting() {
    }

    
    /** full constructor */
    public LearningMeeting(String name, String major, String organization, Date time, String address, String teacherId) {
        this.name = name;
        this.major = major;
        this.organization = organization;
        this.time = time;
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
    
    @Column(name="name")

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="major")

    public String getMajor() {
        return this.major;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    
    @Column(name="organization")

    public String getOrganization() {
        return this.organization;
    }
    
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="time", length=10)
    @JSON(format="yyyy-MM-dd")
    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
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
		return "LearningMeeting [id=" + id + ", name=" + name + ", major="
				+ major + ", organization=" + organization + ", time=" + time
				+ ", address=" + address + ", teacherId=" + teacherId + "]";
	}


	@Override
	@Transient
	 @JSON(serialize=false) 
	public String getFoldName() {
		// TODO Auto-generated method stub
		return "";
	}

	public LearningMeeting(LearningMeeting l,Teacher t) {
		super();
		this.id = l.id;
		this.teacherId = l.teacherId;
		this.teacherName =t.getName();
		this.name = l.name;
		this.major = l.major;
		this.organization = l.organization;
		this.time = l.time;
		this.address = l.address;
	}
   








}