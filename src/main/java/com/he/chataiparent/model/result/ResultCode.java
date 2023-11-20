package com.he.chataiparent.model.result;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "未找到"),
    METHOD_NOT_ALLOWED(405, "方法不允许"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "冲突"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");



    private Integer code;
    private String message;
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
