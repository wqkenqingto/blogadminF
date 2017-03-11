package com.blogadmin.blog.model;

import com.blogadmin.core.model.BaseEntity;

/**
 * 个体模板关联表
 * @author liqifan
 *
 */
public class UnitTemplate extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private String ucode;//个体id
	private String tcode;//模板id
	
	public String getUcode() {
		return ucode;
	}
	public void setUcode(String ucode) {
		this.ucode = ucode;
	}
	public String getTcode() {
		return tcode;
	}
	public void setTcode(String tcode) {
		this.tcode = tcode;
	}

}
