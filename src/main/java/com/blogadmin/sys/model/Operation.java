package com.blogadmin.sys.model;

import com.blogadmin.core.model.BaseEntity;

/**
 * 用户的操作日志，用于记录日常操作；
 * 
 * @author wqkenqin 2016年7月22日
 *
 */
public class Operation extends BaseEntity {
	private Long id;
	private String operator;
	private String title;
	private String type;
	private String content;
	private Integer is_deleted;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIs_deleted() {
		return is_deleted;
	}

  /*  public void setIp(String ip) {
        this.ip = ip;
    }
*/
}
