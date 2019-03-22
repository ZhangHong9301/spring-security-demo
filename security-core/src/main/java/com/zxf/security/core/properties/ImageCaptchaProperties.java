package com.zxf.security.core.properties;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 14:55
 */
public class ImageCaptchaProperties extends SmsCaptchaProperties {
    public ImageCaptchaProperties() {
        setLength(4);
    }

    private int width = 140;

    private int height = 40;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
