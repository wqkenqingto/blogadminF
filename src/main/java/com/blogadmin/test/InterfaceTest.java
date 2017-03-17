package com.blogadmin.test;

import com.blogadmin.common.utils.TestUtil;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @className:InterfaceTest
 * @author:wqkenqing
 * @describe:用于测试接口
 * @date:2017/3/17
 **/
public class InterfaceTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        long before = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            TestUtil.InsertTest("BlogFile");
        }
        long after = System.currentTimeMillis();
        System.out.println(after - before);

    }
}
