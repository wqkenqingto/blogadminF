package com.blogadmin.blog.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.blogadmin.blog.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blogadmin.common.utils.CommUtil;
import com.blogadmin.core.utils.response.BaseResult;
import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.blog.dao.ITemplateDao;
import com.blogadmin.blog.model.Template;

@Component
public class TemplateService implements ITemplateService {

	@Autowired
    private ITemplateDao templateDao;
	
	/**
	 * 分页查询模板
	 */
	@Override
	public ListJsonResult<Template> queryTemplatePage(Map<String, Object> paramMap) {
		return templateDao.queryDTPage(paramMap);
	}

	/**
	 * 处理上传的模板并保存
	 */
	@Override
	public String uploadaTemplate(String userdir) {
		//返回类
		BaseResult result = new BaseResult();
		//新增列表
		List<Template> saveList = new ArrayList<Template>();
		//文件路径
		String filename = "";
		//获取文件
		File file = new File(userdir);
		File[] files = file.listFiles();
		//获取当前全部模板
		List<Template> tList = templateDao.getAll();
		//获取自增code生成位置
		String codePlace = templateDao.getCodePlace();
		//保存标记
		boolean saveFlag = true;
		 //当前行
		String currentLine = null;
		String currentLineTemp = null;
		//上一行
		String previousLine = null;
		String previousLineTemp = null;
		//读取文件
		for (int i = 0; i < files.length; i++) {
	        if (!files[i].isDirectory() && !files[i].getName().equals(".DS_Store")){
	        	//获取文件名
	        	filename = files[i].getAbsolutePath();
	        	try {
	        		//读取文件
		    		InputStreamReader read =new InputStreamReader(new FileInputStream(filename), "utf-8");
		            BufferedReader br = new BufferedReader(read);
		    		
		    		//读取行
		    		while ((currentLine = br.readLine()) != null) {
		    			//如果是第一行，直接赋值进入下一次循环
		            	if(previousLine == null){
		            		previousLine = currentLine;
		            		continue;
		            	}
		            	//如果当前行和上一行相同跳过
		            	currentLineTemp = currentLine.trim();
		            	currentLineTemp = currentLineTemp.replaceAll("%", "");
		            	currentLineTemp = currentLineTemp.replaceAll("\\*", "");
		            	previousLineTemp = previousLine.trim();
		            	previousLineTemp = previousLineTemp.replaceAll("%", "");
		            	previousLineTemp = previousLineTemp.replaceAll("\\*", "");
		            	if(currentLineTemp.equals(previousLineTemp)){
		            		continue;
	            		}
		            	//如果不相同
		            	else{
		            		//判断该模板在所有模板中是否存在
		            		saveFlag = true;
		            		for(Template t : tList){
		            			if(currentLine.equals(t.getContent())){
		            				saveFlag = false;
		            			}
		            		}
		            		if(saveFlag){
		            			Template template = new Template();
		            			template.setContent(currentLine);
		            			codePlace = CommUtil.increaseCode("MT",codePlace);
		            			template.setCode(codePlace);
				            	saveList.add(template);
		            		}
		            	}
		            	//每读50000条保存一次新增列表
		            	if(saveList.size() == 1000){
		    				templateDao.saveBatchEntity(saveList);
		    				saveList.clear();
		    			}
		            	//更新上一行
		            	previousLine = currentLine;
		            }
		    		//保存剩余数据
	            	if(saveList.size() != 0){
		            	templateDao.saveBatchEntity(saveList);
		            	saveList.clear();
		            }
	        	} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	      //上传后删除文件
	      files[i].delete();
	    }
		//返回结果
		return result.toJsonString();
	}

	/**
	 * 删除模板
	 */
	@Override
	public int deleteTemplate(Long id) {
		return templateDao.logicRemove(id);
	}

	/**
	 * 一次获取所有数据
	 */
	@Override
	public List<Template> getAll() {
		return templateDao.getAll();
	}

	/**
	 * 获取模板总数
	 */
	@Override
	public String getCount() {
		return templateDao.getCount();
	}

	@Override
	public List<Template> getLimit(int begin, int end) {
		return templateDao.getLimit(begin, end);
	}
	
}
