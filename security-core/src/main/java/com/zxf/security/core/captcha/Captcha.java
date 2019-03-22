package com.zxf.security.core.captcha;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 10:49
 */
public class Captcha {

    private String captcha;

    private LocalDateTime expireTime;

    public Captcha(String captcha, int expireIn) {
        this.captcha = captcha;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public Captcha(String captcha, LocalDateTime expireTime) {
        this.captcha = captcha;
        this.expireTime = expireTime;
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
