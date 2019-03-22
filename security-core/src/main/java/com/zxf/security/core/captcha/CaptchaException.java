package com.zxf.security.core.captcha;


import org.springframework.security.core.AuthenticationException;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 11:24
 */
public class CaptchaException extends AuthenticationException {

    private static final long serialVersionUID = -7285211528095468156L;

    public CaptchaException(String msg) {
        super(msg);
    }
}
