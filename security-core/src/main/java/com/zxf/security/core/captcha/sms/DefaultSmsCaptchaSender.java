package com.zxf.security.core.captcha.sms;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 17:49
 */
public class DefaultSmsCaptchaSender implements SmsCaptchaSender {
    @Override
    public void send(String mobile, String captcha) {

        System.out.println("向手机" + mobile + "发送短信验证码" + captcha);
    }
}
