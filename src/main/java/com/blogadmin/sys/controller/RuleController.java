package com.blogadmin.sys.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blogadmin.core.annotation.SysLog;
import com.blogadmin.core.annotation.Token;
import com.blogadmin.core.controller.BaseController;
import com.blogadmin.core.utils.response.ListResult;
import com.blogadmin.sys.model.Rule;
import com.blogadmin.sys.service.IRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sys/rule")
public class RuleController extends BaseController {

    @Autowired
    private IRuleService ruleService;

    @RequestMapping(value = "index.htm", method = RequestMethod.GET)
    public String index() {
        return getNameSpace() + "index";
    }

    @RequestMapping(value = "queryPaged.htm", method = RequestMethod.POST)
    public void queryPaged(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> paramMap = bindMapObj(request);
            ListResult<Rule> res = ruleService.queryRuleList(paramMap);
            printJson(response, res.toJsonString());
        } catch (Exception e) {
            LOGGER.error("queryPaged", e);
        }
    }

    @RequestMapping(value = "add.htm")
    @Token(save = true)
    public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Rule> list = ruleService.queryRuleCascade();
        model.addAttribute("ruleList", list);
        return getNameSpace() + "add";
    }

    @RequestMapping(value = "edit.htm")
    @Token(save = true)
    public String edit(Long id, Model model) {
        List<Rule> list = ruleService.queryRuleCascade();
        model.addAttribute("ruleList", list);
        if (id != null) {
            Rule rule = ruleService.queryRuleByid(id);
            model.addAttribute("rule", rule);
        }
        return getNameSpace() + "edit";
    }

    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    @SysLog(title = "新增菜单、编辑菜单", value = "/sys/rule/save", description = "新增菜单、编辑菜单")
    @Token(remove = true)
    public String save(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        try {
            Rule rule = this.bindEntity(request, Rule.class);
            Long id = rule.getId();
            if (id != null) {
                ruleService.updateRule(rule);
            } else {
                ruleService.saveRule(rule);
            }
        } catch (Exception e) {
            LOGGER.error("save", e);
        }
        return "redirect:" + getNameSpace() + "index.htm";
    }

    @RequestMapping(value = "delete.htm", method = RequestMethod.POST)
    @SysLog(title = "删除菜单", value = "/sys/rule/delete.htm", description = "删除菜单")
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            String ids = request.getParameter("ids");
            for (String id : ids.split(",")) {
                ruleService.deleteRule(Long.parseLong(id));
            }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            LOGGER.error("delete", e);
            printJson(response, messageFailureWrap("删除失败！"));
        }
    }

}
