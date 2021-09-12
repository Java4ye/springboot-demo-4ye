package com.java4ye.demo;

import com.java4ye.demo.aop.*;
import com.java4ye.demo.service.ISayService;
import com.java4ye.demo.service.impl.SayServiceImpl;
import org.aopalliance.aop.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.locks.Lock;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	ISayService sayService;

	@Test
	void testAdvice() {
		CountingBeforeAdvice countingBeforeAdvice = new CountingBeforeAdvice();

		AroundAdvice aroundAdvice = new AroundAdvice();

		CountingAfterReturningAdvice countingAfterReturningAdvice = new CountingAfterReturningAdvice();
		FinallyAdvice finallyAdvice=new FinallyAdvice();
		ISayService sayService = new SayServiceImpl();
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		proxyFactoryBean.setTarget(sayService);
		proxyFactoryBean.addAdvice(countingBeforeAdvice);
		proxyFactoryBean.addAdvice(aroundAdvice);
		proxyFactoryBean.addAdvice(countingAfterReturningAdvice);
		proxyFactoryBean.addAdvice(finallyAdvice);

		Object object = proxyFactoryBean.getObject();
		Advised advised = (Advised) object;
		System.out.println(advised);

		ISayService sayServiceProxy = (ISayService) object;
		sayServiceProxy.say();
	}

	@Test
	void testAdvisor() {

		LockMixin lockMixin = new LockMixin();
		lockMixin.lock();
		LockMixinAdvisor lockMixinAdvisor = new LockMixinAdvisor(lockMixin);
		RemoteThrowsAdvice remoteThrowsAdvice = new RemoteThrowsAdvice();

		ISayService sayService = new SayServiceImpl();
		ProxyFactory factory = new ProxyFactory(sayService);
		// 先添加 才能生效！
		factory.addAdvice(remoteThrowsAdvice);

		factory.addAdvisor(lockMixinAdvisor);

		ISayService proxy = (ISayService) factory.getProxy();
		proxy.say();

		try{
			proxy.setName("Java4ye");
		}catch (Exception e){
			System.out.println(e);
		}
	}

	@Test
	void testPointcut() {

		// pointcut
		NameMatchMethodPointcut pointcut=new NameMatchMethodPointcut();
		pointcut.addMethodName("setName");

		// advice
		CountingBeforeAdvice countingBeforeAdvice = new CountingBeforeAdvice();

		// advisor
		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(pointcut, countingBeforeAdvice);

		// target
		ISayService sayService = new SayServiceImpl();

		// proxyFactory
		ProxyFactory proxyFactory = new ProxyFactory(sayService);
		// 增强，使用切面
		proxyFactory.addAdvisor(defaultPointcutAdvisor);

		// 获取代理对象
		ISayService proxy = (ISayService) proxyFactory.getProxy();

		// 判断代理对象
		System.out.println("JDK ? "+AopUtils.isJdkDynamicProxy(proxy));
		System.out.println("CGLIB ? "+AopUtils.isCglibProxy(proxy));

		// call method
		proxy.say();
		try{
			proxy.setName("Java4ye");
		}catch (Exception e){
			System.out.println(e);
		}

	}

	@Test
	void testAdvised() {

		CountingBeforeAdvice countingBeforeAdvice = new CountingBeforeAdvice();

		ISayService sayService = new SayServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(sayService);

		Object proxy = proxyFactory.getProxy();

		ISayService sayServiceProxy = (ISayService) proxy;
		sayServiceProxy.say();

		// 增强代理对象
		Advised advised = (Advised)proxy;
		advised.addAdvice(countingBeforeAdvice);
		sayServiceProxy.say();

	}

	@Test
	void testCglibAndJDK() {

		CountingBeforeAdvice countingBeforeAdvice = new CountingBeforeAdvice();

		ISayService sayService = new SayServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(sayService);

		// true -> cglib , false -> JDK
//		proxyFactory.setProxyTargetClass(true);

		Object proxy = proxyFactory.getProxy();

		ISayService sayServiceProxy = (ISayService) proxy;

		// 增强代理对象
		Advised advised = (Advised)proxy;
		advised.addAdvice(countingBeforeAdvice);
		sayServiceProxy.say();


		System.out.println(AopUtils.isJdkDynamicProxy(sayServiceProxy));
		System.out.println(AopUtils.isCglibProxy(sayServiceProxy));

	}

	@Test
	public void testAnnotationAspect(){

		System.out.println(AopUtils.isJdkDynamicProxy(sayService));
		System.out.println(AopUtils.isCglibProxy(sayService));
		sayService.say();
	}

}
