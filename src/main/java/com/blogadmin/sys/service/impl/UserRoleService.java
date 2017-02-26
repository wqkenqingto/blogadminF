package com.blogadmin.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.blogadmin.sys.dao.IUserRoleDao;
import com.blogadmin.sys.model.UserRole;
import com.blogadmin.sys.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 类UserRoleService.java的实现描述：用户角色service
 * 
 * @author sz.gong 2016年4月20日 下午3:58:51
 */
@Component
public class UserRoleService implements IUserRoleService {

    @Autowired
    private IUserRoleDao userRoleDao;

    /**
     * 通过用户id查询角色
     * 
     * @param userId
     * @return
     */
    public List<UserRole> queryUserRoleByUid(Long userId) {
        Assert.notNull(userId);
        List<UserRole> list = userRoleDao.queryUserRoleByUid(userId);
        return list;
    }

    /**
     * 通过用户id，查询用户角色id集合
     * 
     * @param userId
     * @return
     */
    @Override
    public List<Long> queryRolesByUid(Long userId) {
        List<Long> rList = new ArrayList<Long>();
        List<UserRole> list = this.queryUserRoleByUid(userId);
        if (list != null) {
            for (UserRole userRole : list) {
                Long rid = userRole.getRoleId();
                rList.add(rid);
            }
        }
        return rList;
    }

    /**
     * 新增用户角色
     * 
     * @param userId
     * @param roleIds
     */
    @Override
    public void saveUserRole(Long userId, Long[] roleIds) {
        userRoleDao.removeByUid(userId);
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                UserRole ur = new UserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                ur.setIsDeleted(0);
                userRoleDao.saveUserRole(ur);
            }
        }
    }
    
    
    /**
     * 根据id删除用户角色
     * 
     * @param userId 用户Id
     */
	@Override
	public void deleteUserRole(List<Long> idList) {
		for (Long id : idList) {
			userRoleDao.removeByUid(id);
		}
	}
}
