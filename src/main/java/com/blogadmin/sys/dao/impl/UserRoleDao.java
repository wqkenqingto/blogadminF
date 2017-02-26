package com.blogadmin.sys.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blogadmin.sys.dao.IUserRoleDao;
import org.springframework.stereotype.Repository;

import com.blogadmin.core.dao.BaseDao;
import com.blogadmin.sys.model.UserRole;

/**
 * 类UserRoleDao.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2016年4月20日 下午3:57:37
 */
@Repository
public class UserRoleDao extends BaseDao<UserRole> implements IUserRoleDao {

    @Override
    public List<UserRole> queryUserRoleByUid(Long userId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return this.queryList(paramMap);
    }

    @Override
    public void removeByUid(Long userId) {
        UserRole ur = new UserRole();
        ur.setUserId(userId);
        this.remove(ur);
    }

    @Override
    public void saveUserRole(UserRole ur) {
        this.save(ur);
    }

}
