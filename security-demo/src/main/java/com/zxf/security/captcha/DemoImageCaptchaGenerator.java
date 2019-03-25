package com.zxf.security.captcha;

import com.zxf.security.core.captcha.CaptchaGenerator;
import com.zxf.security.core.captcha.image.ImageCaptcha;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 16:11
 */
// @Component(value = "imageCaptchaGenerator")

/*===============高级开发人员须具有的开发思想： 以增量的方式去适应变化 ====================*/
/**
 *  当出现变化的时候，如图形验证码的生成逻辑改变了，原来的生成逻辑不满足了，
 *  此时的处理方式不是去改原来的代码，而是新增代码，
 *  以保证别人在不改你写的代码的前提下能改变你提供出来的模块的业务逻辑
 */

public class DemoImageCaptchaGenerator implements CaptchaGenerator {
    @Override
    public ImageCaptcha generate(ServletWebRequest request) {
        System.out.println("更高级的图形验证码生成代码");
        return null;
    }
}
