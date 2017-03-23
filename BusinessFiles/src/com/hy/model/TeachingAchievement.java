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
import com.hy.util.StringUtil;


/**
 * TeachingAchievement entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_teachingachievement"
    ,catalog="dpdatabase"
)

public class TeachingAchievement  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;
     @excelExport(name="奖项名称")
     private String prize;//奖项
     @excelExport(name="奖励级别")
     private String level;//级别
     @excelExport(name="等次")
     private String torder;//等次
     @excelExport(name="所属教师")
     private String authorView;
     private String author;//所属教师
     
     @excelExport(name="时间")
     private Date time;//时间
     @excelExport(name="分值")
     private Double score;//分数

     private String status;
    private List<File> file;//图片文件
	private List<String>  fileFileName;//文件上传时的文件名
	@Transient 
  	private String fileContentType;
	private String  fileName;//重新命名之后的字段名,以,分隔,附件

    /** default constructor */
    public TeachingAchievement() {
    }

    
    /** full constructor */
    public TeachingAchievement(String prize, String level, String torder, Date time, Integer rank, Integer totalNum, Double score,String teacherId) {
        this.prize = prize;
        this.level = level;
        this.torder = torder;
        this.time = time;
        this.score = score;
        this.author = teacherId;
        this.authorView=StringUtil.authorNameDeal(author);
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
    @Column(name="torder")
    public String getTorder() {
		return torder;
	}


	public void setTorder(String torder) {
		this.torder = torder;
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
    
 
   
    
    @Column(name = "author")
   	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
		this.authorView=StringUtil.authorNameDeal(author);
	}




	@Override
	public String toString() {
		return "TeachingAchievement [id=" + id + ", prize=" + prize
				+ ", level=" + level + ", torder=" + torder + ", time=" + time
				+ ", score="
				+ score + ", author="
				+ author + "]";
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
		return prize+"-"+level+"-"+author;
	}






}