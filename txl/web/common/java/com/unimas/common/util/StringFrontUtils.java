package com.unimas.common.util;

import java.io.UnsupportedEncodingException;

public class StringFrontUtils {
	
	private final static String encoding = "UTF-8";

	/**
	* @param len 需要显示的长度(<font color="red">注意：长度是以byte为单位的，一个汉字是2个byte</font>)
	* @param symbol 用于表示省略的信息的字符，如“...”,“>>>”等。
	* @return 返回处理后的字符串
	*/
	public static String getLimitLengthString(String str, int len, String symbol) throws UnsupportedEncodingException {
	   int counterOfDoubleByte = 0;
	   byte [] b = str.getBytes(encoding);
	   if(b.length <= len)
	     return str;
	   for(int i = 0; i < len; i++){
	     if(b[i] < 0)
	       counterOfDoubleByte++;
	   }

	   if(counterOfDoubleByte % 2 == 0)
	     return new String(b, 0, len, encoding) + symbol;
	   else
	     return new String(b, 0, len - 1, encoding) + symbol;
	}
	
	/**
	 * 获取字符串的长度，中文长度为2
	 * @param str
	 * @return
	 */
	public static int getLength(String str){
		if(str == null) return 0;
		int n = 0;
		char[] tempChar = str.toCharArray();
		for(int i = 0;i<tempChar.length;i++){
			char cr = tempChar[i];
			int l = String.valueOf(cr).getBytes().length;
			if(l > 1){
				n += 2;
			} else {
				n++;
			}
		}
		return n;
	}

	/**
     * 最多从字符串中取前maxLen个字符
     *
     * @param str     被处理字符串
     * @param maxLen  最多字符数，中文算俩字符
     * @param more    后缀字符串,不能是中文
     * @return String
     */
    public static String maxSubstring(String str, int maxLen,String more){
    	if(getLength(str) > maxLen){
    		StringBuffer sb = new StringBuffer();
    		int n = 0;
    		if(more != null && more != null){
    			maxLen -= more.length();
    		}
    		char[] tempChar = str.toCharArray();
    		for(int i = 0;i<tempChar.length;i++){
    			char cr = tempChar[i];
    			int l = String.valueOf(cr).getBytes().length;
    			if(l > 1){
    				n += 2;
    			} else {
    				n++;
    			}
    			if(n <= maxLen){
    				sb.append(cr);
    			} else {
    				break;
    			}
    		}
    		if(more != null && more != null){
    			sb.append(more);
    		}
    		return sb.toString();
    	} else {
    		return str;
    	}
    }
    
    /**
     * 处理字符串，如果为null，返回空字符串
     * @param str
     * @return
     */
    public static String valueOf(String str){
    	if(str == null){
    		return "";
    	} else {
    		return str;
    	}
    }
    
    /**
     * 将字符串中的html特殊字符处理为html转义字符
     * @param html
     * @return
     */
    public static String encodeHtml(String html){
    	if(html == null || "".equals(html)) return html;
    	String temp = html.replaceAll("<", "&lt;");
    	temp = temp.replaceAll(">", "&gt;");
    	temp = temp.replaceAll("&", "&amp;");
    	temp = temp.replaceAll("\"", "&quot;");
    	return temp;
    }
    
	public static String getMat(double time){
		double s = time/1000.0;
		String temp = String.valueOf(s);
		if(temp.indexOf(".") > 0){
			temp = temp.replaceAll("0+?$", "");
			temp = temp.replaceAll("[.]$", "");
		}
		return temp;
	}

}
