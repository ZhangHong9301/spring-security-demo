package com.zxf.security.core.captcha.sms;

import com.zxf.security.core.captcha.Captcha;
import com.zxf.security.core.captcha.CaptchaGenerator;
import com.zxf.security.core.properties.SecurityProperties;
import com.zxf.security.core.util.ValidateCode;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 15:56
 */
@Component("smsCaptchaGenerator")
public class SmsCaptchaGenerator implements CaptchaGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public Captcha generate(ServletWebRequest request) {
        String captcha = RandomStringUtils.randomNumeric(securityProperties.getCaptcha().getSms().getLength());
        return new Captcha(captcha,securityProperties.getCaptcha().getSms().getExpireIn());
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
