package com.unimas.common.md5;


/**
 * @中文类名:MD5Factory
 * @开发人员:rq
 * @开发日期:Sep 16, 2008
 * @功能说明:
 */
public class MD5Factory {

	private static MD5 md5 = new MD5();

	private static MD5Factory factory = new MD5Factory();

	public static MD5Factory getInstance() {
		return factory;
	}

	private MD5Factory() {
		initMD5Instance();
	}

	private static void initMD5Instance() {
		if (md5 == null) {
			md5 = new MD5();
		}
	}

	public synchronized static String hashCode(String str){
		String result = "";
		if (md5 == null) {
			initMD5Instance();
		}
		result = md5.calcMD5(str);
		return result;
	}

}
