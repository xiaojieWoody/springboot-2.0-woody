package com.woodyfine.test;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 8973591663004294150L;

    private String creat_time;

    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }
}
