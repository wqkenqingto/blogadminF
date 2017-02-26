package com.blogadmin.sys.dao;

import java.util.List;
import java.util.Map;

import com.blogadmin.sys.model.RoleRule;

/**
 * 类IRoleRuleDao.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2016年4月20日 下午4:05:48
 */
public interface IRoleRuleDao {

    List<RoleRule> queryRoleRule(Map<String, Object> paramMap);

    Integer deleteRoleRule(Long roleId);

    void batchSaveRoleRule(List<RoleRule> list);
}
