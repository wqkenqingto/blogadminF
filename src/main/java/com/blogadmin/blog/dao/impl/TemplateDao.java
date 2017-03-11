package com.blogadmin.blog.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.blogadmin.core.dao.BaseDao;
import com.blogadmin.blog.dao.ITemplateDao;
import com.blogadmin.blog.model.Template;

@Repository
public class TemplateDao extends BaseDao<Template> implements ITemplateDao {

	@Override
	public String getCodePlace() {
		Map<String,Object> map = this.getSqlSession().selectOne("Template_get_codePlace", null);
		if(map == null){
			return null;
		}else{
			return String.valueOf(map.get("codePlace"));
		}
	}

	@Override
	public List<Template> getLimit(int begin, int end) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limitBegin", begin);
		paramMap.put("limitEnd", end);
		return this.getSqlSession().selectList("Template_get_limit", paramMap);
	}

	@Override
	public String getCount() {
		Map<String,Object> map = this.getSqlSession().selectOne("Template_count", null);
		if(map == null){
			return null;
		}else{
			return String.valueOf(map.get("count"));
		}
	}

}
