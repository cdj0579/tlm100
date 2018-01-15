package com.unimas.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nxk
 * 
 *         更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class StringUtils {

	/**
	 * 判断字符串不为空<br/>
	 * null对象、""、"  "、"null"、"NULL"，都表示为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && !"".equals(str.trim())
				&& !"null".equalsIgnoreCase(str);
	}

	/**
	 * 判断字符串不为空<br/>
	 * null对象、""，都表示为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return str != null && !"".equals(str);
	}

	public static byte[] longToByte_little(long number) {
		long temp = number;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	public static long byteToLong_little(byte[] b) {
		long s = 0;
		long s0 = b[0] & 0xff;// 最低位
		long s1 = b[1] & 0xff;
		long s2 = b[2] & 0xff;
		long s3 = b[3] & 0xff;
		long s4 = b[4] & 0xff;// 最低位
		long s5 = b[5] & 0xff;
		long s6 = b[6] & 0xff;
		long s7 = b[7] & 0xff;

		// s0不变
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;

		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;

		return s;
	}

	/**
	 * 注释：int到字节数组的转换！
	 * 
	 * @param number
	 * @return
	 */
	public static byte[] intToByte_little(int number) {
		int temp = number;
		byte[] b = new byte[4];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	/**
	 * 注释：字节数组到int的转换！
	 * 
	 * @param b
	 * @return
	 */
	public static int byteToInt_little(byte[] b) {
		int s = 0;
		int s0 = b[0] & 0xff;// 最低位
		int s1 = b[1] & 0xff;
		int s2 = b[2] & 0xff;
		int s3 = b[3] & 0xff;

		s3 <<= 24;
		s2 <<= 16;
		s1 <<= 8;

		s = s0 | s1 | s2 | s3;

		return s;
	}

	/**
	 * 注释：short到字节数组的转换！
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] shortToByte_little(short number) {
		int temp = number;
		byte[] b = new byte[2];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	/**
	 * 注释：字节数组到short的转换！
	 * 
	 * @param b
	 * @return
	 */
	public static short byteToShort_little(byte[] b) {
		short s = 0;
		short s0 = (short) (b[0] & 0xff);// 最低位
		short s1 = (short) (b[1] & 0xff);

		s1 <<= 8;

		s = (short) (s0 | s1);

		return s;
	}

	public static String getNumber(String str) {
		if (str == null || str.length() <= 0) {
			return str;
		}
		String result = "";
		int num = str.length();
		for (int i = 0; i < num; i++) {
			char c = str.charAt(i);
			if (c >= '0' && c <= '9') {
				result += c;
			}
		}

		return result;
	}

	public static String toGbk(String str) throws Exception {
		String result = "";
		if (str != null && !str.equals(""))
			try {
				System.out.println("乱码转换前=>" + str);
				result = new String(str.getBytes("ISO-8859-1"), "GBK");
				System.out.println("乱码转换后=>" + result);
			} catch (UnsupportedEncodingException e) {
				throw e;
			}
		return result;
	}

	public static String toUTF8(String str) throws Exception {
		String result = "";
		if (str != null && !str.equals(""))
			try {
				System.out.println("乱码转换前=>" + str);
				result = new String(str.getBytes("ISO-8859-1"), "UTF-8");
				System.out.println("乱码转换后=>" + result);
			} catch (UnsupportedEncodingException e) {
				throw e;
			}
		return result;
	}

	/**
	 * 得到str中出现stub的个数.
	 * 
	 * @param str
	 * @param stub
	 * @return
	 */
	public static int getRepeatCount(String str, String stub) {
		int count = 0;
		int checkLen = 0;
		while ((checkLen = str.indexOf(stub, checkLen)) >= 0) {
			checkLen += stub.length();
			count++;
		}
		return count;
	}

	/**
	 * 判断sql 中出现参数的问好
	 * 
	 * @param s
	 * @return
	 */
	public static int getRepeatCount(String sql) {
		String where = " WHERE ";
		String str = sql.substring(sql.indexOf(where) + where.length());
		char tag = '\'';
		char q = '?';
		boolean isHalfTag = false;
		int count = 0;
		for (char value : str.toCharArray()) {
			if (value == tag) { // 遇到单引号
				isHalfTag = !isHalfTag; // 判断是否半个标签
			} else if (value == q) { // 说明遇到问号
				if (!isHalfTag) { // 说明问号不在单引号里面 这个问号算参数
					count++;
				}
			}

		}
		return count;
	}

	public static Date getSessionTime(int s) {
		Date result = new Date();
		result = new Date(s * 1000 + new Date().getTime());
		return result;
	}

	public static String isNotNull(String str) {
		if (str != null) {
			return str;
		} else {
			return "";
		}
	}

	/**
	 * 比较2个list 获取不同元素
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<String> compareDiffList(final List<String> list1,final List<String> list2) {
		List<String> list = new ArrayList<String>();
		list.addAll(list1);
		list.removeAll(list2); // 从list中移除与list1相同的元素
		return list;
	}

	


	public static void main(String[] args) {
		byte[] src = { -82, -36 };
		String b = new String(src);
		// short dsc=byteToShort_little(src);
		// String.valueOf(dsc);
		System.out.println(b);

		byte[] l_2 = new byte[] { 53, 28, -36, -33, 2, 0, 0, 0 };
		System.out.println(byteToLong_little(l_2));

		long l = 12345678901L;
		byte[] l_b = longToByte_little(l);
		System.out.print(l + ": ");
		for (int i = 0; i < l_b.length; i++) {
			System.out.print(l_b[i] + " ");
		}
		System.out.println();
	}
}
