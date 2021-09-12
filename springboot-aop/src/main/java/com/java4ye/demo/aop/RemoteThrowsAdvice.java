package com.java4ye.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;
import java.rmi.RemoteException;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Slf4j
public class RemoteThrowsAdvice implements ThrowsAdvice {

    public void afterThrowing(Exception ex) throws Throwable {
        // Do something with remote exception
      log.error("RemoteThrowsAdvice {}",ex.getMessage());
    }

    public void afterThrowing(Method m, Object[] args, Object target, Exception ex) {
        // Do something with all arguments
        log.info("RemoteThrowsAdvice {}",m.getName());
        log.error("RemoteThrowsAdvice {}",ex.getMessage());

    }

}
