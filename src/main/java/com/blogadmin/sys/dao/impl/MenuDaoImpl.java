package com.blogadmin.sys.dao.impl;

import com.blogadmin.core.dao.BaseDao;
import com.blogadmin.sys.dao.MenuDao;
import com.blogadmin.sys.model.Menu;
import org.springframework.stereotype.Repository;

import java.io.Serializable;


/**
 * Created by wqkenqing on 2017/3/21.
 */
@Repository
public class MenuDaoImpl extends BaseDao<Menu> implements MenuDao {
    @Override
    public Long delete(Serializable id) {
        return Long.valueOf(getSqlSession().delete("Menu_delete", id));
    }
}
