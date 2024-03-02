package com.java4ye.demo.rabbitmq;

import com.java4ye.demo.model.TbVoucherOrder;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 *
 * 死信队列，待处理
 *
 */
@Slf4j
@Component
public class DlxConsumer {

//    @RabbitListener(queues = RabbitMQConstants.DLX_QUEUE, concurrency = "1-4",
//            messageConverter = "jackson2JsonMessageConverter")
    public void dlxConsumer(TbVoucherOrder voucherOrder, Message message, Channel channel) {
        log.info("死信队列");

        byte[] body = message.getBody();
        log.info("message:{}", message);
        log.info("收到的消息:{}", new String(body));

        log.info("channelNumber: {}", channel.getChannelNumber());

        log.info("voucherOrder:{}", voucherOrder);

        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            // 业务处理
//            Thread.sleep(1000);
            // 然后手动应答
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("", e);
        }
    }



}
