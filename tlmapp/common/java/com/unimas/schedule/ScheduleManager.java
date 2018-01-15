package com.unimas.schedule;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import static org.quartz.JobBuilder.newJob;

/**
 * User: hxs
 */
public class ScheduleManager {

    protected static final Logger logger = Logger.getLogger(ScheduleManager.class);
    private Scheduler scheduler;
    private static ScheduleManager instance;
    
    public static ScheduleManager getInstance() {
		if (instance == null)
			instance = new ScheduleManager();
		return instance;
	}
    
    private ScheduleManager() {
    	try {
    		startQuartz();
    	} catch (Exception e) {
    		logger.error(e);
		}
    }

    private void startQuartz() throws Exception {
        SchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = stdSchedulerFactory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error(e);
            throw new Exception("启动调度器失败!");
        }

    }

    public boolean removeJob(String jobKey) throws Exception {
        if (!isJobClosed(jobKey)) {
            stopJob(jobKey);
        }
        return true;
    }

    public void startJob(String jobKey, Job job) throws Exception {
        if (scheduler == null) {
            startQuartz();
        }
        SProcess process = job.getProcess();
        JobDetail jobDetail = newJob(job.getClass())
                .withIdentity(jobKey, null)
                .requestRecovery()
                .build();
        jobDetail.getJobDataMap().put(Job.PROCESS_TYPE, process);
        JobListener listener = new MyJobListener();
        Matcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());
        try {
            scheduler.getListenerManager().addJobListener(listener, matcher);
            scheduler.scheduleJob(jobDetail, process.getTrigger());
        } catch (NullPointerException e) {
            logger.error(e);
        } catch (SchedulerException e) {
            logger.error(e);
            process.setRunning(false);
            throw new Exception("Model " + jobKey + " start failure！", e);
        }
    }

    public void stopJob(String jobKey) throws Exception {
        try {
        	scheduler.interrupt(JobKey.jobKey(jobKey));
            if (scheduler != null && scheduler.checkExists(JobKey.jobKey(jobKey))) {
            	SProcess process = (SProcess) scheduler.getJobDetail(JobKey.jobKey(jobKey)).getJobDataMap().get(Job.PROCESS_TYPE);
            	process.setRunning(false);
                scheduler.deleteJob(JobKey.jobKey(jobKey));
            }
        } catch (SchedulerException e) {
            logger.error(e);
            throw new Exception("model" + jobKey + " stop failure!");
        }
    }

    public String getRunStatus(String jobKey) {
        boolean isRunning = false;
        try {
        	SProcess process = (SProcess) scheduler.getJobDetail(JobKey.jobKey(jobKey)).getJobDataMap().get(Job.PROCESS_TYPE);
            isRunning = process.isRunning();
        } catch (Exception errors) {
            logger.error(errors);
        }
        if (!isJobClosed(jobKey) && !isRunning) {//空闲
            return "01";
        } else if (isRunning) {     //正在运行
            return "11";
        } else if (isJobClosed(jobKey) && !isRunning) {  //停止
            return "00";
        } else {                       //异常
            return "error";
        }
    }

    public boolean isJobClosed(String jobKey) {
        boolean tmp = false;
        if (scheduler != null) {
            try {
                tmp = scheduler.checkExists(JobKey.jobKey(jobKey));
            } catch (SchedulerException e) {
                logger.error(e);
            }
        }
        return !tmp;
    }

    public Scheduler getScheduler(){
    	return scheduler;
    }

}
