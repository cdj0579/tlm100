package com.unimas.common.date;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

public class DateBetween {
	
	public static enum Type {
		/**
		 * 最近一天
		 */
		LAST_DAY,
		/**
		 * 最近一月
		 */
		LAST_MONTH,
		/**
		 * 最近一年
		 */
		LAST_YEAR,
		/**
		 * 今天
		 */
		SELF_DAY,
		/**
		 * 本月
		 */
		SELF_MONTH,
		/**
		 * 今年
		 */
		SELF_YEAR,
		/**
		 * 自定义范围
		 */
		COSTOM;
		
		public static Type valueOfIgnoreCase(String type){
			for(Type t : Type.values()){
				if(t.toString().equalsIgnoreCase(type)){
					return t;
				}
			}
			return null;
		}
	}
	
	Type type;
	Date startDate;
	Date endDate;
	
	private DateBetween(Type type, Date startTime, Date endTime){
		if(type == null) throw new NullPointerException();
		this.type = type;
		if(Type.COSTOM.equals(type)){
			if(startTime == null && endTime == null) throw new NullPointerException();
			this.startDate = startTime;
			this.endDate = endTime;
		} else {
			this.initDateTime();
		}
	}
	
	public static DateBetween create(Type type){
		if(type == null || Type.COSTOM.equals(type)) throw new NullPointerException();
		return new DateBetween(type, null, null);
	}
	
	public static DateBetween create(Date startTime, Date endTime){
		return new DateBetween(Type.COSTOM, startTime, endTime);
	}
	
	private void roll(Calendar start, Calendar end){
		start.set(Calendar.SECOND, 0);
		end.set(Calendar.SECOND, 59);
		
		start.set(Calendar.MINUTE, 0);
		end.set(Calendar.MINUTE, 59);
		if(Type.LAST_DAY.equals(this.type) || Type.SELF_DAY.equals(this.type)) return;
		start.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.HOUR_OF_DAY, 23);
		
		if(Type.LAST_MONTH.equals(this.type) || Type.SELF_MONTH.equals(this.type)) return;
		start.set(Calendar.DAY_OF_MONTH, 1);
		end.set(Calendar.DAY_OF_MONTH, 1);
		end.roll(Calendar.DAY_OF_MONTH, false);
		
		if(Type.LAST_YEAR.equals(this.type) || Type.SELF_YEAR.equals(this.type)) return;
		start.set(Calendar.MONTH, 0);
		end.set(Calendar.MONTH, 11);
	}
	
	private void initDateTime(){
		if(Type.COSTOM.equals(this.type)) return;
		Calendar end = Calendar.getInstance();
		Calendar start = Calendar.getInstance();
		if(Type.LAST_DAY.equals(this.type)){
			start.add(Calendar.HOUR_OF_DAY, 1);
			start.add(Calendar.DAY_OF_WEEK, -1);
		} else if(Type.LAST_MONTH.equals(this.type)){
			start.add(Calendar.DAY_OF_MONTH, 1);
			start.add(Calendar.MONTH, -1);
		} else if(Type.LAST_YEAR.equals(this.type)){
			start.add(Calendar.MONTH, 1);
			start.add(Calendar.YEAR, -1);
		} else if(Type.SELF_DAY.equals(this.type)){
			start.set(Calendar.HOUR_OF_DAY, 0);
		} else if(Type.SELF_MONTH.equals(this.type)){
			start.set(Calendar.DAY_OF_MONTH, 1);
		} else if(Type.SELF_YEAR.equals(this.type)){
			start.set(Calendar.MONTH, 1);
		}
		roll(start, end);
		this.startDate = start.getTime();
		this.endDate = end.getTime();
	}

	public Date getStartDate() {
		return startDate;
	}
	public String getStartTime(String pattren) {
		return TimeUtils.format(this.startDate, pattren);
	}
	public String getStartTime() {
		return TimeUtils.format(this.startDate);
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getEndTime(String pattren) {
		return TimeUtils.format(this.endDate, pattren);
	}
	public String getEndTime() {
		return TimeUtils.format(this.endDate);
	}
	
	public String toString(){
		return "["+this.getStartTime()+" -- "+this.getEndTime()+"]";
	}
	
	public String getPattren(int field){
		String pattren = TimeUtils.PATTERN_NORMAL;
		if(Type.LAST_DAY.equals(this.type) || Type.SELF_DAY.equals(this.type)){
			pattren = "yyyy-MM-dd HH";
		} else if(Type.LAST_MONTH.equals(this.type) || Type.SELF_MONTH.equals(this.type)){
			pattren = "yyyy-MM-dd";
		} else if(Type.LAST_YEAR.equals(this.type) || Type.SELF_YEAR.equals(this.type)){
			pattren = "yyyy-MM";
		} else if(Type.COSTOM.equals(this.type)){
			switch (field) {
			case Calendar.HOUR_OF_DAY: ;
			case Calendar.HOUR: {
				pattren = "yyyy-MM-dd HH";
				break;
			}
			case Calendar.DAY_OF_MONTH: ;
			case Calendar.DAY_OF_WEEK: ;
			case Calendar.DAY_OF_WEEK_IN_MONTH: ;
			case Calendar.DAY_OF_YEAR: {
				pattren = "yyyy-MM-dd";
				break;
			}
			case Calendar.MONTH: {
				pattren = "yyyy-MM";
				break;
			}
			default: break;
		}
		}
		return pattren;
	}
	
	public int getField(){
		int field = -1;
		if(Type.LAST_DAY.equals(this.type) || Type.SELF_DAY.equals(this.type)){
			field = Calendar.HOUR;
		} else if(Type.LAST_MONTH.equals(this.type) || Type.SELF_MONTH.equals(this.type)){
			field = Calendar.DAY_OF_MONTH;
		} else if(Type.LAST_YEAR.equals(this.type) || Type.SELF_YEAR.equals(this.type)){
			field = Calendar.MONTH;
		}
		return field;
	}
	
	public List<String> groups(int field){
		List<String> groups = Lists.newArrayList();
		String pattren = getPattren(field);
		int f = getField();
		if(f == -1){
			f = field;
		}
		Calendar s = Calendar.getInstance();
		s.setTime(this.startDate);
		while(s.getTime().compareTo(this.endDate) <= 0){
			groups.add(TimeUtils.format(s.getTime(), pattren));
			s.add(f, 1);
		}
		return groups;
	}
	
	public static void main(String[] args) throws Exception {
		DateBetween between = create(Type.LAST_MONTH);
		System.out.println(between);
		System.out.println(between.groups(Calendar.DAY_OF_MONTH));
		
	}

}
