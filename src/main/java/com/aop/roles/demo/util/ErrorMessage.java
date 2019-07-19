package com.aop.roles.demo.util;

import com.alibaba.fastjson.JSONObject;

public class ErrorMessage extends JSONObject {
    public ErrorMessage fail(String msg){
        this.put("code",20000);
        this.put("error_msg",msg);
        return this;
    }
}
