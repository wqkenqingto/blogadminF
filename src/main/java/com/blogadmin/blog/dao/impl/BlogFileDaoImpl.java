package com.blogadmin.blog.dao.impl;

import com.blogadmin.blog.dao.BlogFileDao;
import com.blogadmin.blog.model.BlogFile;
import com.blogadmin.core.dao.BaseDao;

import java.io.Serializable;

/**
 * Created by wqkenqing on 2017/3/10.
 */
public class BlogFileDaoImpl extends BaseDao<BlogFile> implements BlogFileDao {


    @Override
    public int logicRomove(Serializable id) {
        return 1;
    }

    @Override
    public Integer delete(BlogFile blogFile) {
        return null;
    }
}
