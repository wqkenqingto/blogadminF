package com.blogadmin.blog.service;

import java.util.List;
import java.util.Map;

import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.blog.model.Template;

/**
 * 模板service
 * @author liqifan
 *
 */
public interface ITemplateService {

	ListJsonResult<Template> queryTemplatePage(Map<String, Object> paramMap);

	String uploadaTemplate(String userdir);

	int deleteTemplate(Long id);

	List<Template> getAll();

	String getCount();

	List<Template> getLimit(int begin, int end);

}
