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

import com.blogadmin.core.utils.response.ListResult;
import com.blogadmin.sys.model.Rule;

/**
 * 类IRuleService.java的实现描述：接口
 * 
 * @author sz.gong 2016年4月20日 下午3:50:12
 */
public interface IRuleService {

    ListResult<Rule> queryRuleList(Map<String, Object> paramMap);

    List<Rule> queryRuleList();

    List<Rule> queryRuleCascade();

    Rule queryRuleByid(Long id);

    Long saveRule(Rule rule);

    void updateRule(Rule rule);

    void deleteRule(Long id);

    List<Rule> getRuleTreeByIds(List<Long> idList);
}
