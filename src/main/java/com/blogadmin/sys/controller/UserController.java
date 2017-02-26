/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.blogadmin.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.blogadmin.core.annotation.Token;
import com.blogadmin.core.constant.ResultCode;
import com.blogadmin.core.controller.BaseController;
import com.blogadmin.sys.model.Role;
import com.blogadmin.sys.model.User;
import com.blogadmin.sys.service.IRoleRuleService;
import com.blogadmin.sys.service.IRuleService;
import com.blogadmin.sys.service.IUserRoleService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.blogadmin.common.constant.SessionKeyConstant;
import com.blogadmin.common.utils.CommUtil;
import com.blogadmin.common.utils.DESUtil;
import com.blogadmin.core.annotation.SysLog;
import com.blogadmin.core.utils.response.BaseResult;
import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.sys.model.Rule;
import com.blogadmin.sys.model.UserRole;
import com.blogadmin.sys.service.IRoleService;
import com.blogadmin.sys.service.IUserService;

/**
 * 类UserController.java的实现描述：用户控制器
 * 
 * @author sz.gong 2016年4月21日 上午10:37:54
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IRuleService ruleService;
	@Autowired
	private IUserRoleService userRoleService;
	@Autowired
	private IRoleRuleService roleRuleService;
	String username;
	String password;

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		BaseResult br = new BaseResult();
		Map<String, String> paramMap = bindMap(request);
		username = paramMap.get("username");
		password = paramMap.get("password");
		String vcode = paramMap.get("code");
		if (StringUtils.isBlank(username)) {
			ResultCode.PARAMER_ILLEGLE.build(br, "username");
			printJson(response, br.toJsonString());
			return;
		}
		if (StringUtils.isBlank(password)) {
			ResultCode.PARAMER_ILLEGLE.build(br, "password");
			printJson(response, br.toJsonString());
			return;
		}
		if (StringUtils.isBlank(vcode)) {
			ResultCode.PARAMER_ILLEGLE.build(br, "verifycode");
			printJson(response, "验证码错误");
			return;
		}
		Object code = request.getSession().getAttribute(SessionKeyConstant.VERIFYCODE);
		String code1 = String.valueOf(code);
		if (!vcode.equalsIgnoreCase(code1)) {
			ResultCode.CUSTOM_MESSAGE.build(br, "验证码错误");
			printJson(response, br.toJsonString());
			return;
		}
		if (br.getSuccess()) {
			List<User> list = userService.queryLoginVerify(username, password);
			if (list == null || list.size() > 1 || list.size() == 0) {
				ResultCode.CUSTOM_MESSAGE.build(br, "用户名或密码错误");
			} else {
				List<Rule> ruleList = new ArrayList<Rule>();
				User user = list.get(0);
				if ("admin".equals(user.getUserName())) {
					ruleList = ruleService.queryRuleList();
				} else {
					List<Long> roleIdList = userRoleService.queryRolesByUid(user.getId());
					List<Long> ruleIdList = roleRuleService.queryRulesByRid(roleIdList);
					ruleList = ruleService.getRuleTreeByIds(ruleIdList);
				}
				//id+用户名+时间戳
				Map<String,Object> tokenMap = new HashMap<String,Object>();
				tokenMap.put("user", user.getId()+":"+user.getUserName());
				String vtoken = user.getId()+CommUtil.formatTokenDate(new Date());
				tokenMap.put("vtoken", vtoken);
				String tokenDES = DESUtil.get3DESEncrypt(JSON.toJSONString(tokenMap), "mocentre.com");
				user.setTokenDES(vtoken);
				userService.saveUserTokenDES(user);
				
				request.getSession().setAttribute("token", tokenDES);
				request.getSession().setAttribute(SessionKeyConstant.MENU, ruleList);
				request.getSession().setAttribute(SessionKeyConstant.USER, user);

			}
			printJson(response, br.toJsonString());
		}
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String loginOut(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/login.html";
	}

	@RequestMapping(value = "index.htm", method = RequestMethod.GET)
	public String index() {
		return getNameSpace() + "index";
	}

	/**
	 * 分页查询列表.
	 */
	@RequestMapping(value = "queryPaged.htm", method = RequestMethod.POST)
	public void queryPaged(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> paramMap = bindMapObj(request);
			ListJsonResult<User> result = userService.queryUserPage(paramMap);
			super.printJson(response, result.toJsonString());
		} catch (Exception e) {
			LOGGER.error("queryPaged", e);
		}
	}

	@RequestMapping(value = "add.htm")
	@Token(save = true)
	public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Role> roleList = roleService.queryEnableRoleList();
		model.addAttribute("roleList", roleList);
		return getNameSpace() + "add";
	}

	@RequestMapping(value = "edit.htm")
	@Token(save = true)
	public String edit(Long id, Model model) {
		List<UserRole> urList = userRoleService.queryUserRoleByUid(Long.valueOf(id));
		String roles = "";
		if (urList != null) {
			for (int i = 0; i < urList.size(); i++) {
				UserRole ur = urList.get(i);
				roles += "," + ur.getRoleId();
			}
			model.addAttribute("roles", roles);
		}
		List<Role> roleList = roleService.queryEnableRoleList();
		model.addAttribute("roleList", roleList);
		if (id != null) {
			User user = userService.queryUser(id);
			model.addAttribute("user", user);
		}
		return getNameSpace() + "edit";
	}

	/**
	 * 新增用户
	 * 
	 * @param request
	 * @param response
	 * @param attr
	 * @return
	 */
	@RequestMapping(value = "save.htm", method = RequestMethod.POST)
	@SysLog(title = "新增用户、编辑用户", value = "/sys/user/save", description = "新增用户、编辑用户")
	@Token(remove = true)
	public String save(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
		try {
			User user = this.bindEntity(request, User.class);
			Long id = user.getId();
			String roleIds = user.getRoleIds();
			Long[] roids = null;
			if (StringUtils.isNotBlank(roleIds)) {
				String[] rids = roleIds.split(",");
				roids = new Long[rids.length];
				for (int i = 0; i < rids.length; i++) {
					roids[i] = Long.parseLong(rids[i]);
				}
			}
			if (id != null) {
				if (StringUtils.isNotBlank(user.getPassword())) {
					user.setPassword(DigestUtils.md5Hex(user.getPassword()));
				}
				userService.updateUser(user);
			} else {
				user.setPassword(DigestUtils.md5Hex(user.getPassword()));
				userService.saveUser(user);
				id = user.getId();
			}
			userRoleService.saveUserRole(id, roids);
		} catch (Exception e) {
			LOGGER.error("save", e);
		}
		return "redirect:" + getNameSpace() + "index.htm";
	}

	/**
	 * 用户唯一性
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "verify_user.htm", method = RequestMethod.POST)
	public void verifyUser(HttpServletRequest request, HttpServletResponse response) {
		String uName = request.getParameter("userName");
		String uid = request.getParameter("id");
		Long id = StringUtils.isBlank(uid) ? null : Long.parseLong(uid);
		boolean exist = userService.queryExistUser(uName, id);
		if (exist) {
			printJson(response, "false");
		} else {
			printJson(response, "true");
		}
	}

	@RequestMapping(value = "delete.htm", method = RequestMethod.POST)
	@SysLog(title = "删除用户", value = "/sys/user/delete.htm", description = "删除用户")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ids = request.getParameter("ids");
			for (String id : ids.split(",")) {
				userService.logicDelete(Long.parseLong(id));
			}
			printJson(response, messageSuccuseWrap());
		} catch (Exception e) {
			LOGGER.error("delete", e);
			printJson(response, messageFailureWrap("删除失败！"));
		}
	}

}
