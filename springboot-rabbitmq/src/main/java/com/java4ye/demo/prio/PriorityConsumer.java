package com.java4ye.demo.prio;

import com.java4ye.demo.User;
import com.java4ye.demo.config.RabbitMQConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @知乎 https://www.zhihu.com/people/java4ye-17
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Slf4j
@Component
public class PriorityConsumer {

// 不消费，消息过期，会被 死信队列 处理，达到延迟效果

    @RabbitListener(queues = RabbitMQConstants.PRIORITY_QUEUE, concurrency = "1-4",
            messageConverter="jackson2JsonMessageConverter"
    )
    public void topicConsumer(User user,Message message,  Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        byte[] body = message.getBody();
//        log.info("message:{}", message);
//        log.info("收到的消息:{}", new String(body));
//
//        log.info("channelNumber: {}",channel.getChannelNumber());
//
//        log.info("user:{}", user);
        log.info("priority:{}", user.getPriority());


//        if (user.getPriority()==3){
//            throw new Exception("client error");
//        }

        try {
            // 业务处理
//            Thread.sleep(3000);
            // 然后手动应答
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("", e);
        }
    }


}
