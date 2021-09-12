package com.java4ye.demo.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.AfterAdvice;
import org.springframework.lang.Nullable;

/**
 *
 * 实现最终
 *
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
public class FinallyAdvice  implements MethodInterceptor,AfterAdvice {

    @Override
    @Nullable
    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }
        finally {
            fin();
        }
    }


    public void fin(){
        System.out.println("FinallyAdvice " );
    }

}
