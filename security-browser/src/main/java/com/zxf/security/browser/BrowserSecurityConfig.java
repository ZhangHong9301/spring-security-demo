package com.zxf.security.browser;

import com.zxf.security.core.authentication.mobile.SmsCaptchaAuthenticationSecurityConfig;
import com.zxf.security.core.authorize.AuthorizeConfigManager;
import com.zxf.security.core.authorize.AuthorizeConfigProvider;
import com.zxf.security.core.authorize.MyAuthorizeConfigProvider;
import com.zxf.security.core.captcha.CaptchaSecurityConfig;
import com.zxf.security.core.properties.SecurityConstants;
import com.zxf.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

import com.zxf.security.core.authentication.AbstractChannelSecurityConfig;

/**
 * Create by Mr.ZXF
 * on 2019-03-21 11:45
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private SmsCaptchaAuthenticationSecurityConfig smsCaptchaAuthenticationSecurityConfig;

    @Autowired
    private CaptchaSecurityConfig captchaSecurityConfig;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private SpringSocialConfigurer mySocialSecurityConfig;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyPasswordAuthenticationConfig(http);

        http.apply(captchaSecurityConfig)
                .and()
                .apply(smsCaptchaAuthenticationSecurityConfig)
                .and()
                .apply(mySocialSecurityConfig)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(myUserDetailsService)
                .and()
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and()
                .and()
                .logout()
                .logoutUrl("/signOut")
                .logoutSuccessHandler(logoutSuccessHandler)
                /*清除浏览器中的cookies*/
                .deleteCookies("JSESSIONID")
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringAntMatchers(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM);

        // .csrf().ignoringAntMatchers("/user/list",SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM);

        authorizeConfigManager.config(http.authorizeRequests());

    }
}
