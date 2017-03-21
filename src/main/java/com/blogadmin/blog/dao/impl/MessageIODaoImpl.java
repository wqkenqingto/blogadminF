package com.blogadmin.blog.dao.impl;

import com.blogadmin.blog.dao.MessageIODao;
import com.blogadmin.blog.model.Message;
import com.blogadmin.core.dao.BaseDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by wqkenqing on 2017/3/10.
 */
@Repository
public class MessageIODaoImpl extends BaseDao<Message> implements MessageIODao {

    public Long logicRomove(Serializable id) {
        return Long.valueOf(getSqlSession().delete("Message_logicDelete",id));
    }

    public Integer delete(Message message) {
        return null;
    }
}
