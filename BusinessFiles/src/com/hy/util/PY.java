package com.hy.util;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PY {
	 public static String getPinYinHeadChar(String str) {
            if(str==null||"".equals(str))
            	return "";
	        String convert = "";
	        for (int j = 0; j < str.length(); j++) {
	            char word = str.charAt(j);
	            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
	            if (pinyinArray != null) {
	                convert += pinyinArray[0].charAt(0);
	            } else {
	                convert += word;
	            }
	        }
	        return convert.toUpperCase();
	    }
}
