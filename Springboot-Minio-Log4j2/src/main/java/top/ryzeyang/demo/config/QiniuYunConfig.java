package top.ryzeyang.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Java4ye
 * @date 2020/12/23 下午 03:01
 * @微信公众号： Java4ye
 * @GitHub https://github.com/RyzeYang
 * @博客 https://blog.csdn.net/weixin_40251892
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
