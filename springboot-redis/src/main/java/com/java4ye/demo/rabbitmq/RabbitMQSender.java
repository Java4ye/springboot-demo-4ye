package com.java4ye.demo.rabbitmq;

import com.java4ye.demo.model.TbVoucherOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Component
@Slf4j
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送秒杀消息
     */
    public void sendSeckillMessage(TbVoucherOrder voucherOrder) {
        log.info("开始异步处理 voucherOrder:{}", voucherOrder);
        rabbitTemplate.convertAndSend(RabbitMQConstants.SECKILL_EXCHANGE, RabbitMQConstants.SECKILL_ROUTING_KEY, voucherOrder);
    }

}
