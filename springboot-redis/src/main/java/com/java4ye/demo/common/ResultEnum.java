package com.java4ye.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Java4ye
 * @date 2020/11/12 上午 11:09
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    // 成功
    SUCCESS("00000", "SUCCESS"),

    // 用户端错误
    CLIENT_ERROR("A0001", "CLIENT_ERROR"),
    // 权限不足
    ACCESS_ERROR("A0010", "ACCESS_ERROR"),
    // 认证失败， 账号或密码错误  AuthenticationEntryPoint
    AUTHENTICATION_ERROR("A0100", "AUTHENTICATION_ERROR"),

    // 系统错误
    SERVER_ERROR("B0001", "SERVER_ERROR"),
    // 系统正常提示
    SERVER_NORMAL_TIPS("B0002", "SERVER_NORMAL_TIPS"),
    // 调用第三方服务出错
    THIRD_PARTY_ERROR("C0001", "THIRD_PARTY_ERROR");

    private String code;
    private String message;
}