package com.blogadmin.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.blogadmin.sys.dao.IRoleRuleDao;
import com.blogadmin.sys.model.RoleRule;
import com.blogadmin.sys.service.IRoleRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 类RoleRuleService.java的实现描述：角色模块service
 * 
 * @author sz.gong 2016年4月20日 下午4:08:51
 */
@Component
public class RoleRuleService implements IRoleRuleService {

    @Autowired
    private IRoleRuleDao roleRuleDao;

    /**
     * 通过角色id查询list
     * 
     * @param roleIds
     * @return
     */
    public List<RoleRule> queryRoleRuleByRid(List<Long> roleList) {
        Assert.notNull(roleList);
        if (roleList.size() > 0) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("roleList", roleList);
            return roleRuleDao.queryRoleRule(paramMap);
        } else {
            return new ArrayList<RoleRule>();
        }
    }

    /**
     * 通过角色id集合，查询操作id集合
     * 
     * @param roleIds
     * @return
     */
    @Override
    public List<Long> queryRulesByRid(List<Long> roleList) {
        List<Long> rList = new ArrayList<Long>();
        Set<Long> rids = new HashSet<Long>();
        List<RoleRule> list = this.queryRoleRuleByRid(roleList);
        if (list != null) {
            for (RoleRule rr : list) {
                rids.add(rr.getRuleId());
            }
        }
        Iterator<Long> it = rids.iterator();
        while (it.hasNext()) {
            Long rid = it.next();
            rList.add(rid);
        }
        return rList;
    }

    /**
     * 删除角色权限
     * 
     * @param role
     * @return
     */
    public Integer deleteRoleRule(Long roleId) {
        return roleRuleDao.deleteRoleRule(roleId);
    }

    /**
     * 批量新增
     * 
     * @param roleId
     * @param ruleIds
     */
    public void saveRoleRule(Long roleId, Long[] ruleIds) {
        Assert.notNull(roleId);
        Assert.notNull(ruleIds);
        this.deleteRoleRule(roleId);
        List<RoleRule> rrList = new ArrayList<RoleRule>();
        for (int i = 0; i < ruleIds.length; i++) {
            RoleRule rr = new RoleRule();
            rr.setRoleId(roleId);
            rr.setRuleId(ruleIds[i]);
            rrList.add(rr);
        }
        roleRuleDao.batchSaveRoleRule(rrList);
    }
}
