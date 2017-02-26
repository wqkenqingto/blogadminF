package com.blogadmin.wh.model;

import com.blogadmin.core.model.BaseEntity;

/**
 * 模板类
 * @author liqifan
 */
public class Template extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String code;//模板编码
	private String content;//模板内容

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
