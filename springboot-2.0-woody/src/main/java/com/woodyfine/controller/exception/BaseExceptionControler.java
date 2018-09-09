package com.woodyfine.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 局部异常处理
 */
@Controller
public class BaseExceptionControler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/ex")
    @ResponseBody
    public String error(){
        int i=5/0;
        return "ex";
    }

    //局部异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String exHandler(Exception e){
        // 判断发生异常的类型是除0异常则做出响应
        if(e instanceof ArithmeticException){
            return "发生了除0异常";
        }
        // 未知的异常做出响应
        return "发生了未知异常";
    }
}
