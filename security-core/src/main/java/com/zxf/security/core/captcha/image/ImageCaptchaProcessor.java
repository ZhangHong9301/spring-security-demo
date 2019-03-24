package com.zxf.security.core.captcha.image;

import com.zxf.security.core.captcha.impl.AbstractCaptchaProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * 图片验证码处理器
 */
@Component("imageCaptchaProcessor")
public class ImageCaptchaProcessor extends AbstractCaptchaProcessor<ImageCaptcha> {

    /**
     * 发送图形验证码，将其写到响应中
     * @param request
     * @param imageCaptcha
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest request, ImageCaptcha imageCaptcha) throws Exception {
        ImageIO.write(imageCaptcha.getImage(),"JPEG",request.getResponse().getOutputStream());
    }
}
