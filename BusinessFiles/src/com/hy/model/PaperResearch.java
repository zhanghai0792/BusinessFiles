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
 * PaperResearch entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_paperresearchs"
    ,catalog="dpdatabase"
)

/*更新只需要更新的字段,但需要先查询
 * @DynamicUpdate(true)
	@DynamicInsert(true)
//http://www.cnblogs.com/quanyongan/p/3152290.html
http://blog.sina.com.cn/s/blog_a6acf9dc01015e1r.html
http://blog.csdn.net/he90227/article/details/41310149
http://www.cnblogs.com/quanyongan/p/3152290.html
*/

public class PaperResearch  implements java.io.Serializable,exportModel {


    // Fields    

     private Integer id;
     @excelExport(name="论文名")
     private String paperTitle;//论文篇名
     @excelExport(name="刊物等级")
     private String level;//刊物等级
     
     @excelExport(name="作者")
     private String authorView;
     private String author;
     
     @excelExport(name="发表刊物")
     private String publication;//发表刊物
     
   
     @excelExport(name="发表刊号")
     private String publishNum;//刊号
   
     @excelExport(name="发表时间")
     private Date publishDate;//发表时间
    
     @excelExport(name="分值")
     private double score;//分值
     
  
     private String status;//审核状态,1已审核,0未审核
    
     
/*******多文件上传设置的字段************/    
 	private List<File> fm;//封面部分多个图片文件
 	private List<String>  fmFileName;//文件上传时的文件名
 	private List<String> fmNewName;//新的文件名

  	private String fmContentType;
    private List<File> bqy;
	@Transient 
  	private String bqyContentType;
    private List<File> ml;
	@Transient 
  	private String mlContentType;
    
    private List<File> zw;
    
	@Transient 
  	private String zwContentType;
    private List<File> fd;
	@Transient 
  	private String fdContentType;
    private List<File> js;
	@Transient 
  	private String jsContentType;
 	private List<String>  bqyFileName;
 	private List<String>  mlFileName;
 	private List<String>  zwFileName;
 	private List<String>  fdFileName;
 	
 	private List<String>  jsFileName;
 	
 	
 	private List<String> bqyNewName;
 	private List<String> mlNewName;
 	private List<String> zwNewName;
 	private List<String> fdNewName;
 	private List<String> jsNewName;
 	
 	 /*l 类型为File的xxx属性：用来封装页面文件域对应的文件内容。
	  *l 类型为String的xxxFileName属性：用来封装该文件域对应的文件的文件名。
	  *l 类型为String的xxxContentType属性：用来封装该文件域应用的文件的文件类型。
	  *http://blog.163.com/linfenliang@126/blog/static/127857195201171205937621/
	  *structs2上传文件文件名
	 */
 	//分别存对应文件名到数据库,自定义增加的字段
 	private String  fmName;//重新命名之后的字段名,以,分隔
 	private String  bqyName;
 	private String  mlName;
 	private String  zwName;
 	private String  fdName;
 	private String  jsName;
 	
/*******************************/ 	

 	
 	

    // Constructors

    /** default constructor */
    public PaperResearch() {
    }

    


	




	/** full constructor */
    public PaperResearch(String paperTitle, Date publishDate, String publication, String publishNum, String level,  double score) {
        this.paperTitle = paperTitle;
        this.publishDate = publishDate;
        this.publication = publication;
        this.publishNum = publishNum;
        this.level = level;
        this.score = score;
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
    
    @Column(name="paperTitle")

    public String getPaperTitle() {
        return this.paperTitle;
    }
    
    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="publishDate")
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
    
    @Column(name="publishNum")

    public String getPublishNum() {
        return this.publishNum;
    }
    
    public void setPublishNum(String publishNum) {
        this.publishNum = publishNum;
    }
    
