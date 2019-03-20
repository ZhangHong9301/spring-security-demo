package com.zxf.security.exception;

/**
 * Create by Mr.ZXF
 * on 2019-03-20 9:50
 */
public class UserNotExisException extends RuntimeException{

    private static final long serialVersionUID = -6112780192479692859L;

    private String id;

    public UserNotExisException(String id){
        super("user not exist");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
