package com.blogadmin.sys.service;

import java.util.List;

import com.blogadmin.sys.model.Operation;

/**
 * 
 * 用户于操作用户operateLog的接口。
 * @author wqkenqin 2016年7月22日
 *
 */
public interface IoperateService {
		   public List<Operation> queryOperation(String userName);
		   public Long saveEntity (Operation operation);
}
