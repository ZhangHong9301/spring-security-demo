package com.zxf.security.app.captcha.impl;

import com.zxf.security.core.captcha.Captcha;
import com.zxf.security.core.captcha.CaptchaException;
import com.zxf.security.core.captcha.CaptchaRepository;
import com.zxf.security.core.captcha.CaptchaType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCaptchaRepository implements CaptchaRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public void save(ServletWebRequest request, Captcha captcha, CaptchaType captchaType) {
        redisTemplate.opsForValue().set(buildKey(request, captchaType), captcha, 30, TimeUnit.MINUTES);
    }

    private String buildKey(ServletWebRequest request, CaptchaType captchaType) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new CaptchaException("请在请求头中携带deviceId参数");
        }
        return "captcha:" + captchaType.toString().toLowerCase() + ":" + deviceId;
    }

    @Override
    public Captcha get(ServletWebRequest request, CaptchaType captchaType) {
        Object value = redisTemplate.opsForValue().get(buildKey(request,captchaType));
        if (value == null) {
            return null;
        }
        return (Captcha) value;
    }

    @Override
    public void remove(ServletWebRequest request, CaptchaType captchaType) {
        redisTemplate.delete(buildKey(request,captchaType));

    }
}
