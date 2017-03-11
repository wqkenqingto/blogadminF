package com.blogadmin.blog.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blogadmin.core.controller.BaseController;
import com.blogadmin.sys.model.User;
import com.blogadmin.blog.model.UnitTemplate;
import com.blogadmin.blog.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.blogadmin.common.constant.SessionKeyConstant;
import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.blog.model.Template;
import com.blogadmin.blog.model.Unit;
import com.blogadmin.blog.service.IUnitService;
import com.blogadmin.blog.service.IUnitTemplateService;

@Controller
@RequestMapping("/wh/template")
public class TemplateController extends BaseController {

	@Autowired
    private ITemplateService templateService;
	@Autowired
    private IUnitService 				unitService;
	@Autowired
    private IUnitTemplateService 		unitTemplateService;
	
	@RequestMapping(value = "index.htm", method = RequestMethod.GET)
    public String index() {
        return getNameSpace() + "index";
    }
	
	@RequestMapping(value = "upload.htm", method = RequestMethod.GET)
    public String upload() {
        return getNameSpace() + "upload";
    }
	
	/**
	 * 分页查询模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryPaged.htm", method = RequestMethod.POST)
    public void query(HttpServletRequest request, HttpServletResponse response) {
		try {
        	Map<String, Object> paramMap = bindMapObj(request);
            ListJsonResult<Template> result = templateService.queryTemplatePage(paramMap);
            super.printJson(response, result.toJsonString());
        } catch (Exception e) {
            LOGGER.error("queryPaged", e);
        }
    }
	
	/**
	 * 为个体分配模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "allot.htm", method = RequestMethod.POST)
    public void allot(HttpServletRequest request, HttpServletResponse response) {
		//处理基数
		int saveNum = 10000;
		//保存关联关系list
		List<UnitTemplate> saveUtList = new ArrayList<UnitTemplate>();
		//复制模板列表
		List<Template> templateCopyList = new ArrayList<Template>();
		//获取个体编码为“MU”开头个体
		List<Unit> mUnitList = unitService.getMuUnit();
		//获取模板数量
		int templateCount = Integer.valueOf(templateService.getCount());
		int num = templateCount/saveNum + 1;
		//变量定义
		String ucode = "";
		String uname = "";
		String tcode = "";
		String content = "";
		UnitTemplate ut = null;
		//获取模板
		List<Template> templateList = new ArrayList<Template>();
		for(int i = 0; i < num; i++){
			templateList = templateService.getLimit(saveNum * i, saveNum * (i+1));
			templateCopyList.addAll(templateList);
			//使用MU个体遍历模板
			for(Unit munit : mUnitList){
				ucode = munit.getUcode();
				uname = munit.getUname();
				for(Template template : templateList){
					tcode = template.getCode();
					content = template.getContent();
					//如果模板中包含该个体名称，添加一条个体模板关联表数据
					if(content.contains(uname)){
						ut = new UnitTemplate();
						ut.setUcode(ucode);
						ut.setTcode(tcode);
						
						saveUtList.add(ut);
						if(saveUtList.size() == saveNum){
							unitTemplateService.saveUnitTemplate(saveUtList);
							saveUtList.clear();
						}
						//从模板copy列表中删除该模板
						templateCopyList.remove(template);
					}
				}
			}
			
			//保存剩余数据
			if(saveUtList.size() != 0){
				unitTemplateService.saveUnitTemplate(saveUtList);
			}
		}
		//循环结束后遍历模板copy列表，将剩余模板个体号为*，模板号为模板号存入关联表
//		for(Template templateCopy : templateCopyList){
//			ut = new UnitTemplate();
//			ut.setUcode("*");
//			ut.setTcode(templateCopy.getCode());
//			
//			saveUtList.add(ut);
//			if(saveUtList.size() == saveNum){
//				unitTemplateService.saveUnitTemplate(saveUtList);
//				saveUtList.clear();
//			}
//		}
    }
	
	/**
	 * 添加上传文件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addUploadFile.htm", method = RequestMethod.POST)
    public void upload(HttpServletRequest request, HttpServletResponse response) {
		//获取用户名作为上传文件保存文件夹名
        User user = (User) request.getSession().getAttribute(SessionKeyConstant.USER);
        // 设置处理工厂缓存的临时目录，此目录下的文件需要手动删除。
        String dir = request.getSession().getServletContext().getRealPath("/");
        // 设置文件实际保存的目录
        String userdir = dir + "/upload/" + user.getUserName();
        File fudir = new File(userdir);
        if (!fudir.exists()) {
            fudir.mkdirs();
        }

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        if (multipartResolver.isMultipart(request)) {
            //获取上传文件放入Iterator
            MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            MultipartHttpServletRequest multiRequest = resolver.resolveMultipart(request);

            Iterator<String> iter = multiRequest.getFileNames();
            //遍历上传文件
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                //读取文件后生成list
                try {
                    // 由于客户端向服务器发送的文件是客户端的全路径，在这我们只需要文件名即可
                    String filename = file.getOriginalFilename();
                    int index = filename.lastIndexOf("\\");
                    if (index != -1) {
                        filename = filename.substring(index + 1);
                    }
                    // 向服务器写出文件
                    InputStream in = file.getInputStream();
                    FileOutputStream fos = new FileOutputStream(fudir + "/" + filename);
                    byte[] buf = new byte[1024];
                    int len = -1;
                    while ((len = in.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关闭流
                    if (in != null) {
                        try {
                            in.close();
                        } finally {
                            if (fos != null)
                                fos.close();
                        }
                    }
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    LOGGER.error("上传文件FileNotFound异常："+e.getMessage());
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                	LOGGER.error("上传文件IllegalState异常："+e.getMessage());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                	LOGGER.error("上传文件IO异常："+e.getMessage());
                }
            }
        }
	}
	
	/**
     * 删除上传文件.
     */
    @RequestMapping(value = "removeUploadFile.htm", method = RequestMethod.POST)
    public void removeUploadFile(HttpServletRequest request, HttpServletResponse response) {
        //获取用户名作为上传文件保存文件夹名
        User user = (User) request.getSession().getAttribute(SessionKeyConstant.USER);
        //获取删除文件名
        String removeFileName = request.getParameter("removeFileName");
        //获取服务器路径
        String dir = request.getSession().getServletContext().getRealPath("/");
        //获取文件路径
        String userdir = dir + "/upload/" + user.getUserName() + "/" + removeFileName;
        //获取文件
        File fudir = new File(userdir);
        //删除文件
        fudir.delete();
    }

    /**
     * 提交上传文件.
     */
    @RequestMapping(value = "commitUploadFile.htm", method = RequestMethod.POST)
    public void commitUploadFile(HttpServletRequest request, HttpServletResponse response) {
        //获取用户名作为上传文件保存文件夹名
        User user = (User) request.getSession().getAttribute(SessionKeyConstant.USER);
        //获取服务器路径
        String dir = request.getSession().getServletContext().getRealPath("/");
        //获取文件路径
        String userdir = dir + "/upload/" + user.getUserName();

		super.printJson(response, templateService.uploadaTemplate(userdir));
    }
    
    /**
     * 删除模板
     */
    @RequestMapping(value = "delete.htm", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response) {
    	try {
            String ids = request.getParameter("ids");
            for (String id : ids.split(",")) {
            	templateService.deleteTemplate(Long.parseLong(id));
            }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            LOGGER.error("delete", e);
            printJson(response, messageFailureWrap("删除失败！"));
        }
    }
}
