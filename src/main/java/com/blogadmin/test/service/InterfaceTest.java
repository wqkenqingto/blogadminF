package com.blogadmin.test.service;

import com.blogadmin.blog.dao.BlogContentDao;
import com.blogadmin.blog.model.Article;
import com.blogadmin.blog.model.Othertidings;
import com.blogadmin.blog.service.OthertidingsService;
import com.blogadmin.common.product.BeanProduct;
import com.blogadmin.common.utils.SpringTestCase;
import com.blogadmin.sys.dao.MenuDao;
import com.blogadmin.sys.dao.impl.MenuDaoImpl;
import com.blogadmin.sys.model.Menu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @className:ServiceInterfaceTest
 * @author:wqkenqing
 * @describe:该类作为接口测试基类，可测试controller,service,dao任意层的接口。
 * @date:2017/3/21
 **/
public class InterfaceTest extends SpringTestCase {
    @Autowired
    OthertidingsService othertidingsService;

    @Autowired
    BlogContentDao blogContentDao;

     @Autowired
     MenuDaoImpl menuDao;

    @Test
    public void add() throws Exception {
        othertidingsService.add((Othertidings) BeanProduct.beanProduct("Othertidings"));
//        System.out.println("添加成功");
//        blogContentDao.saveEntity((Article) BeanProduct.beanProduct("Article"));
//        menuDao.saveEntity((Menu) BeanProduct.beanProduct("Menu"));
        System.out.println("添加成功");
    }

    @Test
    public void get() throws Exception {
        Othertidings othertidings = othertidingsService.getOthertidings(2l);
        System.out.println(othertidings.getContent());
    }

    @Test
    public void list() throws Exception {

    }

    @Test
    public void update() throws Exception {
        Othertidings othertidings = (Othertidings) BeanProduct.updateProduct("Othertidings", 2l);
        othertidingsService.updateOthertidings(othertidings);
        System.out.println("修改成功");
    }

    @Test
    public void remove() throws Exception {
    }

}