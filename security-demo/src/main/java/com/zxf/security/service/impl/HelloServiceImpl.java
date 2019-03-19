package com.zxf.security.service.impl;

import com.zxf.security.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * Create by Mr.ZXF
 * on 2019-03-19 17:21
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String greeting(String name) {
        System.out.println("greeting");
        return "hello " + name;
    }
}
