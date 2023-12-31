package com.he.chataiparent.common.exception;

import com.he.chataiparent.common.result.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatException extends RuntimeException {

    //异常状态码
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param code
     * @param message
     */
    public ChatException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     * @param resultCode
     */
    public ChatException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    @Override
    public String toString() {
        return "chatException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }

}
