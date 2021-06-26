package top.ryzeyang.demo.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Java4ye
 * @date 2020/12/25 下午 04:58
 * @微信公众号： Java4ye
 * @GitHub https://github.com/RyzeYang
 * @博客 https://blog.csdn.net/weixin_40251892
 */
@Data
@Builder
public class FileDTO {
    private String name;
    private String url;
}
