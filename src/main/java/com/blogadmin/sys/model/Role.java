package com.blogadmin.sys.model;

import com.blogadmin.core.model.BaseEntity;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 角色名称 **/
    private String            roleName;

    /** 角色状态 **/
    private Integer           status;

    /** 创建时间 **/
    private java.util.Date    gmtCreated;

    /** 更新时间 **/
    private java.util.Date    gmtModified;

    /** 是否删除 **/
    private Integer           isDeleted;

    private String            ruleids;


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setGmtCreated(java.util.Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public java.util.Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtModified(java.util.Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public java.util.Date getGmtModified() {
        return gmtModified;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRuleids() {
        return ruleids;
    }

    public void setRuleids(String ruleids) {
        this.ruleids = ruleids;
    }
}
