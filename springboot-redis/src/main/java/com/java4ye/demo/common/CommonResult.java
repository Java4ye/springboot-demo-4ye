package com.java4ye.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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