package com.java4ye.demo.dlx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java4ye.demo.User;
import com.java4ye.demo.config.RabbitMQConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @知乎 https://www.zhihu.com/people/java4ye-17
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Slf4j
@Component
public class DlxConsumer {

    @RabbitListener(queues = RabbitMQConstants.DLX_QUEUE, concurrency = "1-4",
            messageConverter = "jackson2JsonMessageConverter")
    public void dlxConsumer(User user, Message message, Channel channel) {
        byte[] body = message.getBody();
        log.info("message:{}", message);
        log.info("收到的消息:{}", new String(body));

        log.info("channelNumber: {}", channel.getChannelNumber());

        log.info("user:{}", user);

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
