package com.blogadmin.sys.model;

import com.blogadmin.core.model.BaseEntity;

public class RoleRule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 角色id **/
    private Long              roleId;

    /** 操作id **/
    private Long              ruleId;

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getRuleId() {
        return ruleId;
    }

}
