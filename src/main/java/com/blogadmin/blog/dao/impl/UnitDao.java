package com.blogadmin.blog.dao.impl;

import com.blogadmin.blog.dao.IUnitDao;
import com.blogadmin.core.dao.BaseDao;
import com.blogadmin.blog.model.Unit;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public class UnitDao extends BaseDao<Unit> implements IUnitDao {
	
    @Override
    public Unit getU(String ucode) {
        return  this.getSqlSession().selectOne("Unit_getU",ucode);
    }
    
    @Override
    public List<Unit> queryFirst() {
        return  this.getSqlSession().selectList("Unit_query_mindustry");
    }
    
    @Override
    public List<Unit> querySecond(Map<String,Object> paramMap) {
        return  this.getSqlSession().selectList("Unit_query_sindustry",paramMap);
    }

    @Override
    public List<String> getUnames() {
        return this.getSqlSession().selectList("Unit_query_unames");
    }

	@Override
	public List<Unit> getMuUnit() {
		return this.getSqlSession().selectList("Unit_query_munit");
	}

}
