package com.zxf.security.core.authorize;

import com.zxf.security.core.properties.SecurityConstants;
import com.zxf.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
@Order(Integer.MIN_VALUE) /*最早执行*/
public class MyAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    SecurityProperties securityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        config.antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                securityProperties.getBrowser().getLoginPage(),
                SecurityConstants.DEFAULT_CAPTCHA_URL_PREFIX + "/*",
                securityProperties.getBrowser().getSignUpUrl(),
                securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".json",
                securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".html",
                securityProperties.getBrowser().getSignOutUrl(),
                securityProperties.getSocial().getFilterProcessesUrl() + "/*",
                "/user/regist")
                .permitAll();
    }


}
