package com.zxf.security.core.captcha;

import com.zxf.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 11:18
 * OncePerRequestFilter 保证只被调一次 ; InitializingBean 初始化
 */
public class CaptchaFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;

    /*路径匹配工具*/
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCaptcha().getImage().getUrl(), ",");
        for (String configUrl : configUrls) {
            urls.add(configUrl);
        }
        urls.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean action = false;
        /*匹配需要验证的URL*/
        for (String url : urls) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }

        if (action) {
            try {
                validate(new ServletWebRequest(request));
            } catch (CaptchaException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException, CaptchaException {

        /*生成的captcha*/
        ImageCaptcha captchaInSession = (ImageCaptcha) sessionStrategy.getAttribute(servletWebRequest, CaptchaController.SESSION_KEY);

        /*用户输入的captcha*/
        String captchaInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "captcha");

        if (StringUtils.isBlank(captchaInRequest)) {
            throw new CaptchaException("验证码的值不能为空");
        }

        if (captchaInSession == null) {
            throw new CaptchaException("验证码不存在");
        }

        if (captchaInSession.isExpried()) {
            sessionStrategy.removeAttribute(servletWebRequest, CaptchaController.SESSION_KEY);
            throw new CaptchaException("验证码已过期");
        }

        if (!StringUtils.equals(captchaInSession.getCaptcha(), captchaInRequest)) {
            throw new CaptchaException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(servletWebRequest, CaptchaController.SESSION_KEY);
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
