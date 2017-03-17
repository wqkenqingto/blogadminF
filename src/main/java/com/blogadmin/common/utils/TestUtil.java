package com.blogadmin.common.utils;

import com.blogadmin.common.product.BeanProduct;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;
/**
*@className:TestUtil
*@author:wqkenqing
*@describe:接口测试的工具类
*@date:2017/3/17
**/
public class TestUtil {
    static ApplicationContext applicationContext;
    static ApplicationContext ctx = null;
    static SqlSession sqlSession;
    static String operate = "";
    static String bean = "";

    static {
        //spring的方式加载配置文件，并获取sqlsesion
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-orm.xml");
        ctx = new ClassPathXmlApplicationContext("spring/applicationContext-orm.xml");
        sqlSession = (SqlSession) ctx.getBean("sqlSession");
    }

    public static void InsertTest(String beanName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println(beanName);
        bean = beanName;
        String suffix = "_insert";
        operate = bean + suffix;
        System.out.println(operate);
        int l = sqlSession.insert(operate, BeanProduct.beanProduct(bean));
        System.out.println("-------添加成功--------");
    }

    public static void updateTest(String beanName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        bean = beanName;
        String suffix = "_update";
        operate = bean + suffix;
        int l = sqlSession.insert(operate, BeanProduct.beanProduct(bean));
        System.out.println("-------修改成功--------");
    }

    public static void selectTest(String beanName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        bean = beanName;
        String suffix = "_select";
        operate = bean + suffix;
        int l = sqlSession.insert(operate, BeanProduct.beanProduct(bean));
        System.out.println("-------提取成功--------");
    }

    public static void deleteTest(String beanName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        bean = beanName;
        String suffix = "_select";
        operate = bean + suffix;
        int l = sqlSession.insert(operate, BeanProduct.beanProduct(bean));
        System.out.println("-------删除成功--------");
    }

}
