package com.blogadmin.test.service;

import com.blogadmin.blog.model.Article;
import com.blogadmin.blog.service.impl.BlogContentServiceImpl;
import com.blogadmin.common.product.BeanProduct;
import com.blogadmin.common.utils.SpringTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

/**
 * @className:BlogContentServiceImplTest
 * @author:wqkenqing
 * @describe:BlogContentService的测试类，并做为示例
 * @date:2017/3/20
 **/
public class BlogContentServiceImplTest extends SpringTestCase {
    @Autowired
    private BlogContentServiceImpl blogContentService;

    @Test
    public void saveArticleTest() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        blogContentService.addBlog((Article) BeanProduct.beanProduct("Article"));
        System.out.println("添加成功");
    }

    @Test
    public void deleteArticleTest() {
        blogContentService.removeBlog(3l);
        System.out.println("删除成功");
    }

    @Test
    public void selectArticle() {
        Article article = blogContentService.getBlog(3l);
        System.out.println(article.getContent());
        System.out.println("查询完成-------");
    }

    @Test
    public void updateArticleTest() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {
        blogContentService.updateBlog((Article) BeanProduct.updateProduct("Article", 3l));
        System.out.println("修改成功");
    }

}