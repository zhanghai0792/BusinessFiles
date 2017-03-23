package com.hy.model;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.hy.annotation.excelExport;




/**
 * Teacher entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity //注解Entity表示该类纳入Hibernate管理，能够被持久化
/*@Table(name="t_teacher"
    ,catalog="businessfiles"
)*/
//连接远程数据库要改的配置
@Table(name="tb_manager"
,catalog="dpdatabase"
)
// 指定该实体类对应的数据库表名//
public class Teacher implements java.io.Serializable{


    // Fields    
	 @excelExport(name="工号")
     private String id;//工号
     private String password;//密码	
     @excelExport(name="姓名")
     private String name;//姓名
     private String py;//姓名首字母生成的小写拼音 例：王文华-wwh
     @excelExport(name="性别")
     private byte sex;//性别
     @excelExport(name="出生年月")
     private String birthDate;//出生年月
     @excelExport(name="民族")
     private String nation;//民族
     @excelExport(name="政治面貌")
     private String political;//政治面貌
     @excelExport(name="身份证号")
     private String cardId;//身份证号
     @excelExport(name="评定职称")
     private String professTitle;//评定职称
     @excelExport(name="评定职称时间")
     private String professDate;//评定职称时间
     @excelExport(name="聘任职称")
     private String employTitle;//聘任职称
     @excelExport(name="聘任职称时间")
     private String employDate;//聘任职称时间
     @excelExport(name="获得最后学位时间")
     private String finalDegreeDate;//获得最后学位时间
     @excelExport(name="最后学位")
     private String finalDegree;//最后学位
     @excelExport(name="最后学历")
     private String finalEducat;//最后学历
     @excelExport(name="最后学历时间")
     private String finalEducatDate;//最后学历时间
     @excelExport(name="院系")
     private String grade;//院系
     @excelExport(name="部门/教研室")
     private String dept;//部门/教研室
     @excelExport(name="用户类型")
     private String type;//用户类型
     @excelExport(name="手机号")
     private String phone;//手机,
     @excelExport(name="毕业院校")
     private String school;//毕业院校,
     @excelExport(name="专业")
     private String major;//专业,
     private String positiontype="专职";
     private byte isDelete;//是否删除

    private File photo;//照片
   	private String photoFileName;//文件上传时的文件名
	@Transient 
  	private String photoContentType;
   	private String photoName;//重新命名之后的字段名
    // Constructors

    /** default constructor */
    public Teacher() {
    }
    	
    

	// Property accessors
    @Id 
    // 指定该列为主键。//updatable=false
    @Column(name="id", unique=true, nullable=false)
    public String getId() {
        return this.id;
    }
    


