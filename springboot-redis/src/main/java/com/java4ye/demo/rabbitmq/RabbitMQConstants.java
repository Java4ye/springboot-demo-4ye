package com.java4ye.demo.rabbitmq;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
public interface RabbitMQConstants {

    String DIRECT_EXCHANGE = "direct.exchange";
    String DIRECT_QUEUE = "direct.queue";
    String DIRECT_ROUTING_KEY = "direct";
    String DIRECT_BINDING_KEY = "direct";


    String FANOUT_EXCHANGE = "fanout.exchange";
    String FANOUT_QUEUE = "fanout.queue";



    String TOPIC_EXCHANGE = "topic.exchange";
    String TOPIC_QUEUE = "topic.queue";
    String TOPIC_ROUTING_KEY = "topic.test";
    String TOPIC_ROUTING_KEY2 = "topic.test.test2";
    String TOPIC_BINDING_KEY = "topic.#";


    // 死信队列，交换机
    String DLX_EXCHANGE = "dlx.exchange";
    String DLX_QUEUE = "dlx.queue";
    String DLX_BINDING_KEY = "dlx.key";
    String DLX_ROUTING_KEY = "dlx.key";

    // ttl 队列
    String TTL_EXCHANGE = "ttl.exchange";
    String TTL_QUEUE = "ttl.queue";
    String TTL_BINDING_KEY = "ttl.key";
    String TTL_ROUTING_KEY = "ttl.key";


    String DIRECT_EXCHANGE2 = "direct.exchange2";
    String DIRECT_QUEUE2 = "direct.queue2";
    String DIRECT_ROUTING_KEY2 = "direct2";
    String DIRECT_BINDING_KEY2 = "direct2";

    // 延迟队列
    String DELAY_EXCHANGE = "delay.exchange";
    String DELAY_QUEUE = "delay.queue";
    String DELAY_ROUTING_KEY = "delay.key";
    String DELAY_BINDING_KEY = "delay.key";


    // 优先级队列
    String PRIORITY_EXCHANGE = "priority.exchange";
    String PRIORITY_QUEUE = "priority.queue";
    String PRIORITY_ROUTING_KEY = "priority.key";
    String PRIORITY_BINDING_KEY = "priority.key";

    // 秒杀队列

    String SECKILL_QUEUE = "seckill.queue";
    String SECKILL_EXCHANGE = "seckill.exchange";
    String SECKILL_ROUTING_KEY = "seckill.key";
    String SECKILL_BINDING_KEY = "seckill.key";




}
