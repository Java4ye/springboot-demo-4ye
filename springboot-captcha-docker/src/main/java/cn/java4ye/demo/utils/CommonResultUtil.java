package cn.java4ye.demo.utils;


import cn.java4ye.demo.common.api.CommonResult;
import cn.java4ye.demo.common.api.ResultEnum;

/**
 * @author Java4ye
 * @date 2020/11/12 8:25
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
public class CommonResultUtil {

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultEnum.SUCCESS, data);
    }

    public static <T> CommonResult<T> clientError(T data) {
        return new CommonResult<>(ResultEnum.CLIENT_ERROR, data);
    }

    public static <T> CommonResult<T> serverError(T data) {
        return new CommonResult<>(ResultEnum.SERVER_ERROR, data);
    }

    public static <T> CommonResult<T> serverError(ResultEnum errorCode,T data) {
        return new CommonResult<>(errorCode, data);
    }

    public static <T> CommonResult<T> thirdPartyError(T data) {
        return new CommonResult<>(ResultEnum.THIRD_PARTY_ERROR, data);
    }
}
