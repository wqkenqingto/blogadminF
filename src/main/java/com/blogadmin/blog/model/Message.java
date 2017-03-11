package com.blogadmin.blog.model;

import com.blogadmin.core.model.BaseEntity;

/**
*@className:Message
*@author:wqkenqing
*@describe:信息交流bean
*@date:2017/3/10
**/
public class Message extends BaseEntity{
    private String name;
    private String content;
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
