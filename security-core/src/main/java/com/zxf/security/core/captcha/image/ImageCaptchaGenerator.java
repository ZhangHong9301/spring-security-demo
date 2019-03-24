package com.zxf.security.core.captcha.image;

import com.zxf.security.core.captcha.CaptchaGenerator;
import com.zxf.security.core.properties.SecurityProperties;
import com.zxf.security.core.util.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 15:56
 */
public class ImageCaptchaGenerator implements CaptchaGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ImageCaptcha generate(ServletWebRequest request) {
        /*ServletRequestUtils 从请求中获取参数值*/
        int width = ServletRequestUtils.getIntParameter(request.getRequest(),"width",securityProperties.getCaptcha().getImage().getWidth());

        int height = ServletRequestUtils.getIntParameter(request.getRequest(),"height",securityProperties.getCaptcha().getImage().getHeight());

        ValidateCode vCode = new ValidateCode(width, height, securityProperties.getCaptcha().getImage().getLength(), 10);
        return new ImageCaptcha(vCode.getBuffImg(), vCode.getCode(), securityProperties.getCaptcha().getImage().getExpireIn());
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
