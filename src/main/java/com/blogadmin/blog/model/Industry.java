package com.blogadmin.blog.model;

import com.blogadmin.core.model.BaseEntity;

/**
 * 行业类
 *
 * @author liqifan
 */
public class Industry extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String code;//行业编码
    private String name;//行业名称
    private String hbname;//hbase对应表名
    private String count;//数据量

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHbname() {
        return hbname;
    }

    public void setHbname(String hbname) {
        this.hbname = hbname;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
