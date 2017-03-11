package com.blogadmin.blog.service;

import java.util.List;

import com.blogadmin.blog.model.Industry;

/**
 * 行业service
 * @author liqifan
 *
 */
public interface IIndustryService {

	List<Industry> getAll();

	String saveIndustry(Industry industry);

	String deleteIndustry(String[] idList);

	Industry get(Long id);

}
