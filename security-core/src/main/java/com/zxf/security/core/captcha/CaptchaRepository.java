package com.zxf.security.core.captcha;

import org.springframework.web.context.request.ServletWebRequest;

public interface CaptchaRepository {


    /**
     * 保存验证码
     * @param request
     * @param captcha
     * @param captchaType
     */
    void save(ServletWebRequest request, Captcha captcha, CaptchaType captchaType);

    /**
     * 获取验证码
     * @param request
     * @param captchaType
     * @return
     */
    Captcha get(ServletWebRequest request,CaptchaType captchaType);

    /**
     * 移除验证码
     * @param request
     * @param captchaType
     */
    void remove(ServletWebRequest request,CaptchaType captchaType);
}
