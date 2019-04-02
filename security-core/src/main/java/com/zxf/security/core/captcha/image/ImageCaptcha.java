package com.zxf.security.core.captcha.image;

import com.zxf.security.core.captcha.Captcha;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 10:49
 */
public class ImageCaptcha extends Captcha {

    private static final long serialVersionUID = 3509143183613838122L;

    /*不需要放入Redis*/
    private BufferedImage image;

    public ImageCaptcha(BufferedImage image, String captcha, int expireIn) {
        super(captcha,expireIn);
        this.image = image;
    }

    public ImageCaptcha(BufferedImage image, String captcha, LocalDateTime expireTime) {
        super(captcha,expireTime);
        this.image = image;
    }
    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
