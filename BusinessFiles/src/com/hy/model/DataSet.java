package com.hy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * DataSet entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="t_dataset"
    ,catalog="dpdatabase"
)

public class DataSet  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String value;//值，分值
     private String name;//名字
     private String classFiled;//所属类的字段
     private Integer type;//类型，处理联动的数据


    // Constructors

    /** default constructor */
    public DataSet() {
    }

    
    /** full constructor */
    public DataSet(String value, String name, String classFiled,Integer type) {
        this.value = value;
        this.name = name;
        this.classFiled = classFiled;
        this.type=type;
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
    
    @Column(name="value")

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    
    @Column(name="classFiled")

    public String getClassFiled() {
        return this.classFiled;
    }
    @Column(name="name")
    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setClassFiled(String classFiled) {
        this.classFiled = classFiled;
    }

	@Column(name="type")
	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	@Override
	public String toString() {
		return "DataSet [id=" + id + ", value=" + value + ", name=" + name
				+ ", classFiled=" + classFiled + ", type=" + type + "]";
	}



   








}