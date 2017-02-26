package com.blogadmin.sys.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blogadmin.core.controller.BaseController;
import com.blogadmin.core.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.blogadmin.common.utils.CommUtil;
import com.blogadmin.common.utils.DESUtil;
import com.blogadmin.sys.model.User;
import com.blogadmin.sys.service.IUserService;

@Controller
@RequestMapping("/sys/menu")
public class MenuController extends BaseController {
	@Autowired
	private IUserService userService;

    @RequestMapping(value = "index.htm", method = RequestMethod.GET)
    public String menu(Model model, HttpServletRequest request, HttpServletResponse response) {
        return getNameSpace() + "index";
    }

    @RequestMapping(value = "platform.htm", method = RequestMethod.GET)
    public String platform(Model model, HttpServletRequest request, HttpServletResponse response) {
        return getNameSpace() + "platform";
    }
    
    @RequestMapping(value = "login_validate.htm", method = RequestMethod.GET)
	public String login_validate(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
    	try{
    		//获取token
        	String token = request.getQueryString().split("=")[1];
        	
        	Map<String,Object> tokenMap = new HashMap<String,Object>();
    		tokenMap = JsonUtils.jsonToGenericObject(DESUtil.get3DESDecrypt(token, "mocentre.com"),
                    new TypeReference<Map<String,Object>>() {
                    });
			//获取user
    		if(!tokenMap.isEmpty()){
	    		User user = userService.queryUser(Long.valueOf(String.valueOf(tokenMap.get("user")).split(":")[0]));
	    		if (user.getTokenDES() != "" && user.getTokenDES().equals(String.valueOf(tokenMap.get("vtoken")))) {
	    			Map<String,Object> newtokenMap = new HashMap<String,Object>();
	    			newtokenMap.put("user", user.getId()+":"+user.getUserName());
					String vtoken = user.getId()+CommUtil.formatTokenDate(new Date());
					newtokenMap.put("vtoken", vtoken);
					String tokenDES = DESUtil.get3DESEncrypt(JSON.toJSONString(newtokenMap), "mocentre.com");
					user.setTokenDES(vtoken);
					userService.saveUserTokenDES(user);
					
					request.getSession().setAttribute("token", tokenDES);
					
					return getNameSpace() + "index";
	    		} else {
	    			return "redirect:/login.html";
	    		}
	    	}else{
	    		return "redirect:/login.html";
	    	}
    	}catch(Exception e){
    		return "redirect:/login.html";
    	}
	}
    
    @RequestMapping(value = "login_order_validate.do", method = RequestMethod.GET)
	public void login_order_validate(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try{
        	//获取token
        	String token = request.getQueryString().split("=")[1];
        	Map<String,Object> tokenMap = new HashMap<String,Object>() ;
    		tokenMap = JsonUtils.jsonToGenericObject(DESUtil.get3DESDecrypt(token, "mocentre.com"),
                    new TypeReference<Map<String,Object>>() {
                    });
    		if(!tokenMap.isEmpty()){
    			User user = userService.queryUser(Long.valueOf(String.valueOf(tokenMap.get("user")).split(":")[0]));
        		if (user.getTokenDES() != "" && user.getTokenDES().equals(String.valueOf(tokenMap.get("vtoken")))) {
        			String vtoken = user.getId()+CommUtil.formatTokenDate(new Date());
    				user.setTokenDES(vtoken);
    				userService.saveUserTokenDES(user);
    				
        			resultMap.put("code", "200");
        			resultMap.put("userName", user.getUserName());
        			super.printJson(response, JSON.toJSONString(resultMap));
        		} else {
        			resultMap.put("code", "500");
        			super.printJson(response, JSON.toJSONString(resultMap));
        		}
    		}else{
    			resultMap.put("code", "500");
    			super.printJson(response, JSON.toJSONString(resultMap));
    		}
    	}catch(Exception e){
    		resultMap.put("code", "500");
			super.printJson(response, JSON.toJSONString(resultMap));
    	}
	}
}
