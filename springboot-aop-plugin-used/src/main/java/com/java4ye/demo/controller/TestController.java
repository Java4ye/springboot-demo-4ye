package com.java4ye.demo.controller;

import com.java4ye.demo.service.IHelloService;
import com.java4ye.demo.service.IHiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final IHiService sayService;
    private final IHelloService helloService;

    @GetMapping("/{name}/say")
    public String say(@PathVariable String name){
        String hello = sayService.hello(name);
        log.info(hello);
        return hello;
    }

    @GetMapping("/say2")
    public String say2(){
        String hello = sayService.hello2("喜欢的小伙伴们可以点点 star 鼓励下支持下 【Java4ye】 吗！感谢！！");
        log.info(hello);
        return hello;
    }

    @GetMapping("/{name}/hello")
    public String hello(@PathVariable String name){
        String hello = helloService.hello(name);
        log.info(hello);
        return hello;
    }

    @GetMapping("/hello2")
    public String hello2(){
        String hello = helloService.hello2("喜欢的小伙伴们可以点点 star 鼓励下支持下 【Java4ye】 吗！感谢！！");
        log.info(hello);
        return hello;
    }

}
