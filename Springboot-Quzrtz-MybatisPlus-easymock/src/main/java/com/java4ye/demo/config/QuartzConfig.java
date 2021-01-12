package com.java4ye.demo.config;

import com.java4ye.demo.job.GetMockUserJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Java4ye
 * @date 2021/1/9 10:48
 * @微信公众号： Java4ye
 * @GitHub https://github.com/RyzeYang
 * @博客 https://blog.csdn.net/weixin_40251892
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail myJobDetail(){
        return JobBuilder.newJob(GetMockUserJob.class)
                .withIdentity("GetMockUserJob","GetMockUserJobGroup1")
                .usingJobData("job_data_param","job_data_param1")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger myTrigger(){
        return TriggerBuilder.newTrigger()
                .forJob(myJobDetail())
                .withIdentity("GET MOCK USER TRIGGER","TRIGGER GROUP1")
                .usingJobData("job_trigger_param","job_trigger_param1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                .build();
    }

}
