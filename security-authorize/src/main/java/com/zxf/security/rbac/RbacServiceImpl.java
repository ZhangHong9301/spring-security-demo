package com.zxf.security.rbac;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 授权：判断用户是否有权限继续执行；作对比，将请求的资源（路径以及请求方法）与当前用户已有的资源对比
     * @param request
     * @param authentication
     * @return
     */
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();

            /*读取用户所拥有权限的所有URL*/
            List<Map<String, Object>> urlsMap = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("method", "POST");
            map.put("url", "/user/list");
            Map<String, Object> map1 = new HashMap<>();
            map1.put("method", "GET");
            map1.put("url", "/user/permission");
            urlsMap.add(map);
            urlsMap.add(map1);

            for (Map<String, Object> urlMap : urlsMap) {

                if (StringUtils.isNotBlank(MapUtils.getString(urlMap, "method"))) {
                    if (antPathMatcher.match(MapUtils.getString(urlMap, "url"), request.getRequestURI())
                            && StringUtils.equals(MapUtils.getString(urlMap, "method"), request.getMethod())) {
                        return true;
                    }
                }
                if (antPathMatcher.match(MapUtils.getString(urlMap, "url"), request.getRequestURI())) {
                    return true;
                }


            }

        }
        return false;
    }
}
