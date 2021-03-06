package com.zxf.security.browser.captcha.impl;

import com.zxf.security.core.captcha.Captcha;
import com.zxf.security.core.captcha.CaptchaRepository;
import com.zxf.security.core.captcha.CaptchaType;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class SessionCaptchaRepository implements CaptchaRepository {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CAPTCHA_";

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void save(ServletWebRequest request, Captcha captcha, CaptchaType captchaType) {
        sessionStrategy.setAttribute(request, getSessionKey(request, captchaType), captcha);
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @param captchaType
     * @return
     */
    private String getSessionKey(ServletWebRequest request, CaptchaType captchaType) {
        return SESSION_KEY_PREFIX + captchaType.toString().toUpperCase();
    }

    @Override
    public Captcha get(ServletWebRequest request, CaptchaType captchaType) {
        return (Captcha) sessionStrategy.getAttribute(request, getSessionKey(request, captchaType));
    }

    @Override
    public void remove(ServletWebRequest request, CaptchaType captchaType) {
        sessionStrategy.removeAttribute(request, getSessionKey(request, captchaType));
    }
}
