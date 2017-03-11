package com.blogadmin.test;

import com.blogadmin.blog.dao.BlogContentDao;
import com.blogadmin.blog.model.Aritcle;
import com.blogadmin.blog.service.BlogContentService;
import com.blogadmin.blog.service.impl.BlogContentServiceImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by wqkenqing on 2017/3/10.
 */
public class BlogContentTest {
    static Aritcle aritcle;

    //   @Autowired
//    private BlogContentService blogContentService;
    public static Aritcle productEntity() {
        aritcle = new Aritcle();
        aritcle.setName("ken");
        aritcle.setType("life");
        aritcle.setContent("dsjlskjsd");
        aritcle.setMemo("dsdsd");
        return aritcle;
    }

    static ApplicationContext applicationContext;

    public static void setup() {//spring的方式加载配置文件

        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-orm.xml");

    }

    public static void main(String[] args) throws IOException {

//        BlogContentService blogContentService= new BlogContentServiceImpl();
//        blogContentService.addBlog(productEntity());
//        System.out.println("测试通过");
//        Reader reader = Resources.getResourceAsReader("/Users/wqkenqing/Desktop/blogadmin/src/main/resources/mybatis/mybatis-config.xml")
//        //通过配置信息构建SqlSessionFactory
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        //通过sqlSessionFactory打开数据库会话
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        setup();
        ApplicationContext ctx=null;
        ctx=new ClassPathXmlApplicationContext("spring/applicationContext-orm.xml");
//        BlogContentDao blogContentDao= (BlogContentDao) ctx.getBean("sqlSession");
//        System.out.println(blogContentDao);
//        blogContentDao.saveEntity(aritcle);
//        blogContentDao.saveEntity(productEntity());
        SqlSession sqlSession=(SqlSession)ctx.getBean("sqlSession");
       int l= sqlSession.insert("Aritcle_insert",productEntity());

        System.out.println(l);
        System.out.println("保存成功");

    }
}
