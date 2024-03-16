package com.java4ye.demo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @知乎 https://www.zhihu.com/people/java4ye-17
 * @掘金 https://juejin.cn/user/2304992131153981
 *
 * 消息主体
 */
@Data
@Accessors(chain = true)
public class User {

    private String name;
    private Integer age;
    private String exchange;
    private String time;
    private Integer priority;

}
