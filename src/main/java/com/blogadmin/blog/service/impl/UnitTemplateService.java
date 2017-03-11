package com.blogadmin.blog.service.impl;

import java.util.List;

import com.blogadmin.blog.dao.IUnitTemplateDao;
import com.blogadmin.blog.model.UnitTemplate;
import com.blogadmin.blog.service.IUnitTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitTemplateService implements IUnitTemplateService {
	
	@Autowired
    private IUnitTemplateDao unitTemplateDao;

	@Override
	public String saveUnitTemplate(List<UnitTemplate> saveUtList) {
		unitTemplateDao.saveBatchEntity(saveUtList);
		return null;
	}

}
