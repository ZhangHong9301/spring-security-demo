package com.zxf.security.core.captcha.impl;

import com.zxf.security.core.captcha.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public abstract class AbstractCaptchaProcessor<C extends Captcha> implements CaptchaProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 收集系统中所有的 {@link CaptchaGenerator} 接口的实现
     */
    @Autowired
    private Map<String, CaptchaGenerator> captchaGenerators;


    /**
     * (non-Javadoc)
     *
     * @param request
     * @throws Exception
     * @see com.zxf.security.core.captcha.CaptchaProcessor#create(ServletWebRequest)
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        C captcha = generate(request);
        save(request, captcha);
        send(request, captcha);

    }

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getCaptchaType(request).toString().toLowerCase();
        String generatorName = type + CaptchaGenerator.class.getSimpleName();
        CaptchaGenerator captchaGenerator = captchaGenerators.get(generatorName);
        if (captchaGenerator == null) {
            throw new CaptchaException("验证码生成器" + generatorName + "不存在");
        }
        return (C) captchaGenerator.generate(request);
    }

    /**
     * 保存校验码
     *
     * @param request
     * @param captcha
     */
    private void save(ServletWebRequest request, C captcha) {
        logger.info("Save SessionKey is " + getSessionKey(request));
        /*只把编码和过期时间存入session中*/
        Captcha captcha1 = new Captcha(captcha.getCaptcha(),captcha.getExpireTime());
        sessionStrategy.setAttribute(request, getSessionKey(request), captcha1);
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param captcha
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C captcha) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private CaptchaType getCaptchaType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CaptchaProcessor");
        return CaptchaType.valueOf(type.toUpperCase());
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getCaptchaType(request).toString().toUpperCase();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        CaptchaType processorType = getCaptchaType(request);
        String sessionKey = getSessionKey(request);

        logger.info("=====验证sessionKey ：" + sessionKey);
        C captchaInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

        String captchaInRequest;
        try {
            captchaInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new CaptchaException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(captchaInRequest)) {
            throw new CaptchaException(processorType + "验证码的值不能为空");
        }

        if (captchaInSession == null) {
            throw new CaptchaException(processorType + "验证码不存在");
        }

        if (captchaInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new CaptchaException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(captchaInSession.getCaptcha(), captchaInRequest)) {
            throw new CaptchaException(processorType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }
}
