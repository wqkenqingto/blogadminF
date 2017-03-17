package com.blogadmin.blog.dao.impl;

import com.blogadmin.blog.dao.MessageIODao;
import com.blogadmin.blog.model.Message;
import com.blogadmin.core.dao.BaseDao;

import java.io.Serializable;

/**
 * Created by wqkenqing on 2017/3/10.
 */
public class MessageIODaoImpl extends BaseDao<Message> implements MessageIODao {

    @Override
    public int logicRomove(Serializable id) {
        return 0;
    }

    @Override
    public Integer delete(Message message) {
        return getSqlSession().delete("Message_delete");
    }
}
