package com.java4ye.demo.config;

import com.java4ye.demo.HelloTest;
import com.java4ye.demo.properties.HelloProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Java4ye
 * @date 2021/7/5 22:24
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@EnableConfigurationProperties(HelloProperties.class)
public class HelloAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(HelloTest.class)
    public HelloTest getHelloTest(){
        return new HelloTest();
    }

}