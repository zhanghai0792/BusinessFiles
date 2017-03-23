package com.hy.model;
// default package

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
 * MonographTextbook entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_monographtextbook"
    ,catalog="dpdatabase"
)

public class MonographTextbook  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;
     @excelExport(name="专著/编写教材名称")
     private String bookName;
     @excelExport(name="出版时间")
     private Date publishDate;
     @excelExport(name="出版社")
     private String publication;
     @excelExport(name="分值")
     private Double score;
     private String attachment;
     @excelExport(name="编撰人员")
     private String authorView;
     
     private String author;
    
     private String status;
     private List<File> file;//图片文件
   	private List<String>  fileFileName;//文件上传时的文件名
   	private List<String> fileNewName;//新的文件名
   
  	private String fileContentType;
   	private String  fileName;//重新命名之后的字段名,以,分隔

    // Constructors

    /** default constructor */
    public MonographTextbook() {
    }

    
    /** full constructor */
    public MonographTextbook(String bookName, Date publishDate, String publication, Integer rank, Integer totalNum, Double score, String attachment, String author) {
        this.bookName = bookName;
        this.publishDate = publishDate;
        this.publication = publication;
        this.score = score;
        this.attachment = attachment;
        this.author = author;
        this.authorView=StringUtil.bookAuthorNameDeal(author);
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
    
    @Column(name="bookName")

    public String getBookName() {
        return this.bookName;
    }
    
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="publishDate", length=10)
    @JSON(format="yyyy-MM-dd")
    public Date getPublishDate() {
        return this.publishDate;
    }
    
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    
    @Column(name="publication")

    public String getPublication() {
        return this.publication;
    }
    
    public void setPublication(String publication) {
        this.publication = publication;
    }
    
    
    @Column(name="score", precision=255)

    public Double getScore() {
        return this.score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
    @Column(name="attachment")

    public String getAttachment() {
        return this.attachment;
    }
    
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    


    

    @Column(name="author")
	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
		this.authorView=StringUtil.bookAuthorNameDeal(author);
	}


	@Override
	public String toString() {
		return "MonographTextbook [id=" + id + ", bookName=" + bookName
				+ ", publishDate=" + publishDate + ", publication="
				+ publication + ", score=" + score + ", attachment=" + attachment
				+ ", author=" + author + "]";
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
		return bookName+"-"+author;
	}
   








}