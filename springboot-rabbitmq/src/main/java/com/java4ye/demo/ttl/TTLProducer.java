package com.java4ye.demo.ttl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java4ye.demo.IMessageSender;
import com.java4ye.demo.User;
import com.java4ye.demo.config.RabbitMQConstants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @知乎 https://www.zhihu.com/people/java4ye-17
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Component("6")
public class TTLProducer implements IMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${author}")
    private String author;

    int i = 1;

    /**
     * 上一次执行结束后，等待 500 ms 再次执行
     */
//    @Scheduled(initialDelay = 200, fixedDelay = 10_000)
    @Override
    public void sendMessage() {
        User user = new User();
        user.setAge(i++);
        user.setName(author);
        user.setExchange(RabbitMQConstants.TTL_EXCHANGE);

        LocalDateTime now = LocalDateTime.now();
        String format = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss").format(now);
        user.setTime(format);

        // 交换机，路由键，信息
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.TTL_EXCHANGE,
                RabbitMQConstants.TTL_ROUTING_KEY,
                user);
    }

    /**
     * TTL（Time-To-Live），消息存活的时间，即消息的有效期。如果我们希望消息能够有一个存活时间，那么我们可以通过设置 TTL 来实现这一需求。
     * 如果消息的存活时间超过了 TTL 并且还没有被消息，此时消息就会变成死信，关于死信以及死信队列，
     */
//    @Scheduled(initialDelay = 200, fixedDelay = 1_000)
    public void sendMessage2() throws JsonProcessingException {
        User user = new User();
        user.setAge(i++);
        user.setName(author);
        user.setExchange(RabbitMQConstants.DIRECT_EXCHANGE2);

        LocalDateTime now = LocalDateTime.now();
        String format = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss").format(now);
        user.setTime(format);

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(user);

        // 发送到 DIRECT_EXCHANGE 中，同时关闭 DIRECT_QUEUE 消费者，观察数据

        // 10 s 后过期
        Message message =
                MessageBuilder.withBody(bytes)
                        .setExpiration("10000").build();


        // 交换机，路由键，信息
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.DIRECT_EXCHANGE2,
                RabbitMQConstants.DIRECT_ROUTING_KEY2,
                message
        );

    }

}
