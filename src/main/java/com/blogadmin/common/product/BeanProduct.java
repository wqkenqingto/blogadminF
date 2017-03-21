package com.blogadmin.common.product;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @className:BeanProduct
 * @author:wqkenqing
 * @describe:bean的生成类，主要用于生成测试数据
 * @date:2017/3/17
 **/
public class BeanProduct {
    //bean的自动注入
    static String beanprefix = "com.blogadmin.blog.model.";
    static String beanprefix1 = "com.blogadmin.sys.model.";
    static String beanName = "";

    public static Object beanProduct(String beanName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        beanName = beanName;
        if (beanName.equals("Menu")){
            beanprefix = beanprefix1;
        }
        Class clss = Class.forName(beanprefix + beanName);
        Field[] fields = clss.getDeclaredFields();//获取所有属性
//        Field[] sfields = clss.getSuperclass().getDeclaredFields();
        Object o = clss.newInstance();
        Field.setAccessible(fields, true);
        System.out.println(fields.length);

        for (Field field : fields) {
            field.set(o, "test" + (int) (Math.random() * 100));
        }

        return o;
    }

    public static Object updateProduct(String beanName, Long id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        beanName = beanName;
        Class clss = Class.forName(beanprefix + beanName);
        Field[] fields = clss.getDeclaredFields();//获取所有属性
//        Field[] sfields = clss.getSuperclass().getDeclaredFields();

        Object o = clss.newInstance();
        Field.setAccessible(fields, true);
        System.out.println(fields.length);
        for (Field field : fields) {
            field.set(o, "test" + (int) (Math.random() * 100));
        }
        Field f = clss.getSuperclass().getDeclaredField("id");
        f.setAccessible(true);
        f.set(o, id);
        System.out.println("生成成功");
        return o;
    }
}
