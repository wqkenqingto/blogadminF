package com.blogadmin.blog.dao;

import java.util.List;

import com.blogadmin.blog.model.UnitTemplate;

/**
 * 个体模板关联dao
 * @author liqifan
 */
public interface IUnitTemplateDao {

	Long saveBatchEntity(List<UnitTemplate> saveUtList);

}
