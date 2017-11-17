package com.unimas.common.encode;

/**
 * unicode工具类
 * @author cj
 * 
 */
public class UnicodeUtils {
	/**
	 * 将字符串转化为unicode 如"中国"，转化为"\\u4e2d\\u56fd"
	 * 
	 * @param str
	 * @return
	 */
	public static String toHTMLUnicode(String str) {
		StringBuffer ostr = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if ((ch >= 0x0020) && (ch <= 0x007e)) //在ASCII范围内的字符不需要被转换
			{
				ostr.append(ch);
			} else {
				ostr.append("\\u"); // 标准unicode格式,以\\u开头.
				String hex = Integer.toHexString(str.charAt(i) & 0xFFFF); // 获取16进制值.
				for (int j = 0; j < 4 - hex.length(); j++){
					ostr.append("0");// 位长少于4位的补位为0
				}
				ostr.append(hex.toLowerCase()); //转为小写.
			}
		}
		return (new String(ostr));
	}

	public static void main(String[] args) {
		System.out.println(UnicodeUtils.toHTMLUnicode("中国"));

	}
}
