package com.java4ye.demo.controller;

import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.java4ye.demo.common.CommonResult;
import com.java4ye.demo.model.TbVoucherOrder;
import com.java4ye.demo.rabbitmq.RabbitMQSender;
import com.java4ye.demo.utils.CommonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 * <p>
 * <p>
 * 优惠券秒杀 demo
 */
@RestController
@RequestMapping("seckill")
@Slf4j
public class SeckillController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisScript<Long> seckillScript;

    @Autowired
    RabbitMQSender rabbitMQSender;

    /**
     * 秒杀活动 ID
     */
    private static final String SECKILL_ID = "seckill0001";
    /**
     * 内存标记
     * key voucher id
     * value true 空
     */
    public static final Map<String, Boolean> VOUCHER_EMPTY_MAP = new HashMap<>();


    @PostConstruct
    public void init() {
        VOUCHER_EMPTY_MAP.put("1", false);
        // 初始化 redis 库存  seckill0001:stock:voucher:1
        stringRedisTemplate.opsForValue().set(SECKILL_ID + ":stock:voucher:1", String.valueOf(100));
        log.info("init");
    }

    @GetMapping("/reset")
    public CommonResult<String> reset() {
        init();
        return CommonResultUtil.success(VOUCHER_EMPTY_MAP.size() + "");
    }

    /**
     * 所有的请求头中都要带上 authorization token
     * 拿到 token ， 再去获取 用户信息
     * <p>
     * 这里直接跳过
     */
    @GetMapping("/path")
    public CommonResult<String> getPath(
            @RequestParam(value = "vid", required = false, defaultValue = "1") String voucherId,
            @RequestParam(value = "uid", defaultValue = "1") String userId) {

        // seckill0001:path:voucherId:userId  value 为 md5 混淆的
        String key = SECKILL_ID + ":path:" + voucherId + ":" + userId;
        String path = SecureUtil.md5(key);

        stringRedisTemplate.opsForValue().set(key, path, 3600, TimeUnit.SECONDS);
        log.info("userId:{},path:{}", userId, path);
        return CommonResultUtil.success(path);

    }

    /**
     * 开始秒杀
     *
     * @param path
     * @param voucherId
     * @param userId
     * @return
     */
    @GetMapping("/{path}/doseckill")
    public CommonResult<Object> doSeckill(@PathVariable String path,
                                          @RequestParam(value = "vid", required = false, defaultValue = "1") String voucherId,
                                          @RequestParam(value = "uid", defaultValue = "1") String userId) {
        log.info("开始秒杀 userId: {}", userId);
        Object realPath = stringRedisTemplate.opsForValue().get(SECKILL_ID + ":path:" + voucherId + ":" + userId);
        // 校验地址，这里同时也确定了 优惠券id 的真假，用户 的真假
        if (ObjectUtil.isNull(realPath) || ObjectUtil.notEqual(path, realPath)) {
            log.info("秒杀地址不存在, userId:{},path:{}", userId, path);

            return CommonResultUtil.clientError("秒杀地址不存在");
        }

        // 是否下单过
        // seckill0001:order:voucherId:userId  表示下单了的用户，value 为 订单id
        String orderKey = SECKILL_ID + ":order:" + voucherId + ":" + userId;
        Object orderId = stringRedisTemplate.opsForValue().get(orderKey);
        if (ObjectUtil.isNotNull(orderId)) {
            log.info("不允许重复下单,userId:{}, orderId:{}", userId, orderId);
            return CommonResultUtil.clientError("不允许重复下单,订单ID: " + orderId);
        }

        // 内存标记
        if (VOUCHER_EMPTY_MAP.get(voucherId)) {
            log.info("内存标记 库存不足 voucherId:{} , userId:{}", voucherId, userId);
            return CommonResultUtil.serverNormalTips("库存不足");
        }

        //参数1为终端ID
        //参数2为数据中心ID
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        orderId = snowflake.nextId();

        // redis lua 脚本 预扣库存  seckill0001:stock:voucher:1

        Long result = stringRedisTemplate.execute(
                seckillScript,
                Collections.emptyList(),
                voucherId, userId, orderId + ""
        );
        int nums = result.intValue();
        if (nums == -2) {
            orderId = stringRedisTemplate.opsForValue().get(orderKey);
            log.warn("lua脚本 不允许重复下单,userId:{}, orderId:{}", userId, orderId);
            return CommonResultUtil.clientError("不允许重复下单,订单ID: " + orderId);
        } else if (nums == -1) {
            // key 不存在，告警,这里先直接报错
            log.warn("lua脚本 key 不存在 voucherId:{}", voucherId);

            return CommonResultUtil.serverError("key 不存在" + voucherId);
        } else if (nums == 0) {
            VOUCHER_EMPTY_MAP.put(voucherId, true);
            log.warn("lua脚本 库存不足 voucherId:{} , userId:{}", voucherId, userId);
            return CommonResultUtil.serverNormalTips("库存不足");
        }

        // 预扣成功，生成 订单编号，流水号
        // 订单id，ObjectId是MongoDB数据库的一种唯一ID生成策略，是UUID version1的变种
        // 生成类似：5b9e306a4df4f8c54a39fb0c
//        orderId = ObjectId.next();


        TbVoucherOrder tbVoucherOrder = new TbVoucherOrder();
        tbVoucherOrder.setId((Long) orderId);
        tbVoucherOrder.setUserId(Long.parseLong(userId));
        tbVoucherOrder.setVoucherId(Long.parseLong(voucherId));

        // MQ 异步处理
        rabbitMQSender.sendSeckillMessage(
                tbVoucherOrder
        );

        // 返回订单 ID
        return CommonResultUtil.success(orderId);
    }

}
