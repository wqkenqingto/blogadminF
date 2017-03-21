package com.blogadmin.blog.service.impl;

import com.blogadmin.blog.dao.BlogFileDao;
import com.blogadmin.blog.model.BlogFile;
import com.blogadmin.blog.service.BlogFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wqkenqing on 2017/3/10.
 */
@Component
public class BlogFileServiceImpl implements BlogFileService {
    @Autowired
    BlogFileDao blogFileDao;

    @Override
    public int uploadBlogFile() {
        return 0;
    }

    @Override
    public int updateBlogFile() {
        return 0;
    }

    @Override
    public int removeBlogFile() {
        return 0;
    }

    @Override
    public BlogFile getBlogFile(Long id) {
        return blogFileDao.get(id);
    }

    @Override
    public List<BlogFile> ListBlogFile() {
        return null;
    }
}
