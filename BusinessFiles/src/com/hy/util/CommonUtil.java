package com.hy.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class CommonUtil {

	

	public static <T> T isNull(T object, T defaultValue) {
		return object == null ? defaultValue : object;
	}

	public static Long stringToLong(String value) {
		if (StringUtils.isNotEmpty(value)) {
			try {
				return Long.parseLong(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Integer stringToInteger(String value) {
		if (StringUtils.isNotEmpty(value)) {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Integer[] stringToIntegerArray(String value) {
		if (StringUtils.isNotEmpty(value)) {
			String[] datas = value.split(",");
			Integer[] keys = new Integer[datas.length];
			for (int i = 0; i < datas.length; i++) {
				keys[i] = stringToInteger(datas[i]);
			}
			return keys;
		}
		return new Integer[0];
	}

	

	

	

	public static <E> E copyBean(Object source, Class<E> clazz) {
		try {
			E dest = clazz.newInstance();
			BeanUtils.copyProperties(dest, source);
			return dest;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> void copySelective(T dest, T source) {
		if (dest == null || source == null) {
			return;
		}
		try {
			for (Field field : source.getClass().getDeclaredFields()) {
				Object value = BeanUtils.getProperty(source, field.getName());
				if (value != null) {
					BeanUtils.copyProperty(dest, field.getName(), value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public final static String md5(String string) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = string.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Float stringToFloat(String value) {
		if (StringUtils.isNotEmpty(value)) {
			try {
				return Float.parseFloat(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Object stringToDouble(String value) {
		if (StringUtils.isNotEmpty(value)) {
			try {
				return Double.parseDouble(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Double stringToDoubles(String value) {
		if (StringUtils.isNotEmpty(value)) {
			try {
				return Double.parseDouble(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void filterNullInt(Map<String, Object> params, String... strings) {
		if (strings == null || strings.length == 0) {
			return;
		}
		for (String field : strings) {
			Object value = params.get(field);
			if (value == null || "".equals(String.valueOf(value))) {
				continue;
			}
			if (!isNotNumberic(String.valueOf(value))) {
				params.put(field, "-1000");
			}
		}
	}

	private static boolean isNotNumberic(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 合并List
	 * 
	 * @param <T>
	 * @param list
	 * @param spliter
	 * @return
	 */
	public static <T> String join(List<T> list, String spliter, boolean withQuot) {
		if (list == null || list.isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (T obj : list) {
			String value = "" + obj;
			if (withQuot) {
				value = "'" + value + "'";
			}
			if (i == 0) {
				sb.append(value);
			} else {
				sb.append(spliter).append(value);
			}
			i++;
		}
		return sb.toString();
	}

	/**
	 * 合并List
	 * 
	 * @param <T>
	 * @param list
	 * @param spliter
	 * @return
	 */
	public static <T> String join(List<T> list, String spliter) {
		return join(list, spliter, false);
	}

	/**
	 * 合并List
	 * 
	 * @param <T> list
	 * @return
	 */
	public static <T> String join(List<T> list) {
		return join(list, ",", false);
	}

	public static String doubleToString(Double amount) {
		if (amount == null) {
			return "0";
		}
		return new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}

	

	/** 获取随机数 */
	public static String getRandomStr(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append((int) (10 * Math.random()));
		}
		return sb.toString();
	}

	/** 获取字段串的编码 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}

	public static String nullToEmpty(String value) {
		if (value == null) {
			return StringUtils.EMPTY;
		}
		value = value.replace("null", "");
		return value;
	}

	/**
	 * 将emoji表情替换成*
	 * 
	 * @param source
	 * @return 过滤后的字符串
	 */
	public static String filterEmoji(String source) {
		if (StringUtils.isNotBlank(source)) {
			return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
		} else {
			return source;
		}
	}

	

	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			// 取出每一个字符
			char c = string.charAt(i);
			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}

	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);
			// 追加成string
			string.append((char) data);
		}
		return string.toString();
	}
	
	//生成随机数字和字母,  
    public static String getStringRandom(int num) {  
          
        String val = "";  
        Random random = new Random();  
          
        //参数length，表示生成几位随机数  
        for(int i = 0; i < num; i++) {  
              
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }  
	
	
	
	
	

}
