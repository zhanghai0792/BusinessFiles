package com.hy.model;

import java.io.File;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.hy.annotation.excelExport;


/**
 * Certificate entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_certificate"
    ,catalog="dpdatabase"
)

public class Certificate  implements java.io.Serializable,exportModel {


    // Fields    
     private Integer id;//证书id,自增长
     @excelExport(name="工号")
     private String teacherId;
 @excelExport(name="教师")
	 private String teacherName;
     
    @excelExport(name="证书名称")
     private String name;//证书名称
   
     private String attachment;//证书附件

    
     
     private List<File> file;//图片文件
     private List<String>  fileFileName;//文件上传时的文件名
     private String  fileName;//重新命名之后的字段名,以,分隔
     
     private String fileContentType;
     

    @Transient
    @JSON(serialize=false) 
	public String getFileContentType() {
		return fileContentType;
	}


	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	 @excelExport(name="证书类型")
	private String type;

    // Constructors

    /** default constructor */
    public Certificate() {
    }

    
    /** full constructor */
    public Certificate(String name, String attachment,String teacherId) {
        this.name = name;
        this.attachment = attachment;
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
    
    @Column(name="attachment")

    public String getAttachment() {
        return this.attachment;
    }
    
    public void setAttachment(String attachment) {
        this.attachment = attachment;
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
		return "Certificate [id=" + id + ", name=" + name
				+ ", attachment=" + attachment + ", teacherId=" + teacherId
				+ "]";
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

	@Column(name="type")
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	@Override
	@Transient
	public String getFoldName() {
		if(type!=null)
			return type;
		return "unKnowCertificateType";
	}

	@Transient
	public String getTeacherName() {
		return teacherName;
	}


	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}


	public Certificate(Certificate c,Teacher t) {
		super();
		this.id = c.id;
		this.teacherId = c.teacherId;
		this.teacherName = t.getName();
		this.name = c.name;
		this.type = c.type;
		this.fileName=c.fileName;
	}





}