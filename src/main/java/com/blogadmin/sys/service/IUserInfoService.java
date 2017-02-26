package com.blogadmin.sys.service;


import java.util.List;
import java.util.Map;

import com.blogadmin.sys.model.User;


/**
 * 企业用户信息管理.
 * 
 * Created by yukaiji on 2016/09/22.
 */
public interface IUserInfoService {


	Long addUserInfo(Map<String, Object> paramMap);

	List<User> getAllUserInfo();

	Long editUserInfo(Map<String, Object> paramMap);

	void deleteUserInfo(List<Long> idList);
	
	void updataUserInfoFlag(Long id);
	
	User getAllToId(Long id);

	User getAllToName(String loginName);

	Long editUserCenter(Map<String, Object> paramMap);

	void updataPassword(Map<String, Object> paramMap);

	 Long updateSecurityInfo(Map<String, Object> paramMap);

}
