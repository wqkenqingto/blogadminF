package com.blogadmin.blog.dao.impl;

import com.blogadmin.blog.dao.MessageIODao;
import com.blogadmin.blog.model.Message;
import com.blogadmin.core.dao.BaseDao;

import java.io.Serializable;

/**
 * Created by wqkenqing on 2017/3/10.
 */
public class MessageIODaoImpl extends BaseDao<Message> implements MessageIODao {

    public Long logicRomove(Serializable id) {
        return null;
    }

    public Integer delete(Message message) {
        return null;
    }
}
