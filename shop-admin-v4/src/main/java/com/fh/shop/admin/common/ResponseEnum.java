package com.fh.shop.admin.common;

import java.io.Serializable;

public enum ResponseEnum implements Serializable {
    USERNAME_PASSWORD_IS_NULL(201,"用户名或密码为空!"),
    USERNAME_ERROR(202,"用户名不存在!"),
    PASSWORD_ERROR(203,"密码错误!"),
    USER_LOCK(204,"用户被锁定!"),
    DBY(205,"当前删除的数据被占用!"),

    ;
    private Integer code;

    private String msg;

    ResponseEnum(Integer code,String msg) {
        this.code = code;
        this.msg =msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
