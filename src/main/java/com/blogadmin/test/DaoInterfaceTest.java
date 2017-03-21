package com.blogadmin.test;

import com.blogadmin.blog.model.Article;
import com.blogadmin.blog.model.BlogFile;
import com.blogadmin.blog.model.Message;
import com.blogadmin.common.utils.TestUtil;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import sun.jvm.hotspot.debugger.cdbg.TemplateType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @className:InterfaceTest
 * @author:wqkenqing
 * @describe:用于测试接口
 * @date:2017/3/17
 **/
public class DaoInterfaceTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        long before = System.currentTimeMillis();
        /**测试工具类使用示例
         TestUtil.insertTest("Article");
         TestUtil.updateTest("Article",2l);
         TestUtil.selectTest("Article",2l);
         TestUtil.deleteTest("Article",2l);
         **/
        TestUtil.updateTest("Article",3l);
        long after = System.currentTimeMillis();
        System.out.println(after - before);

    }
}
