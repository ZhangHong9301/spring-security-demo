package com.zxf.security.core.captcha.sms;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 17:48
 */
public interface SmsCaptchaSender {

    void send(String mobile, String captcha);
}
