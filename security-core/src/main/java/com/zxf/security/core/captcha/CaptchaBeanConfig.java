package com.zxf.security.core.captcha;

import com.zxf.security.core.captcha.sms.DefaultSmsCaptchaSender;
import com.zxf.security.core.captcha.sms.SmsCaptchaSender;
import com.zxf.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 16:01
 */
@Configuration
public class CaptchaBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    /**
     * @ConditionalOnMissingBean
     * 在初始化Bean CaptchaGenerator之前，先在spring容器中找imageCaptchaGenerator，
     * 若找到就不会初始化Bean,反之则初始化
     * 实现保证别人在不改你写的代码的前提下能改变你提供出来的模块的业务逻辑
     */
    @ConditionalOnMissingBean(name = "imageCaptchaGenerator")
    public CaptchaGenerator imageCaptchaGenerator(){
        ImageCaptchaGenerator captchaGenerator = new ImageCaptchaGenerator();
        captchaGenerator.setSecurityProperties(securityProperties);
        return captchaGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCaptchaSender.class)
    public SmsCaptchaSender smsCaptchaSender(){
        return new DefaultSmsCaptchaSender();
    }
}
