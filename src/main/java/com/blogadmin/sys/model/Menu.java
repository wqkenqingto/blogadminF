package com.blogadmin.sys.model;

import com.blogadmin.core.model.BaseEntity;

/**
*@className:Menu
*@author:wqkenqing
*@describe:菜单bean
*@date:2017/3/10
**/
public class Menu extends BaseEntity{

    private String name;
    private String type;
    private String memo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
