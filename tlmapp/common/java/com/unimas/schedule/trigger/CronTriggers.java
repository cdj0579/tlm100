package com.unimas.schedule.trigger;

import org.quartz.CronTrigger;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * User: zhangyoulong
 * Date: 12-2-16
 * Time: 下午8:06
 */
public class CronTriggers {

    /**
     * Create a CronTrigger set to TriggerKey with flagName
     * and expression.
     *
     * @return the new SimpleTrigger
     */
    public static CronTrigger startEveryDayByPointedTime(String flagName, String expression){
        return newTrigger()
                .withIdentity(flagName, null)
                .withSchedule(cronSchedule(expression)).build();
    }
}
