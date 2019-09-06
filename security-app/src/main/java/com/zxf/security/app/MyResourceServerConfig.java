package com.zxf.security.app;

import com.zxf.security.core.authentication.mobile.SmsCaptchaAuthenticationSecurityConfig;
import com.zxf.security.core.authorize.AuthorizeConfigManager;
import com.zxf.security.core.captcha.CaptchaSecurityConfig;
import com.zxf.security.core.properties.SecurityConstants;
import com.zxf.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableResourceServer  /*资源服务器*/
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    protected AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private SpringSocialConfigurer mySpringSocialConfigurer;

    @Autowired
    private CaptchaSecurityConfig captchaSecurityConfig;

    @Autowired
    private SmsCaptchaAuthenticationSecurityConfig smsCaptchaAuthenticationSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(securityProperties.getBrowser().getLoginPage())
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler);

        http.apply(captchaSecurityConfig)
                .and()
                .apply(smsCaptchaAuthenticationSecurityConfig)
                .and()
                .apply(mySpringSocialConfigurer)
                .and()
                /*.cors()
                .and()*/
                .csrf().disable();

        authorizeConfigManager.config(http.authorizeRequests());
    }
}
