package com.blogadmin.sys.dao;


import com.blogadmin.blog.model.Article;
import com.blogadmin.sys.model.Menu;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wqkenqing on 2017/3/21.
 */
public interface MenuDao {
    Menu get(Serializable id);

    int logicRemove(Serializable id);

    List<Menu> getAll();

    Long updateEntity(Menu menu);

    Long saveEntity(Menu menu);

    Long delete(Serializable id);
}
