package com.unimas.txl.service.fenpei;

import com.unimas.schedule.Job;
import com.unimas.schedule.SProcess;
import com.unimas.schedule.ScheduleManager;
import com.unimas.schedule.trigger.STrigger;
import com.unimas.schedule.trigger.STrigger.Type;

public class FenpeiJob extends SProcess {

	public FenpeiJob(String jobKey, STrigger triggerInfo)
			throws Exception {
		super(jobKey, triggerInfo);
	}

	@Override
	protected void run() {
		System.out.println(11111);
		
	}
	
	public static void startJob() throws Exception {
		String jobKey = "test";
		ScheduleManager schedule = ScheduleManager.getInstance();
		if(schedule.isJobClosed(jobKey)){
			Job job = new Job(new FenpeiJob(jobKey, new STrigger("d,11:27:00", Type.DAY)));
			ScheduleManager.getInstance().startJob(jobKey, job);
		}
	}
	
	public static void main(String[] args) throws Exception {
		startJob();
	}

}
