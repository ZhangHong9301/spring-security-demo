package com.zxf.security.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Create by Mr.ZXF
 * on 2019-03-19 15:21
 */
public class UserQueryCondition {

    private String userName;

    @ApiModelProperty(value = "用户年龄起始值",example = "123")
    private Integer age;
    @ApiModelProperty(value = "用户年龄终止值",example = "123")
    private Integer ageTo;

    private String xxx;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }

    public String getXxx() {
        return xxx;
    }

    public void setXxx(String xxx) {
        this.xxx = xxx;
    }

}
