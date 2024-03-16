package com.java4ye.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;

import java.io.IOException;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @知乎 https://www.zhihu.com/people/java4ye-17
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Configuration
@Slf4j
public class RabbitMQConfig {


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
                    log.info("【rabbitTemplate exchange confirm】 correlationData:{}," +
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


    @Bean
    public RabbitTemplate retryRabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // Mandatory 为 false 时, 消息 匹配不到会直接被丢弃

        rabbitTemplate.setMandatory(true);

        // 设置消息的confirm监听，监听消息是否到达exchange

        rabbitTemplate.setConfirmCallback(
                (correlationData, ack, cause) -> {
                    log.info("【retryRabbitTemplate exchange confirm】 correlationData:{}," +
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


        RetryTemplate retryTemplate = RetryTemplate.builder()
                .maxAttempts(3)
                .exponentialBackoff(100, 2, 10000)
                .retryOn(Exception.class)
                .traversingCauses()
                .withListener(
                        new RetryListenerSupport(){
                            @Override
                            public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                                log.info("onClose");
                                super.close(context, callback, throwable);
                            }

                            @Override
                            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                                log.info("onError");
                                super.onError(context, callback, throwable);
                            }

                            @Override
                            public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
                                log.info("onOpen");
                                return super.open(context, callback);
                            }
                        }
                )
                .build();

        rabbitTemplate.setRetryTemplate(retryTemplate);

        return rabbitTemplate;
    }


    // 不会保存到磁盘
//    @Bean
//    public Queue autoDeleteQueue() {
//        return new AnonymousQueue();
//    }


}
