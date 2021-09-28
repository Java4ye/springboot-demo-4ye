package com.java4ye.demo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Data
@Builder
public class User {

    private Integer id;

    private String name;

    private Integer age;

    private String desc;

}
