package com.java4ye.demo.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Slf4j
public class CountingBeforeAdvice implements MethodBeforeAdvice {

    private int count;

    @Override
    public void before(Method m, Object[] args, Object target) throws Throwable {
        ++count;
        log.info("{}",this.getClass().getName());
        log.info("call method {} times : {}",m.getName(),count);
    }

    public int getCount() {
        return count;
    }

}
