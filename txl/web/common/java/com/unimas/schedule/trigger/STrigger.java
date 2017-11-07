package com.unimas.schedule.trigger;

import org.quartz.Trigger;

/**
 * @author hxs
 *
 */
public class STrigger implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum Type {
		/**
		 * 周期调度，不计算运行的时间<br/>
		 * 上次启动后，指定间隔时间后运行
		 */
		INTERVAL(0),
		/**
		 * 循环调度，计算运行的时间<br/>
		 * 上次运行完成后，指定间隔时间后运行
		 */
		LOOP(1),
		/**
		 * 按天调度
		 */
		DAY(2),
		/**
		 * 按周调度
		 */
		WEEK(3),
		/**
		 * 按月调度
		 */
		MONTH(4);
		private int value;
		private Type(int value){this.value = value;}
		public int value(){return this.value;}
	}
	
	/**
	 * 将数值转换成Type类型
	 * @param type
	 * @return
	 */
	public static Type transformType(int type){
		if(type == Type.LOOP.value()) {
			return Type.LOOP;
		} else if(type == Type.INTERVAL.value()) {
			return Type.INTERVAL;
		}else if(type == Type.DAY.value()) {
			return Type.DAY;
		} else if(type == Type.WEEK.value()) {
			return Type.WEEK;
		} else if(type == Type.MONTH.value()) {
			return Type.MONTH;
		} else {
			return null;
		}
	}

	private Type type;
	private String value;
	
	/**
	 * 	周期调度 value格式: "3,8"，表示调度3次，间隔时间为8秒<br/>
	 *  循环调度 value格式: "3,8"，表示调度3次，间隔时间为8秒<br/>
	 *  按天调度 value格式: "d,12:30:25"，表示每天12点30分25秒开始调度<br/>
	 *  按周调度 value格式: "1,12:30:25"，表示每周一12点30分25秒开始调度<br/>
	 *  按月调度 value格式: "12,12:30:25"，表示每月第12天12点30分25秒开始调度
	 * @param value
	 * @param type
	 */
	public STrigger(String value, Type type){
		this.type = type;
		this.value = value;
	}
	
	public STrigger(String value, int type){
		this(value, transformType(type));
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public void setType(int type) {
		this.type = transformType(type);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String[] formatValue() {
		if (value == null || "".equals(value.trim())) {
			return null;
		}
		return value.split(",");
	}
	
	public Trigger toTrigger(String jobKey) throws Exception {
		String [] schedules = formatValue();
		if (Type.INTERVAL.equals(type)) {
			int count = 1;
			int time = 0;
			if(schedules != null){
				count = Integer.parseInt(schedules[0]);
				time = Integer.parseInt(schedules[1]);
			}
        	return SimpleTriggers.repeatSecondlyForTotalCount(jobKey, count, time);
        } else if (Type.LOOP.equals(type)) {
        	return SimpleTriggers.repeatSecondlyForTotalCount(jobKey, 1, 0);
        } else if (Type.DAY.equals(type)) {
        	return CronTriggers.startEveryDayByPointedTime(jobKey, stringFormatToExpression(schedules[1]));
        } else if (Type.WEEK.equals(type)) {
        	return CronTriggers.startEveryDayByPointedTime(jobKey, stringEveryWeekFormatToExpression(schedules[1], Integer.parseInt(schedules[0])));
        } else if (Type.MONTH.equals(type)) {
        	return CronTriggers.startEveryDayByPointedTime(jobKey, stringEveryMonthFormatToExpression(schedules[1], Integer.parseInt(schedules[0])));
        } else {
        	throw new Exception("调度类型["+type+"]不存在！");
        }
	}
	
	private static String stringFormatToExpression(String str) {
	    String[] temp = str.split(":");
	    return temp[2] + " " + temp[1] + " " + temp[0] + " ? * *";
	}
	
	private static String stringEveryMonthFormatToExpression(String time, int day) {    			
	    String[] temp = time.split(":");
	    return temp[2] + " " + temp[1] + " " + temp[0] + " " + day + " * ?";
	}
	
	private static String stringEveryYearFormatToExpression(String time, int day, int month) {
	    String[] temp = time.split(":");
	    return temp[2] + " " + temp[1] + " " + temp[0] + " " + day + " " + month +  " ?";
	}
	
	private static String stringEveryWeekFormatToExpression(String time, int week) {
		String weekString = "";
		switch (week) {
			case 1:
				weekString = "MON";
				break;
			case 2:
				weekString = "TUE";
				break;
			case 3:
				weekString = "WED";
				break;
			case 4:
				weekString = "THU";
				break;
			case 5:
				weekString = "FRI";
				break;
			case 6:
				weekString = "SAT";
				break;
			case 7:
				weekString = "SUN";
				break;
			default:
			break;
		}
	    String[] temp = time.split(":");
	    return temp[2] + " " + temp[1] + " " + temp[0] + " ? * " + weekString;
	}

}
