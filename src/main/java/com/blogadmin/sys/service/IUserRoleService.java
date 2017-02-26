/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.blogadmin.sys.service;

import java.util.List;

import com.blogadmin.sys.model.UserRole;

/**
 * 类IUserRoleService.java的实现描述：接口
 * 
 * @author sz.gong 2016年4月20日 下午3:58:31
 */
public interface IUserRoleService {

    List<Long> queryRolesByUid(Long userId);

    List<UserRole> queryUserRoleByUid(Long userId);

    void saveUserRole(Long userId, Long[] roleIds);
    
    void deleteUserRole(List<Long> idList);
}
