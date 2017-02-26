package com.blogadmin.sys.service.impl;


import java.util.List;



import java.util.Map;

import com.blogadmin.sys.dao.IUserInfoDao;
import com.blogadmin.sys.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blogadmin.sys.service.IUserInfoService;


/**
 * 企业用户信息管理.
 * 
 * Created by yukaiji on 2016/09/22.
 */
@Component
public class UserInfoService implements IUserInfoService {

	@Autowired
	private IUserInfoDao iUserInfoDao;
	
	
	@Override
	public Long addUserInfo(Map<String, Object> paramMap) {
		User User = new User();
		User.setUsertype((String) paramMap.get("usertype"));
		User.setUserName((String) paramMap.get("username"));
		User.setPassword(DigestUtils.md5Hex((String) paramMap.get("password")));
		User.setRoleIds((String) paramMap.get("role"));
		User.setEnterprise_code((String) paramMap.get("enterprise_code"));
		User.setEnterprise_name((String) paramMap.get("enterprise_name"));
		User.setEnterprise_industry((String) paramMap.get("enterprise_industry"));
		User.setEnterprise_address((String) paramMap.get("enterprise_address"));
		User.setEnterprise_contacts((String) paramMap.get("enterprise_contacts"));
		User.setEnterprise_telephone((String) paramMap.get("enterprise_telephone"));
		User.setEnterprise_email((String) paramMap.get("enterprise_email"));
		User.setTokenDES((String) paramMap.get("token"));
		User.setMessage_interface((String) paramMap.get("message_interface"));
		User.setIp((String) paramMap.get("ip"));
		User.setPrice((String) paramMap.get("price"));
		return iUserInfoDao.saveEntity(User);
	}


	@Override
	public List<User> getAllUserInfo() {
		return iUserInfoDao.getAll();
	}


	@Override
	public Long editUserInfo(Map<String, Object> paramMap) {
		User User = new User();
		User.setUsertype((String) paramMap.get("usertype"));
		User.setUserName((String) paramMap.get("username"));
		User.setRoleIds((String) paramMap.get("role"));
		User.setEnterprise_code((String) paramMap.get("enterprise_code"));
		User.setEnterprise_name((String) paramMap.get("enterprise_name"));
		User.setEnterprise_industry((String) paramMap.get("enterprise_industry"));
		User.setEnterprise_address((String) paramMap.get("enterprise_address"));
		User.setEnterprise_contacts((String) paramMap.get("enterprise_contacts"));
//		User.setEnterprise_telephone((String) paramMap.get("enterprise_telephone"));
//		User.setEnterprise_email((String) paramMap.get("enterprise_email"));
		User.setTokenDES((String) paramMap.get("token"));
		User.setMessage_interface((String) paramMap.get("message_interface"));
		User.setIp((String) paramMap.get("ip"));
		User.setPrice((String) paramMap.get("price"));
		User.setId(Long.parseLong(String.valueOf(paramMap.get("id"))));
		return iUserInfoDao.updateEntity(User);
	}

	/**
	 * 修改安全中心字段
	 * @param paramMap
	 * @return
     */
	@Override
	public Long updateSecurityInfo(Map<String, Object> paramMap) {
		User User = new User();
		User.setEnterprise_telephone((String) paramMap.get("enterprise_telephone"));
		User.setEnterprise_email((String) paramMap.get("enterprise_email"));
		User.setId(Long.parseLong(String.valueOf(paramMap.get("id"))));
		return iUserInfoDao.updateEntity(User);
	}

	@Override
	public void deleteUserInfo(List<Long> idList) {
		for (Long id : idList) {
			iUserInfoDao.logicRemove(id);
		}		
	}


	@Override
	public void updataUserInfoFlag(Long id) {
		iUserInfoDao.updateFlag(id);
	}


	@Override
	public User getAllToId(Long id) {
		return iUserInfoDao.get(id);
	}


	@Override
	public User getAllToName(String username) {
		return iUserInfoDao.getToName(username);
	}


	@Override
	public Long editUserCenter(Map<String, Object> paramMap) {
		User User = new User();
		User.setUsertype((String) paramMap.get("usertype"));
		User.setUserName((String) paramMap.get("username"));
		User.setEnterprise_code((String) paramMap.get("enterprise_code"));
		User.setEnterprise_name((String) paramMap.get("enterprise_name"));
		User.setEnterprise_industry((String) paramMap.get("enterprise_industry"));
		User.setEnterprise_address((String) paramMap.get("enterprise_address"));
		User.setEnterprise_contacts((String) paramMap.get("enterprise_contacts"));
		User.setEnterprise_telephone((String) paramMap.get("enterprise_telephone"));
		User.setEnterprise_email((String) paramMap.get("enterprise_email"));
		User.setTokenDES((String) paramMap.get("token"));
		User.setMessage_interface((String) paramMap.get("message_interface"));
		User.setIp((String) paramMap.get("ip"));
		User.setId(Long.parseLong(String.valueOf(paramMap.get("id"))));
		return iUserInfoDao.update(User);
	}


	@Override
	public void updataPassword(Map<String, Object> paramMap) {
		User User = new User();
		User.setUserName((String) paramMap.get("username"));
		User.setPassword(DigestUtils.md5Hex((String) paramMap.get("password")));
		iUserInfoDao.updatePassword(User);		
	}




}
