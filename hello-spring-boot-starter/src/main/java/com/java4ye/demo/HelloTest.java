package com.java4ye.demo;

import com.java4ye.demo.properties.HelloProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Java4ye
 * @date 2021/7/5 22:32
 * @微信公众号： Java4ye
 * @GitHub https://github.com/RyzeYang
 * @博客 https://blog.csdn.net/weixin_40251892
 */
public class HelloTest {

    @Autowired
    HelloProperties helloProperties;

    public String hello(){
        return "hello: "+ helloProperties.getName();
    }

}
