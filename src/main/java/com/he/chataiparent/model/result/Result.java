package com.he.chataiparent.model.result;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public Result() {

    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    // 自定义设置数据方法
    public static<T> Result<T> build(T data, Integer code, String message) {
        Result<T> result = new Result<>();
        // 判断data中是否有数据，如果有则设置，否则不设置
        if (data != null) {
            result.setData(data);
        }
        // 设置状态码
        result.setCode(code);
        result.setMessage(message);

        // 返回结果
        return result;
    }

    // 自定义设置数据方法(枚举类中状态码及信息)
    public static<T> Result<T> build(T data, ResultCode resultCodeEnum) {
        Result<T> result = new Result<>();
        // 判断data中是否有数据，如果有则设置，否则不设置
        if (data != null) {
            result.setData(data);
        }
        // 设置状态码
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());

        // 返回结果
        return result;
    }

    // 成功
    public static<T> Result<T> ok(T data) {
        return build(data, ResultCode.SUCCESS);
    }

    // 失败
    public static<T> Result<T> fail(String message) {
        if (StrUtil.isNotBlank(message)){
            return build(null, ResultCode.FAIL.getCode(), message);
        }
        return build(null, ResultCode.FAIL);
    }

}
