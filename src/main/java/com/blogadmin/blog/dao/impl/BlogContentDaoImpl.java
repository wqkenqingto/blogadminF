package com.blogadmin.blog.dao.impl;

import com.blogadmin.blog.dao.BlogContentDao;
import com.blogadmin.blog.model.Article;
import com.blogadmin.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by wqkenqing on 2017/3/10.
 */
@Repository
public class BlogContentDaoImpl extends BaseDao<Article> implements BlogContentDao {
    @Override
    public Integer delete(Article article) {
        return getSqlSession().delete("Aritcle_delete", article);
    }
}
