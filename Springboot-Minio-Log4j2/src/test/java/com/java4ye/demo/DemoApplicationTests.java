package com.java4ye.demo;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.naming.Context;
import javax.naming.directory.InitialDirContext;
import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;

@SpringBootTest
@Slf4j
class DemoApplicationTests {

    @Test
    void contextLoads() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());

        cn.hutool.core.lang.UUID uuid1 = cn.hutool.core.lang.UUID.randomUUID();
        System.out.println(uuid1.toString());
    }

    @Test
    void log4j2() throws Exception {
        Runtime.getRuntime().exec("calc").waitFor();

        String poc="${java:os}";
       log.error("username: {}",poc);
    }
}
