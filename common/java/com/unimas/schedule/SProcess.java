package com.unimas.schedule;

import org.apache.log4j.Logger;
import org.quartz.Trigger;

import com.unimas.schedule.trigger.STrigger;

/**
 * @author hxs
 *
 */
public abstract class SProcess {
	protected static final Logger logger = Logger.getLogger(SProcess.class);
	
	protected boolean running = false;
	protected boolean _interrupt = false;
	protected String jobKey;
	private Trigger trigger;
	private int schNo = 1;
	protected STrigger triggerInfo;
	
	public SProcess(String jobKey, STrigger triggerInfo) throws Exception {
		this.jobKey = jobKey;
		this.triggerInfo = triggerInfo;
        this.trigger = this.triggerInfo.toTrigger(this.jobKey);
    }
	
	public SProcess(String jobKey, STrigger triggerInfo, int startSchNo) throws Exception {
		this.jobKey = jobKey;
		this.triggerInfo = triggerInfo;
		this.schNo = startSchNo;
        this.trigger = this.triggerInfo.toTrigger(this.jobKey);
    }

	public STrigger getTriggerInfo() {
		return triggerInfo;
	}
	
	public String getJobKey() {
		return this.jobKey;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRunning() {
		return running;
	}

	public void start() {
		running = true;
		try {
			this.run();
		} finally {
			schNo++;
			running = false;
		}
	}
	
	public Trigger getTrigger() {
		return trigger;
	}
	
	/**
	 * 获取当前运行的调度号
	 */
	public int getCurrentSchNo(){
		return this.schNo;
	}
	
	public void setInterrupt(boolean interrupt) {
        this._interrupt = interrupt;
    }
	
	public boolean getInterrupt() {
        return this._interrupt;
    }
	
	protected abstract void run();
}
