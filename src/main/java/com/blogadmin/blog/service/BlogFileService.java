package com.blogadmin.blog.service;


import com.blogadmin.blog.model.BlogFile;

import java.util.List;

/**
 * @className:BlogFileService
 * @author:wqkenqing
 * @describe:blog中相关文件的处理接口
 * @date:2017/3/12
 **/
public interface BlogFileService {
    public int uploadBlogFile();

    public int updateBlogFile();

    public int removeBlogFile();

    public BlogFile getBlogFile(Long id);

    public List<BlogFile> ListBlogFile();
}
