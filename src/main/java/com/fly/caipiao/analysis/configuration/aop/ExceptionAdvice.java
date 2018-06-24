package com.fly.caipiao.analysis.configuration.aop;

import com.fly.caipiao.analysis.framework.response.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author baidu
 * @date 2018/5/20 下午10:30
 * @description 异常处理
 **/

@ControllerAdvice
public class ExceptionAdvice {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public Object handlerException(
            Throwable throwable) throws Throwable {
        logger.error("server exception：", throwable);
        Throwable cause = throwable;

        Result result = new Result();

        if (cause instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) cause;
            List<String> list = new ArrayList<>();
            for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
                list.add(fieldError.getDefaultMessage());
            }
            result.setErrorMessage(StringUtils.join(list, ","));
        }else{
            result.setErrorMessage(cause.getMessage());
        }
        return result;
    }
}
