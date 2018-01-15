package com.unimas.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wildcard {
	
	public static final Pattern ip_pattern = Pattern.compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
	
	public static Matcher ip(String str){
		if(str == null) throw new NullPointerException();
		return ip_pattern.matcher(str);
	}
	
	/**
	 * 查找@通配符
	 * 如：this is @name --> @name
	 * @param str   查找对象
	 * @return
	 */
	public static Matcher equalWildcard(String str){
		if(str == null) throw new NullPointerException();
		Pattern p = Pattern.compile("[\\w]+=");
		return p.matcher(str);
	}
	
	/**
	 * 查找@通配符
	 * 如：this is @name --> @name
	 * @param str   查找对象
	 * @return
	 */
	public static Matcher atWildcard(String str){
		if(str == null) throw new NullPointerException();
		Pattern p = Pattern.compile("@[\\w]+");
		return p.matcher(str);
	}
	
	public static void main(String[] args) {
		String str = "this is @name1 sdf @ @, that are no @name2.";
		String newStr = str;
		Matcher matcher = Wildcard.atWildcard(str);
		System.out.println("***********处理前**********");
		System.out.println(newStr);
		int i = 1;
		System.out.println("----------------------");
		while(matcher.find()){
			String atName = matcher.group();
			System.out.println(i+": "+atName+" --> " +("?"+i));
			newStr = newStr.replaceFirst(atName, "?"+i);
			System.out.println("newStr: "+newStr);
			i++;
		}
		System.out.println("----------------------");
		System.out.println("***********处理后**********");
		System.out.println(newStr);
	}

}
