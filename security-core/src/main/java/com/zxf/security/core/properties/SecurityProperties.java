package com.zxf.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Create by Mr.ZXF
 * on 2019-03-21 15:50
 */
@ConfigurationProperties(prefix = "zxf.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
