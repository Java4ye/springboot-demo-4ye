package com.java4ye.demo.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 方法调用次数插件
 *
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Slf4j
public class MethodCountingTimesPlugin implements MethodBeforeAdvice {

    /**
     * 针对不同类中的方法做统计
     */
    private Map<String, Integer> methodMap = new HashMap<>();

    @Override
    public void before(Method m, Object[] args, Object target) throws Throwable {

        String className = target.getClass().getSimpleName();
        String methodName = m.getName();
        String key = className + "." + methodName;

        Integer methodCount = methodMap.getOrDefault(key, 0);
        ++methodCount;

        methodMap.put(key, methodCount);

        log.info("{}", this.getClass().getName());
        log.info("call class: {} , method {} , times : {}", className, methodName, methodCount);
    }

}
