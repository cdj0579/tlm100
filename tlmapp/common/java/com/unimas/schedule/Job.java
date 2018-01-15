package com.unimas.schedule;

import org.apache.log4j.Logger;
import org.quartz.*;

import com.unimas.common.date.TimeUtils;
import com.unimas.schedule.trigger.STrigger;

/**
 * User: hxs
 */
public class Job implements InterruptableJob {
	
	private static final Logger logger = Logger.getLogger("UMSLogger");
	
	/**
	 * 任务对象标识
	 */
	public static final String PROCESS_TYPE = "process";

    SProcess process;
    public Job(SProcess process){
    	this.process = process;
    }
    public SProcess getProcess() {
    	return this.process;
    }
    
    /**
     * 不要用这个构造函数，此构造函数是只给调度器使用
     */
    @Deprecated
    public Job(){}
    
    @Override
    public void interrupt() throws UnableToInterruptJobException {
        process.setInterrupt(true);
        System.out.println("@@被叫停了。。。。。");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	JobDetail jobDetail = jobExecutionContext.getJobDetail();
    	logger.error("任务["+jobDetail.getKey()+"]开始: " + TimeUtils.getTime());
    	System.out.println("任务["+jobDetail.getKey()+"]开始: " + TimeUtils.getTime());
    	JobDataMap jobDataMap = jobDetail.getJobDataMap();
        process = (SProcess) jobDataMap.get(PROCESS_TYPE);
        if (!process.getInterrupt()) {
        	process.start();
        }
        logger.error("任务["+jobDetail.getKey()+"]结束: " + TimeUtils.getTime());
        System.out.println("任务["+jobDetail.getKey()+"]结束: " + TimeUtils.getTime());
        handlerLoopTrigger(jobDetail, jobExecutionContext.getScheduler(), process);
    }
    
    /**
     * 实现LOOP调度
     * @param jobDetail
     * @param scheduler
     */
    private void handlerLoopTrigger(JobDetail jobDetail, Scheduler scheduler, SProcess p){
        STrigger trigger = p.getTriggerInfo();
        String jobKey = p.getJobKey();
        if (STrigger.Type.LOOP == trigger.getType() && !p.getInterrupt()) {
            try {
                String [] schedules = trigger.formatValue();
                int count = Integer.parseInt(schedules[0]);
                int time = Integer.parseInt(schedules[1]);
                try {
                	for(int i = 0;i<time&&!p.getInterrupt();i++){
                		Thread.sleep(1000L);
                	}
                } catch (InterruptedException e1) {
                    logger.error(e1);
                }
                if(!p.getInterrupt()){
                	if (0 == count) {
                		taskJob(jobDetail, scheduler, jobKey, trigger);
                	} else {
                		int schNo = p.getCurrentSchNo();
                		if (schNo <= count) {
                			taskJob(jobDetail, scheduler, jobKey, trigger);
                		}
                	}
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }
    
    private void taskJob(JobDetail jobDetail, Scheduler scheduler, String jobKey, STrigger trigger) {
		try {
			JobKey jk = JobKey.jobKey(jobKey);
			if(scheduler.checkExists(jk)){
				scheduler.deleteJob(jk);
			}
			scheduler.scheduleJob(jobDetail, trigger.toTrigger(jobKey));
		} catch (Exception e) {
			logger.error(e);
		}
    }
}
