package com.zxf.security.security;

import com.zxf.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
@Order(Integer.MAX_VALUE) /*使最后加载，order值越大越晚被加载*/
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {


    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //config.anyRequest().access("@rbacService.hasPermission(request,authentication)");  /*anyRequest一定得在最后被加载*/
        config.anyRequest().permitAll();
    }
}
