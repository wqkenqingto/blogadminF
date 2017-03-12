package com.blogadmin.blog.dao;

import com.blogadmin.blog.model.Message;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wqkenqing on 2017/3/10.
 */
public interface MessageIODao {
    Message get(Serializable id);

    int logicRomove(Serializable id);

    List<Message> getAll();

    Long updateEntity(Message message);

    Long saveEntity(Message message);

    Integer delete(Message message);
}
