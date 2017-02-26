package com.blogadmin.sys.service.impl;

import java.util.List;

import com.blogadmin.sys.dao.IoperateDao;
import com.blogadmin.sys.model.Operation;
import com.blogadmin.sys.service.IoperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperateService implements IoperateService {
	@Autowired
	private IoperateDao operateDao;

    @Override
	public List<Operation> queryOperation(String userName) {

        return operateDao.queryOperation(userName);
    }

    @Override
    public Long saveEntity(Operation operation) {
        return operateDao.saveEntity(operation);
    }

}
