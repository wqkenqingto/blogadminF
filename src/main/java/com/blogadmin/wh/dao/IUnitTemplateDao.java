package com.blogadmin.wh.dao;

import java.util.List;

import com.blogadmin.wh.model.UnitTemplate;

/**
 * 个体模板关联dao
 * @author liqifan
 */
public interface IUnitTemplateDao {

	Long saveBatchEntity(List<UnitTemplate> saveUtList);

}
