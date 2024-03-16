package com.java4ye.demo.prio;

import com.java4ye.demo.config.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
public class PriorityConfig {

    /**
     * 定义交换机
     **/
    @Bean
    public DirectExchange directPriorityExchange() {
        return new DirectExchange(RabbitMQConstants.PRIORITY_EXCHANGE, true, false);
    }

    /**
     * 定义队列
     **/
    @Bean
    public Queue directPriorityQueue() {
        Map<String, Object> args = new HashMap<>();
       // 最大优先级
        args.put("x-max-priority", 10);
        return new Queue(RabbitMQConstants.PRIORITY_QUEUE, true, false, false, args);
    }

    /**
     * 队列和交换机绑定
     **/
    @Bean
    Binding bindingPriorityQueue() {
        return BindingBuilder.bind(directPriorityQueue()).to(directPriorityExchange()).with(RabbitMQConstants.PRIORITY_BINDING_KEY);
    }

}
