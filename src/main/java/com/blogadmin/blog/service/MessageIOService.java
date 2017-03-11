package com.blogadmin.blog.service;

import com.blogadmin.blog.model.Message;

import java.util.List;

/**
*@className:MessageIOService
*@author:wqkenqing
*@describe:message交流接口
*@date:2017/3/10
**/
public interface MessageIOService {
    public int add();
    public Message getMessage();
    public List<Message> ListMessage();
    public int updateMessage(Message message);
    public int removeMessage(int i);
}
