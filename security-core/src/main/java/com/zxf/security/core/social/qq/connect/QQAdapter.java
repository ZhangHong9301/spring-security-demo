package com.zxf.security.core.social.qq.connect;

import com.zxf.security.core.social.qq.api.QQ;
import com.zxf.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * Create by Mr.ZXF
 * on 2019-03-25 11:33
 */
public class QQAdapter implements ApiAdapter<QQ> {
    @Override
    public boolean test(QQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getQQUserInfo();

        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        //个人主页
        values.setProfileUrl(null);
        //QQ用户在服务商的ID
        values.setProviderUserId(userInfo.getOpenId());


    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {

        //do nothing
    }
}
