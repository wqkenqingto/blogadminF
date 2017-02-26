/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.blogadmin.sys.service;

import java.util.List;

/**
 * 类IRoleRuleService.java的实现描述：接口
 * 
 * @author sz.gong 2016年4月20日 下午4:08:07
 */
public interface IRoleRuleService {

    List<Long> queryRulesByRid(List<Long> roleList);

    void saveRoleRule(Long roleId, Long[] ruleIds);

}
