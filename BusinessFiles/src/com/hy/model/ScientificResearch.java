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
 * ScientificResearch entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_scientificresearch"
    ,catalog="dpdatabase"
)

public class ScientificResearch  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;
     @excelExport(name="项目类别")
     private String projectType;//项目性质
     
     @excelExport(name="项目名称")
     private String topic;
     
     @excelExport(name="项目类型")
     private String approver;
     @excelExport(name="项目成员")
     private String authorView;
     private String author;
     
     @excelExport(name="项目编号")
     private String topicNum;
     @excelExport(name="立项时间")
     private Date startDate;
     @excelExport(name="结题时间")
     private Date endDate;
    
     
     private String level;
     @excelExport(name="经费")
     private Double funds;
     @excelExport(name="分值")
     private Double score;
     private String attachment;
     private String status;//审核状态,1已审核,0未审核
    

     /*******多文件上传设置的字段************/ 
    //附件压缩包 
  	private List<File> file;//图片文件
  	private List<String>  fileFileName;//文件上传时的文件名
  	private String  fileName;//重新命名之后的字段名,以,分隔.数据库中
	@Transient 
  	private String fileContentType;
  	private List<File> pc;
	@Transient 
  	private String pcContentType;
  	private List<File> kc;
	@Transient 
  	private String kcContentType;
  	private List<File> sf;//申报书
	@Transient 
  	private String sfContentType;
  	private List<String> pcFileName;
  	private List<String> kcFileName;
  	private List<String> sfFileName;
  	
    private String projectCertificat;//立项PDF
    private String knotCertificat;//结题PDF
    private String sfbook;//申报书
  	
    /** default constructor */
    public ScientificResearch() {
    }

    
    /** full constructor */
    public ScientificResearch(Integer id, String topic, String topicNum,
			Date startDate, Date endDate, String approver, String level,
			Double funds,  Double score,
			String attachment, String projectType, String projectCertificat,
			String knotCertificat,String author,String sfbook) {
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
		this.projectType = projectType;
		this.projectCertificat = projectCertificat;
		this.knotCertificat = knotCertificat;
		this.author=author;
		this.sfbook=sfbook;
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
    
   
   
    @Column(name="projectType")
	public String getProjectType() {
		return projectType;
	}


	public void setProjectType(String projectType) {
		this.projectType = projectType;
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
	public List<File> getSf() {
		return sf;
	}


	public void setSf(List<File> sf) {
		this.sf = sf;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getSfFileName() {
		return sfFileName;
	}


	public void setSfFileName(List<String> sfFileName) {
		this.sfFileName = sfFileName;
	}

	@Column(name="sfbook")
	public String getSfbook() {
		return sfbook;
	}


	public void setSfbook(String sfbook) {
		this.sfbook = sfbook;
	}


	@Override
	public String toString() {
		return "ScientificResearch [id=" + id + ", topic=" + topic
				+ ", topicNum=" + topicNum + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", approver=" + approver
				+ ", level=" + level + ", funds=" + funds + ", score=" + score
				+ ", attachment=" + attachment + ", projectType=" + projectType
				+ ", projectCertificat=" + projectCertificat
				+ ", knotCertificat=" + knotCertificat + "]";
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
	public List<String> getKcFileName() {
		return kcFileName;
	}


	public void setKcFileName(List<String> kcFileName) {
		this.kcFileName = kcFileName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getPcFileName() {
		return pcFileName;
	}
	public void setPcFileName(List<String> pcFileName) {
		this.pcFileName = pcFileName;
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
		// TODO Auto-generated method stub
		return topic+"-"+projectType+"-"+author;
	}







}