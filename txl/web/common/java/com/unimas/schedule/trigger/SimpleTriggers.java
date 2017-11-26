package com.unimas.schedule.trigger;

import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import java.util.Date;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * User: zhangyoulong
 * Date: 12-1-29
 * Time: 下午1:22
 */
public class SimpleTriggers {

    /**
     * Create a SimpleTrigger set to TriggerKey with flagName
     * and repeat forever with an interval of the given number of hours.
     *
     * @return the new SimpleTrigger
     */
    public static SimpleTrigger repeatHourlyForever(String flagName, int hours) throws SchedulerException {


        return newTrigger()
                .withIdentity(flagName, null)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInHours(hours)
                        .repeatForever())
                .build();
    }

    /**
     * Create a SimpleTrigger set to repeat forever with an interval
     * of the given number of minutes and TriggerKey with flagName.
     *
     * @return the new SimpleTrigger
     */
    public static SimpleTrigger repeatMinutelyForever(String flagName, int minutes){
        return newTrigger()
                .withIdentity(flagName, null)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(minutes)
                        .repeatForever())
                .build();
    }

    /**
     * Create a SimpleTrigger set to repeat forever with an interval
     * of the given number of seconds and TriggerKey with flagName.
     *
     * @return the new SimpleTrigger
     */
    public static SimpleTrigger repeatSecondlyForever(String flagName, int seconds) throws SchedulerException {
        return newTrigger()
                .withIdentity(flagName, null)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(seconds)
                        .repeatForever())
                .build();
    }

    /**
     * Create a SimpleTrigger set to repeat the given number
     * of times - 1  with an interval of the given number of hours and TriggerKey of flagName.
     * <p/>
     * <p>Note: Total count = 1 (at start time) + repeat count</p>
     *
     * @return the new SimpleTrigger
     */
    public static SimpleTrigger repeatHourlyForTotalCount(String flagName, int count, int hours) throws SchedulerException {
        if (count < 1)
            throw new IllegalArgumentException("Total count of firings must be at least one! Given count: " + count);

        return newTrigger()
                .withIdentity(flagName, null)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInHours(hours)
                        .withRepeatCount(count - 1))
                .build();
    }

    /**
     * Create a SimpleTrigger set to repeat the given number
     * of times - 1  with an interval of the given number of minutes and TriggerKey of flagName.
     * <p/>
     * <p>Note: Total count = 1 (at start time) + repeat count</p>
     *
     * @return the new SimpleTrigger
     */
    public static SimpleTrigger repeatMinutelyForTotalCount(String flagName, int count, int minutes) {
        if (count < 1)
            throw new IllegalArgumentException("Total count of firings must be at least one! Given count: " + count);

        return  newTrigger()
                .withIdentity(flagName, null)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(minutes)
                        .withRepeatCount(count - 1))
                .build();
    }

    /**
     * Create a SimpleTrigger set to repeat the given number
     * of times - 1  with an interval of the given number of seconds and TriggerKey of flagName.
     * <p/>
     * <p>Note: Total count = 1 (at start time) + repeat count</p>
     *
     * @return the new SimpleTrigger
     */
    public static SimpleTrigger repeatSecondlyForTotalCount(String flagName, int count, int seconds) throws SchedulerException {
        if (count < 0)
            throw new IllegalArgumentException("Total count of firings must be at least one! Given count: " + count);

        return newTrigger()
                .withIdentity(flagName, null)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(seconds)
                        .withRepeatCount(count - 1))
                .build();
    }

    /**
     * Create a SimpleTrigger set to  repeat forever with an interval of the span time and TriggerKey of flagName.
     * <p/>
     * <p>Note: span time = startTime-endTime</p>
     *
     * @return the new SimpleTrigger
     */
    public static SimpleTrigger repeatSpanForever(String flagName, long seconds, Date startTime, Date endTime) {
        return newTrigger()
                .withIdentity(flagName, null)
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInMilliseconds(seconds)
                        .repeatForever())
                .endAt(endTime)
                .build();
    }

    /**
     * Create a SimpleTrigger set to  repeat the given number
     * of times - 1 with an interval of the span time and TriggerKey of flagName.
     * <p/>
     * <p>Note: Total count = 1 (at start time) + repeat count</p>
     * <p>Note: Total count = 1 (at start time) + repeat count</p>
     *
     * @return the new SimpleTrigger
     */
    public static SimpleTrigger repeatSpanForTotalCount(String flagName, long seconds, int count, Date startTime, Date endTime) {
        if (count < 1)
            throw new IllegalArgumentException("Total count of firings must be at least one! Given count: " + count);
        return newTrigger()
                .withIdentity(flagName, null)
                .startAt(startTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInMilliseconds(seconds)
                        .withRepeatCount(count - 1)
                )
                .endAt(endTime)
                .build();
    }
}
