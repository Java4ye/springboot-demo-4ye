package com.java4ye.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Java4ye
 * @date 2021/2/8 21:11
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Slf4j
public class HelloWorldJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        log.info("HelloWorld");
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        log.info("[JobKey] name:{},group:{}", key.getName(), key.getGroup());

    }
}
