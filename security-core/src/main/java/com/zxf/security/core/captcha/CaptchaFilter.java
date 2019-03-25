package com.zxf.security.core.captcha;

import com.zxf.security.core.properties.SecurityConstants;
import com.zxf.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Create by Mr.ZXF
 * on 2019-03-22 11:18
 * OncePerRequestFilter 保证只被调一次 ; InitializingBean 初始化
 */
@Component("captchaFilter")
public class CaptchaFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 验证码失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private CaptchaProcessorHolder captchaProcessorHolder;

    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, CaptchaType> urlMap = new HashMap<>();

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 初始化要拦截的url配置信息
     *
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, CaptchaType.IMAGE);
        addUrlToMap(securityProperties.getCaptcha().getImage().getUrl(), CaptchaType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, CaptchaType.SMS);
        addUrlToMap(securityProperties.getCaptcha().getSms().getUrl(), CaptchaType.SMS);
    }

    /**
     * 将系统中配置的需要校验验证码的url根据校验的类型放入map
     */
    protected void addUrlToMap(String urlString, CaptchaType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        CaptchaType type = getCaptchaType(request);

        if (type != null) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                captchaProcessorHolder.findCaptchaProcessor(type).validate(new ServletWebRequest(request,response));
                logger.info("验证码校验通过");
            } catch (CaptchaException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private CaptchaType getCaptchaType(HttpServletRequest request) {
        CaptchaType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }

}
