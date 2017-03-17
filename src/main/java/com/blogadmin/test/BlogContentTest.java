package com.blogadmin.test;

import com.blogadmin.blog.model.Article;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by wqkenqing on 2017/3/10.
 */
public class BlogContentTest {

    static Article article;

    //   @Autowired
//    private BlogContentService blogContentService;
    public static Article productEntity() {
        article = new Article();
        article.setName("ken");
        article.setType("life");
        article.setContent("dsjlskjsd");
        article.setMemo("dsdsd");
        return article;
    }

    public static Article updateEntity() {
        article = new Article();
        article.setId(1L);
        Date date = new Date();
        System.out.println(date);
        article.setGmtCreated(date);
        article.setName("ken_u");
        article.setType("life");
        article.setContent("这里是修改内容");
        article.setMemo("updating");
        return article;
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
        ApplicationContext ctx = null;
        ctx = new ClassPathXmlApplicationContext("spring/applicationContext-orm.xml");
//        BlogContentDao blogContentDao= (BlogContentDao) ctx.getBean("sqlSession");
//        System.out.println(blogContentDao);
//        blogContentDao.saveEntity(article);
//        blogContentDao.saveEntity(productEntity());
        SqlSession sqlSession = (SqlSession) ctx.getBean("sqlSession");
//        int l= sqlSession.insert("Article_insert",updateEntity());
//       sqlSession.update("Aritcle_update",updateEntity());
//        System.out.println(l);
//        sqlSession.update("Article_logicDelete",updateEntity());
//        sqlSession.delete("Article_delete",updateEntity());
        List<Article> list = sqlSession.selectList("Article_list");
        Article article = list.get(4);
        System.out.println(article.getName());
        System.out.println(list.size());
        System.out.println("操作成功");

    }
}
