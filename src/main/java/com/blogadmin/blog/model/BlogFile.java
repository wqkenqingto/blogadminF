package com.blogadmin.blog.model;

import com.blogadmin.core.model.BaseEntity;

/**
 * @className:BlogFile
 * @author:wqkenqing
 * @describe:用于存放一些博客文件，如图片，模板等
 * @date:2017/3/10
 **/
public class BlogFile extends BaseEntity {
    private String name;
    private String filepath;
    private String memo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
