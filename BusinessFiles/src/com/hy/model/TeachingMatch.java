package com.hy.model;

import java.io.File;
import java.util.Date;
import java.util.List;

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
 * TeachingAchievement entity. @teacherId MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_teachingmatch"
    ,catalog="dpdatabase"
)

public class TeachingMatch  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;
     @excelExport(name="工号")
     private String teacherId;
 @excelExport(name="教师")
	 private String teacherName;
     @excelExport(name="奖项名称")
     private String prize;//奖项
     @excelExport(name="奖项级别")
     private String level;//级别
     @excelExport(name="等次")
     private String torder;
     
     @excelExport(name="时间")
     private Date time;//时间
     @excelExport(name="分值")
     private Double score;//分数
    

     @Column(name="teacherName")
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
     
    
     private String status;
    private List<File> file;//图片文件
	private List<String>  fileFileName;//文件上传时的文件名
	private List<String> fileNewName;//新的文件名
	@Transient 
  	private String fileContentType;
	private String  fileName;//重新命名之后的字段名,以,分隔,附件

    /** default constructor */
    public TeachingMatch() {
    }

  
    
    
    
    /** full constructor */
    public TeachingMatch(String prize, String level, Date time, Integer rank, Integer totalNum, Double score,String teacherId) {
        this.prize = prize;
        this.level = level;
        this.time = time;
        this.score = score;
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
    
    @Column(name="prize")

    public String getPrize() {
        return this.prize;
    }
    
    public void setPrize(String prize) {
        this.prize = prize;
    }
    
    @Column(name="level")
    public String getLevel() {
        return this.level;
    }
    
    public void setLevel(String level) {
        this.level = level;
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
    

    
    @Column(name="score", precision=10, scale=2)

    public Double getScore() {
        return this.score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
 
   
    
    
   	




	@Override
	public String toString() {
		return "TeachingAchievement [id=" + id + ", prize=" + prize
				+ ", level=" + level + ", time=" + time
				+ ", score="
				+ score + ", teacherId="
				+ teacherId + ",torder="+this.torder+"]";
	}
	@Column(name = "teacherId")
	public String getTeacherId() {
		return teacherId;
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

	@Column(name="fileName")
	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="status")
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="torder")
	public String getTorder() {
		return torder;
	}


	public void setTorder(String torder) {
		this.torder = torder;
	}

	@Transient 
	 @JSON(serialize=false) 
	public String getFileContentType() {
		return fileContentType;
	}


	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	@Transient
	 @JSON(serialize=false) 
	public String getFoldName() {
		// TODO Auto-generated method stub
		return prize+"-"+level+"-"+teacherId;
	}

	public TeachingMatch(TeachingMatch t,Teacher th) {
		super();
		this.id = t.id;
		this.teacherId = t.teacherId;
		this.teacherName = th.getName();
		this.prize = t.prize;
		this.level = t.level;
		this.time = t.time;
		this.score = t.score;
		this.status = t.status;
		this.torder = t.torder;
		this.fileName=t.getFileName();
	}






}