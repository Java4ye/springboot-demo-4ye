package com.java4ye.demo.aop;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.AdviceName;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
public class AroundAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("AroundAdvice Before: invocation=[" + invocation + "]");
        Object rval = invocation.proceed();
        System.out.println("AroundAdvice After");
        return rval;
    }

}
