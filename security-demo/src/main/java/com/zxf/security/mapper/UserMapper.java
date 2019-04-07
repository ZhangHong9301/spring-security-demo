package com.zxf.security.mapper;


import java.util.List;
import java.util.Map;

public interface UserMapper {


    List<Map<String, Object>> getAll();

    List<Map<String ,Object>> getPermission();
}
