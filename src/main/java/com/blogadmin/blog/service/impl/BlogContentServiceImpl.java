package com.blogadmin.blog.service.impl;

import com.blogadmin.blog.dao.BlogContentDao;
import com.blogadmin.blog.model.Aritcle;
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

    public Long addBlog(Aritcle aritcle) {
        return blogContentDao.saveEntity(aritcle);
    }

    @Override
    public Aritcle getBlog(Long id) {
        return blogContentDao.get(id);
    }

    @Override
    public List<Aritcle> ListBlog() {
        return blogContentDao.getAll();
    }

    @Override
    public Long updateBlog(Aritcle aritcle) {
        return  blogContentDao.updateEntity(aritcle);
    }

    @Override
    public int removeBlog(Long id) {
        return blogContentDao.logicRemove( id);
    }
}
