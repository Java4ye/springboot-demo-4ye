package com.java4ye.demo.advice;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 *
 * 计算方法花费时间插件
 *
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Slf4j
public class MethodSpendTimePlugin implements MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String name = invocation.getMethod().getName();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object rval = invocation.proceed();
        stopWatch.stop();
        String s = stopWatch.prettyPrint();

        log.info("{}",this.getClass().getName());
        log.info("call method {} spend time : {}",name,s);

        return rval;
    }

}
