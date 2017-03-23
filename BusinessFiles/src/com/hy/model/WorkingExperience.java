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



@SuppressWarnings("serial")
@Entity
@Table(name="t_workingexperience"
    ,catalog="dpdatabase"
)

public class WorkingExperience  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;//自增长id
     @excelExport(name="工号")
     private String teacherId;//所属教师id
     @excelExport(name="教师")
   	 private String teacherName;
     @excelExport(name="工作单位")
     private String company;//工作单位
     @excelExport(name="职务")
     private String role;//职务
     @excelExport(name="入职时间")
     private Date startDate;//入职时间
     @excelExport(name="离职时间")
     private Date endDate;//离职时间
    
   
	@Transient
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
     
  


    // Constructors

    /** default constructor */
    public WorkingExperience() {
    }

    
    /** full constructor */
    public WorkingExperience(String company, String role, Date startDate, Date endDate,String teacherId) {
        this.company = company;
        this.role = role;
        this.startDate = startDate;
        this.endDate = endDate;
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
    
    @Column(name="company")

    public String getCompany() {
        return this.company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    @Column(name="role")

    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    @JSON(format="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name="startDate", length=10)

    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="endDate", length=10)
    @JSON(format="yyyy-MM-dd")
    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
   
    @Column(name = "teacherId")
	public String getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}


	@Override
	public String toString() {
		return "WorkingExperience [id=" + id + ", company=" + company
				+ ", role=" + role + ", startDate=" + startDate + ", endDate="
				+ endDate + ", teacherId=" + teacherId + "]";
	}


	@Override
	@Transient
	 @JSON(serialize=false) 
	public String getFoldName() {
		// TODO Auto-generated method stub
		return "";
	}

	public WorkingExperience(WorkingExperience w,Teacher t) {
		super();
		this.id = w.id;
		this.teacherId = w.teacherId;
		this.teacherName = t.getName();
		this.company = w.company;
		this.role = w.role;
		this.startDate = w.startDate;
		this.endDate = w.endDate;
	}







}