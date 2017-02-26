package com.blogadmin.sys.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.sys.model.User;

public interface IUserDao {

    User queryUser(Long id);

    int logicRemove(Serializable id);

    ListJsonResult<User> queryDTPage(Map<String, Object> paramMap);

    List<User> queryList(Map<String, Object> paramMap);

    Integer getCount(Map<String, Object> paramMap);

    List<User> queryUserLogin(String name, String password);

    Long saveUser(User user);

    void updateUser(User user);

	void saveUserTokenDES(User user);

}
