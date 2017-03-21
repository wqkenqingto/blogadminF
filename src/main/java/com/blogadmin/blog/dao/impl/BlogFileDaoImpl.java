package com.blogadmin.blog.dao.impl;

import com.blogadmin.blog.dao.BlogFileDao;
import com.blogadmin.blog.model.BlogFile;
import com.blogadmin.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by wqkenqing on 2017/3/10.
 */
@Repository
public class BlogFileDaoImpl extends BaseDao<BlogFile> implements BlogFileDao {

    public Long logicRomove(Serializable id) {
        return null;
    }

    public Integer delete(BlogFile blogFile) {
        return null;
    }
}
