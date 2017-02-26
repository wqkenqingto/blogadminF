package com.blogadmin.sys.dao;

import java.util.List;

import com.blogadmin.sys.model.Operation;

public interface IoperateDao {
			
	public List<Operation> queryOperation(String userName);
	public Long saveEntity (Operation operation);
	
}
