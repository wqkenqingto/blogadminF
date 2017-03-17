package com.blogadmin.blog.model;

import com.blogadmin.core.model.BaseEntity;

/**
 * @className:Message
 * @author:wqkenqing
 * @describe:信息交流bean
 * @date:2017/3/10
 **/
public class Message extends BaseEntity {
    private String content;
    private String usermark;
    private String meomo;

    public String getMeomo() {
        return meomo;
    }

    public void setMeomo(String meomo) {
        this.meomo = meomo;
    }

    public String getUsermark() {
        return usermark;
    }

    public void setUsermark(String usermark) {
        this.usermark = usermark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
