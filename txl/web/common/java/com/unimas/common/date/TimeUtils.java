package com.unimas.common.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理工具类
 * @author cj
 *
 */
public class TimeUtils {
	
	/**
	 * 格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 格式：yyyy-MM-dd
	 */
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	/**
	 * 格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String PATTERN_NORMAL = PATTERN_DATETIME;
	
	/**
	 * 获取当前时间，格式为：yyyy-MM-dd HH:mm:ss
	 * 输出如：2011-06-30 15:01:55
	 * @return
	 */
	public static String getTime(){
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat(PATTERN_NORMAL);
		String time=df.format(date);
		return time;

	}
	
	public static Date add(Date date, int dayCount){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, dayCount);
		return c.getTime();
	}
	
	public static String format(Date date) {
        return format(date, PATTERN_NORMAL);
    }
	
	public static String format(Date date, String s) {
        return (new SimpleDateFormat(s)).format(date);
    }
	
	public static Date parse(String str) throws ParseException {
        return parse(str, PATTERN_NORMAL);
    }
	
	public static Date parse(String str, String pattern) throws ParseException {
        return (new SimpleDateFormat(pattern)).parse(str);
    }
	
	/**
	 * 判断date1是否在date2之后
	 * @param date1   时间字符串
	 * @param date2  时间字符串
	 * @param pattern 时间格式  
	 * @return
	 * @throws ParseException
	 */
	public static boolean isAfter(String date1, String date2, String p) throws ParseException {
	    SimpleDateFormat simpledateformat = new SimpleDateFormat(p);
	    Date d1 = simpledateformat.parse(date1);
	    Date d2 = simpledateformat.parse(date2);
	    return d1.after(d2);
	}
	
	/**
	 * 判断date是否超时
	 * @param date     要判断的时间
	 * @param outtime  超时的时间，毫秒
	 * @param pattern 时间格式  
	 * @return
	 * @throws ParseException
	 */
	public static boolean timeout(Date date, long outtime) {
	    return new Date().getTime() > (date.getTime()+outtime);
	}

	/**
	 * 判断date1是否在date2之后，时间格式为：yyyy-MM-dd HH:mm:ss
	 * @param date1   时间字符串
	 * @param date2   时间字符串
	 * @return
	 * @throws ParseException
	 */
	public static boolean isAfter(String date1, String date2) throws ParseException {
	    return isAfter(date1, date2, PATTERN_NORMAL);
	}
	
	public static void main(String[] args) throws ParseException {
		String date1 = "2014-12-24 12:15:55";
		String date2 = "2014-12-23 12:15:56";
		System.out.println(TimeUtils.isAfter(date1, date2));
	}
}
