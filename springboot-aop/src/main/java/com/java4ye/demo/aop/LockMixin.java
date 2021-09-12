package com.java4ye.demo.aop;

import com.java4ye.demo.exceptions.LockedException;
import com.java4ye.demo.service.Lockable;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
public class LockMixin extends DelegatingIntroductionInterceptor implements Lockable {

    private boolean locked;

    @Override
    public void lock() {
        this.locked = true;
    }

    @Override
    public void unlock() {
        this.locked = false;
    }

    @Override
    public boolean locked() {
        return this.locked;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("LockMixin "+ invocation.getMethod().getName());
        if (locked() && invocation.getMethod().getName().indexOf("set") == 0) {
            throw new LockedException("locked is true and the method name is start with set");
        }
        return super.invoke(invocation);
    }

}

