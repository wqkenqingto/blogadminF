package com.blogadmin.sys.dao;

import java.util.List;
import java.util.Map;

import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.sys.model.Role;

/**
 * 类IRoleDao.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2016年4月20日 下午3:40:59
 */
public interface IRoleDao {

    ListJsonResult<Role> queryDTPage(Map<String, Object> paramMap);

    List<Role> queryList(Map<String, Object> paramMap);

    Role queryRoleById(Long id);

    Long updateEntity(Role role);

    Long saveEntity(Role role);

    int logicDelete(Long id);

}
