/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.blogadmin.sys.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blogadmin.sys.dao.IRoleRuleDao;
import com.blogadmin.sys.model.RoleRule;
import org.springframework.stereotype.Repository;

import com.blogadmin.core.dao.BaseDao;

/**
 * 类RuleRoleDao.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2016年4月20日 下午4:06:03
 */
@Repository
public class RoleRuleDao extends BaseDao<RoleRule> implements IRoleRuleDao {

    @Override
    public List<RoleRule> queryRoleRule(Map<String, Object> paramMap) {
        return this.queryList(paramMap);
    }

    @Override
    public Integer deleteRoleRule(Long roleId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("roleId", roleId);
        return this.getSqlSession().delete("RoleRule_delete_role", paramMap);
    }

    @Override
    public void batchSaveRoleRule(List<RoleRule> list) {
        this.getSqlSession().insert("RoleRule_insert_batch", list);
    }

}
