package com.blogadmin.blog.model;

import com.blogadmin.core.model.BaseEntity;

import java.util.Date;

/**
 * @className:Aritcle
 * @author:wqkenqing
 * @describe:文章bean，可以用作通过type,区分technology、life
 * @date:2017/3/10
 **/
public class Article extends BaseEntity {
    private String name;
    private String content;
    private String memo;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", memo='" + memo + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
