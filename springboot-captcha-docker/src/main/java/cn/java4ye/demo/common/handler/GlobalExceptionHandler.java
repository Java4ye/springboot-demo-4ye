package cn.java4ye.demo.common.handler;

import cn.java4ye.demo.common.api.CommonResult;
import cn.java4ye.demo.common.api.ResultEnum;
import cn.java4ye.demo.common.exception.DownloadException;
import cn.java4ye.demo.utils.CommonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({DownloadException.class})
    public CommonResult<String> handlerDownloadException(DownloadException e) {
        log.error(e.getMessage(), e.getCause());
        return CommonResultUtil.serverError(ResultEnum.DOWNLOAD_ERROR,"下载出错，请联系 Java4ye");
    }

}