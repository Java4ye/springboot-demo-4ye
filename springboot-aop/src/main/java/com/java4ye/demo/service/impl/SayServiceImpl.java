package com.java4ye.demo.service.impl;

import com.java4ye.demo.service.ISayService;
import org.springframework.stereotype.Service;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Service
public class SayServiceImpl implements ISayService {

    @Override
    public void say(){
        System.out.println("Hello Java4ye");
    }

    @Override
    public void setName(String name) {
        System.out.println("name: "+ name);
    }
}
