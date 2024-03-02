package com.java4ye.demo.redis;

import io.lettuce.core.ReadFrom;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;


/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private long timeout;

    @Value("${spring.redis.lettuce.shutdown-timeout}")
    private long shutDownTimeout;

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.lettuce.pool.max-wait}")
    private long maxWait;


    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {


        GenericObjectPoolConfig<Object> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxWait(Duration.ofMillis(maxWait));
        // 作为验证连接是否有效的时间周期
        genericObjectPoolConfig.setTimeBetweenEvictionRuns(Duration.ofMillis(60_000));


        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                // 从 replication 读
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .commandTimeout(Duration.ofMillis(timeout))
                .shutdownTimeout(Duration.ofMillis(shutDownTimeout))
                .poolConfig(genericObjectPoolConfig)
                .build();

        RedisStaticMasterReplicaConfiguration redisStaticMasterReplicaConfiguration = new RedisStaticMasterReplicaConfiguration(host, port);
        redisStaticMasterReplicaConfiguration.setDatabase(database);
        redisStaticMasterReplicaConfiguration.setPassword(password);
//        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(host, port);
//        serverConfig.setDatabase(database);
//        serverConfig.setPassword(RedisPassword.of(password));


        return new LettuceConnectionFactory(redisStaticMasterReplicaConfiguration, clientConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//
////        如果注释掉 enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)，
////        那存储到redis里的数据将是没有类型的纯json，我们调用redis API获取到数据后，
////        java解析将是一个LinkHashMap类型的key-value的数据结构，我们需要使用的话就要自行解析，这样增加了编程的复杂度。
//
////         除了 final 外 的所有都将被序列化和反序列化
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
//                ObjectMapper.DefaultTyping.NON_FINAL);
//
////         可以序列化没有 get/set 方法的字段
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//
//
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        template.setDefaultSerializer(jackson2JsonRedisSerializer);

        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());

        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.json());
        return template;
    }

    @Bean
    public DefaultRedisScript<Long> seckillScript(){
        DefaultRedisScript<Long> redisScript =new DefaultRedisScript<>();
        //lock.lua脚本位置和application.yml 同级目录
        redisScript.setLocation(new ClassPathResource("seckill.lua"));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    @Bean
    public DefaultRedisScript<Long> initScript(){
        DefaultRedisScript<Long> redisScript =new DefaultRedisScript<>();
        //lock.lua脚本位置和application.yml 同级目录
        redisScript.setLocation(new ClassPathResource("init.lua"));
        redisScript.setResultType(Long.class);
        return redisScript;
    }


}
