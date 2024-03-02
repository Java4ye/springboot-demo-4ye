package com.java4ye.demo.rabbitmq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.java4ye.demo.controller.SeckillController;
import com.java4ye.demo.model.TbVoucherOrder;
import com.java4ye.demo.service.ITbSeckillVoucherService;
import com.java4ye.demo.service.ITbVoucherOrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Component
@Slf4j
public class RabbitMQReceiver {

    @Autowired
    private RedissonClient redissonClient;

    // 订单
    @Autowired
    private ITbVoucherOrderService tbVoucherOrderService;

    // 库存
    @Autowired
    private ITbSeckillVoucherService seckillVoucherService;

    @Autowired
    private TransactionTemplate transactionTemplate;

//    private static AtomicInteger k = new AtomicInteger(0);

    @RabbitListener(queues = RabbitMQConstants.SECKILL_QUEUE, concurrency = "1",
            messageConverter = "jackson2JsonMessageConverter"
    )
    public void doSeckill(TbVoucherOrder voucherOrder, Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        log.info("message:{}", message);
        log.info("收到的消息:{}", new String(body));

        log.info("channelNumber: {}", channel.getChannelNumber());

        log.info("voucherOrder:{}", voucherOrder);

        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        Long voucherId = voucherOrder.getVoucherId();

        Long userId = voucherOrder.getUserId();

        // 业务处理
        String lockKey = "lock:seckill:" + voucherId + ":" + userId;

        RLock rLock = redissonClient.getLock(lockKey);

        try {
            // 先获取锁，成功执行业务逻辑，不成功表示重复订单
            if (rLock.tryLock()) {

                QueryWrapper<TbVoucherOrder> query = Wrappers.query();
                query.select("id")
                        .eq("voucher_id", voucherId)
                        .eq("user_id", userId);

                TbVoucherOrder one = tbVoucherOrderService.getOne(query);
                if (one != null && one.getId() != null) {
                    log.warn("不允许重复下单,voucherOrder : {}", voucherOrder);
                    channel.basicNack(deliveryTag, false, false);
                    return;
                }
                try {
                    // 编程式事务
                    transactionTemplate.executeWithoutResult(status -> {

                        // 扣减库存
                        boolean success = seckillVoucherService.update().setSql("stock = stock -1")
                                .eq("voucher_id", voucherId)
                                .gt("stock", 0).update();

                        if (!success) {
                            log.error("db 库存不足！");
                            SeckillController.VOUCHER_EMPTY_MAP.put(voucherId + "", true);
                            try {
                                channel.basicNack(deliveryTag, false, false);
                            } catch (IOException e) {
                                log.error("", e);
                            }
                            return;
                        }


                        tbVoucherOrderService.save(voucherOrder);
                        // 然后手动应答
                        try {

                            channel.basicAck(deliveryTag, false);

                        } catch (IOException e) {
                            log.error("", e);
                        }

//                        if (k.getAndIncrement() == 1) {
//                            int i = 1 / 0;
//                        }

                    });
                } catch (Exception e) {
                    log.error(" 编程式事务\n\n ", e);
                    throw new Exception(e);
                }


            } else {
                log.warn("不允许重复下单,voucherOrder : {}", voucherOrder);
                channel.basicNack(deliveryTag, false, false);
            }


        } catch (Exception e) {
            // 得抛出异常，才能触发重试机制
            throw new Exception(e);
        } finally {
            rLock.unlock();
        }

    }

}
