package com.blogadmin.sys.dao.impl;
import org.springframework.stereotype.Repository;


import com.blogadmin.core.dao.BaseDao;
import com.blogadmin.sys.dao.IUserInfoDao;
import com.blogadmin.sys.model.User;


/**
 * 企业用户信息管理.
 * 
 * Created by yukaiji on 2016/09/22.
 */
@Repository
public class UserInfoDao extends BaseDao<User> implements IUserInfoDao {

	@Override
	public int logicRemove(Long id) {
		User userInfo = new User();
		userInfo.setId(id);
		return super.logicRemove(userInfo);
	}

	@Override
	public int updateFlag(Long id) {
		return getSqlSession().update("User_updataflag",id);
	}

	@Override
	public User get(Long id) {
		 return super.get(id);
	}

	@Override
	public User getToName(String username) {
		return getSqlSession().selectOne("User_getToName",username);
	}

	@Override
	public Long update(User userInfo) {
		getSqlSession().update("User_updataUserCenter", userInfo);
		return userInfo.getId();
	}

	@Override
	public void updatePassword(User userInfo) {
		getSqlSession().update("User_updataPassword",userInfo);
	}

}
