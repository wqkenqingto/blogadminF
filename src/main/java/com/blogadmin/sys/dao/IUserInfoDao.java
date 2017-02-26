package com.blogadmin.sys.dao;

import java.util.List;


import com.blogadmin.sys.model.User;


/**
 * 企业用户信息管理.
 * 
 * Created by yukaiji on 2016/09/22.
 */
public interface IUserInfoDao {

	Long saveEntity(User User);

	List<User> getAll();

	Long updateEntity(User User);

	int logicRemove(Long id);
	
	int updateFlag(Long id);
	
	User get(Long id);

	User getToName(String username);

	Long update(User User);

	void updatePassword(User User);
}
