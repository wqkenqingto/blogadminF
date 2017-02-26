package com.blogadmin.sys.model;

import com.blogadmin.core.model.BaseEntity;

/**
*
* @ClassName: UserInfo
* @Description: 企业用户信息
* @author yukaiji
* @date 2016年9月22日
*/
public class User extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/** 用户类型  **/
	private String  usertype;
	/** 登录名  **/
	private String  userName;
	/** 密码  **/
	private String  password;
	/** 用户角色  **/
	private String  roleIds;
	/** 企业code  **/
	private String  enterprise_code;
	/** 企业名称  **/
	private String  enterprise_name;
	/** 企业行业  **/
	private String  enterprise_industry;
	/** 企业地址  **/
	private String  enterprise_address;
	/** 企业联系方式  **/
	private String  enterprise_contacts;
	/** 企业电话  **/
	private String  enterprise_telephone;
	/** 企业邮箱  **/
	private String  enterprise_email;
	/** 用户token  **/
	private String  tokenDES;
	/** 短信接口  **/
	private String  message_interface;
	/** Ip  **/
	private String  ip;
	/** 单价信息  **/
	private String  price;
	/** 审核状态  **/
	private String  user_flag;
	
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getEnterprise_code() {
		return enterprise_code;
	}
	public void setEnterprise_code(String enterprise_code) {
		this.enterprise_code = enterprise_code;
	}
	public String getEnterprise_name() {
		return enterprise_name;
	}
	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}
	public String getEnterprise_industry() {
		return enterprise_industry;
	}
	public void setEnterprise_industry(String enterprise_industry) {
		this.enterprise_industry = enterprise_industry;
	}
	public String getEnterprise_address() {
		return enterprise_address;
	}
	public void setEnterprise_address(String enterprise_address) {
		this.enterprise_address = enterprise_address;
	}
	public String getEnterprise_contacts() {
		return enterprise_contacts;
	}
	public void setEnterprise_contacts(String enterprise_contacts) {
		this.enterprise_contacts = enterprise_contacts;
	}
	public String getEnterprise_telephone() {
		return enterprise_telephone;
	}
	public void setEnterprise_telephone(String enterprise_telephone) {
		this.enterprise_telephone = enterprise_telephone;
	}
	public String getEnterprise_email() {
		return enterprise_email;
	}
	public void setEnterprise_email(String enterprise_email) {
		this.enterprise_email = enterprise_email;
	}
	public String getTokenDES() {
		return tokenDES;
	}
	public void setTokenDES(String tokenDES) {
		this.tokenDES = tokenDES;
	}
	public String getMessage_interface() {
		return message_interface;
	}
	public void setMessage_interface(String message_interface) {
		this.message_interface = message_interface;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUser_flag() {
		return user_flag;
	}
	public void setUser_flag(String user_flag) {
		this.user_flag = user_flag;
	}
	
}
