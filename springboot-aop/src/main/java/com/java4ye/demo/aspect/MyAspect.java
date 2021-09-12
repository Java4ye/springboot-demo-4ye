package com.java4ye.demo;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Aspect
public class MyAspect {

    @Pointcut("execution(* com.java4ye.demo.service.ISayService.*(..))")
    public void a(){}

    @Before("a()")
    public void before(JoinPoint joinPoint){
        System.out.println("MyAspect before ");
    }

}
