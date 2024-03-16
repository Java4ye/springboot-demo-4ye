package com.java4ye.demo.direct;

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
public class DirectConfig {

    @Bean
    public DirectExchange directExchange() {
        /**
         * 交换机名，后面两个是默认值就：持久化，不自动删除
         */
        return new DirectExchange(RabbitMQConstants.DIRECT_EXCHANGE, true, false);
    }

    @Bean
    public Queue directQueue() {
        /**
         * 队列名，后面三个是默认值：持久化，不独占，不自动删除
         * exclusive 为 true 时，连接中断时该队列会被删除。（此时不看 autoDelete）
         */
        return new Queue(RabbitMQConstants.DIRECT_QUEUE, true, false, false);
    }

    /**
     * 将队列绑定到交换机上
     *
     * @return
     */
    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(RabbitMQConstants.DIRECT_BINDING_KEY);
    }

}
