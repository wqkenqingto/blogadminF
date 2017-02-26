package com.blogadmin.sys.dao.impl;

import java.util.List;
import java.util.Map;

import com.blogadmin.sys.dao.IRuleDao;
import com.blogadmin.sys.model.Rule;
import org.springframework.stereotype.Repository;

import com.blogadmin.core.dao.BaseDao;

/**
 * 类RuleDao.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2016年4月20日 下午3:49:16
 */
@Repository
public class RuleDao extends BaseDao<Rule> implements IRuleDao {

    @Override
    public List<Rule> queryRuleAll() {
        return this.getAll();
    }

    @Override
    public List<Rule> queryRule(Map<String, Object> paramMap) {
        return this.queryList(paramMap);
    }

    @Override
    public Rule queryByid(Long id) {
        return this.get(id);
    }

    @Override
    public Long saveRule(Rule rule) {
        return this.save(rule);
    }

    @Override
    public void updateRule(Rule rule) {
        this.update(rule);
    }

    @Override
    public int logicDelete(Long id) {
        return this.logicRemove(id);
    }

}
