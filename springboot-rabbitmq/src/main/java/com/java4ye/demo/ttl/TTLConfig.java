package com.java4ye.demo.ttl;

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
 *
 * 延迟队列实现方案
 * 1. 利用 死信队列+过期时间 去处理，消息过期被转发到死信交换机，死信交换机路由到死信队列进行处理
 * 2. 使用插件  rabbitmq_delayed_message_exchange
 */
@Configuration
public class TTLConfig {


    /**
     * 定义交换机
     **/
    @Bean
    public DirectExchange ttlExchange() {
        /**
         * 交换机名称
         * 持久性标志：是否持久化,默认是 true 即声明一个持久的 exchange,该exchange将在服务器重启后继续运行
         * 自动删除标志：是否自动删除，默认为 false, 如果服务器想在 exchange不再使用时删除它，则设置为 true
         **/
        return new DirectExchange(RabbitMQConstants.TTL_EXCHANGE, true, false);
    }


    /**
     * 创建队列时设置
     * @return
     */
    @Bean
    public Queue ttlQueue() {
        Map<String, Object> args = new HashMap<>();
        //设置消息过期时间
        args.put("x-message-ttl", 5000);
        //设置死信交换机
        args.put("x-dead-letter-exchange", RabbitMQConstants.DLX_EXCHANGE);
        //设置死信 routing_key
        args.put("x-dead-letter-routing-key", RabbitMQConstants.DLX_ROUTING_KEY);
        return new Queue(RabbitMQConstants.TTL_QUEUE, true, false, false, args);
    }

    /**
     * 队列和交换机绑定
     * 设置路由键：directRouting
     **/
    @Bean
    Binding bindingTtl() {
        return BindingBuilder.bind(ttlQueue()).to(ttlExchange()).with(RabbitMQConstants.TTL_BINDING_KEY);
    }




}
