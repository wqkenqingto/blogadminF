package com.blogadmin.blog.service;


import com.blogadmin.blog.model.BlogFile;

import java.util.List;

public interface BlogFileService {
    public int uploadBlogFile();
    public int updateBlogFile();
    public int removeBlogFile();
    public BlogFile getBlogFile();
    public List<BlogFile> ListBlogFile();
}
