package com.blogadmin.blog.dao;

import com.blogadmin.blog.model.Article;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wqkenqing on 2017/3/10.
 */
public interface BlogContentDao {
    Article get(Serializable id);

    int logicRemove(Serializable id);

    List<Article> getAll();

    Long updateEntity(Article article);

    Long saveEntity(Article article);

    Integer delete(Article article);
}
