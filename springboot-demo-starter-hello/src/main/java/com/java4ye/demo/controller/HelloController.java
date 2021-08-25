package com.java4ye.demo.controller;

import com.java4ye.demo.HelloTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Java4ye
 * @date 2021/7/5 22:56
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@RestController

public class HelloController {

    @Autowired
    HelloTest helloTest;

    @GetMapping("/hello")
    public String hello(){
        return helloTest.hello();
    }

}
