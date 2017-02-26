package com.blogadmin.sys.model;

import com.blogadmin.core.model.BaseEntity;

/**
 * @author wangkuiqing
 * @date 创建时间：2016年8月5日 下午2:13:44
 * @version 1.0
 * @parameter
 * @since * @return
 * @description:
 */
public class PlatformRedirect extends BaseEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	// 平台code
	private String code;
	// 平台名称
	private String name;
	// 平台描述
	private String description;
	// 图片地址
	private String imgDir;
	// 图片链接
	private String url;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgDir() {
		return imgDir;
	}
	public void setImgDir(String imgDir) {
		this.imgDir = imgDir;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
