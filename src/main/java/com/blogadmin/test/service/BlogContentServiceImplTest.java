package com.blogadmin.test.service;

import com.blogadmin.blog.model.Article;
import com.blogadmin.blog.service.BlogContentService;
import com.blogadmin.common.product.BeanProduct;
import com.blogadmin.common.utils.SpringTestCase;
import com.blogadmin.sys.dao.MenuDao;
import com.blogadmin.sys.model.Menu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by wqkenqing on 2017/3/21.
 */
public class BlogContentServiceImplTest extends SpringTestCase {
//   @Autowired
//    BlogContentService blogContentService;
   @Autowired
    MenuDao menuDao;
    @Test
    public void addBlog() throws Exception {

//        blogContentService.addBlog((Article) BeanProduct.beanProduct("Article"));
        menuDao.saveEntity((Menu) BeanProduct.beanProduct("Menu"));
        System.out.println("添加成功");
    }

    @Test
    public void getBlog() throws Exception {
    }

    @Test
    public void listBlog() throws Exception {
    }

    @Test
    public void updateBlog() throws Exception {
    }

    @Test
    public void removeBlog() throws Exception {
    }

}