	public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="password")
    @JSON(serialize=false) 
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="name")
    public String getName() {
        return this.name;
    }
	@Column(name="sex",nullable=false)
    public byte getSex() {
		return sex;
	}
	
	public void setSex(byte sex) {
		this.sex = sex;
	}
	public void setName(String name) {
        this.name = name;
    }
    @Column(name="birthDate", length=10)
    public String getBirthDate() {
        return this.birthDate;
    }
    
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    
    @Column(name="nation")

    public String getNation() {
        return this.nation;
    }
    
    public void setNation(String nation) {
        this.nation = nation;
    }
    
    @Column(name="political")

    public String getPolitical() {
        return this.political;
    }
    
    public void setPolitical(String political) {
        this.political = political;
    }
    
    @Column(name="cardId", length=18)
    public String getCardId() {
        return this.cardId;
    }
    
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    
    @Column(name="professTitle")

    public String getProfessTitle() {
        return this.professTitle;
    }
    
    public void setProfessTitle(String professTitle) {
        this.professTitle = professTitle;
    }
   
    @Column(name="professDate", length=10)
    
    public String getProfessDate() {
        return this.professDate;
    }
    
    public void setProfessDate(String professDate) {
        this.professDate = professDate;
    }
    
    @Column(name="employTitle")

    public String getEmployTitle() {
        return this.employTitle;
    }
    
    public void setEmployTitle(String employTitle) {
        this.employTitle = employTitle;
    }
   
    @Column(name="employDate", length=10)
    
    public String getEmployDate() {
        return this.employDate;
    }
    
    public void setEmployDate(String employDate) {
        this.employDate = employDate;
    }
   
    @Column(name="finalDegreeDate", length=10)
    
    public String getFinalDegreeDate() {
        return this.finalDegreeDate;
    }
    
    public void setFinalDegreeDate(String finalDegreeDate) {
        this.finalDegreeDate = finalDegreeDate;
    }
    
    @Column(name="finalDegree")

    public String getFinalDegree() {
        return this.finalDegree;
    }
    
    public void setFinalDegree(String finalDegree) {
        this.finalDegree = finalDegree;
    }
    
    @Column(name="finalEducat")

    public String getFinalEducat() {
        return this.finalEducat;
    }
    
    public void setFinalEducat(String finalEducat) {
        this.finalEducat = finalEducat;
    }
    
    @Column(name="grade")

    public String getGrade() {
        return this.grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    @Column(name="dept")

    public String getDept() {
        return this.dept;
    }
    
    public void setDept(String dept) {
        this.dept = dept;
    }

    @Column(name="type")
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	@Column(name="phone")
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name="school")
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name="major")
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}


	@Column(name="isDelete", length=1)
	public byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(byte isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name="positiontype")
	public String getPositiontype() {
		return positiontype;
	}



	public void setPositiontype(String positiontype) {
		this.positiontype = positiontype;
	}


	@Column(name="finalEducatDate")
	public String getFinalEducatDate() {
		return finalEducatDate;
	}



	public void setFinalEducatDate(String finalEducatDate) {
		this.finalEducatDate = finalEducatDate;
	}



	public Teacher(String id, String password, String name, byte sex,
			String birthDate, String nation, String political, String cardId,
			String professTitle, String professDate, String employTitle,
			String employDate, String finalDegreeDate, String finalDegree,
			String finalEducat, String grade, String dept, String type,
			String phone, String school, String major, String positiontype,
			String finalEducatDate,String py,
			byte isDelete) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.sex = sex;
		this.birthDate = birthDate;
		this.nation = nation;
		this.political = political;
		this.cardId = cardId;
		this.professTitle = professTitle;
		this.professDate = professDate;
		this.employTitle = employTitle;
		this.employDate = employDate;
		this.finalDegreeDate = finalDegreeDate;
		this.finalDegree = finalDegree;
		this.finalEducat = finalEducat;
		this.grade = grade;
		this.dept = dept;
		this.type = type;
		this.phone = phone;
		this.school = school;
		this.major = major;
		this.positiontype = positiontype;
		this.finalEducatDate=finalEducatDate;
		this.py=py;
		this.isDelete = isDelete;
	}


	@Transient
	 @JSON(serialize=false) 
	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	@Transient
	 @JSON(serialize=false) 
	public String getPhotoFileName() {
		return photoFileName;
	}



	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	@Column(name="photoName")
	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}


	@Column(name="py",updatable=false)
	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}



	@Override
	public String toString() {
		return "Teacher [id=" + id + ", password=" + password + ", name="
				+ name + ", py=" + py + ", sex=" + sex + ", birthDate="
				+ birthDate + ", nation=" + nation + ", political=" + political
				+ ", cardId=" + cardId + ", professTitle=" + professTitle
				+ ", professDate=" + professDate + ", employTitle="
				+ employTitle + ", employDate=" + employDate
				+ ", finalDegreeDate=" + finalDegreeDate + ", finalDegree="
				+ finalDegree + ", finalEducat=" + finalEducat
				+ ", finalEducatDate=" + finalEducatDate + ", grade=" + grade
				+ ", dept=" + dept + ", type=" + type + ", phone=" + phone
				+ ", school=" + school + ", major=" + major + ", positiontype="
				+ positiontype + ", isDelete=" + isDelete + ", photo=" + photo
				+ ", photoFileName=" + photoFileName + ", photoName="
				+ photoName + "]";
	}

   @Transient
	 @JSON(serialize=false) 
	public String getPhotoContentType() {
		return photoContentType;
	}



	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

}