package com.zxf.security.core.captcha.sms;

import com.zxf.security.core.captcha.Captcha;
import com.zxf.security.core.captcha.impl.AbstractCaptchaProcessor;
import com.zxf.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 图片验证码处理器
 */
@Component("smsCaptchaProcessor")
public class SmsCaptchaProcessor extends AbstractCaptchaProcessor<Captcha> {

    @Autowired
    private SmsCaptchaSender smsCaptchaSender;

    /**
     * 发送图形验证码，将其写到响应中
     * @param request
     * @param captcha
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest request, Captcha captcha) throws Exception {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(),paramName);
        smsCaptchaSender.send(mobile,captcha.getCaptcha());
    }
}
