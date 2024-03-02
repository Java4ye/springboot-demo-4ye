package com.java4ye.demo.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import static com.java4ye.demo.rabbitmq.RabbitMQConstants.*;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */

@Configuration
@Slf4j
public class RabbitMQConfig {

    // 绑定死信队列
    @Bean
    public Queue directQueue() {
        return QueueBuilder
                .durable(RabbitMQConstants.SECKILL_QUEUE)
                .deadLetterExchange(RabbitMQConstants.DLX_EXCHANGE)
                .deadLetterRoutingKey(RabbitMQConstants.DLX_ROUTING_KEY)
                .build();
    }

    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(SECKILL_EXCHANGE).build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(SECKILL_BINDING_KEY);
    }


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

    /**
     * 配置了 mandatory，还有两个监听函数
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // Mandatory 为 false 时, 消息 匹配不到会直接被丢弃

        rabbitTemplate.setMandatory(true);

        // 设置消息的confirm监听，监听消息是否到达exchange

        rabbitTemplate.setConfirmCallback(
                (correlationData, ack, cause) -> {
                    log.info("correlationData:{}," +
                            "ack:{}," +
                            "cause:{}", correlationData, ack, cause);
                    if (!ack) {
                        log.error("无法发送到交换机,原因： {}", cause);
                    }
                }
        );

        // 消息通过交换器无法匹配到队列会返回给生产者 并触发 ReturnsCallback

        rabbitTemplate.setReturnsCallback(
                returned -> {
                    // 使用 延迟插件后，每次发送时都会触发这个回调
                    String exchange = returned.getExchange();
                    if (RabbitMQConstants.DELAY_EXCHANGE.equals(exchange)) {
                        return;
                    }
                    byte[] body = returned.getMessage().getBody();
                    log.error("无法发送到队列,原因： {}", returned);
                    log.error("具体信息： {}", new String(body));
                }
        );

        // 序列化对象
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


//    @Bean
//    public RabbitTemplate retryRabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        RetryTemplate retryTemplate = new RetryTemplate();
//        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
//        backOffPolicy.setInitialInterval(500);
//        backOffPolicy.setMultiplier(2.0);
//        backOffPolicy.setMaxInterval(10000);
//        retryTemplate.setBackOffPolicy(backOffPolicy);
//        template.setRetryTemplate(retryTemplate);
//        return template;
//    }


}
