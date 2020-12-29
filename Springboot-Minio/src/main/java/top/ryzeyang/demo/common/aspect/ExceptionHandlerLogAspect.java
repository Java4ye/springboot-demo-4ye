package top.ryzeyang.demo.common.aspect;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Java4ye
 * @date 2020/12/25 上午 10:32
 * @微信公众号： Java4ye
 * @GitHub https://github.com/RyzeYang
 * @博客 https://blog.csdn.net/weixin_40251892
 */
@Component
@Aspect
@Slf4j
public class ExceptionHandlerLogAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    private void pointcut(){}

    @Before("pointcut()")
    public void beforeAdvice(){
        String uuid = UUID.randomUUID().toString();
        log.error("Error uuid: {}" , uuid);
    }


}
