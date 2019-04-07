package com.zxf.security.rbac;

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

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();

            /*读取用户所拥有权限的所有URL*/
            List<Map<String, Object>> urls = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("method", "POST");
            map.put("url", "/user/list");
            Map<String, Object> map1 = new HashMap<>();
            map1.put("method", "GET");
            map1.put("url", "/user/permission");
            urls.add(map);
            urls.add(map1);

            for (Map<String, Object> url : urls) {
                if (antPathMatcher.match(url.get("url").toString(), request.getRequestURI())
                        && StringUtils.equals(url.get("method").toString(), request.getMethod())) {
                    hasPermission = true;
                    break;
                }
            }

        }

        return hasPermission;
    }
}