    @Column(name="level")
    public String getLevel() {
        return this.level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    
    
    @Column(name="score")
    public double getScore() {
        return this.score;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    
    
   
    
    @Column(name="fmName")
    public String getFmName() {
		return fmName;
	}


	public void setFmName(String fmName) {
		this.fmName = fmName;
	}

	@Column(name="bqyName")
	public String getBqyName() {
		return bqyName;
	}


	public void setBqyName(String bqyName) {
		this.bqyName = bqyName;
	}

	@Column(name="mlName")
	public String getMlName() {
		return mlName;
	}


	public void setMlName(String mlName) {
		this.mlName = mlName;
	}

	@Column(name="zwName")
	public String getZwName() {
		return zwName;
	}


	public void setZwName(String zwName) {
		this.zwName = zwName;
	}

	@Column(name="fdName")
	public String getFdName() {
		return fdName;
	}


	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	@Transient
	 @JSON(serialize=false) 
	public List<File> getFm() {
		return fm;
	}


	


	public void setFm(List<File> fm) {
		this.fm = fm;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<File> getBqy() {
		return bqy;
	}

	
	public void setBqy(List<File> bqy) {
		this.bqy = bqy;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<File> getMl() {
		return ml;
	}


	public void setMl(List<File> ml) {
		this.ml = ml;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<File> getZw() {
		return zw;
	}


	public void setZw(List<File> zw) {
		this.zw = zw;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<File> getFd() {
		return fd;
	}


	public void setFd(List<File> fd) {
		this.fd = fd;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getFmFileName() {
		return fmFileName;
	}


	public void setFmFileName(List<String> fmFileName) {
		this.fmFileName = fmFileName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getBqyFileName() {
		return bqyFileName;
	}

	
	public void setBqyFileName(List<String> bqyFileName) {
		this.bqyFileName = bqyFileName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getMlFileName() {
		return mlFileName;
	}


	public void setMlFileName(List<String> mlFileName) {
		this.mlFileName = mlFileName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getZwFileName() {
		return zwFileName;
	}


	public void setZwFileName(List<String> zwFileName) {
		this.zwFileName = zwFileName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getFdFileName() {
		return fdFileName;
	}


	public void setFdFileName(List<String> fdFileName) {
		this.fdFileName = fdFileName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getFmNewName() {
		return fmNewName;
	}


	public void setFmNewName(List<String> fmNewName) {
		this.fmNewName = fmNewName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getBqyNewName() {
		return bqyNewName;
	}


	public void setBqyNewName(List<String> bqyNewName) {
		this.bqyNewName = bqyNewName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getMlNewName() {
		return mlNewName;
	}

	
	public void setMlNewName(List<String> mlNewName) {
		this.mlNewName = mlNewName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getZwNewName() {
		return zwNewName;
	}


	public void setZwNewName(List<String> zwNewName) {
		this.zwNewName = zwNewName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getFdNewName() {
		return fdNewName;
	}


	public void setFdNewName(List<String> fdNewName) {
		this.fdNewName = fdNewName;
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
	public List<File> getJs() {
		return js;
	}


	public void setJs(List<File> js) {
		this.js = js;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getJsFileName() {
		return jsFileName;
	}


	public void setJsFileName(List<String> jsFileName) {
		this.jsFileName = jsFileName;
	}

	@Transient
	 @JSON(serialize=false) 
	public List<String> getJsNewName() {
		return jsNewName;
	}


	public void setJsNewName(List<String> jsNewName) {
		this.jsNewName = jsNewName;
	}

	@Column(name="jsName")
	public String getJsName() {
		return jsName;
	}


	public void setJsName(String jsName) {
		this.jsName = jsName;
	}


	@Override
	public String toString() {
		return "PaperResearch [id=" + id + ", paperTitle=" + paperTitle
				+ ", publishDate=" + publishDate + ", publication="
				+ publication + ", publishNum=" + publishNum + ", level="
				+ level + ", status="
				+ status + ", fmName=" + fmName + ", bqyName=" + bqyName
				+ ", mlName=" + mlName + ", zwName=" + zwName + ", fdName="
				+ fdName + "]";
	}








	@Transient 
	 @JSON(serialize=false) 
	public String getFmContentType() {
		return fmContentType;
	}









	public void setFmContentType(String fmContentType) {
		this.fmContentType = fmContentType;
	}








	@Transient 
	 @JSON(serialize=false) 
	public String getBqyContentType() {
		return bqyContentType;
	}









	public void setBqyContentType(String bqyContentType) {
		this.bqyContentType = bqyContentType;
	}








	@Transient 
	 @JSON(serialize=false) 
	public String getMlContentType() {
		return mlContentType;
	}









	public void setMlContentType(String mlContentType) {
		this.mlContentType = mlContentType;
	}








	@Transient 
	 @JSON(serialize=false) 
	public String getZwContentType() {
		return zwContentType;
	}









	public void setZwContentType(String zwContentType) {
		this.zwContentType = zwContentType;
	}








	@Transient 
	 @JSON(serialize=false) 
	public String getFdContentType() {
		return fdContentType;
	}









	public void setFdContentType(String fdContentType) {
		this.fdContentType = fdContentType;
	}








	@Transient
	 @JSON(serialize=false) 
	public String getJsContentType() {
		return jsContentType;
	}









	public void setJsContentType(String jsContentType) {
		this.jsContentType = jsContentType;
	}








	@Override
	@Transient
	 @JSON(serialize=false) 
	public String getFoldName() {
		// TODO Auto-generated method stub
		return paperTitle+"-"+author;
	}
	
}