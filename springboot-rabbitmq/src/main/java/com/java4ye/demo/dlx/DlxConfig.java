package com.java4ye.demo.dlx;

import com.java4ye.demo.config.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @知乎 https://www.zhihu.com/people/java4ye-17
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Configuration
public class DlxConfig {


    // 死信交换机
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(RabbitMQConstants.DLX_EXCHANGE);
    }

    @Bean
    public Queue dlxQueue() {
        return new Queue(RabbitMQConstants.DLX_QUEUE, true, false, false);
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(RabbitMQConstants.DLX_BINDING_KEY);
    }


}
