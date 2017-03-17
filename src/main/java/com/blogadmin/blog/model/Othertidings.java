package com.blogadmin.blog.model;

import com.blogadmin.core.model.BaseEntity;

/**
 * @className:Othertidings
 * @author:wqkenqing
 * @describe:其它文本
 * @date:2017/3/10
 **/
public class Othertidings extends BaseEntity {
    private String name;
    private String content;
    private String memo;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
