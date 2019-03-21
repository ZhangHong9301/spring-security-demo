package com.zxf.security.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxf.security.dto.User;
import com.zxf.security.dto.UserQueryCondition;
import com.zxf.security.exception.UserNotExisException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by Mr.ZXF
 * on 2019-03-19 14:59
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user){
        return user;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {

        /*if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(resources.error -> System.out.println(resources.error.getDefaultMessage()));
        }*/
        System.out.println(user.getId());
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());
        user.setId("1");
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors) {

        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
                // FieldError fieldError = (FieldError) resources.error;
                // String message = fieldError.getField()+" "+resources.error.getDefaultMessage();
                System.out.println(error.getDefaultMessage());
            });

        }
        System.out.println(user.getId());
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());
        user.setId("1");
        return user;
    }

    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable String id){
        System.out.println(id);
    }

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "用户查询服务")
    public List<User> query(UserQueryCondition userQueryCondition, @PageableDefault(page = 2, size = 17, sort = "userName,asc") Pageable pageable) {
        System.out.println(ReflectionToStringBuilder.toString(userQueryCondition, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    /* :\d+ 正则表达式限制数字*/
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@ApiParam("用户id") @PathVariable String id) {

        // throw new RuntimeException("user not exist");

        System.out.println("进入getInfo服务");
        User user = new User();
        user.setUserName("tom");
        return user;
    }

}
