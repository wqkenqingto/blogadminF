//package com.blogadmin.core;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.web.servlet.DispatcherServlet;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.blogadmin.common.constant.DictConstant;
//import com.blogadmin.common.constant.SessionKeyConstant;
//
//public class CentreServlet extends DispatcherServlet {
//
//    private static final long serialVersionUID = 1L;
//
//    protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        //        RedisClientTemplate redisTemp = SpringContextUtil.getBean("redisClientTemplate");
//        //        System.out.println(redisTemp);
//        String url = request.getRequestURI();
//        String base = request.getContextPath();
//        if (url != null) {
//            if (!"/".equals(base)) {
//                url = url.replace(base, "");
//            }
//            if (url.startsWith("//", 0)) {
//                url = url.replaceFirst("//", "");
//            } else if (url.startsWith("/", 0)) {
//                url = url.replaceFirst("/", "");
//            }
//            int index = url.indexOf(".htm");
//            if (index > 0) {
//                url = url.substring(0, index) + ".htm";
//            }
//        }
//        List<Rule> ruleList = (List<Rule>) request.getSession().getAttribute(SessionKeyConstant.MENU);
//        List<Rule> menuList = new ArrayList<Rule>();
//        List<Rule> pcList = new ArrayList<Rule>();
//        if (ruleList != null) {
//            //排序好的父子链
//            List<Rule> pcs = this.getPClist(ruleList, url);
//            for (int i = pcs.size() - 1; i >= 0; i--) {
//                Rule rule = pcs.get(i);
//                pcList.add(rule);
//            }
//            if (pcList != null) {
//                if (pcList.size() > 0)
//                    mv.addObject("menus", pcList.get(0));
//                if (pcList.size() > 1)
//                    mv.addObject("cMenus", pcList.get(1));
//                if (pcList.size() > 2)
//                    mv.addObject("ccMenus", pcList.get(2));
//                if (pcList.size() > 3)
//                    mv.addObject("cccMenus", pcList.get(3));
//            }
//            if (pcs != null && pcs.size() >= 2) {
//                Rule rule = pcs.get(0);
//                mv.addObject("curMenuid", rule.getId());
//                if (rule.getType() == 2) {
//                    Rule r = pcs.get(1);
//                    mv.addObject("showMenuid", r.getId());
//                } else {
//                    mv.addObject("showMenuid", rule.getId());
//                }
//            }
//            //排序好的菜单树
//            for (int i = 0; i < ruleList.size(); i++) {
//                Rule rule = (Rule) ruleList.get(i);
//                if (DictConstant.RULE_MUEN == rule.getType().intValue()) {
//                    Rule menu = new Rule();
//                    menu.setTitle(rule.getTitle());
//                    menu.setId(rule.getId());
//                    menu.setPid(rule.getPid());
//                    menu.setIcon(rule.getIcon());
//                    menu.setType(rule.getType());
//                    menu.setUrl(rule.getUrl());
//                    menu.setChildren(rule.getChildren());
//                    menuList.add(menu);
//                }
//            }
//        }
//        mv.addObject("menuList", getMenuTree(menuList));
//        mv.addObject("pcList", pcList);
//        mv.addObject("base", base);
//        mv.addObject("baseDescribe", "摩森特云平台");
//        super.render(mv, request, response);
//    }
//
//    /*
//     * 父子关系列表
//     */
//    private List<Rule> getPClist(List<Rule> list, String url) {
//        List<Rule> pList = new ArrayList<Rule>();
//        Rule rule = null;
//        for (int i = 0; i < list.size(); i++) {
//            Rule r = list.get(i);
//            if (r.getUrl().indexOf(url) != -1) {
//                rule = r;
//                pList.add(r);
//                break;
//            }
//        }
//        if (rule != null) {
//            buildPClist(pList, list, rule);
//        }
//        return pList;
//    }
//
//    private void buildPClist(List<Rule> pList, List<Rule> list, Rule child) {
//        Rule rule = null;
//        for (int i = 0; i < list.size(); i++) {
//            Rule ru = list.get(i);
//            if (ru.getId().intValue() == child.getPid().intValue()) {
//                rule = ru;
//                pList.add(ru);
//            }
//        }
//        if (rule != null) {
//            if (rule.getPid() == 0) {
//                return;
//            } else {
//                buildPClist(pList, list, rule);
//            }
//        }
//    }
//
//    /*
//     * 菜单树
//     */
//    private List<Rule> getMenuTree(List<Rule> list) {
//
//        List<Rule> parents = new ArrayList<Rule>();
//        List<Rule> others = new ArrayList<Rule>();
//        for (Rule rule : list) {
//            if (rule.getPid() == 0) {
//                rule.setChildren(new ArrayList<Rule>());
//                parents.add(rule);
//            } else {
//                others.add(rule);
//            }
//        }
//        this.buildTree(parents, others);
//        return parents;
//    }
//
//    private void buildTree(List<Rule> parents, List<Rule> others) {
//
//        List<Rule> record = new ArrayList<Rule>();
//        for (Iterator<Rule> it = parents.iterator(); it.hasNext();) {
//            Rule vi = it.next();
//            if (vi.getId() != null) {
//                for (Iterator<Rule> otherIt = others.iterator(); otherIt.hasNext();) {
//                    Rule inVi = otherIt.next();
//                    if (vi.getId().longValue() == inVi.getPid().longValue()) {
//                        if (null == vi.getChildren()) {
//                            vi.setChildren(new ArrayList<Rule>());
//                        }
//                        vi.getChildren().add(inVi);
//                        record.add(inVi);
//                        otherIt.remove();
//                    }
//                }
//            }
//        }
//        if (others.size() == 0) {
//            return;
//        } else {
//            buildTree(record, others);
//        }
//    }
//}
