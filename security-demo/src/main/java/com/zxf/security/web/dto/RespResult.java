package com.zxf.security.web.dto;

import com.zxf.security.enums.RespResultEnums;

import java.io.Serializable;
public class RespResult implements Serializable {

    private static final long serialVersionUID = -4395670706354633827L;

    private Integer code;

    private String msg;

    private Object data;

    public RespResult(String msg){
        this.msg = msg;
    }

    public RespResult(RespResultEnums enums) {
        this.code = enums.getCode();
        this.msg = enums.getMessage();
    }

    public static RespResult ok() {
        return new RespResult(RespResultEnums.SUCCESS);
    }

    public static RespResult ok(Object data){
        RespResult result = new RespResult(RespResultEnums.SUCCESS);
        result.setData(data);
        return result;
    }

    public static RespResult fail(RespResultEnums enums){
        return new RespResult(enums);
    }

    public static RespResult fail(RespResultEnums enums,Object data){
        RespResult result = new RespResult(enums);
        result.setData(data);
        return result;
    }

    public static RespResult fail(String msg){
        return new RespResult(msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
