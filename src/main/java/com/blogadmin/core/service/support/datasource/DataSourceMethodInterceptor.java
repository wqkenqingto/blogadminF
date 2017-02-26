package com.blogadmin.core.service.support.datasource;

import java.lang.reflect.Method;

import com.blogadmin.core.service.support.datasource.annotation.DataSource;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;

public class DataSourceMethodInterceptor implements MethodInterceptor {

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        DataSourceContextHolder.clearCustomerType();
        String dsStr = null;
        DataSource ds = null;
        Method method = methodInvocation.getMethod();
        ds = method.getAnnotation(DataSource.class);
        if (ds == null) {
            Class<?> clazz = methodInvocation.getThis().getClass();
            ds = clazz.getAnnotation(DataSource.class);
        }
        if (ds != null)
            dsStr = ds.value();
        if (!StringUtils.isEmpty(dsStr)) {
            DataSourceContextHolder.setCustomerType(dsStr);
        }
        Object result = methodInvocation.proceed();
        return result;
    }
}
