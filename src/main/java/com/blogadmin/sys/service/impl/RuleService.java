package com.blogadmin.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.blogadmin.core.utils.response.ListResult;
import com.blogadmin.sys.dao.IRuleDao;
import com.blogadmin.sys.model.Rule;
import com.blogadmin.sys.service.IRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 类RuleService.java的实现描述：模块service
 * 
 * @author sz.gong 2016年4月20日 下午3:50:30
 */
@Component
public class RuleService implements IRuleService {

    @Autowired
    private IRuleDao ruleDao;

    /**
     * 查询list列表
     * 
     * @param paramMap
     * @return
     */
    @Override
    public ListResult<Rule> queryRuleList(Map<String, Object> paramMap) {
        ListResult<Rule> resList = new ListResult<Rule>();
        List<Rule> allList = ruleDao.queryRule(paramMap);
        List<Rule> childList = this.getChildrenTreeList(allList, "0", 0);
        if (childList != null && childList.size() > 0) {
            resList.setData(childList);
        } else {
            resList.setData(allList);
        }
        return resList;

    }

    @Override
    public List<Rule> queryRuleList() {
        return ruleDao.queryRuleAll();
    }

    /**
     * 查询所有级联列表
     * 
     * @return
     */
    @Override
    public List<Rule> queryRuleCascade() {
        List<Rule> list = ruleDao.queryRuleAll();
        return getChildrenTreeList(list, "0", 0);
    }

    /**
     * 通过id集合，查询列表
     * 
     * @param ids
     * @return
     */
    public List<Rule> queryRuleByIds(List<Long> idList) {
        Assert.notNull(idList);
        if (idList.size() > 0) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("ids", idList);
            paramMap.put("status", 1);
            return ruleDao.queryRule(paramMap);
        }
        return new ArrayList<Rule>();
    }

    /**
     * 通过id集合，查询列表
     * 
     * @param idList
     * @return
     */
    @Override
    public List<Rule> getRuleTreeByIds(List<Long> idList) {
        return this.queryRuleByIds(idList);
    }

    /**
     * 查询所有父子关系列表的树
     * 
     * @return
     */
    public List<Rule> getRuleTreeList() {
        List<Rule> list = ruleDao.queryRuleAll();
        return this.getRuleTree(list);
    }

    /*
     * 查询列表树
     */
    private List<Rule> getRuleTree(List<Rule> list) {

        List<Rule> parents = new ArrayList<Rule>();
        List<Rule> others = new ArrayList<Rule>();
        for (Rule rule : list) {
            if (rule.getPid() == 0) {
                rule.setChildren(new ArrayList<Rule>());
                parents.add(rule);
            } else {
                others.add(rule);
            }
        }
        this.buildTree(parents, others);
        return parents;
    }

    private void buildTree(List<Rule> parents, List<Rule> others) {

        List<Rule> record = new ArrayList<Rule>();
        for (Iterator<Rule> it = parents.iterator(); it.hasNext();) {
            Rule vi = it.next();
            if (vi.getId() != null) {
                for (Iterator<Rule> otherIt = others.iterator(); otherIt.hasNext();) {
                    Rule inVi = otherIt.next();
                    if (vi.getId().longValue() == inVi.getPid().longValue()) {
                        if (null == vi.getChildren()) {
                            vi.setChildren(new ArrayList<Rule>());
                        }
                        vi.getChildren().add(inVi);
                        record.add(inVi);
                        otherIt.remove();
                    }
                }
            }
        }
        if (others.size() == 0) {
            return;
        } else {
            buildTree(record, others);
        }
    }

    /*
     * 查询父节点下的所有子节点
     */
    private List<Rule> getChildrenTreeList(List<Rule> list, String fid, int lvl) {
        List<Rule> result = new ArrayList<Rule>();
        List<Rule> newlist = new Vector<Rule>();
        for (int i = 0; i < list.size(); i++) {
            Rule rule = list.get(i);
            String allfid = rule.getPid().toString();
            if (allfid.equals(fid)) {
                if (rule.getTitle() != null && !"".equals(rule.getTitle())) {
                    newlist.add(list.get(i));
                }
            }
        }
        for (int f = 0; f < newlist.size(); f++) {
            Rule rule = newlist.get(f);
            String name = rule.getTitle().toString();
            String id = rule.getId().toString();
            String pagenbsp = "";
            for (int l = 0; l < lvl; l++) {
                pagenbsp = pagenbsp + "-";
            }
            String pagename = pagenbsp + name;
            rule.setTitle(pagename);
            result.add(rule);
            result.addAll(getChildrenTreeList(list, id, lvl + 1));
        }
        return result;
    }

    @Override
    public Rule queryRuleByid(Long id) {
        return ruleDao.queryByid(id);
    }

    @Override
    public Long saveRule(Rule rule) {
        return ruleDao.saveRule(rule);
    }

    @Override
    public void updateRule(Rule rule) {
        ruleDao.updateRule(rule);
    }

    @Override
    public void deleteRule(Long id) {
        ruleDao.logicDelete(id);
    }

}
