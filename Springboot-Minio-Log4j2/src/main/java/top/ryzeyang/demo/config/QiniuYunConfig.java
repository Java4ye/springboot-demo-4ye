package top.ryzeyang.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Java4ye
 * @date 2020/12/23 下午 03:01
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Configuration
@ConfigurationProperties(prefix = "qiniu")
@Data
public class QiniuYunConfig {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String prefix;
}
