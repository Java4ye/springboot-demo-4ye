package cn.java4ye.demo.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Java4ye
 * @date 2020/10/24 14:50
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    /**
     * 描述
     */
    private String msg;
    /**
     * 状态码
     */
    private String code;
    /**
     * 数据
     */
    private T data;

    public CommonResult(ResultEnum resultEnum, T data) {
        this.msg = resultEnum.getMessage();
        this.code = resultEnum.getCode();
        this.data = data;
    }

}
