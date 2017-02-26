package com.blogadmin.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blogadmin.common.constant.DictConstant;
import com.blogadmin.sys.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.sys.dao.IRoleDao;
import com.blogadmin.sys.service.IRoleService;

/**
 * 类RoleService.java的实现描述：角色Service
 * 
 * @author sz.gong 2016年4月21日 上午10:37:23
 */
@Component
public class RoleService implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    public ListJsonResult<Role> queryRolePage(Map<String, Object> paramMap) {
        return roleDao.queryDTPage(paramMap);
    }

    /**
     * 查询启用的角色
     * 
     * @return
     */
    public List<Role> queryEnableRoleList() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", DictConstant.ROLE_ENABLE);
        return roleDao.queryList(paramMap);
    }

    /**
     * 角色是否存在
     * 
     * @param roleName
     * @param id
     * @return
     */
    public boolean queryExistRole(String roleName, Long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("rName", roleName);
        List<Role> list = roleDao.queryList(paramMap);
        if (list != null && list.size() > 1) {
            return true;
        }
        if (list == null || list.size() == 0) {
            return false;
        }
        if (id == null && list.size() > 0) {
            return true;
        }
        Role role = list.get(0);
        if (id != null && role.getId().intValue() == id.intValue()) {
            return false;
        }
        return true;
    }

    @Override
    public Role queryRoleById(Long id) {
        return roleDao.queryRoleById(id);
    }

    @Override
    public void updateRole(Role role) {
        roleDao.updateEntity(role);
    }

    @Override
    public Long saveRole(Role role) {
        return roleDao.saveEntity(role);
    }

    @Override
    public int deleteRole(Long id) {
        return roleDao.logicDelete(id);
    }

}
