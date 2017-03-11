package com.blogadmin.blog.service;

import com.blogadmin.blog.model.Aritcle;

import java.util.List;

/**
*@className:BlogContentService
*@author:wqkenqing
*@describe:对blog内容进行操作的接口
*@date:2017/3/10
**/
public interface BlogContentService {
    public Long addBlog(Aritcle aritcle);
    public Aritcle getBlog(Long id);
    public List<Aritcle> ListBlog();
    public Long updateBlog(Aritcle aritcle);
    public int removeBlog(Long id);
}
