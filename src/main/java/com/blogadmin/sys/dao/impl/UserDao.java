package com.blogadmin.sys.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blogadmin.sys.dao.IUserDao;
import com.blogadmin.sys.model.User;
import org.springframework.stereotype.Repository;

import com.blogadmin.core.dao.BaseDao;

@Repository
public class UserDao extends BaseDao<User> implements IUserDao {

    @Override
    public User queryUser(Long id) {
        return this.get(id);
    }

    @Override
    public Integer getCount(Map<String, Object> parameterMap) {
        return this.getCount(User.class, parameterMap);
    }

    @Override
    public List<User> queryUserLogin(String name, String password) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userName", name);
        paramMap.put("password", password);
        return this.query("User_login", paramMap);
    }

    @Override
    public Long saveUser(User user) {
        return this.save(user);
    }

    @Override
    public void updateUser(User user) {
        this.update(user);
    }

	@Override
	public void saveUserTokenDES(User user) {
		super.getSqlSession().update(user.getClass().getSimpleName() + "_tokenDES", user);
	}

}
