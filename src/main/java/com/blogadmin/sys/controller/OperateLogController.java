package com.blogadmin.sys.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blogadmin.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.sys.model.OperateLog;
import com.blogadmin.sys.service.IOperateLogServie;

@Controller
@RequestMapping("/sys/log")
public class OperateLogController extends BaseController {

    @Autowired
    private IOperateLogServie operateLogService;

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
            ListJsonResult<OperateLog> result = operateLogService.queryPage(paramMap);
            super.printJson(response, result.toJsonString());
        } catch (Exception e) {
            LOGGER.error("queryPaged", e);
        }
    }
}
