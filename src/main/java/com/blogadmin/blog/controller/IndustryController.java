package com.blogadmin.blog.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blogadmin.common.utils.CommUtil;
import com.blogadmin.core.controller.BaseController;
import com.blogadmin.blog.model.Template;
import com.blogadmin.blog.model.Unit;
import com.blogadmin.blog.service.IIndustryService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.blogadmin.blog.model.Industry;

@Controller
@RequestMapping("/wh/industry")
public class IndustryController extends BaseController {

	@Autowired
    private IIndustryService industryService;
	
	@RequestMapping(value = "index.htm", method = RequestMethod.GET)
    public String index() {
        return getNameSpace() + "index";
    }
	
	@RequestMapping(value = "allot.htm", method = RequestMethod.GET)
	public String allot(Long id, String level, Model model) {
		Industry industry = industryService.get(id);
		model.addAttribute("industry", industry);
		model.addAttribute("flag", level);
		if(level.equals("true")){
			model.addAttribute("industryLevel", "主行业");
		}else{
			model.addAttribute("industryLevel", "次行业");
		}
		return getNameSpace() + "allot";
	}
	
	/**
	 * 查询所有行业
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "query.htm", method = RequestMethod.POST)
    public void query(HttpServletRequest request, HttpServletResponse response) {
        List<Industry> result = industryService.getAll();
        super.printJson(response, JSON.toJSONString(result));
    }
	
	/**
	 * 保存行业
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "save.htm", method = RequestMethod.POST)
    public String add(HttpServletRequest request, HttpServletResponse response) {
		String oper = request.getParameter("oper");
		//新增
		if ("add".equals(oper)) {
			Industry industry = new Industry();
			//行业编码
			String code = request.getParameter("code");
			industry.setCode(request.getParameter("code"));
	        //行业名称
			industry.setName(request.getParameter("name"));
			//行业hb表名
			String hbName = "hb_I_"+ code;
			industry.setHbname(hbName);
			//数量
			industry.setCount("0");
			
			industryService.saveIndustry(industry);
        }
		//删除
		else if ("del".equals(oper)) {
    		//获取删除的id列表
    		String[] delIdList = request.getParameter("id").split(",");
    		
        	industryService.deleteIndustry(delIdList);
        }
		
		return getNameSpace() + "index";
    }
	
	/**
	 * 创建仓库
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "createDataWare.htm", method = RequestMethod.POST)
    public void createDataWare(HttpServletRequest request, HttpServletResponse response) {
		/*--------------------mapreduce初始加载全局配置--------------------*/
		//获取所有行业
		List<Industry> industryList = new ArrayList<Industry>();
		//获取所有个体
		List<Unit> unitList = new ArrayList<Unit>();
		//生成个体名称与code map
		Map<String,String> unitNameCode = new HashMap<String,String>();
		//生成个体code与主行业code
		Map<String,String> unitCodeMind = new HashMap<String,String>();
		//生成个体code与次行业code
		Map<String,String> unitCodeSind = new HashMap<String,String>();
		//获取所有模板
		List<Template> templateList = new ArrayList<Template>();
		//创建Hbase本地配置
    	Configuration conf = HBaseConfiguration.create();
        conf.setLong(HConstants.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD, 2000000);
        conf.setLong(HConstants.HBASE_CLIENT_SCANNER_CACHING, 100000);
        conf.set("hbase.zookeeper.quorum", "hadoop220,hadoop221,hadoop234,hadoop235,hadoop236,hadoop237,hadoop238,hadoop239,hadoop240,hadoop241,hadoop242");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("fs.default.name", "hdfs://mocentreware/hbase");
        conf.set("dfs.nameservices", "mocentreware");
        conf.set("dfs.ha.namenodes.mocentreware", "nn1,nn2");
        conf.set("dfs.namenode.rpc-address.mocentreware.nn1", "hadoop220:8020");
        conf.set("dfs.namenode.rpc-address.mocentreware.nn2", "hadoop221:8020");
        conf.set("dfs.client.failover.proxy.provider.mocentreware","org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        //初始化所有行业表
        Map<String,Table> hbTableMap = new HashMap<String,Table>();
        try {
        	Connection connection = ConnectionFactory.createConnection(conf);
	        for(Industry industry : industryList){
				Table hbTable = connection.getTable(TableName.valueOf(industry.getHbname()));
				hbTableMap.put(industry.getCode(),hbTable);
	        }
        
			//solr服务
	        SystemDefaultHttpClient httpClient = new SystemDefaultHttpClient();
	        HttpSolrClient solrServer = new HttpSolrClient("http://hadoop220:8080/solr/rawcollection_shard11_replica1",httpClient);
			/*--------------------模拟原始数据行内容--------------------*/
			//城市
			String city = "";
			//省份
			String provice = "";
			//日期
			String date = "";
			//当前内容
			String content = "";
			//发送是否成功
			String isSend = "";
			/*------------------------变量定义------------------------*/
			 //存储hbase put list
			List<Put> mPutSaveList = new ArrayList<Put>();
			List<Put> sPutSaveList = new ArrayList<Put>();
			//存储solr document list
			List<SolrInputDocument> solrSaveList = new ArrayList<SolrInputDocument>();
			//solr查询
			SolrQuery solrQuery = null;
			//solr查询返回
			QueryResponse solrResponse = null;
			//solr结果list
			SolrDocumentList solrResultsList = null;
			//solr结果
			SolrDocument solrReslult = null;
			//个体对应行业
			String industry = "";
			//行业表
			Table mIndHbTable;
			//行业表
			Table sIndHbTable;
			//行业表自增位置
			String increment = "";
			//行业表自增位置solr key
			String incrementSolrKey = "";
			//rowkey
			String rowkey = "";
			//4位格式日期 由date处理获得，年两位加月份两位，例 ：1610表示2016年10月
			String date4 = "";
			String unitName = "";
			String unitCode = "";
			String templateCode = "";
			String mindCode = "";
			String sindCode = "";
			String mincrementSolrKey = "";
			String sincrementSolrKey = "";
			String mincrement = "";
			String sincrement = "";
			String mRowkey = "";
			String sRowkey = "";
			
			/*----------------------map中处理开始------------------------*/
			//获取4位格式date(方法待实现)
			date4 = date;
			//提取原始短信内容中个体(方法略)
			unitName = content;
			//获取对应个体code
			unitCode = unitNameCode.get(unitName);
			//如果原始短信内容中没有个体，存“*”
			if(unitCode == null){
				unitCode = "*";
			}
			//获取对应模板code
			for(Template templateTemp : templateList){
				//如果包括当前模板
				if(content.contains(templateTemp.getContent())){
					//获取当前模板编号
					templateCode = templateTemp.getCode();
					//结束循环
					break;
				}
			}
			//获取手机号 略
			//获取省份 略
			//获取城市 略
			//获取内容 略
			//获取发送状态 略
			/*----------------------reduce中处理开始------------------------*/
			//获取solr中保存的自增位置
			solrQuery = new SolrQuery("rowkey:incrementPosition");
			solrResponse = solrServer.query(solrQuery);
			solrResultsList = solrResponse.getResults();
			if(solrResultsList.size() != 0){
				solrReslult = solrResultsList.get(0);
			}
			
			//遍历数据
				//获取主行业code
				mindCode = unitCodeMind.get(unitCode);
				//获取次行业code
				sindCode = unitCodeSind.get(unitCode);
				
				//根据行业code与date4提取应该自增的位置
				mincrementSolrKey = mindCode+"_"+date4+"_solr_s";
				sincrementSolrKey = sindCode+"_"+date4+"_solr_s";
				mincrement = String.valueOf(solrReslult.get(mincrementSolrKey));
				sincrement = String.valueOf(solrReslult.get(sincrementSolrKey));
				mincrement = CommUtil.incrementRowkey12(mincrement);
				sincrement = CommUtil.incrementRowkey12(sincrement);
				solrReslult.setField(mincrementSolrKey, mincrement);
				solrReslult.setField(sincrementSolrKey, sincrement);
				mRowkey = date4 + mincrement;
				sRowkey = date4 + sincrement;
				
				Put mPut = new Put(Bytes.toBytes(mRowkey));
				//存储个体code
				mPut.addColumn(Bytes.toBytes("info"), Bytes.toBytes("ucode"), Bytes.toBytes(unitCode));
				//存储模板code
				mPut.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tcode"), Bytes.toBytes(templateCode));
				//存储省份 略
				//存储城市 略
				//存储手机号 略
				//存储短信内容 略
				//存储状态 略
				
				Put sPut = new Put(Bytes.toBytes(sRowkey));
				//同主行业相同存储 略
				
				//put放入保存列表
				mPutSaveList.add(mPut);
				sPutSaveList.add(sPut);
				
				//存储主行业solr
				SolrInputDocument mSolrDoc = new SolrInputDocument();
				//存储对应rowkey
				mSolrDoc.addField("rowkey",mRowkey);
				//存储行业编码
				mSolrDoc.addField("icode_solr_s", mindCode);
				//存储个体编码
				mSolrDoc.addField("ucode_solr_s", unitCode);
				//存储模板编码
				mSolrDoc.addField("tcode_solr_s", templateCode);
				
				//存储次行业solr
				SolrInputDocument sSolrDoc = new SolrInputDocument();
				//存储对应rowkey
				sSolrDoc.addField("rowkey",sRowkey);
				//存储行业编码
				mSolrDoc.addField("icode_solr_s", sindCode);
				//存储个体编码
				sSolrDoc.addField("ucode_solr_s", unitCode);
				//存储模板编码
				sSolrDoc.addField("tcode_solr_s", templateCode);
				
				//solr放入保存列表
				solrSaveList.add(mSolrDoc);
				
				//50000添加一次 略，以下是保存方法
		    		//获取行业表
					mIndHbTable = hbTableMap.get(mindCode);
					sIndHbTable = hbTableMap.get(mindCode);
					//保存put
					mIndHbTable.put(mPutSaveList);
					mIndHbTable.close();
					sIndHbTable.put(sPutSaveList);
					sIndHbTable.close();
					//保存solr
					solrServer.add(solrSaveList);
					solrServer.commit(true, true, true);
					//清空list
					mPutSaveList.clear();
					sPutSaveList.clear();
					solrSaveList.clear();
				
			//所有数据添加完后将新的rowkey存回solr保留
			//创建solr document
			SolrInputDocument solrDoc = new SolrInputDocument();
    		for(String key : solrReslult.keySet()){
    			solrDoc.addField(key, solrReslult.get(key));
    		}
    		solrServer.add(solrDoc);
			solrServer.commit(true, true, true);
    		
		
        } catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
