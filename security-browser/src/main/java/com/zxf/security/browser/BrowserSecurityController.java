package com.zxf.security.browser;

import com.zxf.security.browser.support.SimpleResponse;
import com.zxf.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by Mr.ZXF
 * on 2019-03-21 15:26
 */
@RestController
public class BrowserSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); /*跳转*/

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要身份认证时，跳转到这里
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED) /*未授权状态码*/
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是： " + targetUrl);

            if (StringUtils.endsWithIgnoreCase(targetUrl,".html")) {
                /*如果是网页请求重定向到指定URL*/
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
            }
        }
        /*反之则返回相应状态码及json字符串信息*/
        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }

}
