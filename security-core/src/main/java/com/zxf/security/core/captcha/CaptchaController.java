package com.zxf.security.core.captcha;

import com.zxf.security.core.captcha.sms.SmsCaptchaSender;
import com.zxf.security.core.properties.SecurityProperties;
import com.zxf.security.core.util.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 10:55
 */
@RestController
public class CaptchaController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CAPTCHA";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private CaptchaGenerator imageCaptchaGenerator;

    @Autowired
    private CaptchaGenerator smsCaptchaGenerator;

    @Autowired
    private SmsCaptchaSender smsCaptchaSender;

    @GetMapping("/captcha/image")
    public void createCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ImageCaptcha imageCaptcha = (ImageCaptcha) imageCaptchaGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCaptcha);
        ImageIO.write(imageCaptcha.getImage(), "JPEG", response.getOutputStream());
    }

    @GetMapping("/captcha/sms")
    public void createSmsCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {

        Captcha smsCaptcha = smsCaptchaGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCaptcha);
        /*短信服务商发送短信*/
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile"); /*getRequiredStringParameter 必须包含参数*/
        smsCaptchaSender.send(mobile, smsCaptcha.getCaptcha());

    }
}
