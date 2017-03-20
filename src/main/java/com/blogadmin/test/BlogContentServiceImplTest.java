package com.blogadmin.test;

import com.blogadmin.blog.model.Article;
import com.blogadmin.blog.service.impl.BlogContentServiceImpl;
import com.blogadmin.common.product.BeanProduct;
import com.blogadmin.common.utils.SpringTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by wqkenqing on 2017/3/20.
 */
public class BlogContentServiceImplTest extends SpringTestCase {
    @Autowired
    private BlogContentServiceImpl blogContentService;

    @Test
    public void sayHelloTest() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        blogContentService.addBlog((Article) BeanProduct.beanProduct("Article"));
        System.out.println("添加成功");
    }
    @Test
    public void deleteArticleTest() {
        blogContentService.removeBlog(3l);
        System.out.println("删除成功");
    }

}