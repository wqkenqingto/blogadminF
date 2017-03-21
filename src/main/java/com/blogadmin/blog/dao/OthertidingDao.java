package com.blogadmin.blog.dao;

import com.blogadmin.blog.model.Article;
import com.blogadmin.blog.model.Othertidings;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wqkenqing on 2017/3/17.
 */
public interface OthertidingDao {
    Othertidings get(Serializable id);

    int logicRemove(Serializable id);

    List<Othertidings> getAll();

    Long updateEntity(Othertidings othertidings);

    Long saveEntity(Othertidings othertidings);

    Integer delete(Othertidings othertidings);
}
