package com.java4ye.demo.aop;

import com.java4ye.demo.service.Lockable;
import org.springframework.aop.DynamicIntroductionAdvice;
import org.springframework.aop.support.DefaultIntroductionAdvisor;

public class LockMixinAdvisor extends DefaultIntroductionAdvisor {

    public LockMixinAdvisor(DynamicIntroductionAdvice advice) {
        super(advice, Lockable.class);
    }
}