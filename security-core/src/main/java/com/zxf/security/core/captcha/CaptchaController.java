package com.zxf.security.core.captcha;

import com.zxf.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 10:55
 */
@RestController
public class CaptchaController {

    @Autowired
    private CaptchaProcessorHolder captchaProcessorHolder;


    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link CaptchaProcessor} 接口实现
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_CAPTCHA_URL_PREFIX + "/{type}")
    public void createCaptcha(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        captchaProcessorHolder.findCaptchaProcessor(type).create(new ServletWebRequest(request, response));
    }

}
