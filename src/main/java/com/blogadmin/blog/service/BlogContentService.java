package com.blogadmin.blog.service;

import com.blogadmin.blog.model.Article;

import java.util.List;

/**
 * @className:BlogContentService
 * @author:wqkenqing
 * @describe:对blog内容进行操作的接口
 * @date:2017/3/10
 **/
public interface BlogContentService {
    public Long addBlog(Article article);

    public Article getBlog(Long id);

    public List<Article> ListBlog();

    public Long updateBlog(Article article);

    public int removeBlog(Long id);
}
