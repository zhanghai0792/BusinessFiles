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
 * EducationalResearch entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_educationalresearch"
    ,catalog="dpdatabase"
)

public class EducationalResearch  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;

     @excelExport(name="教改课题名称")
     private String topic;
     
     @excelExport(name="课题类型")
     private String approver;
     
    
     private String author;
     @excelExport(name="课题成员")
     private String authorView;
     @excelExport(name="课题编号")
     private String topicNum;
     @excelExport(name="立项时间")
     private Date startDate;
     @excelExport(name="结束时间")
     private Date endDate;

    
     private String level;
     @excelExport(name="课题经费")
     private Double funds;
     @excelExport(name="课题分值")
     private Double score;
     private String attachment;
     private String status;

     
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
  	
  	
  	private List<File> pc;
  	private List<File> kc;
  	private List<File> sf;//申报书
  	private List<String> pcFileName;
  	private List<String> kcFileName;
  	private List<String> sfFileName;
	@Transient 
  	private String pcContentType;
	@Transient 
  	private String kcContentType;
	
	@Transient 
  	private String sfContentType;
  	
  	
  	
    private String projectCertificat;//立项PDF
    private String knotCertificat;//结题PDF
    private String sfbook;//申报书
    
    // Constructors

    /** default constructor */
    public EducationalResearch() {
    	
    }

    
    /** full constructor */
 

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="id", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
    


	public EducationalResearch(Integer id, String topic, String topicNum,
			Date startDate, Date endDate, String approver, String level,
			Double funds, Double score, String attachment, String status,
			String author, List<File> file, List<String> fileFileName,
			String fileName, String projectCertificat, String knotCertificat,
			String sfbook) {
		super();
		this.id = id;
		this.topic = topic;
		this.topicNum = topicNum;
		this.startDate = startDate;
		this.endDate = endDate;
		this.approver = approver;
		this.level = level;
		this.funds = funds;
		this.score = score;
		this.attachment = attachment;
		this.status = status;
		this.author = author;
		this.authorView=StringUtil.authorNameDeal(author);
		this.file = file;
		this.fileFileName = fileFileName;
		this.fileName = fileName;
		this.projectCertificat = projectCertificat;
		this.knotCertificat = knotCertificat;
		this.sfbook = sfbook;
	}


	public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="topic")

    public String getTopic() {
        return this.topic;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    @Column(name="topicNum")

    public String getTopicNum() {
        return this.topicNum;
    }
    
    public void setTopicNum(String topicNum) {
        this.topicNum = topicNum;
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
    @Temporal(TemporalType.DATE)
    @Column(name="endDate", length=10)
    @JSON(format="yyyy-MM-dd")
    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @Column(name="approver")

    public String getApprover() {
        return this.approver;
    }
    
    public void setApprover(String approver) {
        this.approver = approver;
    }
    
    @Column(name="level")

    public String getLevel() {
        return this.level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    @Column(name="funds", precision=255)

    public Double getFunds() {
        return this.funds;
    }
    
    public void setFunds(Double funds) {
        this.funds = funds;
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
    
	 @Override
	public String toString() {
		return "EducationalResearch [id=" + id + ", topic=" + topic
				+ ", topicNum=" + topicNum + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", approver=" + approver
				+ ", level=" + level + ", funds=" + funds + ", score=" + score
				+ ", attachment=" + attachment + ", status=" + status
				+ ", author=" + author + ", file=" + file + ", fileFileName="
				+ fileFileName + ", fileName=" + fileName
				+ ", projectCertificat=" + projectCertificat
				+ ", knotCertificat=" + knotCertificat + ", sfbook=" + sfbook
				+ "]";
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
	public List<File> getPc() {
		return pc;
	}


	public void setPc(List<File> pc) {
		this.pc = pc;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<File> getKc() {
		return kc;
	}


	public void setKc(List<File> kc) {
		this.kc = kc;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<File> getSf() {
		return sf;
	}


	public void setSf(List<File> sf) {
		this.sf = sf;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getPcFileName() {
		return pcFileName;
	}


	public void setPcFileName(List<String> pcFileName) {
		this.pcFileName = pcFileName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getKcFileName() {
		return kcFileName;
	}


	public void setKcFileName(List<String> kcFileName) {
		this.kcFileName = kcFileName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getSfFileName() {
		return sfFileName;
	}


	public void setSfFileName(List<String> sfFileName) {
		this.sfFileName = sfFileName;
	}

	@Column(name="projectCertificat")
	public String getProjectCertificat() {
		return projectCertificat;
	}


	public void setProjectCertificat(String projectCertificat) {
		this.projectCertificat = projectCertificat;
	}

	@Column(name="knotCertificat")
	public String getKnotCertificat() {
		return knotCertificat;
	}


	public void setKnotCertificat(String knotCertificat) {
		this.knotCertificat = knotCertificat;
	}

	@Column(name="sfbook")
	public String getSfbook() {
		return sfbook;
	}


	public void setSfbook(String sfbook) {
		this.sfbook = sfbook;
	}

	@Transient
	 @JSON(serialize=false) 
	public String getPcContentType() {
		return pcContentType;
	}


	public void setPcContentType(String pcContentType) {
		this.pcContentType = pcContentType;
	}

	@Transient
	 @JSON(serialize=false) 
	public String getKcContentType() {
		return kcContentType;
	}


	public void setKcContentType(String kcContentType) {
		this.kcContentType = kcContentType;
	}

	@Transient 
	 @JSON(serialize=false) 
	public String getSfContentType() {
		return sfContentType;
	}


	public void setSfContentType(String sfContentType) {
		this.sfContentType = sfContentType;
	}


	@Transient
	 @JSON(serialize=false) 
	public String getFoldName() {
		return topic+"-"+author;
	}




	
   
    







}