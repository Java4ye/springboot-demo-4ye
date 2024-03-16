package com.java4ye.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @知乎 https://www.zhihu.com/people/java4ye-17
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@RestController
@Slf4j
public class DemoController {

    @GetMapping("/demo/{id}")
    public String chooseDemo(@PathVariable String id) {
        Object bean;

        try {
            bean = SpringUtil.getBean(id);
            if (bean instanceof IMessageSender) {
                try {
                    ((IMessageSender) bean).sendMessage();
                } catch (Exception e) {
                    log.error("", e);
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            return "/demo/1 is direct demo ; <br>" +
                    "/demo/2 is fanout demo ; <br>" +
                    "/demo/3 is topic demo ; <br>" +
                    "/demo/4 is delay demo ; <br>" +
                    "/demo/5 is priority demo ; <br>" +
                    "/demo/6 is ttl + dlx demo ; <br>";
        }

        return bean.getClass().getName();
    }

}
