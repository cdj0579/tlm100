package com.unimas.common.date;


/**
 * @author cj
 * <pre>
 * 一个简单的工具类，用于计算执行时间
 * 
 * 使用方法：
 * TimeCounter tc = new TimeCounter().run();
 * ......
 * consoleLogger.debug("completed in ["+ tc.stop().getMilliSeconds() +"]ms.");
 * </pre>
 */
public class TimeCounter {
	private long _start;
	private long _end;
	
	/**开始
	 * @return
	 */
	public TimeCounter run(){
		_start = System.nanoTime();
		return this;
	}
	
	/**停止
	 * @return
	 */
	public TimeCounter stop(){
		_end = System.nanoTime();
		return this;
	}
	
	/**得到纳秒数
	 * @return
	 */
	public long getNanoTime(){
		long _howLong = _end - _start;
		return _howLong;
	}
	
	/**得到微秒数
	 * @return
	 */
	public long getMicroSeconds(){
		long _howLong = _end - _start;
		_howLong = _howLong/1000;
		return _howLong;
	}
	
	/**得到毫秒数
	 * @return
	 */
	public long getMilliSeconds (){
		long _howLong = _end - _start;
		_howLong = _howLong/1000/1000;
		return _howLong;
	}
}
