package com.zxf.security.web.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * Create by Mr.ZXF
 * on 2019-03-20 10:25
 */
// @Component
public class TimeFilter implements Filter {

    @Override
    /*初始化*/
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("time filter init");

    }

    @Override
    /*过滤逻辑*/
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("time filter start");
        Long start = new Date().getTime();
        chain.doFilter(request, response);
        System.out.println("time filter 耗时:" + (new Date().getTime() - start));
        System.out.println("time filter finish");

    }

    @Override
    /*销毁*/
    public void destroy() {
        System.out.println("time filter destroy");
    }
}
