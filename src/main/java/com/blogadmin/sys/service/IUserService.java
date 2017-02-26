/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.blogadmin.sys.service;

import java.util.List;
import java.util.Map;

import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.sys.model.User;

/**
 * 类IUserService.java的实现描述：接口
 * 
 * @author sz.gong 2016年4月20日 下午3:33:14
 */
public interface IUserService {

    ListJsonResult<User> queryUserPage(Map<String, Object> paramMap);

    User queryUser(Long id);

    Long saveUser(User user);

    void updateUser(User user);

    List<User> queryLoginVerify(String name, String password);

    boolean queryExistUser(String uname, Long id);

    void logicDelete(Long id);

	void saveUserTokenDES(User user);

}
