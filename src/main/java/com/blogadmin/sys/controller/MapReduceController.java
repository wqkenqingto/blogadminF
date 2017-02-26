package com.blogadmin.sys.controller;


import com.blogadmin.core.controller.BaseController;
import com.blogadmin.sys.service.IMapReducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqkenqin on 2016/10/28.
 * Description:
 */
@Controller
@RequestMapping("/sys/mr")
public class MapReduceController extends BaseController {
    @Autowired
    IMapReducerService mapReducerService;

    @RequestMapping(value = "add.htm", method = RequestMethod.GET)
    public String menu(Model model, HttpServletRequest request, HttpServletResponse response) {
        return getNameSpace() + "add";
    }
    @RequestMapping(value = "test.htm", method = RequestMethod.GET)
    public String testMr(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("进入了接口-----");
        List<String> arg = new ArrayList<String>();
        //参数初始化：根目录、输入地址、输出地址、指定map类、指定reduce类
        String root = request.getParameter("root");
        String input = request.getParameter("input");
        String output = request.getParameter("output");
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

        return null;
    }
}
