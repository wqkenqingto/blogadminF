package com.blogadmin.blog.dao;

import java.io.Serializable;
import java.util.List;

import com.blogadmin.blog.model.Industry;

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
