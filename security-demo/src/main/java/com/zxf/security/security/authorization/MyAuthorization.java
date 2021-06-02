package com.zxf.security.security.authorization;

import com.zxf.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: ZhangHong
 * @Date: 2019-09-23 10:36
 */
@Component("myAuthorization")
public class MyAuthorization {

    @Autowired
    private SecurityProperties securityProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "Bearer ");

        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                    .parseClaimsJws(token).getBody();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("claims : {}", claims.toString());
        String company = (String) claims.get("company");
        String userName = (String) claims.get("user_name");
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
        return false;
    }
}
