package com.blogadmin.blog.dao;

import com.blogadmin.blog.model.Aritcle;
import com.blogadmin.blog.model.Unit;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wqkenqing on 2017/3/10.
 */
public interface BlogContentDao {
    Aritcle get(Serializable id);

    int logicRemove(Serializable id);

    List<Aritcle> getAll();

    Long updateEntity(Aritcle aritcle);

    Long saveEntity(Aritcle aritcle);
}
