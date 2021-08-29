package com.java4ye.demo.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Slf4j
@Component
@Order(3)
public class OtherListener {

    /**
     * 还可以直接配置，监听多个事件 等
     */
    @EventListener(ReaderFeedbackEvent.class)
    public void doSomething(){
        log.info("[4ye] doSomething……");
    }
}
