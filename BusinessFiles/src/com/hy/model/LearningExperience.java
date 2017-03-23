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
@Table(name = "t_learningexperience", catalog = "dpdatabase")
public class  LearningExperience   implements java.io.Serializable,exportModel {

	// Fields
	private Integer id;//自增长序号
	
	@excelExport(name="工号")
    private String teacherId;
@excelExport(name="教师")
	 private String teacherName;


 
	 @excelExport(name="层次")
	private String level;//层次
	 @excelExport(name="学校")
	private String school;//学校
	 @excelExport(name="专业")
	private String major;//专业
	 @excelExport(name="毕业时间")
	private Date graduatDate;//毕业时间





	// Constructors

	/** default constructor */
	public LearningExperience() {
	}

	/** full constructor */
	public LearningExperience(String level, String school, String major,
			Date graduatDate, String teacherId) {
		this.level = level;
		this.school = school;
		this.major = major;
		this.graduatDate = graduatDate;
		this.teacherId = teacherId;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "level")
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(name = "school")
	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "major")
	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
	@JSON(format="yyyy-MM-dd")//将日期序列化为指定格式
	//@JSON(format ="yyyy-MM-dd'T'HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name = "graduatDate", length = 10)
	public Date getGraduatDate() {
		return this.graduatDate;
	}

	public void setGraduatDate(Date graduatDate) {
		this.graduatDate = graduatDate;
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
		return "LearningExperience [id=" + id + ", level=" + level
				+ ", school=" + school + ", major=" + major + ", graduatDate="
				+ graduatDate + ", teacherId=" + teacherId + "]";
	}

	
	@Override
	@Transient
	 @JSON(serialize=false) 
	public String getFoldName() {
		// TODO Auto-generated method stub
		return "";
	}
    @Transient
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public LearningExperience(LearningExperience L,Teacher t) {
		super();
		this.id = L.id;
		this.teacherId = L.teacherId;
		this.teacherName = t.getName();
		this.level = L.level;
		this.school = L.school;
		this.major = L.major;
		this.graduatDate = L.graduatDate;
	}
	
}