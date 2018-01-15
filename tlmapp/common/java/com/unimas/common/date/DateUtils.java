/*
 * 标　　题：信息安全传输平台
 * 说　　明：
 * 公　　司：杭州合众信息工程有限公司
 * 项目名称：Newdrs
 * 包 名 称：com.unimas.common.base.unimanagesys.common.utils
 * 创建日期：2006-1-5 
 * 版　　本：V1.0
 */

package com.unimas.common.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class DateUtils {

	protected static final Logger log = Logger.getLogger(DateUtils.class);

	/**
	 * 构造函数
	 */
	public DateUtils() {
		super();
	}

	/**
	 * 注释：日期转换成字符串，不包含时分秒！
	 * 
	 * @param date
	 * @throws Exception
	 */
	public static String DatetoStrWithoutHour(Date date) throws Exception {
		String result = "";
		try {
			if (date != null) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				result = calendar.get(Calendar.YEAR) + "-"
						+ (calendar.get(Calendar.MONTH) + 1) + "-"
						+ (calendar.get(Calendar.DATE)) + " "
						+ (calendar.get(Calendar.HOUR_OF_DAY)) + ":"
						+ (calendar.get(Calendar.MINUTE)) + ":"
						+ (calendar.get(Calendar.SECOND));
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	}

	/*
	 * @author nxk StrToDate() 将页面获得的违法时间转化为日期型数据 return
	 */
	public static Date StrToDate(String str) throws Exception {
		if (str.length() == 0) {
			return null;
		}
		Date date = new Date();
		try {
			String str1 = StrToWellStr(str, null);
			int start = str1.indexOf('-');
			String year = str1.substring(0, start);
			start++;
			int start1 = str1.indexOf('-', start);
			String month = str1.substring(start, start1);
			int hourStart = str1.indexOf(' ');
			String day = str1.substring(start1 + 1, hourStart);
			int hourEnd = str1.indexOf(':');
			String hour = str1.substring(hourStart + 1, hourEnd);
			int minuteEnd = str1.indexOf(':', hourEnd + 1);
			String minute = str1.substring(hourEnd + 1, minuteEnd);
			String sec = str1.substring(minuteEnd + 1, minuteEnd + 3);

			int yearint = new Integer(year).intValue();
			int monthint = new Integer(month).intValue();
			int dayint = new Integer(day).intValue();
			int hourint = new Integer(hour).intValue();
			int minuteint = new Integer(minute).intValue();
			int secint = new Integer(sec).intValue();

			if ((yearint > 9999) || (yearint < 1000)) {
				throw new Exception("年份不对！");
			}
			if ((monthint > 12) || (monthint < 1)) {
				throw new Exception("月份不对！");
			}
			if ((dayint > 31) || (dayint < 1)) {
				throw new Exception("日期不对！");
			}
			if ((hourint > 23) || (hourint < 0)) {
				throw new Exception("小时不对！");
			}
			if ((minuteint > 59) || (minuteint < 0)) {
				throw new Exception("分钟不对！");
			}
			if ((secint > 59) || (secint < 0)) {
				throw new Exception("秒钟不对！");
			}
			/*
			 * date.setYear(yearint - 1900); date.setMonth(monthint - 1);
			 * date.setDate(dayint); date.setHours(hourint);
			 * date.setMinutes(minuteint); date.setSeconds(secint);
			 */
		} catch (Exception ex) {
			log.error(ex);
		}
		return date;
	}

	/**
	 * 
	 * 注释：请在这里加入说明！
	 * 
	 * @param str
	 *            时间字符串
	 * @param type
	 *            时间类型（end 一天的结束 , start 一天的开始）
	 * @return
	 * @throws ExceptionTODO
	 */
	public static String StrToWellStr(String str, String type) throws Exception {
		String newStr = null;
		if (str.length() == 0) {
			return null;
		}
		int start = str.indexOf('-');
		String year = str.substring(0, start);
		start++;
		int start1 = str.indexOf('-', start);
		String month = str.substring(start, start1);
		if (month.length() < 2) {
			month = "0" + month;
		}
		int hourStart = str.indexOf(' ');
		String day = "";
		if (hourStart > 0) {
			day = str.substring(start1 + 1, hourStart);
			if (day.length() < 2) {
				day = "0" + day;
			}
		} else {
			day = str.substring(start1 + 1, 10);
		}
		int hourEnd = str.indexOf(':');
		String hour = "";
		if (hourEnd > 0) {
			hour = str.substring(hourStart + 1, hourEnd);
			if (hour.length() < 2) {
				hour = "0" + hour;
			}
			int minuteEnd = str.indexOf(':', hourEnd + 1);
			String minute = "";
			String sec = "";
			if (minuteEnd == -1) {
				minute = str.substring(hourEnd + 1, str.length());
				if (minute.length() < 2) {
					minute = "0" + minute;
				}
				sec = "00";
			} else {
				minute = str.substring(hourEnd + 1, minuteEnd);
				if (minute.length() < 2) {
					minute = "0" + minute;
				}
				sec = str.substring(minuteEnd + 1, minuteEnd + 3);
				if (sec.length() < 2) {
					sec = "0" + sec;
				}
			}
			newStr = year + month + day + hour + minute + sec;
		} else {
			if (type != null) {
				if (type.equals("start")) {
					hour = "000000000";
				} else {
					hour = "235959999";
				}
			}

			newStr = year + month + day + hour;
		}

		return newStr;
	}

	/**
	 * 
	 * 注释：将数据库中的日期字符串（20060817125012）转化成日期格式字符串（2006-08-17 12:50:12）！
	 * 
	 * @param number
	 * @return
	 * @throws ExceptionTODO
	 */
	public static String StringToDateStr(String number) throws Exception {
		String result = "";
		try {
			String[] str = number.split("");
			result = str[1] + str[2] + str[3] + str[4] + "-" + str[5] + str[6]
					+ "-" + str[7] + str[8] + " " + str[9] + str[10] + ":"
					+ str[11] + str[12] + ":" + str[13] + str[14];
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	}

	// lyh
	/**
	 * Logger for this class
	 */
	// private static final Logger logger = Logger.getLogger(DateUtils.class);
	/**
	 * 日期格式化对象
	 */
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 日期时间格式化对象
	 */
	private static DateFormat dateTimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	/**
	 * 时间格式化对象
	 */
	private static DateFormat timeFormat = new SimpleDateFormat("HH:mm");

	/**
	 * 时间格式化对象"yyyy-MM-dd HH:mm:ss"
	 */
	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取时间格式化对象 "yyyy-MM-dd"
	 * 
	 * @return
	 */
	public static final DateFormat getDateFormat() {
		return dateFormat;
	}

	/**
	 * 获取时间日期格式化对象 "yyyy-MM-dd HH:mm"
	 * 
	 * @return
	 */
	public static final DateFormat getDateTimeFormat() {
		return dateTimeFormat;
	}

	
	/**
	 * 获取时间日期格式化对象 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return
	 */
	public static final SimpleDateFormat getSimpleDateFormat() {
		return sdf;
	}

	/**
	 * 获取当前时间的时间对象
	 * 
	 * @return
	 */
	public static final Date nowDate() {
		return new Date();
	}

	/**
	 * 系统最小时间
	 * 
	 * @return
	 */
	public static final Date minDate() {
		return dateBegin(getDate(1900, 1, 1));
	}

	/**
	 * 系统最大时间
	 * 
	 * @return
	 */
	public static final Date maxDate() {
		return dateEnd(getDate(2079, 1, 1));
	}

	/**
	 * 获取指定时间的年
	 * 
	 * @param date
	 * @return
	 */
	public static final int year(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取指定时间的月
	 * 
	 * @param date
	 * @return
	 */
	public static final int month(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定时间的日
	 * 
	 * @param date
	 * @return
	 */
	public static final int day(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取一个时间对象
	 * 
	 * @param year
	 *            格式为：2004
	 * @param month
	 *            从1开始
	 * @param date
	 *            从1开始
	 * @return
	 */
	public static final Date getDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date);
		return calendar.getTime();
	}

	/**
	 * 获取一个时间对象
	 * 
	 * @param year
	 *            格式为：2004
	 * @param month
	 *            从1开始
	 * @param date
	 *            从1开始
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static final Date getDateTime(int year, int month, int date,
			int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date, hour, minute, second);
		return calendar.getTime();
	}

	/**
	 * 在一个已知时间的基础上增加指定的时间
	 * 
	 * @param oleDate
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static final Date addDate(Date oldDate, int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(oldDate);
		calendar.add(Calendar.YEAR, year);
		calendar.add(Calendar.MONTH, month);
		calendar.add(Calendar.DATE, date);
		return calendar.getTime();
	}

	/**
	 * 返回两个时间相差的天数
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static final int dateSub(Date a, Date b) {
		int date = (int) (a.getTime() / (24 * 60 * 60 * 1000) - b.getTime()
				/ (24 * 60 * 60 * 1000));
		return date <= 0 ? 0 : date;
	}
	public static final int dateSubAddOne(Date a, Date b) {
		int date = (int) (a.getTime() / (24 * 60 * 60 * 1000) - b.getTime()
				/ (24 * 60 * 60 * 1000));
		return date <= 0 ? 1 : date + 1;
	}

	/**
	 * 返回两个时间相差多少分钟
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static final int subSecond(Date a, Date b) {
		return (int) (a.getTime() / (1000) - b.getTime() / (1000));
	}

	public static final int subSecond(String str, Date b) {
		Date a = null;
		try {
			a = timeFormat.parse(str);
		} catch (ParseException e) {

			return 0;
		}
		return (int) ((a.getTime() % (24 * 60 * 60 * 1000)) / 1000 - (b
				.getTime() % (24 * 60 * 60 * 1000)) / 1000);
	}

	/**
	 * 一天的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static final Date dateBegin(Date date) {
		if (date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		dateBegin(calendar);
		return calendar.getTime();
	}

	public static final String strDateBegin(Date date) throws Exception {
		if (date == null)
			return null;
		return DateUtils.formatDate(dateBegin(date));
	}

	public static final String strMonthBegin(Date date) {
		return formatDate(monthBegin(date));
	}

	/**
	 * 一天的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static final Date dateEnd(Date date) {
		if (date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		dateEnd(calendar);
		return calendar.getTime();
	}

	/**
	 * 一天的结束时间
	 * 
	 * @param calendar
	 * @return
	 */
	public static final Calendar dateEnd(Calendar calendar) {
		if (calendar == null)
			return null;
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	/**
	 * 一天的开始时间
	 * 
	 * @param calendar
	 * @return
	 */
	public static final Calendar dateBegin(Calendar calendar) {
		if (calendar == null)
			return null;
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	/**
	 * 一月的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static final Date monthBegin(Date date) {
		if (date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DATE, day);
		dateBegin(calendar);
		return calendar.getTime();
	}

	/**
	 * 一月的技术时间
	 * 
	 * @param date
	 * @return
	 */
	public static final Date monthEnd(Date date) {
		if (date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DATE, day);
		dateEnd(calendar);
		return calendar.getTime();
	}

	/**
	 * 一年的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static final Date yearBegin(Date date) {
		if (date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.getActualMinimum(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DATE, month);
		dateBegin(calendar);
		return calendar.getTime();
		// return parseDate(formatDate(date).substring(0,4)+"-01-01");
	}

	/**
	 * 一年的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static final Date yearEnd(Date date) {
		if (date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DATE, day);
		dateEnd(calendar);
		return calendar.getTime();
		// return parseDate(formatDate(date).substring(0,4)+"-12-31");
	}

	/**
	 * 从字符串转换为date 默认格式为 "yyyy-MM-dd"
	 * 
	 * @param source
	 * @return
	 */
	public static final Date parseDate(String source) {
		// if (logger.isDebugEnabled()) {
		// logger.debug("parseDate(String) - start");
		// }

		if (source == null || source.length() == 0)
			return null;
		try {
			Date returnDate = dateFormat.parse(source);
			// if (logger.isDebugEnabled()) {
			// logger.debug("parseDate(String) - end");
			// }
			return returnDate;
		} catch (ParseException e) {
			// logger.error("DateUtil parseDate error", e);
			//
			// if (logger.isDebugEnabled()) {
			// logger.debug("parseDate(String) - end");
			// }
			return null;
		}
	}

	/**
	 * 从字符串转换为date 默认格式为 "yyyy-MM-dd HH:mm"
	 * 
	 * @param source
	 * @return
	 */
	public static final Date parseDateTime(String source) {
		if (source == null || source.length() == 0)
			return null;
		try {
			return dateTimeFormat.parse(source);
		} catch (ParseException e) {
			log.error(e);
			return null;
		}
	}

	/**
	 * 从字符串转换为date 默认格式为 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param source
	 * @return
	 */
	public static final Date parseDateToHMS(String source) {
		if (source == null || source.length() == 0)
			return null;
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			log.error(e);
			return null;
		}
	}

	/**
	 * 格式化输出 默认格式为 "yyyy-MM-dd"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		if (date == null)
			return "";
		return dateFormat.format(date);
	}

	
	
	public static final String formatDate(Date date,String pattern){
		if (date == null)
			return "";
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 格式化输出 默认格式为 "yyyy-MM-dd HH:mm"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		if (date == null)
			return "";
		return dateTimeFormat.format(date);
	}

	/**
	 * 从Date装化为字符串 格式化输出 默认格式为 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToHMS(Date date) {
		if (date == null)
			return "";
		return sdf.format(date);
	}

	/**
	 * 注释：日期转换成字符串，不包含时分秒！
	 * 
	 * @param date
	 * @throws Exception
	 */
	// public static String DatetoStrWithoutHour(Date date) throws Exception {
	// String result = "";
	// try {
	// if (date != null) {
	// Calendar calendar = new GregorianCalendar();
	// calendar.setTime(date);
	// result = calendar.get(Calendar.YEAR) + "-"
	// + (calendar.get(Calendar.MONTH) + 1) + "-"
	// + (calendar.get(Calendar.DATE)) + " "
	// + (calendar.get(Calendar.HOUR_OF_DAY)) + ":"
	// + (calendar.get(Calendar.MINUTE)) + ":"
	// + (calendar.get(Calendar.SECOND));
	// }
	// } catch (Exception e) {
	// throw e;
	// }
	// return result;
	// }
	/**
	 * 注释：验证单个时间是否合法！
	 * 
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static boolean checkoutTime(String time) throws Exception {
		String[] timearray = time.split("\\:");// ":"为特殊符号，不能直接使用split(":")来分隔字符串，必须用split("\\:")
		// 判断输入的时间是否为2位
		if (timearray.length != 3) {
			throw new Exception("输入的时间长度有误！");
		}
		String timeHour = timearray[0];
		String timeMinute = timearray[1];
		// 判断是否为数字
		if (!isNumber(timeHour) || !isNumber(timeMinute)) {
			throw new Exception("输入的时间不是数字！");
		}
		// 判断IP地址是否规范
		try {
			ipCriterion(timeHour, timeMinute);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return true;
	}

	/**
	 * 注释：判断时间输入是否合法！
	 * 
	 * @param timeHour
	 * @param timeMinute
	 * @return
	 * @throws Exception
	 */
	private static boolean ipCriterion(String timeHour, String timeMinute)
			throws Exception {
		int timeone = Integer.parseInt(timeHour);
		int timetwo = Integer.parseInt(timeMinute);
		// 第一个时间在0-23之间，第二个时间在0-59之间；
		if (timeone < 0 || timeone > 23 || timetwo < 0 || timetwo > 59) {
			throw new Exception("输入的时间值错误！");
		}
		return true;
	}

	/**
	 * 注释：判断时间是否为数字！
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumber(String str) {
		if (str == null || str.length() <= 0) {
			return false;
		}
		int num = str.length();
		for (int i = 0; i < num; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	public static int byteToInt(byte[] b) {
		int s = 0;
		for (int i = 0; i < 3; i++) {
			if (b[i] >= 0)
				s = s + b[i];
			else

				s = s + 256 + b[i];
			s = s * 256;
		}
		if (b[3] >= 0) // 最后一个之所以不乘，是因为可能会溢出
			s = s + b[3];
		else
			s = s + 256 + b[3];
		return s;
	}

	/**
	 * 注释：字符串转化为日期（calendar）！
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Calendar stringToCalendar(String date) throws Exception {
		Calendar calendar = Calendar.getInstance();
		try {
			if (date != null && !date.equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date d = sdf.parse(date);
				calendar.setTime(d);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return calendar;
	}

	/**
	 * 注释：日期（calendar）转化为字符串！
	 * 
	 * @param calendar
	 * @return
	 * @throws Exception
	 */
	public static String calendarToString(Calendar calendar) throws Exception {
		String result = "";
		try {
			if (calendar != null) {
				Date d = calendar.getTime();
				SimpleDateFormat sdf = getSimpleDateFormat();
				result = sdf.format(d);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	public static Date getDateToLong(long ldate) throws Exception {
		Date date = new Date();
		try {
			date = new Date(ldate);
		} catch (Exception e) {
			log.error(e);
		}
		return date;
	}

	/**
	 * 注释：根据开始时间和结束时间获取这段时间的每一天！
	 * 
	 * @param kssj
	 * @param jssj
	 * @return
	 * @throws Exception
	 */
	public static String[] getDateSegment(String kssj, String jssj)
			throws Exception {
		int total = dateSub(new SimpleDateFormat("yyyy-MM-dd").parse(jssj),
				new SimpleDateFormat("yyyy-MM-dd").parse(kssj));
		String[] dateString = new String[total + 1];
		Calendar startCalendar = stringToCalendar(kssj);
		Calendar endCalendar = stringToCalendar(jssj);
		dateString[0] = kssj;
		try {
			for (int i = 1; !startCalendar.equals(endCalendar); i++) {
				startCalendar.add(Calendar.DAY_OF_YEAR, 1);
				dateString[i] = new SimpleDateFormat("yyyy-MM-dd")
						.format(startCalendar.getTime());
			}
		} catch (Exception e) {
			log.error(e);
		}
		return dateString;
	}
	/**
	 * 注释：根据开始时间和结束时间获取这段时间的每一月！
	 * 
	 * @param kssj
	 * @param jssj
	 * @return
	 * @throws Exception
	 */
	public static String[] getDateSegment2(String kssj, String jssj)throws Exception {
		String[] dateString = new String[12];
		Calendar startCalendar = stringToCalendar(kssj);
		Calendar endCalendar = stringToCalendar(jssj);
		dateString[0] = kssj;
		try {
			for (int i = 1; !startCalendar.equals(endCalendar); i++) {
				startCalendar.add(Calendar.MONTH, 1);
				dateString[i] = new SimpleDateFormat("yyyy-MM-dd")
						.format(startCalendar.getTime());
			}
		} catch (Exception e) {
			log.error(e);
		}
		return dateString;
	}

	public static void main(String[] args) throws Exception {
		String[] date = DateUtils.getDateSegment("2008-01-01", "2008-09-09");
		for (int i = 0; i < date.length; i++) {
			System.out.println(date[i]);
		}
	}

}
