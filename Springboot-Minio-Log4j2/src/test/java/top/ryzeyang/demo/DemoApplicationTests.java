package top.ryzeyang.demo;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

//@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());

        cn.hutool.core.lang.UUID uuid1 = cn.hutool.core.lang.UUID.randomUUID();
        System.out.println(uuid1.toString());
    }
}
