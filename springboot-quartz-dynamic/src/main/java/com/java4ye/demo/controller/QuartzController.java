package com.java4ye.demo.controller;


import lombok.SneakyThrows;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Enumeration;

/**
 * @author Java4ye
 * @date 2021/2/8 21:19
 * @微信公众号： Java4ye
 * @GitHub https://github.com/RyzeYang
 * @博客 https://blog.csdn.net/weixin_40251892
 */
@RestController
public class QuartzController {

    private final String jobClassName = "com.java4ye.demo.job.HelloWorldJob";
    private final String jobGroupName = "test";
    private final String cronExpression = "0/5 * 21 * * ?";

    private final Scheduler scheduler;

    @Autowired
    public QuartzController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    @SneakyThrows
    @GetMapping("/delete")
    public void deleteJob() {
        JobKey jobKey = getJobKey(jobClassName, jobGroupName);
        scheduler.deleteJob(jobKey);
    }

    @SneakyThrows
    @GetMapping("/add")
    public void addJob() {
        addScheduler();

//        scheduler.scheduleJob();

        scheduler.deleteJob(getJobKey(jobClassName, jobGroupName));

        scheduler.pauseJob(getJobKey(jobClassName, jobGroupName));

        scheduler.resumeJob(getJobKey(jobClassName, jobGroupName));

        scheduler.unscheduleJob(getTriggerKey(jobClassName, jobGroupName));

        scheduler.triggerJob(getJobKey(jobClassName, jobGroupName));

        TriggerKey triggerKey = getTriggerKey(jobClassName, jobGroupName);
        JobKey jobKey = getJobKey(jobClassName, jobGroupName);
        JobDetail jobDetail = getJobDetail(jobClassName, jobKey);
        scheduler.rescheduleJob(
                triggerKey,
                getCronTrigger(jobDetail, triggerKey, getCronScheduleBuilder(cronExpression))
        );
    }

    private JobKey getJobKey(String jobClassName, String jobGroupName) {
        return JobKey.jobKey(jobClassName, jobGroupName);
    }

    private TriggerKey getTriggerKey(String jobClassName, String jobGroupName) {
        return TriggerKey.triggerKey(jobClassName, jobGroupName);
    }


    /**
     * 获取 JobDetail
     *
     * @param jobClassName Job Class
     * @param jobKey       JobKey
     * @return
     * @throws ClassNotFoundException
     */
    private JobDetail getJobDetail(String jobClassName, JobKey jobKey) throws ClassNotFoundException {
        Class<?> jobClass = Class.forName(jobClassName);
        return JobBuilder.newJob((Class<? extends Job>) jobClass)
                .withIdentity(jobKey)
                .build();
    }

    /**
     * 获取 CronScheduleBuilder
     *
     * @param cronExpression cron表达式
     * @return
     */
    private CronScheduleBuilder getCronScheduleBuilder(String cronExpression) {
        if (CronExpression.isValidExpression(cronExpression)) {
            return CronScheduleBuilder.cronSchedule(cronExpression);
        } else {
            throw new RuntimeException("不是cron表达式");
        }
    }

    /**
     * 创建 触发器 Trigger
     *
     * @param jobDetail           job
     * @param triggerKey          triggerKey
     * @param cronScheduleBuilder cron
     * @return
     * @throws ClassNotFoundException
     */
    private CronTrigger getCronTrigger(JobDetail jobDetail, TriggerKey triggerKey, CronScheduleBuilder cronScheduleBuilder) throws ClassNotFoundException {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(triggerKey)
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    /**
     * 添加新的JOb到scheduler中
     *
     * @throws ClassNotFoundException
     * @throws SchedulerException
     */
    private void addScheduler() throws ClassNotFoundException, SchedulerException {
        scheduler.start();

        JobKey jobKey = getJobKey(jobClassName, jobGroupName);
        JobDetail jobDetail1 = scheduler.getJobDetail(jobKey);
        if (jobDetail1 != null) {
            throw new SchedulerException("重复");
        }
        JobDetail jobDetail = getJobDetail(jobClassName, jobKey);

        CronScheduleBuilder cronScheduleBuilder = getCronScheduleBuilder(cronExpression);
        TriggerKey triggerKey = getTriggerKey(jobClassName, jobGroupName);
        CronTrigger cronTrigger = getCronTrigger(jobDetail, triggerKey, cronScheduleBuilder);
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

}
