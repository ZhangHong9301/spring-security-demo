package com.zxf.security.web.async;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by Mr.ZXF
 * on 2019-03-20 15:54
 */
@Component
public class DeferredResultHolder {

    private Map<String ,DeferredResult<String >> map = new HashMap<>();

    public Map<String, DeferredResult<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<String>> map) {
        this.map = map;
    }

}