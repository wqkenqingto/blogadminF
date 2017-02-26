package com.blogadmin.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blogadmin.core.controller.BaseController;
import com.blogadmin.sys.model.Role;
import com.blogadmin.sys.service.IRuleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blogadmin.core.annotation.SysLog;
import com.blogadmin.core.annotation.Token;
import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.sys.model.Rule;
import com.blogadmin.sys.service.IRoleRuleService;
import com.blogadmin.sys.service.IRoleService;

@Controller
@RequestMapping("/sys/role")
public class    RoleController extends BaseController {

    @Autowired
    private IRuleService ruleService;
    @Autowired
    private IRoleService     roleService;
    @Autowired
    private IRoleRuleService roleRuleService;

    @RequestMapping(value = "index.htm", method = RequestMethod.GET)
    public String index() {
        return getNameSpace() + "index";
    }

    /**
     * 分页查询列表.
     */
    @RequestMapping(value = "queryPaged.htm", method = RequestMethod.POST)
    public void queryPaged(HttpServletRequest request, HttpServletResponse response) {
        try {
        	Map<String, Object> paramMap = bindMapObj(request);
            ListJsonResult<Role> result = roleService.queryRolePage(paramMap);
            super.printJson(response, result.toJsonString());
        } catch (Exception e) {
            LOGGER.error("queryPaged", e);
        }
    }

    @RequestMapping(value = "add.htm")
    @Token(save = true)
    public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Rule> ruleList = ruleService.queryRuleList();
        model.addAttribute("ruleList", ruleList);
        return getNameSpace() + "add";
    }

    @RequestMapping(value = "edit.htm")
    @Token(save = true)
    public String edit(Long id, Model model) {
        try {
            List<Long> roleList = new ArrayList<Long>();
            roleList.add(id.longValue());
            List<Long> ruleIds = roleRuleService.queryRulesByRid(roleList);
            List<Rule> ruleList = ruleService.queryRuleList();
            Role role = roleService.queryRoleById(id);
            model.addAttribute("ckrule", ruleIds);
            model.addAttribute("ruleList", ruleList);
            model.addAttribute("role", role);
        } catch (Exception e) {
            LOGGER.error("edit", e);
        }
        return getNameSpace() + "edit";
    }

    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    @SysLog(title = "新增角色、编辑角色", value = "/sys/role/save", description = "新增角色、编辑角色")
    @Token(remove = true)
    public String save(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        try {
            Role role = this.bindEntity(request, Role.class);
            Long id = role.getId();
            String ruids = role.getRuleids();
            Long[] ruleIds = null;

            if (StringUtils.isNotBlank(ruids)) {
                String[] rids = ruids.split(",");
                ruleIds = new Long[rids.length];
                for (int i = 0; i < rids.length; i++) {
                    ruleIds[i] = Long.parseLong(rids[i]);
                }
            }

            if (ruleIds != null) {
                if (id != null) {
                    roleService.updateRole(role);
                } else {
                    roleService.saveRole(role);
                    id = role.getId();
                }
                roleRuleService.saveRoleRule(id, ruleIds);
            }
        } catch (Exception e) {
            LOGGER.error("save", e);
        }

        return "redirect:" + getNameSpace() + "index.htm";
    }

    @RequestMapping(value = "delete.htm", method = RequestMethod.POST)
    @SysLog(title = "删除角色", value = "/sys/role/delete.htm", description = "删除角色")
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            String ids = request.getParameter("ids");
            for (String id : ids.split(",")) {
                roleService.deleteRole(Long.parseLong(id));
            }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            LOGGER.error("delete", e);
            printJson(response, messageFailureWrap("删除失败！"));
        }
    }

    /**
     * 角色唯一性
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "verify_role.htm", method = RequestMethod.POST)
    public void verifyRole(HttpServletRequest request, HttpServletResponse response) {
        String rName = request.getParameter("roleName");
        String rid = request.getParameter("id");
        Long id = StringUtils.isBlank(rid) ? null : Long.parseLong(rid);
        boolean exist = roleService.queryExistRole(rName, id);
        if (exist) {
            printJson(response, "false");
        } else {
            printJson(response, "true");
        }

    }
}
