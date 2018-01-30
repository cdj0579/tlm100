package com.unimas.tlm.service.jfrw.job;

import com.unimas.schedule.Job;
import com.unimas.schedule.SProcess;
import com.unimas.schedule.ScheduleManager;
import com.unimas.schedule.trigger.STrigger;
import com.unimas.schedule.trigger.STrigger.Type;
import com.unimas.tlm.service.jfrw.RwService;

public class RwMonitor extends SProcess {
	
	private RwService service = new RwService();

	public RwMonitor() throws Exception {
		super("rw-monitor-job", new STrigger("10 * * * * ?", Type.CUSTOM));
	}

	@Override
	protected void run() {
		try {
			service.clearCswwcRw();
		} catch (Exception e) {
			logger.error("任务监控任务执行失败！", e);
		}
	}
	
	public static void startMonitorJob() throws Exception {
		RwMonitor monitorJob = new RwMonitor();
		String jobKey = monitorJob.getJobKey();
		ScheduleManager schedule = ScheduleManager.getInstance();
		if(schedule.isJobClosed(jobKey)){
			Job job = new Job(monitorJob);
			ScheduleManager.getInstance().startJob(jobKey, job);
		}
	}
	
	public static void main(String[] args) throws Exception {
		startMonitorJob();
	}
}
