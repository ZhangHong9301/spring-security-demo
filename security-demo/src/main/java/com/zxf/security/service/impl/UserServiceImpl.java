package com.zxf.security.service.impl;

import com.zxf.security.mapper.UserMapper;
import com.zxf.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<Map<String, Object>> userList() {
        return userMapper.getAll();
    }

    @Override
    public List<Map<String, Object>> userPermission() {
        return userMapper.getPermission();
    }
}
