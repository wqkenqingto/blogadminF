/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.blogadmin.sys.service;

import java.util.List;
import java.util.Map;

import com.blogadmin.sys.model.Role;
import com.blogadmin.core.utils.response.ListJsonResult;

/**
 * 类IRoleService.java的实现描述：接口
 * 
 * @author sz.gong 2016年4月20日 下午3:42:01
 */
public interface IRoleService {

    ListJsonResult<Role> queryRolePage(Map<String, Object> paramMap);

    List<Role> queryEnableRoleList();

    boolean queryExistRole(String roleName, Long id);

    Role queryRoleById(Long id);

    void updateRole(Role role);

    Long saveRole(Role role);

    int deleteRole(Long id);
}
