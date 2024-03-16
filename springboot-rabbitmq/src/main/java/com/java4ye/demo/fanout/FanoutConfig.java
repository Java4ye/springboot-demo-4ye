package com.java4ye.demo.fanout;

import com.java4ye.demo.config.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @知乎 https://www.zhihu.com/people/java4ye-17
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Configuration
public class FanoutConfig {


    @Bean
    public FanoutExchange fanoutExchange() {
        /**
         * 交换机名，后面两个是默认值就：持久化，不自动删除
         */
        return new FanoutExchange(RabbitMQConstants.FANOUT_EXCHANGE, true, false);
    }

    @Bean
    public Queue fanoutQueue() {
        /**
         * 队列名，后面三个是默认值：持久化，不独占，不自动删除
         * exclusive 为 true 时，连接中断时该队列会被删除。（此时不看 autoDelete）
         */
        return new Queue(RabbitMQConstants.FANOUT_QUEUE, true, false, false);
    }

    /**
     * 将队列绑定到交换机上
     *
     * @return
     */
    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

    /**
     * 绑定到 交换机 上
     * @return
     */
    @Bean
    public Binding bindingDirectExchange(DirectExchange directExchange,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(directExchange).to(fanoutExchange);
    }

}
