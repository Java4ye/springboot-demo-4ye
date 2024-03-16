package com.java4ye.demo.dlx;

import com.java4ye.demo.config.RabbitMQConstants;
import org.springframework.amqp.core.*;
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
public class DirectConfig2 {

    // 死信队列测试，不设置 消费队列

    @Bean
    public DirectExchange directExchange2() {
        /**
         * 交换机名，后面两个是默认值就：持久化，不自动删除
         */
        return new DirectExchange(RabbitMQConstants.DIRECT_EXCHANGE2, true, false);
    }

    @Bean
    public Queue directQueue2() {
        return QueueBuilder
                .durable(RabbitMQConstants.DIRECT_QUEUE2)
                .deadLetterExchange(RabbitMQConstants.DLX_EXCHANGE)
                .deadLetterRoutingKey(RabbitMQConstants.DLX_ROUTING_KEY)
                .build();
    }

    /**
     * 将队列绑定到交换机上
     *
     * @return
     */
    @Bean
    public Binding directBinding2() {
        return BindingBuilder.
                bind(directQueue2()).
                to(directExchange2()).
                with(RabbitMQConstants.DIRECT_BINDING_KEY2);
    }

}
