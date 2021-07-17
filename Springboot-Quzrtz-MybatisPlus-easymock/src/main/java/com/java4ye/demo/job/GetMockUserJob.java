package com.java4ye.demo.job;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java4ye.demo.entity.User;
import com.java4ye.demo.service.IUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Java4ye
 * @date 2021/1/11 23:16
 * @微信公众号： Java4ye
 * @GitHub https://github.com/RyzeYang
 * @博客 https://blog.csdn.net/weixin_40251892
 */
@Slf4j
public class GetMockUserJob extends QuartzJobBean {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    IUserService userService;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        log.info("GetMockUserJob start: ");
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://192.168.80.128:7300/mock/5f8c1175994a570020e4dfe4/user/arry#!method=get", String.class);
        HttpStatus statusCode = forEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            String body = forEntity.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);
            JsonNode data = jsonNode.get("data");
            System.out.println(data);
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.registerModule(new JavaTimeModule());
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, User.class);
            List<User> users = objectMapper.readValue(data.toString(), javaType);
            StopWatch stopWatch = new StopWatch("save mock users : " + users.size());
            stopWatch.start("save mock users");
// 插入一个用户试试
//            User user = users.get(0);
//            userService.save(user);
//            批量插入用户
            userService.saveBatch(users, 5000);
            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
        }
    }
}
