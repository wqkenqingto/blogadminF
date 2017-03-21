package com.blogadmin.sys.service;

import com.blogadmin.blog.model.Article;
import com.blogadmin.sys.model.Menu;

import java.util.List;

/**
 * Created by wqkenqing on 2017/3/21.
 */
public interface MenuService {
     Long addMenu(Menu menu);

     Menu getMenu(Long id);

     List<Menu> ListMenu();

     Long updateBlog(Menu menu);

     int removeMenu(Long id);
}
