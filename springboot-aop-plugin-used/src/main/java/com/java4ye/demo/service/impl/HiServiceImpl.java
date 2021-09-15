package com.java4ye.demo.service.impl;

import com.java4ye.demo.service.IHiService;
import org.springframework.stereotype.Service;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Service
public class HiServiceImpl implements IHiService {
    @Override
    public String hello(String name) {
        return "[Java4ye] say hi "+name;
    }

    @Override
    public String hello2(String name) {
        return name;
    }
}
