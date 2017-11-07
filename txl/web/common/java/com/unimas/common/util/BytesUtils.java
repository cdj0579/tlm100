package com.unimas.common.util;

/**
 * 字节操作工具类
 * @author cj
 *
 */
public class BytesUtils {
	/**
	  * byte数组转换成16进制字符串
	  * @param src
	  * @return
	  */
	public static String bytesToHexString(byte[] src){
		StringBuilder stringBuilder = new StringBuilder();     
		if (src == null || src.length <= 0) {     
		    return null;     
		} 
		for (int i = 0; i < src.length; i++) {     
		    int v = src[i] & 0xFF;     
		    String hv = Integer.toHexString(v);     
		    if (hv.length() < 2) {     
		        stringBuilder.append(0);     
		    }     
		    stringBuilder.append(hv);     
		}     
		return stringBuilder.toString();     
	}

	/**
	 * 将int转为字节数组
	 * 将最低位保存在低字节
	 * 即：0f 00 00 00 代表int值15
	 * @param number
	 * @return
	 */
	public static byte[] intToByte_little(int number) {
	    int temp = number;
	    byte[] b = new byte[4];
	    for (int i = 0; i < b.length; i++) {
	        b[i] = new Integer(temp & 0xff).byteValue(); // 将最低位保存在低字节
	        temp = temp >> 8; // 向右移8位
	    }
	    return b;
	}
	
	/**
	 * 将int转为字节数组
	 * 将最低位保存在高字节
	 * 即：00 00 00 0f 代表int值15
	 * @param number
	 * @return
	 */
	public static byte[] intToByte_big(int number) {
	    int temp = number;
	    byte[] b = new byte[4];
	    for (int i = b.length-1; i >= 0; i--) {
	        b[i] = new Integer(temp & 0xff).byteValue(); // 将最低位保存在高字节
	        temp = temp >> 8; // 向右移8位
	    }
	    return b;
	}
	
	 /**
     * 将字节数组转化为int值
     * 最低位保存在低字节的情况
     * 即0f 00 00 00 代表int值15
     * @param b
     * @return
     */
    public static int byteToInt_little(byte[] b) {
        int s = 0;
        int s0 = b[0] & 0xff; // 最低位
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
     * 将字节数组转化为int值
     * 最低位保存在高字节的情况
     * 即00 00 00 0f 代表int值15
     * @param b
     * @return
     */
    public static int byteToInt_big(byte[] b) {
        int s = 0;
        int s0 = b[0] & 0xff; // 最低位
        int s1 = b[1] & 0xff;
        int s2 = b[2] & 0xff;
        int s3 = b[3] & 0xff;
        s0 <<= 24;
        s1 <<= 16;
        s2 <<= 8;
        s = s0 | s1 | s2 | s3;
        return s;
    }
}
