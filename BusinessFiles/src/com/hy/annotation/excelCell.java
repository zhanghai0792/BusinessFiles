package com.hy.annotation;

import java.lang.reflect.Field;

public class excelCell {
 private String name;
 private Field field;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Field getField() {
	return field;
}
public void setField(Field field) {
	this.field = field;
}
public excelCell() {
	super();
	// TODO Auto-generated constructor stub
}
public excelCell(String name, Field field) {
	super();
	this.name = name;
	this.field = field;
}
 
 
}
