package com.java4ye.demo.delay;

import com.java4ye.demo.config.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
public class DelayConfig {

    // 延迟交换机
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        // 交换机类型
        args.put("x-delayed-type", "direct");
        return new CustomExchange(RabbitMQConstants.DELAY_EXCHANGE, "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue delayedQueue() {
        return new Queue(RabbitMQConstants.DELAY_QUEUE);
    }

    @Bean
    public Binding delayedBinding() {
        return BindingBuilder.bind(delayedQueue()).to(delayedExchange()).with(RabbitMQConstants.DELAY_BINDING_KEY).noargs();
    }



}
