package com.java4ye.demo.controller;

import com.java4ye.demo.model.Student;
import io.lettuce.core.dynamic.annotation.Param;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/test")
    public void test(@Param("key") String key) {
        Student student = new Student();
        student.setAge(1);
        student.setName("4ye");

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", student.getName());
        map.put("age", student.getAge());
        map.put("student", student);

        redisTemplate.opsForHash().putAll("hash_" + key, map);
        redisTemplate.opsForValue().set("str_" + key, student);
        redisTemplate.opsForValue().set("str1_" + key, "student");

        System.out.println("list: left push :" + redisTemplate.opsForList().leftPush("list_" + key, "age"));
        System.out.println("set: add :" + redisTemplate.opsForSet().add("set_" + key, "age"));

        Set<ZSetOperations.TypedTuple<Object>> set = new HashSet<>();
        set.add(new DefaultTypedTuple<>("age", 1.0));
        set.add(new DefaultTypedTuple<>("level", 2.0));
        System.out.println("sorted set: add :" + redisTemplate.opsForZSet().add("zset_" + key, set));
        System.out.println("sorted set: add :" + redisTemplate.opsForZSet().add("zset_" + key, "name", 1));

        System.out.println(redisTemplate.opsForValue().setBit("bit_" + key, 1, true)
        );
        System.out.println(redisTemplate.opsForValue().setBit("bit_" + key, 10, false)
        );
        System.out.println(redisTemplate.opsForHyperLogLog().add("hyper_" + key, 123)
        );


    }

    @GetMapping("/get")
    public void get(@Param("key") String key) {
        Student student1 = (Student) redisTemplate.opsForHash().get("hash_" + key, "student");
        System.out.println(student1);

        Student student = (Student) redisTemplate.opsForValue().get("str_" + key);
        System.out.println(student);

        redisTemplate.opsForHash().increment("hash_" + key, "age", 1L);

        LinkedList<Object> linkedList = new LinkedList<>();
        linkedList.add("name");
        linkedList.add("age");
        linkedList.add("sex");

        List<Object> objects = redisTemplate.opsForHash().multiGet("hash_" + key, linkedList);
        System.out.println(objects);

        System.out.println(redisTemplate.opsForHash().get("hash_" + key, "name"));

        RedisOperations<String, ?> operations = redisTemplate.opsForHash().getOperations();
        System.out.println(operations.boundHashOps("hash_" + key).get("name"));


        System.out.println("list: range :" + redisTemplate.opsForList().range("list_" + key, 0, -1));
        System.out.println("set: size :" + redisTemplate.opsForSet().size("set_" + key));

        Set<ZSetOperations.TypedTuple<Object>> set = new HashSet<>();
        set.add(new DefaultTypedTuple<>("age", 1.0));
        set.add(new DefaultTypedTuple<>("level", 2.0));
        System.out.println("sorted set: score :" + redisTemplate.opsForZSet().score("zset_" + key, set));
        System.out.println("sorted set: score :" + redisTemplate.opsForZSet().score("zset_" + key, "name", "age"));

        System.out.println(redisTemplate.opsForValue().getBit("bit_" + key, 1)
        );
        System.out.println(redisTemplate.opsForValue().getBit("bit_" + key, 10)
        );
        System.out.println(redisTemplate.opsForHyperLogLog().size("hyper_" + key)
        );

    }

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/redisson")
    public void a(@Param("key") String key) {

        RLock lock = redissonClient.getLock(key);
        try {
            String name = Thread.currentThread().getName();
            long id = Thread.currentThread().getId();
            System.out.println("[start] Thread-name: " + name + " Thread-id: " + id + " key:" + key);
            lock.lock();
//            if (lock.tryLock(21, TimeUnit.SECONDS)) {
                try {
                    Thread.sleep(20_000);
                    System.out.println("[end] Thread-name: " + name + " Thread-id: " + id + " key:" + key);
                } finally {
                    lock.unlock();
                }
//            } else {
//                System.out.println("[try lock failed ] Thread-name: " + name + " Thread-id: " + id + " key:" + key);
//            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
