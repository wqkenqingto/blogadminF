package com.blogadmin.sys.model;

import com.blogadmin.core.model.BaseEntity;

/**
 * 类OperateLog.java的实现描述：操作日志
 * 
 * @author sz.gong 2016年3月13日 下午5:42:37
 */
public class OperateLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 操作者 **/
    private String            operator;

    /** 标题 **/
    private String            title;

    /** 类型 **/
    private Integer           type;

    /** 操作内容 **/
    private String            content;

    /** ip **/
    private String            ip;

    /** 所在城市 **/
    private String            ipCity;

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIpCity(String ipCity) {
        this.ipCity = ipCity;
    }

    public String getIpCity() {
        return ipCity;
    }

}
