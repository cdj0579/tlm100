package com.unimas.common.util;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {

	public static String getip(HttpServletRequest request){
		return request.getHeader("host");
	}

    /**
     * 判断两个IP是否在同个IP段
     * @param netMask
     * @param iP1
     * @param iP2
     * @return
     */
    public static boolean judgeByNetMask(String netMask, String iP1, String iP2) {
        String[] ntMask = splitString(netMask);
        String[] ntMaskBinary = getBinaryString(ntMask);
        String[] ip1 = splitString(iP1);
        String[] ip1Binary = getBinaryString(ip1);
        String[] ip2 = splitString(iP2);
        String[] ip2Binary = getBinaryString(ip2);
        boolean in = true;
        for (int i = 0; i < ntMaskBinary.length && in; i++) {
            for (int j = 0; j < ntMaskBinary[i].length() && in; j++) {
                char k = '0';
                if (ntMaskBinary[i].charAt(j) == '1') {
                    if (ntMaskBinary[i].charAt(j) == ip1Binary[i].charAt(j)) {
                        k = '1';
                    } else {
                        k = '0';
                    }
                    if (ip2Binary[i].charAt(j) != k) {
                        in = false;
                    }
                }
            }
        }
        return in;
    }

    public static String[] splitString(String s) {
        return s.split("\\.");
    }
    
    public static int LEN = 4;
    
    /**
     * 得到二进制数字
     * @param s
     * @return
     */
    public static String[] getBinaryString(String[] s) {
        String[] bS = new String[LEN];
        for (int i = 0; i < s.length; i++) {
            StringBuffer sb = new StringBuffer();
            String sTemp = Integer.toBinaryString(Integer.valueOf(s[i]));
            if (sTemp.length() < 8) {
                for (int j = 0; j < 8 - sTemp.length(); j++) {
                    sb.append("0");
                }
                sb.append(sTemp);
                bS[i] = sb.toString();
            } else {
                bS[i] = sTemp;
            }
        }
        return bS;
    }

}
