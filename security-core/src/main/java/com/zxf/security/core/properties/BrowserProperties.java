package com.zxf.security.core.properties;


/**
 * Create by Mr.ZXF
 * on 2019-03-21 15:50
 */
public class BrowserProperties {

    private String loginPage = "/security-signIn.html";

    private LoginType loginType = LoginType.JSON;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
