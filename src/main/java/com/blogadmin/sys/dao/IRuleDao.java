package com.blogadmin.sys.dao;

import java.util.List;
import java.util.Map;

import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.sys.model.Rule;

/**
 * 类IRuleDao.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2016年4月20日 下午3:48:54
 */
public interface IRuleDao {

    ListJsonResult<Rule> queryDTList(Map<String, Object> paramMap);

    List<Rule> queryRuleAll();

    List<Rule> queryRule(Map<String, Object> paramMap);

    Rule queryByid(Long id);

    Long saveRule(Rule rule);

    void updateRule(Rule rule);

    int logicDelete(Long id);

}
