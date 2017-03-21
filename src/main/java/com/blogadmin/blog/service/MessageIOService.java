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
    public Long add(Message message);
    public Message getMessage(Long id);
    public List<Message> ListMessage();
    public Long updateMessage(Message message);
    public Long removeMessage(Long id);
}
