package com.blogadmin.wh.dao;

import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.wh.model.Unit;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Unitdao
 * @author wangkuiqing
 */
public interface IUnitDao {

	Unit get(Serializable id);

	Unit getU(String ucode);

	List<Unit> getAll();
	
	int logicRemove(Serializable id);
	
	Long saveBatchEntity(List<Unit> tList);

	ListJsonResult<Unit> queryDTPage(Map<String, Object> paramMap);

	List<Unit> queryFirst();

	List<Unit> querySecond(Map<String, Object> paramMap);

	Long updateEntity(Unit unit);

	Long saveEntity(Unit unit);
	List<String>getUnames();

	List<Unit> getMuUnit();
}
