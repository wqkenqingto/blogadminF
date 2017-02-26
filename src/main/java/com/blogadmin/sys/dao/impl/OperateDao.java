package com.blogadmin.sys.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blogadmin.sys.dao.IoperateDao;
import com.blogadmin.sys.model.Operation;
import org.springframework.stereotype.Repository;

import com.blogadmin.core.dao.BaseDao;

/**
 * @author wqkenqin
 *
 */

@Repository
public class OperateDao  extends BaseDao<Operation> implements IoperateDao {
	public List<Operation> queryOperation(String userName) {
		List<Operation> olist=new ArrayList<Operation>();
		 Map<String, String> paramMap = new HashMap<String, String>();
		 paramMap.put("operator", userName);
		 olist=this.query("Operation_getTimeline", paramMap);
		 return olist;
	}
	public Long saveEntity (Operation operation) {
			return this.save(operation);
	}


}
