package com.hy.test;

import java.lang.reflect.Field;

import com.hy.annotation.excelExport;
import com.hy.model.Certificate;

public class annotationTest {

	public static void main(String[] args) {
		Class claz=Certificate.class;
		Field[] fields=claz.getDeclaredFields();
            for(Field f:fields){
            	if(f.isAnnotationPresent((excelExport.class))){
            	excelExport e=f.getAnnotation(excelExport.class);
            	System.out.println(e.name()+" "+f.getName()+" "+f.getType().getName());
            	}
            }
	}

}
