package com.zxf.security.core.properties;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 14:55
 */
public class SmsCaptchaProperties {

    private int length = 6;

    private int expireIn = 100;

    private String url="";


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
