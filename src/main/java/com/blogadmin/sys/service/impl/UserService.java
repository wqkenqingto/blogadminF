package com.blogadmin.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.sys.dao.IUserDao;
import com.blogadmin.sys.model.User;
import com.blogadmin.sys.service.IUserService;

/**
 * 类UserService.java的实现描述：用户service
 * 
 * @author sz.gong 2016年4月20日 下午3:33:43
 */
@Component
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public ListJsonResult<User> queryUserPage(Map<String, Object> paramMap) {
        return userDao.queryDTPage(paramMap);
    }

    @Override
    public User queryUser(Long id) {
        return userDao.queryUser(id);
    }

    @Override
    public Long saveUser(User user) {
        return userDao.saveUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void logicDelete(Long id) {
        userDao.logicRemove(id);
    }

    /**
     * 登入验证
     * 
     * @param name
     * @param password
     * @return
     */
    @Override
    public List<User> queryLoginVerify(String name, String password) {
        Assert.notNull(name);
        Assert.notNull(password);
        password = DigestUtils.md5Hex(password);
        List<User> list = userDao.queryUserLogin(name, password);
        return list;
    }

    /**
     * 用户是否存在
     * 
     * @param uname
     * @param id
     * @return
     */
    @Override
    public boolean queryExistUser(String uname, Long id) {
        Assert.notNull(uname);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userName", uname);
        paramMap.put("id", id);
        Integer count = userDao.getCount(paramMap);
        if (count > 0) {
            return true;
        }
        return false;
    }

	@Override
	public void saveUserTokenDES(User user) {
		userDao.saveUserTokenDES(user);
	}

}
