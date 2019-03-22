package com.zxf.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Create by Mr.ZXF
 * on 2019-03-21 15:50
 */
@ConfigurationProperties(prefix = "zxf.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    private CaptchaProperties captcha = new CaptchaProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public CaptchaProperties getCaptcha() {
        return captcha;
    }

    public void setCaptcha(CaptchaProperties captcha) {
        this.captcha = captcha;
    }
}
