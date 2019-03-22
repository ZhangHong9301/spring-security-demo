package com.zxf.security.core.properties;

import com.zxf.security.core.captcha.ImageCaptcha;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 14:59
 */
public class CaptchaProperties {

    private ImageCaptchaProperties image = new ImageCaptchaProperties();

    private SmsCaptchaProperties sms = new SmsCaptchaProperties();

    public ImageCaptchaProperties getImage() {
        return image;
    }

    public void setImage(ImageCaptchaProperties image) {
        this.image = image;
    }

    public SmsCaptchaProperties getSms() {
        return sms;
    }

    public void setSms(SmsCaptchaProperties sms) {
        this.sms = sms;
    }
}
