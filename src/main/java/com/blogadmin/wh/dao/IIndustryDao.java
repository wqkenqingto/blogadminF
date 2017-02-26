package com.blogadmin.wh.dao;

import java.io.Serializable;
import java.util.List;

import com.blogadmin.wh.model.Industry;

/**
 * 行业dao
 * @author liqifan
 */
public interface IIndustryDao {

	Industry get(Serializable id);
	
	List<Industry> getAll();
	
	int logicRemove(Serializable id);
	
	Long saveEntity(Industry industry);
	
	Long updateEntity(Industry industry);
}
