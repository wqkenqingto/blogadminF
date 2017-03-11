package com.blogadmin.blog.service;

import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.blog.model.Unit;

import java.util.List;
import java.util.Map;

/**
 * Unitservice
 * @author wangkuiqing
 *
 */
public interface IUnitService {

	ListJsonResult<Unit> queryUnitPage(Map<String, Object> paramMap);

    Unit getUnitU(String ucode) ;
	List<String>getUnames();
	int deleteUnit(Long id);

	List<Unit> queryFirst();

	List<Unit> querySecond(Map<String, Object> paramMap);

	Unit getUnit(Long uid);

	String saveUnit(Unit unit);

	String saveUnits(List<Unit>units);

	List<Unit> getMuUnit();
}
