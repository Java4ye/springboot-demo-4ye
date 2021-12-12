package com.java4ye.demo.common.aspect;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import com.java4ye.demo.common.api.CommonResult;

/**
 * @author Java4ye
 * @date 2020/12/25 上午 10:32
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Component
@Aspect
@Slf4j
public class ExceptionHandlerLogAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    private void pointcut() {
    }

    @Pointcut("within(com.java4ye.demo.controller.MinioController)")
    public void a(){

    }
    /**
     * 唯一 ID， 方便在日志中直接定位到问题点
     *
     * @param point
     * @return
     */
    @Around("pointcut() || a()")
    public CommonResult<String> around(ProceedingJoinPoint point) {
        String uuid = UUID.randomUUID().toString();
        log.error("Error uuid: {}", uuid);
        try {
            CommonResult proceed = (CommonResult<String>) point.proceed();
            String msg = proceed.getMsg();
            proceed.setMsg(msg + " : " + uuid);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }


}
