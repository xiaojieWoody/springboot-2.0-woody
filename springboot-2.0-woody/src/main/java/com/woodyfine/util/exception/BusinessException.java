package com.woodyfine.util.exception;

import java.io.Serializable;

public class BusinessException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -1736438204890775986L;

    private String msg;
    private int code = 500;

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BusinessException(Throwable e, String msg) {
        super(msg,e);
        this.msg = msg;
    }

    public BusinessException(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public BusinessException(Throwable cause, String msg, int code) {
        super(cause);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
