package com.hy.conterver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

/**
 * ClassName: DateConverter01 
 * @Description: 全局日期转换器
 * @author wwh
 * @date 2016-9-27
 */
public class DateConverter01 extends StrutsTypeConverter {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		
		try {
			//System.out.println("length:"+values.length);
			if(values.length<=1) {
				String d = values[0];
			//	System.out.println("d:"+d);
				return sdf.parse(d);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("error");
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		//System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&********************2");
		return sdf.format((Date)o);
	}

}
