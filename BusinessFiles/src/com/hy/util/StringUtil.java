package com.hy.util;

public class StringUtil {
	public static String authorNameDeal(String name) {
		if (name != null && !"".equals(name)) {
			return name.replaceAll("\\[\\w{1,}\\]", "");
		}
		return name;
	}
	
	public static String bookAuthorNameDeal(String name){
		if (name != null && !"".equals(name)) {
			return name.replaceAll("\\|[A-Za-z0-9]{1,}\\|", "|");
		}
		return name;
	}
}
