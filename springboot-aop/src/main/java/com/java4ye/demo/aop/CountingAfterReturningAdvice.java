package com.java4ye.demo.aop;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
public class CountingAfterReturningAdvice implements AfterReturningAdvice {

    private int count;

    public int getCount() {
        return count;
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        ++count;
        System.out.println("CountingAfterReturningAdvice "+method.getName()+" "+count);
    }

}
