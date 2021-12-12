package com.java4ye.demo.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.java4ye.demo.utils.CommonResultUtil;
import com.java4ye.demo.common.api.CommonResult;
import com.java4ye.demo.common.exceptions.AllMinioException;

/**
 * @author Java4ye
 * @date 2020/12/24 上午 10:35
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({AllMinioException.class, Exception.class})
    public CommonResult<String> handlerMinioException(Exception e) {
        log.error(e.getMessage(), e.getCause());
        return CommonResultUtil.thirdPartyError("服务器异常，请联系工作人员");
    }

}
