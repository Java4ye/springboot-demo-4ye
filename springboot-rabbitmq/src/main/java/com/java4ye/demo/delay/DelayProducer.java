package com.java4ye.demo.delay;

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
import org.springframework.web.bind.annotation.GetMapping;

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
@Component("4")
public class DelayProducer implements IMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${author}")
    private String author;

    int i = 1;

    /**
     * 上一次执行结束后，等待 500 ms 再次执行
     */
//  @Scheduled(initialDelay = 200, fixedDelay = 2_000)
    @Override
    public void sendMessage() throws JsonProcessingException {
        User user = new User();
        user.setAge(i++);
        user.setName(author);
        user.setExchange(RabbitMQConstants.DELAY_EXCHANGE);

        LocalDateTime now = LocalDateTime.now();
        String format = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss").format(now);
        user.setTime(format);

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(user);


        //延迟 10 s
        Message message =
                MessageBuilder.withBody(bytes)
                        .setHeader("x-delay", 10_000).build();


        // 交换机，路由键，信息
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.DELAY_EXCHANGE,
                RabbitMQConstants.DELAY_ROUTING_KEY,
                message
        );


    }

}
