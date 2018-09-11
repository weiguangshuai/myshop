package com.cqupt.project.shop.common;

import com.cqupt.project.shop.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author weigs
 * @date 2018/9/6 0006
 */
@ControllerAdvice
public class ExceptionResolver {

    private static final Logger log = LoggerFactory.getLogger(ExceptionResolver.class);

    /**
     * 拦截全局异常
     *
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = SystemException.class)
    public ServerResponse handlerException(SystemException e) {
        log.error("异常信息：{}", e.getMessage());
        return ServerResponse.createByErrorMessage("系统异常");
    }
}
