/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.blogadmin.sys.service;

import java.util.Map;

import com.blogadmin.sys.model.OperateLog;
import com.blogadmin.core.utils.response.ListJsonResult;

/**
 * 类IOperateLogServie.java的实现描述：接口
 * 
 * @author sz.gong 2016年4月21日 下午3:08:18
 */
public interface IOperateLogServie {

    ListJsonResult<OperateLog> queryPage(Map<String, Object> paramMap);

    void saveOperateLog(OperateLog log);

}
