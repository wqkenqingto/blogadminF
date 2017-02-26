/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.blogadmin.sys.controller;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import com.blogadmin.core.controller.BaseController;
import com.blogadmin.sys.model.Role;
import com.blogadmin.sys.model.User;
import com.blogadmin.sys.service.IRoleService;
import com.blogadmin.sys.service.IUserRoleService;
import com.blogadmin.sys.service.impl.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 企业用户信息管理.
 * 
 * Created by yukaiji on 2016/09/22.
 */
@Controller
@RequestMapping("/sys/userinfo")
public class UserInfoController extends BaseController {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserRoleService userRoleService;

	/**
	 * 用户管理主界面
	 */
	@RequestMapping(value = "index.htm", method = RequestMethod.GET)
	public String index() {
		return getNameSpace() + "index";
	}

	/**
	 * 用户注册画面
	 */
	@RequestMapping(value = "add.htm", method = RequestMethod.GET)
	public String add(Model model) {
		List<Role> roleList = roleService.queryEnableRoleList();
		model.addAttribute("roleList", roleList);
		return getNameSpace() + "add";
	}

	/**
	 * 修改画面
	 */
	@RequestMapping(value = "edit.htm", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		List<Role> roleList = roleService.queryEnableRoleList();
		model.addAttribute("roleList", roleList);
		User userInfo = userInfoService.getAllToId(id);
		model.addAttribute("userList", JSON.toJSONString(userInfo));
		return getNameSpace() + "edit";
	}
	
	/**
	 * 用户信息详情展示页面
	 */
	@RequestMapping(value = "view.htm", method = RequestMethod.GET)
	public String view(Long id, Model model) {
		List<Role> roleList = roleService.queryEnableRoleList();
		model.addAttribute("roleList", roleList);
		User userInfo = userInfoService.getAllToId(id);
		model.addAttribute("userList", JSON.toJSONString(userInfo));
		return getNameSpace() + "view";
	}

	/**
	 * 分页查询列表.
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "query.htm", method = RequestMethod.POST)
	public void query(HttpServletRequest request, HttpServletResponse response) {
		List<User> allUserInfo = userInfoService.getAllUserInfo();
		super.printJson(response, JSON.toJSONString(allUserInfo));
	}

	/**
	 * 审核页面.
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "examine.htm", method = RequestMethod.GET)
	public String examine(Long id, Model model) {
		List<Role> roleList = roleService.queryEnableRoleList();
		model.addAttribute("roleList", roleList);
		User userInfo = userInfoService.getAllToId(id);
		model.addAttribute("userList", JSON.toJSONString(userInfo));
		return getNameSpace() + "examine";
	}

	/**
	 * 用户注册信息添加
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "addUserInfo.htm", method = RequestMethod.POST)
	public void addUserInfo(HttpServletRequest request, HttpServletResponse response) {
		String paramStr = request.getParameter("paramMap");
		JSONObject paramMap = JSON.parseObject(paramStr);
		userInfoService.addUserInfo(paramMap);
		//根据用户名获取用户
		User user = userInfoService.getAllToName((String)paramMap.get("username"));
		//将RoleId转换为Long数组
		String[] ids = user.getRoleIds().split(",");
		Long[] longIds = new Long[ids.length]; 
		for (int i = 0; i < ids.length; i++) {
			longIds[i] = Long.valueOf(ids[i]);
		}
		//添加用户权限
		userRoleService.saveUserRole(user.getId(),longIds);
		super.printJson(response, "true");
	}

	/**
	 * 修改用户注册信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "editUserInfo.htm", method = RequestMethod.POST)
	public void editUserInfo(HttpServletRequest request, HttpServletResponse response) {

		String paramStr = request.getParameter("paramMap");
		JSONObject paramMap = JSON.parseObject(paramStr);

		// 获取当前用户ID,根据ID判断是否未通过审核
		Map<String, Object> params = paramMap;
		Long id = Long.parseLong(String.valueOf(params.get("id")));
		User userInfo = userInfoService.getAllToId(id);
		JSONObject json = JSON.parseObject(JSON.toJSONString(userInfo));
		params = json;

		// 未通过审核进行修改操作
		if (String.valueOf(params.get("user_flag")).equals("0")) {
			userInfoService.editUserInfo(paramMap);
			//获取用户RoleId并转换为Long数组
			String roleIds = (String) paramMap.get("role");
			String[] ids = roleIds.split(",");
			Long[] longIds = new Long[ids.length]; 
			for (int i = 0; i < ids.length; i++) {
				longIds[i] = Long.valueOf(ids[i]);
			}
			//修改用户权限
			userRoleService.saveUserRole(Long.parseLong(String.valueOf(paramMap.get("id"))),longIds);
		}

		super.printJson(response,"true");
	}

	/**
	 * 用户信息审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateFlag.htm", method = RequestMethod.POST)
	public void updateFlag(HttpServletRequest request, HttpServletResponse response) {
		Long id = Long.valueOf((String) request.getParameter("id"));
		userInfoService.updataUserInfoFlag(id);
		super.printJson(response,"true");
	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteUserInfo.htm", method = RequestMethod.POST)
	public String deleteUserInfo(HttpServletRequest request, HttpServletResponse response) {
		// 获取删除数据id
		long id;
		List<Long> idList = new ArrayList<Long>();
		String values = request.getParameter("id");
		if (values != null) {
			String[] split = values.split(",");
			for (String string : split) {
				id = Long.valueOf(String.valueOf(string));
				idList.add(id);
			}
		}
		// 删除数据定位
		userInfoService.deleteUserInfo(idList);
		
		userRoleService.deleteUserRole(idList);

		return getNameSpace() + "index";
	}

	/**
	 * 验证用户名是否重复.
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "repeatName.htm", method = RequestMethod.POST)
	public void repeatName(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		User userInfo = userInfoService.getAllToName(username);
		super.printJson(response, JSON.toJSONString(userInfo));
	}
}
