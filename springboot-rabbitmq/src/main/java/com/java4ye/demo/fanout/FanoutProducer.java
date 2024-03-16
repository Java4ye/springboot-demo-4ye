package com.java4ye.demo.fanout;

import com.java4ye.demo.IMessageSender;
import com.java4ye.demo.User;
import com.java4ye.demo.config.RabbitMQConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @知乎 https://www.zhihu.com/people/java4ye-17
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Component("2")
public class FanoutProducer implements IMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${author}")
    private String author;

    int i = 1;
    /**
     * 上一次执行结束后，等待 500 ms 再次执行
     */
//    @Scheduled(initialDelay = 200, fixedDelay = 60_000)
    @Override
    public void sendMessage() {
        User user = new User();
        user.setAge(i++);
        user.setName(author);
        user.setExchange(RabbitMQConstants.FANOUT_EXCHANGE);

        // 交换机，路由键，信息
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.FANOUT_EXCHANGE,
                RabbitMQConstants.DIRECT_ROUTING_KEY,
                user);
    }

}
