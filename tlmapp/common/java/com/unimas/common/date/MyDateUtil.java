package com.unimas.common.date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateUtil {
    /** 
     * 获取 当前年、半年、季度、月、日、小时 开始结束时间 
     */  
  
    private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");  
    private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat hourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
    private final static String Daystart = "yyyy-MM-dd 00:00:00";
    private final static String Dayend = "yyyy-MM-dd 23:59:59";
    private final static String ends = " 00:00:00";
    private final static String ende = " 23:59:59";
    private final static String hends = ":00:00";
    private final static String hende = ":59:59";
    /**
	 * 一个小时的开始时间
	 * 
	 * @param calendar
	 * @return
	 */
	public static final String hourBegin(String calendar) {
		if (calendar == null)
			return null;
		return calendar.concat(hends);
	}
	/**
	 * 一个小时的结束时间
	 * 
	 * @param calendar
	 * @return
	 */
	public static final String hourEnd(String calendar) {
		if (calendar == null)
			return null;
		return calendar.concat(hende);
	}
 
    /**
	 * 一天的结束时间
	 * 
	 * @param calendar
	 * @return
	 */
	public static final String dateEnd(String calendar) {
		if (calendar == null)
			return null;
		return Dayend.replace("yyyy-MM-dd",calendar);
	}
	

	/**
	 * 一天的开始时间
	 * 
	 * @param calendar
	 * @return
	 */
	public static final String dateBegin(String calendar) {
		if (calendar == null)
			return null;
		return Daystart.replace("yyyy-MM-dd",calendar);
	}

	/**
	 * 一月的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static final String monthBegin(String date) {
		if (date == null)
			return null;
		return date.concat(ends);
	}

	/**
	 * 一月的结束时间
	 * 
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static final String monthEnd(String data) {
		if (data == null)
			return null;
		Date date=null;
		try {
			date = shortSdf.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DATE, day);
		DateUtils.dateEnd(calendar);
		return longSdf.format(calendar.getTime());
	}

    /** 
     * 获得本天的开始时间，即2012-01-01 00:00:00 
     *  
     * @return 
     */  
    public static String getCurrentDayStartTime() {  
        Date now = new Date();  
        String time=null;
        try {  
            now = shortSdf.parse(shortSdf.format(now)); 
            time=longSdf.format(now);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return time;  
    }  
  
    /** 
     * 获得本天的结束时间，即2012-01-01 23:59:59 
     *  
     * @return 
     */  
    public static String getCurrentDayEndTime() {  
        Date now = new Date(); 
        String time=null;
        try {  
            now = longSdf.parse(shortSdf.format(now) + " 23:59:59"); 
            time=longSdf.format(now);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return time;  
    }  
    /** 
     * 获得本月的开始时间，即2012-01-01 00:00:00 
     *  
     * @return 
     */  
    public static String getCurrentMonthStartTime() {  
        Calendar c = Calendar.getInstance();  
        String  time=null;
        try {  
            c.set(Calendar.DATE, 1);  
            time = shortSdf.format(c.getTime());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return time;  
    }  
  
    /** 
     * 当前月的结束时间，即2012-01-31 23:59:59 
     *  
     * @return 
     */  
    public static String getCurrentMonthEndTime() {  
        Calendar c = Calendar.getInstance();  
        Date now = null;
        String time=null;
        try {  
            c.set(Calendar.DATE, 1);  
            c.add(Calendar.MONTH, 1);  
            c.add(Calendar.DATE, -1);  
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"); 
            time=longSdf.format(now);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return time;  
    }  
    /** 
     * 当前年的开始时间，即2012-01-01 00:00:00 
     *  
     * @return 
     */  
    public static String getCurrentYearStartTime() {  
        Calendar c = Calendar.getInstance();  
        String time=null;
        try {  
            c.set(Calendar.MONTH, 0);  
            c.set(Calendar.DATE, 1);  
            time =shortSdf.format(c.getTime());
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return time;  
    }  
  
    /** 
     * 当前年的结束时间，即2012-12-31 23:59:59 
     *  
     * @return 
     */  
    public static String getCurrentYearEndTime() {  
        Calendar c = Calendar.getInstance();  
        Date now = null;
        String time=null;
        try {  
            c.set(Calendar.MONTH, 11);  
            c.set(Calendar.DATE, 31);  
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
            time=longSdf.format(now);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return time;  
    }  
    
    /** 
     * 当前时间，即2012-12-31 23:59:59 
     *  
     * @return 
     */  
    public static String getCurrentTime() {  
        Calendar c = Calendar.getInstance();  
        String time=null;
        try {  
            time=shortSdf.format(c.getTime());
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return time;  
    }  
    /**
     * 
     * 获得一个月的开始时间到当前时间
     * @param args
     * @throws Exception 
     */
    public static String[] getDateBeginToCurr() {  
        String  begin=getCurrentMonthStartTime();
        String  current=getCurrentTime();
    
        String[] dates=null;
		try {
			dates = DateUtils.getDateSegment(begin, current);
		} catch (Exception e) {	
		}
        return dates;  
    } 
    /**
     * 
     * 获得到当前时间30天
     * @param args
     * @throws Exception 
     */
    public static String[] getDateBeginToCurr2() {  
    	 String[] datess=new String[30];
         Calendar   ca=Calendar.getInstance();
         String str1=shortSdf.format(ca.getTime());
         datess[datess.length-1]=str1;
         int day=ca.get(Calendar.MONTH);
         String str2=null;
         for(int i=1;i<30;i++){
         	 ca.add(Calendar.DATE, -1);
         	 str2=shortSdf.format(ca.getTime());
         	 datess[datess.length-1-i]=str2;
         }
         return datess;   
    } 
    /**
     * 
     * 获得一个年的开始时间到当前时间
     * @param args
     * @throws Exception 
     */
    public static String[] getYearBeginToCurr() {  
        String  begin=getCurrentYearStartTime();
        String  current=getCurrentMonthStartTime();
        String[] dates=null;
		try {
			dates = DateUtils.getDateSegment2(begin, current);
		} catch (Exception e) {	
		}
		int i=0;
		for(String d:dates){
			if(d==null){
				
			}else
			{
			i++;
		    }
		}
		String[] datess=new String[i];
		for(int j=0;j<i;j++){
			datess[j]=dates[j];
		}
        return datess;  
    } 
    /**
     * 
     * 获得到当前时间12个月
     * @param args
     * @throws Exception 
     */
    public static String[] getYearBeginToCurr2() {  
        String[] datess=new String[12];
        Calendar   ca=Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 1);
        String str1=shortSdf.format(ca.getTime());
        datess[datess.length-1]=str1;
        int day=ca.get(Calendar.MONTH);
        String str2=null;
        for(int i=1;i<12;i++){
        	 ca.add(Calendar.MONTH, -1);
        	 str2=shortSdf.format(ca.getTime());
        	 datess[datess.length-1-i]=str2;
        }
        return datess;  
    } 
    
    
    
	public static void main(String[] args) {
       /* String[] date =getYearBeginToCurr() ;
		for (int i = 0; i < date.length; i++) {
			System.out.println(date[i]);
		}
		System.out.println(dateBegin("2016-03-16"));
		System.out.println(dateEnd("2016-03-16"));
		System.out.println(monthBegin("2016-03-01"));
		System.out.println(monthEnd("2016-03-16"));*/
		
		
		String[] date =getHourBeginToCurr();
		
		for (int i = 0; i < date.length; i++) {
			System.out.println(date[i]);
		}
	}
	   /**
     * 
     * 获得一天的的开始时间到当前时间的每一个小时
     * @param args
     * @throws Exception 
     */
	 public static String[] getHourBeginToCurr() {
		
		Calendar c = Calendar.getInstance();  
		int hour=c.get(Calendar.HOUR_OF_DAY);
		String[] hours=new String[hour+1];
		String str1=hourSdf.format(c.getTime());
//		System.out.println(str1);
		hours[hour]=str1;
		String  str2=null;
		hour--;
		while(hour>=c.getMinimum(Calendar.HOUR_OF_DAY)){
			c.add(Calendar.HOUR_OF_DAY, -1);
			str2=hourSdf.format(c.getTime());
//			System.out.println(str2);
			hours[hour]=str2;
			hour--;
		}
		return hours; 
		 
		 
		 
	 }
	  /* 
	     * 获得一天的的开始时间到当前时间的每一个小时24个小时
	     * 
	     * @param args
	     * @throws Exception 
	     */
		 public static String[] getHourBeginToCurr2() {
			
			Calendar c = Calendar.getInstance();  
			int hour=c.get(Calendar.HOUR_OF_DAY);
			String[] hours=new String[24];
			String str1=hourSdf.format(c.getTime());
//			System.out.println(str1);
			hours[hours.length-1]=str1;
			String  str2=null;
		    for(int i=1;i<24;i++){
		    	c.add(Calendar.HOUR_OF_DAY, -1);
	        	 str2=hourSdf.format(c.getTime());
	        	 hours[hours.length-1-i]=str2; 	
		    }
			
			return hours; 
			 
			 
			 
		 }


}
