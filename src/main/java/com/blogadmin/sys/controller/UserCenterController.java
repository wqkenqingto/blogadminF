/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.blogadmin.sys.controller;





import java.util.Properties;


import javax.mail.Authenticator;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import com.blogadmin.core.controller.BaseController;
import com.blogadmin.sys.model.User;
import com.blogadmin.common.constant.SessionKeyConstant;
import com.blogadmin.sys.service.impl.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 用户个人中心
 * 
 * Created by yukaiji on 2016/09/22.
 */
@Controller
@RequestMapping("/sys/usercenter")
public class UserCenterController extends BaseController {


	@Autowired
	private UserInfoService userInfoService;

	
	/**
	 * 初始化画面--展示用户信息
	 */
	@RequestMapping(value = "index.htm", method = RequestMethod.GET)
	public String index(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(SessionKeyConstant.USER);
		String UserName = user.getUserName();
		User result = userInfoService.getAllToName(UserName);
		model.addAttribute("userList", JSON.toJSONString(result));
		return getNameSpace() + "index";
	}

	/**
	 * 修改画面
	 */
	@RequestMapping(value = "edit.htm", method = RequestMethod.GET)
	public String edit(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(SessionKeyConstant.USER);
		String UserName = user.getUserName();
		User result = userInfoService.getAllToName(UserName);
		model.addAttribute("userList",JSON.toJSONString(result));
		return getNameSpace() + "edit";
	}
	
	
	
	/**
	 * 修改用户注册信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "editUserCenter.htm", method = RequestMethod.POST)
	public String editUserCenter(HttpServletRequest request, HttpServletResponse response) {
		String paramStr = request.getParameter("paramMap");
		JSONObject paramMap = JSON.parseObject(paramStr);
		userInfoService.editUserCenter(paramMap);
		return "redirect:" + getNameSpace() + "index.htm";
	}
	
	
	
	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "editPassword.htm", method = RequestMethod.POST)
	public void editPassword(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute(SessionKeyConstant.USER);
		String identifyingCode = request.getSession().getAttribute("identifyingCode").toString();
		
		String paramStr = request.getParameter("paramMap");
		JSONObject paramMap = JSON.parseObject(paramStr);
		paramMap.put("username",user.getUserName());
		
		if (identifyingCode.equals((String)paramMap.get("number"))) {
			userInfoService.updataPassword(paramMap);
			super.printJson(response, JSON.toJSONString("true"));
		} else {
			super.printJson(response, JSON.toJSONString("false"));
		}
	}
	
	
	
	/**
	 * 邮箱验证码发送
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws MessagingException 
	 */
	@RequestMapping(value = "mail.htm", method = RequestMethod.POST)
	public String mail(HttpServletRequest request, HttpServletResponse response) throws MessagingException {
		String mail = request.getParameter("mail");
		StringBuffer identifyingCode = new StringBuffer();
		//获取6位验证码
		for (int i = 0; i < 6; i++) {
			Long num =(long) (Math.random()*10);
			identifyingCode.append(String.valueOf(num));
		}
		//存放到session
		request.getSession().setAttribute("identifyingCode", identifyingCode);
		
		 final Properties props = new Properties();
	        // 表示SMTP发送邮件，需要进行身份验证
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", "smtp.exmail.qq.com");
	        // 发件人的账号
	        props.put("mail.user", "yukaiji@mocentre.com");
	        // 访问SMTP服务时需要提供的密码
	        props.put("mail.password", "Maniykj0703");

	        // 构建授权信息，用于进行SMTP进行身份验证
	        Authenticator authenticator = new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                // 用户名、密码
	                String userName = props.getProperty("mail.user");
	                String password = props.getProperty("mail.password");
	                return new PasswordAuthentication(userName, password);
	            }
	        };
	        // 使用环境属性和授权信息，创建邮件会话
	        Session mailSession = Session.getInstance(props, authenticator);
	        // 创建邮件消息
	        MimeMessage message = new MimeMessage(mailSession);
	        // 设置发件人
	        InternetAddress form = new InternetAddress(
	                props.getProperty("mail.user"));
	        message.setFrom(form);

	        // 设置收件人
	        InternetAddress to = new InternetAddress(mail);
	        message.setRecipient(RecipientType.TO, to);
	        // 设置邮件标题
	        message.setSubject("摩森特修改密码验证");

	        // 设置邮件的内容体
	        message.setContent("<h1>您的验证码为"+identifyingCode+"</h1>", "text/html;charset=UTF-8");

	        // 发送邮件
	        Transport.send(message);
		
		return "redirect:" + getNameSpace() + "index.htm";
	}

}
