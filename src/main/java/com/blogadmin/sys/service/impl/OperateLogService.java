package com.blogadmin.sys.service.impl;

import java.util.Map;

import com.blogadmin.sys.dao.IOperateLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.sys.model.OperateLog;
import com.blogadmin.sys.service.IOperateLogServie;

@Component
public class OperateLogService implements IOperateLogServie {

    @Autowired
    private IOperateLogDao operateLogDao;

    @Override
    public ListJsonResult<OperateLog> queryPage(Map<String, Object> paramMap) {
        return operateLogDao.queryDTPage(paramMap);
    }

    @Override
    public void saveOperateLog(OperateLog log) {
        operateLogDao.saveEntity(log);
    }
}
