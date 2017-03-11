package com.blogadmin.blog.model;

import com.blogadmin.core.model.BaseEntity;

/**
 * Created by wqkenqin on 2016/11/8.
 * Description:个体的实体类
 */
public class Unit extends BaseEntity {
    private String ucode;
    private String uname;
    private String cid;
    private String mindustry;
    private String sindustry;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSindustry() {
        return sindustry;
    }

    public void setSindustry(String sindustry) {
        this.sindustry = sindustry;
    }

    public String getMindustry() {
        return mindustry;
    }

    public void setMindustry(String mindustry) {
        this.mindustry = mindustry;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

}
