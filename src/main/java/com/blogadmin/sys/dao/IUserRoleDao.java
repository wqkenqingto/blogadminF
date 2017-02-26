package com.blogadmin.sys.dao;

import java.util.List;

import com.blogadmin.sys.model.UserRole;

/**
 * 类IUserRoleDao.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2016年4月20日 下午3:57:09
 */
public interface IUserRoleDao {

    List<UserRole> queryUserRoleByUid(Long userId);

    void removeByUid(Long userId);

    void saveUserRole(UserRole ur);
}
