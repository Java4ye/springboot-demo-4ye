package com.java4ye.demo.prio;

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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @知乎 https://www.zhihu.com/people/java4ye-17
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Component("5")
public class PriorityProducer implements IMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitTemplate retryRabbitTemplate;

    @Value("${author}")
    private String author;

    int i = 1;

    /**
     * 上一次执行结束后，等待 500 ms 再次执行
     */
//    @Scheduled(initialDelay = 200, fixedDelay = 10_000)
    @Override
    public void sendMessage() throws Exception {
        User user = new User();
        user.setAge(i++);
        user.setName(author);
        user.setExchange(RabbitMQConstants.PRIORITY_EXCHANGE);

        LocalDateTime now = LocalDateTime.now();
        String format = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss").format(now);
        user.setTime(format);

        ObjectMapper objectMapper = new ObjectMapper();

        // 发送到 DIRECT_EXCHANGE 中，同时关闭 DIRECT_QUEUE 消费者，观察数据

//        if (i % 3 == 0) {
//            System.out.println("sender error [i] : "+i);
//            throw new Exception("sender error");
//        }

        // 交换机，路由键，信息, 优先级 3
        retryRabbitTemplate.convertAndSend(
                RabbitMQConstants.PRIORITY_EXCHANGE,
                RabbitMQConstants.PRIORITY_ROUTING_KEY,
                MessageBuilder.withBody(objectMapper.writeValueAsBytes(user.setPriority(3))).setPriority(3).build()
        );
        // 交换机，路由键，信息, 优先级 5
        retryRabbitTemplate.convertAndSend(
                RabbitMQConstants.PRIORITY_EXCHANGE,
                RabbitMQConstants.PRIORITY_ROUTING_KEY,
                MessageBuilder.withBody(
                        objectMapper.writeValueAsBytes(user.setPriority(5))).setPriority(5).build()
        );
        // 交换机，路由键，信息, 优先级 10
        retryRabbitTemplate.convertAndSend(
                RabbitMQConstants.PRIORITY_EXCHANGE,
                RabbitMQConstants.PRIORITY_ROUTING_KEY,
                MessageBuilder.withBody(
                        objectMapper.writeValueAsBytes(user.setPriority(10))).setPriority(10).build()
        );

    }

}
