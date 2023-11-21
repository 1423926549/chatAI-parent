package com.he.chataiparent.common.exception;

import com.he.chataiparent.common.result.Result;
import com.he.chataiparent.common.result.ResultCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody  // 将对象转化为JSON对象返回
    public Result error(Exception e){
        log.error(e.getClass().getName() + ": " + e.getMessage());
        return Result.fail(null);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public Result error(ExpiredJwtException e){
        log.error(e.getClass().getName() + ": " + e.getMessage());
        return Result.build(null, ResultCode.UNAUTHORIZED);
    }

    @ExceptionHandler(ChatException.class)
    @ResponseBody
    public Result error(ChatException e) {
        log.error(e.getClass().getName() + ": " + e.getMessage());
        return Result.build(null,e.getCode(), e.getMessage());
    }

}
