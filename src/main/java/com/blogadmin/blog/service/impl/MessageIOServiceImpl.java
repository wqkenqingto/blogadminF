package com.blogadmin.blog.service.impl;

import com.blogadmin.blog.dao.MessageIODao;
import com.blogadmin.blog.model.Message;
import com.blogadmin.blog.service.MessageIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wqkenqing on 2017/3/10.
 */
@Component
public class MessageIOServiceImpl implements MessageIOService {
    @Autowired
    MessageIODao messageIODao;

    @Override
    public Long add(Message message) {
        return messageIODao.saveEntity(message);
    }

    @Override
    public Message getMessage(Long id) {
        return messageIODao.get(id);
    }

    @Override
    public List<Message> ListMessage() {
        return null;
    }

    @Override
    public Long updateMessage(Message message) {
        return messageIODao.updateEntity(message);
    }

    @Override
    public Long removeMessage(Long id) {
        return messageIODao.logicRomove(id);
    }
}
