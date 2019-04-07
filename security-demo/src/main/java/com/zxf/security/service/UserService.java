package com.zxf.security.service;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<Map<String, Object>> userList();

    List<Map<String, Object>> userPermission();
}
