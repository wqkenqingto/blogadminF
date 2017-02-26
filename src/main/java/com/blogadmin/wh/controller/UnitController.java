package com.blogadmin.wh.controller;


import com.alibaba.fastjson.JSON;
import com.blogadmin.wh.service.IUnitMapReducerService;
import com.blogadmin.common.hdfs.HfileToEntity;
import com.blogadmin.core.controller.BaseController;
import com.blogadmin.wh.model.Unit;
import com.blogadmin.wh.service.IUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author wangkuiqing
 * @Date 2016/11/7 17:36
 * @ClassName:UnitController
 * @Description:个体管理模块
 */
@Controller
@RequestMapping("/wh/unit")
public class UnitController extends BaseController {
    String root, input, output;
    List<String> names;
    @Autowired
    private IUnitMapReducerService mapReducerService;
    @Autowired
    private IUnitService unitService;

    @RequestMapping(value = "add.htm", method = RequestMethod.GET)
    public String menu(Model model, HttpServletRequest request, HttpServletResponse response) {
        return getNameSpace() + "add";
    }

    @RequestMapping(value = "analyse.htm", method = RequestMethod.GET)
    public String analyseUnit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("进入了接口-----");
        List<String> arg = new ArrayList<String>();
        //参数初始化：根目录、输入地址、输出地址、指定map类、指定reduce类
        root = request.getParameter("root");
        input = request.getParameter("input");
        output = request.getParameter("output");
        String MClassName = request.getParameter("MClassName");
        String RClassName = request.getParameter("RClassName");
        arg.add(root);
        arg.add(input);
        arg.add(output);
        arg.add(MClassName);
        arg.add(RClassName);
        String[] array = {arg.get(0), arg.get(1), arg.get(2), arg.get(3), arg.get(4)};
        try {
            String result = mapReducerService.testMr(array);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("这里是异常" + e);
        }
        return "redirect:read.htm";
    }

    @RequestMapping(value = "read.htm", method = RequestMethod.GET)
    public String readUnit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        output = output + "/part-r-00000";
        List<Unit> units = new ArrayList<Unit>();
        units = HfileToEntity.htoUnit(output);
        Unit unit = new Unit();
        System.out.println(units.size());
        System.out.println(units.get(units.size() - 1).getUname() + units.get(0).getUcode());
        names = unitService.getUnames();
        for (Unit unit1 : units) {
            if (names.contains(unit.getUname())) ;
            units.remove(unit);
        }
        if(units.size()>0){
        unitService.saveUnits(units);
        System.out.println("更新完成");
        }else {
            System.out.println("个体数据库未更新");
        }
        return null;
    }
    
    @RequestMapping(value = "queryFirst.htm", method = RequestMethod.POST)
    public void queryFirst(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	List<Unit> unitList = unitService.queryFirst();
    	super.printJson(response, JSON.toJSONString(unitList));
    }
    
    @RequestMapping(value = "querySecond.htm", method = RequestMethod.POST)
    public void querySecond(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("mindustry", request.getParameter("mindustry"));
    	List<Unit> unitList = unitService.querySecond(paramMap);
    	super.printJson(response, JSON.toJSONString(unitList));
    }
}
