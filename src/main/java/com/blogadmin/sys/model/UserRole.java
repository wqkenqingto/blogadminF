package com.blogadmin.sys.model;

import com.blogadmin.core.model.BaseEntity;

public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户id **/
    private Long              userId;

    /** 角色id **/
    private Long              roleId;

    /** 创建时间 **/
    private java.util.Date    gmtCreated;

    /** 更新时间 **/
    private java.util.Date    gmtModified;

    /** 是否删除 **/
    private Integer           isDeleted;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
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
}
