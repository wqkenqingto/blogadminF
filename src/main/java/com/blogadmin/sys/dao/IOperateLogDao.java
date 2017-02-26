/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.blogadmin.sys.dao;

import java.util.Map;

import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.sys.model.OperateLog;

/**
 * 类IOperateLogDao.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2016年4月21日 下午3:08:38
 */
public interface IOperateLogDao {

    ListJsonResult<OperateLog> queryDTPage(Map<String, Object> paramMap);

    Long saveEntity(OperateLog log);
}
