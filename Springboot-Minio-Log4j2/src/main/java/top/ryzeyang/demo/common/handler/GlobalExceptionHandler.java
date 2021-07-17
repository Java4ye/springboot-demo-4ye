package top.ryzeyang.demo.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.ryzeyang.demo.utils.CommonResultUtil;
import top.ryzeyang.demo.common.api.CommonResult;
import top.ryzeyang.demo.common.exceptions.AllMinioException;

/**
 * @author Java4ye
 * @date 2020/12/24 上午 10:35
 * @微信公众号： Java4ye
 * @GitHub https://github.com/RyzeYang
 * @博客 https://blog.csdn.net/weixin_40251892
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
