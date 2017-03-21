package com.blogadmin.blog.service.impl;

import com.blogadmin.blog.dao.BlogContentDao;
import com.blogadmin.blog.model.Article;
import com.blogadmin.blog.service.BlogContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wqkenqing on 2017/3/10.
 */
@Component
public class BlogContentServiceImpl implements BlogContentService {
    @Autowired
    private BlogContentDao blogContentDao;

    public Long addBlog(Article article) {
        return blogContentDao.saveEntity(article);
    }

    @Override
    public Article getBlog(Long id) {
        return blogContentDao.get(id);
    }

    @Override
    public List<Article> ListBlog() {
        return blogContentDao.getAll();
    }

    @Override
    public Long updateBlog(Article article) {
        return blogContentDao.updateEntity(article);
    }

    @Override
    public int removeBlog(Long id) {
        return blogContentDao.logicRemove(id);
    }
}
