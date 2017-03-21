package com.blogadmin.test.service;

import com.blogadmin.blog.dao.BlogContentDao;
import com.blogadmin.blog.model.Article;
import com.blogadmin.blog.model.Othertidings;
import com.blogadmin.blog.service.OthertidingsService;
import com.blogadmin.common.product.BeanProduct;
import com.blogadmin.common.utils.SpringTestCase;
import com.blogadmin.core.model.BaseEntity;
import com.blogadmin.sys.dao.MenuDao;
import com.blogadmin.sys.dao.impl.MenuDaoImpl;
import com.blogadmin.sys.model.Menu;
import com.blogadmin.sys.service.MenuService;
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
    MenuService menuService;

    @Test
    public void add() throws Exception {
//        menuService.addMenu((Menu) BeanProduct.beanProduct("Menu"));
        menuService.removeMenu(1l);
        System.out.println("测试成功");
    }

    @Test
    public void get() throws Exception {
//        Menu menu=menuDao.get(1l);
//        System.out.println(menu.getName());
//        System.out.println("查询成功");
    }

    @Test
    public void list() throws Exception {

    }

    @Test
    public void update() throws Exception {
//        menuDao.update((Menu) BeanProduct.updateProduct("Menu", 1l));
//        System.out.println("修改成功");
    }

    @Test
    public void remove() throws Exception {
//        menuDao.logicRemove(2l);
//        System.out.println("删除成功");
    }

}