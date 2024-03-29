package com.java4ye.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Java4ye
 * @date 2021/7/5 22:27
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@ConfigurationProperties("hello")
public class HelloProperties {

    private String name="Java4ye";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
