package com.blogadmin.blog.dao;

import com.blogadmin.blog.model.BlogFile;

import java.io.Serializable;

import java.util.List;

/**
 * Created by wqkenqing on 2017/3/10.
 */
public interface BlogFileDao {

    Long  logicRomove(Serializable id);

    Long updateEntity(BlogFile blogFile);

    Long saveEntity(BlogFile blogFile);

    List<BlogFile> getAll();

    Integer delete(BlogFile blogFile);
}

