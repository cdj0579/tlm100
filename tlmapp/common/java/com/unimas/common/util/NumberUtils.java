/*
 * 标　　题：信息安全传输平台
 * 说　　明：
 * 公　　司：杭州合众信息工程有限公司
 * 项目名称：Newdrs
 * 包 名 称：com.tiansong.infosafetranflag.common.utils
 * 创建日期：2005-12-31 
 * 版　　本：V1.0
 */

package com.unimas.common.util;

/**
 * 中文类名：数据处理 开发人员：朱松涛 创建时间：2007-4-26 14:55:10 功能说明：请在这里加入说明！
 * <p>
 * <a href="NumberUtils.java.html"><i>察看源代码</i></a>
 * </p>
 */
public class NumberUtils {
	/**
	 * 声明构造函数为私有的情况下，页面引用中将不会被实例化
	 */
	private NumberUtils() {
	}

	/**
	 * 生成随机数
	 * 
	 * @param sLen
	 * @return String
	 */
	public static String randomKey(int sLen) {
		String base;
		String temp;
		int i;
		int p;

		base = "1234567890";
		temp = "";
		for (i = 0; i < sLen; i++) {
			p = (int) (Math.random() * 10);
			temp += base.substring(p, p + 1);
		}
		return (temp);
	}

	/**
	 * short类型数值转换为byte类型，不是按字母转换。 例如 10数值转换后为 [0]:00 [1]:0a
	 * 
	 * @param text
	 * @return
	 */
	public static byte[] shortToByte(short text) {
		byte[] array = new byte[2];
		array[0] = (byte) ((text >> 8) & 0xff);
		array[1] = (byte) (text & 0xff);

		return array;
	}

	/**
	 * int类型数值转换为byte类型，不是按字母转换。 例如 10 数值转换后为 [0]:00 [1]:00 [2]:00 [3]:0a
	 * 
	 * @param text
	 * @return
	 */
	public static byte[] intToByte(int text) {
		byte[] array = new byte[4];
		array[0] = (byte) ((text >> 24) & 0xff);
		array[1] = (byte) ((text >> 16) & 0xff);
		array[2] = (byte) ((text >> 8) & 0xff);
		array[3] = (byte) (text & 0xff);

		return array;
	}

	public static void main(String[] args) {
		System.out.println(randomKey(8));
	}
}
