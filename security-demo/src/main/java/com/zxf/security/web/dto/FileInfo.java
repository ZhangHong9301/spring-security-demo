package com.zxf.security.web.dto;

/**
 * Create by Mr.ZXF
 * on 2019-03-20 15:00
 */
public class FileInfo {
    public FileInfo(String path){
        this.path = path;
    }

    /**
     *
     */
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
