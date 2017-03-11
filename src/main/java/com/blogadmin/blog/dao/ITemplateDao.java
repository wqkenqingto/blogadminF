package com.blogadmin.blog.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.blogadmin.blog.model.Template;
import com.blogadmin.core.utils.response.ListJsonResult;

/**
 * 模板dao
 * @author liqifan
 */
public interface ITemplateDao {

	Template get(Serializable id);
	
	List<Template> getAll();
	
	int logicRemove(Serializable id);
	
	Long saveBatchEntity(List<Template> tList);

	ListJsonResult<Template> queryDTPage(Map<String, Object> paramMap);

	String getCodePlace();
	
	List<Template> getLimit(int begin, int end);
	
	String getCount();
}
