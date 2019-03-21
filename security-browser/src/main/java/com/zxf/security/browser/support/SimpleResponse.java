package com.zxf.security.browser.support;

/**
 * Create by Mr.ZXF
 * on 2019-03-21 15:37
 */
public class SimpleResponse {

    public SimpleResponse(Object content){
        this.content = content;
    }

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
