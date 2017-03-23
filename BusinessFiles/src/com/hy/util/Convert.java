package com.hy.util;


/**
 * <p style="font-size: 12px; line-height: 22px;">Convert类主要提供了将字符串中的汉字转换为汉语的全拼或者汉语拼音的首字母。<br />
 * 下面给出了具体的使用示例：<br />
 * Example1 将给出的字符s转换成汉语的全拼拼音：<br />
 * String s = "中华人民共和国";<br />
 * String pinYinString = Convert.convertToAllChineseCharacter(s);<br />
 * //System.out.println(pinYinString);<br />
 * <br />
 * Example2 将给出的字符s转换成汉语的全拼拼音并去除其空格：<br />
 * String s = "中华 人民共      和国";<br />
 * String pinYinString = Convert.convertToAllChineseCharacter(s, true);<br />
 * //System.out.println(pinYinString);<br />
 * </p>
 */
public class Convert {

	/**
	 * <span style="font-size: 12px;">将传入的字符串中的汉字转换对应的拼音。</span>
	 * @param s isUpper <span style="font-size: 12px;">需要转换的字符串。</span>
	 * @param isRemoveSpace <span style="font-size: 12px;">是否去除空格。</span>
	 * @return <span style="font-size: 12px;">返回转换后的拼音字符串。</span>
	 */
	public static final String convertToAllChineseCharacter(String s, boolean isRemoveSpace) {
		if (null == s || s.length() == 0) {
			return null;
		}
		char [] charArray = s.toCharArray();
		byte [] byteArray = new byte[2];
		StringBuilder returnString = new StringBuilder();
		for (int i = 0; i < charArray.length; i++) {
			byteArray = Character.toString(charArray[i]).getBytes();
			if (byteArray.length == 1) {
				returnString.append(charArray[i]);
				continue;
			}
			//判断是否为汉字。
			if (byteArray[0] < 0 && byteArray[1] < 0) {
				boolean isFind = false;
				for (int j = 0; j < Data.ALLCHINESECHAR.length; j++) {
					if (Data.ALLCHINESECHAR[j][1].indexOf(charArray[i]) != -1) {
						returnString.append(Data.ALLCHINESECHAR[j][0]);
						isFind = true;
						break;
					}
				}
				if (!isFind) {
					returnString.append(charArray[i]);
				}
			} else {
				returnString.append(charArray[i]);
			}
		}
		if (isRemoveSpace) {
			return returnString.toString().replaceAll(" ", "");
		}
		return returnString.toString();
	}
	
	/**
	 * <span style="font-size: 12px;">将传入的字符串中的汉字转换对应的拼音。</span>
	 * @param s isUpper <span style="font-size: 12px;">需要转换的字符串。</span>
	 * @return <span style="font-size: 12px;">返回转换后的拼音字符串。</span>
	 */
	public static final String convertToAllChineseCharacter(String s) {
		return convertToAllChineseCharacter(s, false);
	}
	
	/**
	 * <span style="font-size: 12px;">将传入的字符中的汉字转换为其汉字的首字母的大写字母。</span>
	 * @param s isUpper <span style="font-size: 12px;">需要转换的字符串。</span>
	 * @return <span style="font-size: 12px;">返回转换后的拼音首字母字符串。</span>
	 */
	public static final String convertToFirstChineseCharacter(String s) {
		return convertToFirstChineseCharacter(s, true, false);
	}
	
	/**
	 * <span style="font-size: 12px;">将传入的字符中的汉字转换为其汉字的首字母的大写字母。</span>
	 * @param s isUpper <span style="font-size: 12px;">需要转换的字符串。</span>
	 * @param isRemoveSpace <span style="font-size: 12px;">是否去除空格。</span>
	 * @return <span style="font-size: 12px;">返回转换后的拼音首字母字符串。</span>
	 */
	public static final String convertToFirstChineseCharacter(String s, boolean isRemoveSpace) {
		return convertToFirstChineseCharacter(s, true, isRemoveSpace);
	}
	
	/**
	 * <span style="font-size: 12px;">将传入的字符中的汉字转换为其汉字的首字母。</span>
	 * @param s <span style="font-size: 12px;">需要转换的字符串。</span>
	 * @param isUpper <span style="font-size: 12px;">是否转换为大写， 为true时转换为全大写，否则转换为全小写。</span>
	 * @param isRemoveSpace <span style="font-size: 12px;">是否去除空格。</span>
	 * @return <span style="font-size: 12px;">返回转换后的拼音首字母字符串。</span>
	 */
	public static final String convertToFirstChineseCharacter(String s, boolean isUpper, boolean isRemoveSpace) {
		if (null == s || s.length() == 0) {
			return null;
		}
		StringBuilder returnStringBuilder = new StringBuilder();
		char [] charArray = s.toCharArray();
		for (char c : charArray) {
			if ((int)c >= 19968 && (int)c <= 40869) {
				for (String str : Data.FIRSTCHINESECHAR) {
					if (str.indexOf(c) > 0) {
						returnStringBuilder.append(str.toCharArray()[0]);
					}
				}
			} else {
				returnStringBuilder.append(c);
			}
		}
		String returnString = returnStringBuilder.toString();
		if (!isUpper) {
			returnString = returnString.toLowerCase();
		}
		if (isRemoveSpace) {

			returnString = returnString.replaceAll(" ", "");
		}
		return returnString;
	}
	
	public static void main(String[] args) {
		System.out.println(convertToFirstChineseCharacter("张三", true, true));
	}

}
