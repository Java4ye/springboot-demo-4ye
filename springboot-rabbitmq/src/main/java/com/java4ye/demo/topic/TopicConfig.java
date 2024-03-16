package com.java4ye.demo.topic;

import com.java4ye.demo.config.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @知乎 https://www.zhihu.com/people/java4ye-17
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Configuration
public class TopicConfig {

    @Bean
    public TopicExchange topicExchange() {
        /**
         * 交换机名，后面两个是默认值就：持久化，不自动删除
         */
        return new TopicExchange(RabbitMQConstants.TOPIC_EXCHANGE, true, false);
    }

    @Bean
    public Queue topicQueue() {
        /**
         * 队列名，后面三个是默认值：持久化，不独占，不自动删除
         * exclusive 为 true 时，连接中断时该队列会被删除。（此时不看 autoDelete）
         */
        return new Queue(RabbitMQConstants.TOPIC_QUEUE, true, false, false);
    }

    /**
     * 将队列绑定到交换机上
     *
     * @return
     */
    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(topicQueue()).to(topicExchange())
                .with(RabbitMQConstants.TOPIC_BINDING_KEY);
    }
}
