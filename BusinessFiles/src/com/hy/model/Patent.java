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
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.hy.annotation.excelExport;
import com.hy.util.StringUtil;


/**
 * PaperResearch entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_patent"
    ,catalog="dpdatabase"
)


public class Patent  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;
     @excelExport(name="专利号")
     private String patentNum;//专利号
     @excelExport(name="专利类型")
     private String type;//类型
     @excelExport(name="所有者")
     private String authorView;
     private String author;///授权所有人
     
     @excelExport(name="发布时间")
     private Date time;//发表时间
     @excelExport(name="转换情况")
     private String transform;//转化情况
     @excelExport(name="分值")
     private double score;//分值
     private String status;//审核状态,1已审核,0未审核
    
     
     
/*******多文件上传设置的字段************/    
 	private List<File> cert;//证书复印件,
  	private String certContentType;
 	private List<String>  certFileName;//文件上传时的文件名
 	
 	private List<File> trans;//转化鉴定
  	private String transContentType;
 	private List<String>  transFileName;//文件上传时的文件名
 	
 	//分别存对应文件名到数据库,自定义增加的字段
 	private String  certName;//证书复印件,以,分隔
 	private String  transName;//重新命名之后的字段名,以,分隔
/*******************************/ 	

    // Constructors
    /** default constructor */
    public Patent() {
    }

    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="id", unique=true, nullable=false)
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

    
    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name="score")
    public double getScore() {
        return this.score;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    @Column(name="patentNum")
	public String getPatentNum() {
		return patentNum;
	}
	public void setPatentNum(String patentNum) {
		this.patentNum = patentNum;
	}

	 @Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	 @Column(name="time")
	 @JSON(format="yyyy-MM-dd")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	 @Column(name="transform")
	public String getTransform() {
		return transform;
	}

	public void setTransform(String transform) {
		this.transform = transform;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<File> getCert() {
		return cert;
	}


	public void setCert(List<File> cert) {
		this.cert = cert;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getCertFileName() {
		return certFileName;
	}


	public void setCertFileName(List<String> certFileName) {
		this.certFileName = certFileName;
	}


	 @Column(name="certName")
	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}
	@Column(name="status")
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="author")
	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
		this.authorView=StringUtil.authorNameDeal(author);
	}
	@Transient
	 @JSON(serialize=false) 
	public List<File> getTrans() {
		return trans;
	}

	public void setTrans(List<File> trans) {
		this.trans = trans;
	}
	@Transient
	 @JSON(serialize=false) 
	public List<String> getTransFileName() {
		return transFileName;
	}

	public void setTransFileName(List<String> transFileName) {
		this.transFileName = transFileName;
	}
	@Column(name="transName")
	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}
	@Transient 
	 @JSON(serialize=false) 
	public String getCertContentType() {
		return certContentType;
	}

	public void setCertContentType(String certContentType) {
		this.certContentType = certContentType;
	}
	@Transient
	 @JSON(serialize=false) 
	public String getTransContentType() {
		return transContentType;
	}

	public void setTransContentType(String transContentType) {
		this.transContentType = transContentType;
	}



	@Override
	@Transient
	 @JSON(serialize=false) 
	public String getFoldName() {
		// TODO Auto-generated method stub
		return type+"-"+author;
	}





	
	


	
	
	

}