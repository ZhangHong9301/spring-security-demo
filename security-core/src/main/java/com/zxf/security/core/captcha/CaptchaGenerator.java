package com.zxf.security.core.captcha;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 15:53
 */
public interface CaptchaGenerator {

    Captcha generate(ServletWebRequest request);
}
