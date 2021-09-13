package com.java4ye.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Data
public class Plugin {

    @JsonProperty("name")
    private String name;

    @JsonProperty("jarPath")
    private String jarPath;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("class")
    private String clazz;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("active")
    private boolean active;

}
