package com.unimas.schedule;

import org.apache.log4j.Logger;
import org.quartz.*;

import com.unimas.common.date.TimeUtils;

/**
 * User: hxs
 */
public class MyJobListener implements JobListener {

    private static final Logger logger = Logger.getLogger(MyJobListener.class);
    private String jobName;

    @Override
    public String getName() {
        if (this.jobName == null) {
            return "Job is not initialize";
        }
        return this.jobName;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
    	JobDetail jobDetail = jobExecutionContext.getJobDetail();
        logger.info("任务["+jobDetail.getKey()+"]开始: " + TimeUtils.getTime());
        this.jobName = jobExecutionContext.getJobDetail().getKey().getName();
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
    	JobDetail jobDetail = jobExecutionContext.getJobDetail();
    	logger.info("任务["+jobDetail.getKey()+"]终止: " + TimeUtils.getTime());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        logger.info("任务["+jobDetail.getKey()+"]结束: " + TimeUtils.getTime());
    }
    
}
