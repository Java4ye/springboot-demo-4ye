package com.java4ye.demo.service.impl;

import com.java4ye.demo.service.IHelloService;
import org.springframework.stereotype.Service;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Service
public class HelloServiceImpl implements IHelloService {

    @Override
    public String hello(String name) {
        return "[Java4ye] say hello "+name;
    }

    @Override
    public String hello2(String name) {
        return name;
    }

}
