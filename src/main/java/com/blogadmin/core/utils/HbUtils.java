//package com.blogadmin.core.utils;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.HColumnDescriptor;
//import org.apache.hadoop.hbase.HConstants;
//import org.apache.hadoop.hbase.HTableDescriptor;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.Admin;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.apache.hadoop.hbase.client.Get;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.hbase.client.Table;
//import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
//import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
//import org.apache.hadoop.hbase.filter.FilterList;
//import org.apache.hadoop.hbase.filter.FilterList.Operator;
//import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
//import org.apache.hadoop.hbase.filter.SubstringComparator;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.HttpSolrClient;
//import org.apache.solr.client.solrj.response.FieldStatsInfo;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//
//import com.blogadmin.common.utils.CommUtil;
//
//public class HbUtils {
//    //hbase configuration
//    private static Configuration conf = initConfiguration();
//    private static Connection connection = initConnection();
//    private static Table user_information = initTable("user_information");
//    private static HttpSolrClient solrServer = new HttpSolrClient("http://111.13.55.75:8080/solr/hbcollection_shard8_replica2");
//
//    /**
//     * 初始化hbase configuration
//     */
//    public static Configuration initConfiguration() {
//        //创建Hbase本地配置
//    	Configuration conf = HBaseConfiguration.create();
//        conf.setLong(HConstants.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD, 2000000);
//        conf.setLong(HConstants.HBASE_CLIENT_SCANNER_CACHING, 100000);
//        conf.set("hbase.zookeeper.quorum", "hadoop220,hadoop221,hadoop234,hadoop235,hadoop236,hadoop237,hadoop238,hadoop239,hadoop240,hadoop241,hadoop242");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//        conf.set("fs.default.name", "hdfs://mocentreware/hbase");
//        conf.set("dfs.nameservices", "mocentreware");
//        conf.set("dfs.ha.namenodes.mocentreware", "nn1,nn2");
//        conf.set("dfs.namenode.rpc-address.mocentreware.nn1", "hadoop220:8020");
//        conf.set("dfs.namenode.rpc-address.mocentreware.nn2", "hadoop221:8020");
//        conf.set("dfs.client.failover.proxy.provider.mocentreware","org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
//
//        return conf;
//    }
//
//    /**
//     * 初始化connection
//     * @throws IOException
//     */
//    private static Connection initConnection(){
//        try {
//			return ConnectionFactory.createConnection(conf);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//    }
//
//    /**
//     * 初始化table
//     */
//    private static Table initTable(String tablename) {
//    	Table table = null;
//        try {
//            //获取user_information表
//            table = connection.getTable(TableName.valueOf(tablename));
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return table;
//	}
//
//	/**
//     * 创建hbase表
//     * @tableName 表名
//     * @family 列族
//     */
//    public static void createHBTable(String tableName, String family) {
//        try {
//            Admin admin = connection.getAdmin();
//            HTableDescriptor hbaseTable = new HTableDescriptor(TableName.valueOf(tableName));
//            hbaseTable.addFamily(new HColumnDescriptor(family));
//            admin.createTable(hbaseTable);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 删除hbase表
//     * @tableName 表名
//     */
//    public static void deleteTable(String tableName) {
//        try {
//            Admin admin = connection.getAdmin();
//            admin.disableTable(TableName.valueOf(tableName));
//            admin.deleteTable(TableName.valueOf(tableName));
//            System.out.println(tableName + "is deleted!");
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获取AggregationClient
//     */
//    public static AggregationClient getAggregationClient() {
//        AggregationClient aggregationClient = new AggregationClient(conf);
//        return aggregationClient;
//    }
//
//    /**
//     * 根据getList获取数据
//     */
//    public static Result[] queryHbDataByGetList(List<Get> getList, String tableName) {
//    	Result[] resultList = null;
//    	try {
//			resultList = user_information.get(getList);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return resultList;
//	}
//
//	/**
//     * 保存数据
//     */
//    public static String saveData(List<Put> putListFinal, String tableName) {
//    	try {
//    		user_information.put(putListFinal);
//    		user_information.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//    /**
//     * solr统计
//     * @param paramMap 	查询条件
//     * @param rowList	统计属性列(格式:["xx:str,xx:boo,xx:max,xx:min,xx:sum,xx:avg"])
//     * @return
//     */
//    public static Map<String,Object> solrStatCount(Map<String, List<?>> paramMap, List<String> rowList){
//    	//返回结果集
//    	Map<String, Object> resultMap = new HashMap<String, Object>();
//    	//循环查询条件list
//        try {
//	        SolrQuery query = new SolrQuery(getSolrQueryParam(paramMap));
//	        Map<String, String> rowTypeMap = new HashMap<String, String>();
//	        //列名称
//	        String rowA = "";
//	        //列类型
//	    	String rowB = "";
//	        for(String row : rowList){
//	        	rowA = row.split(":")[0];
//	        	rowB = row.split(":")[1];
//	        	if(rowA.equals("rowkey")){
//	        		query.setGetFieldStatistics(rowA);
//	        	}else if(rowB.equals("str") || rowB.equals("boo")){
//	        		query.setGetFieldStatistics(rowA+"_solr_s");
//	        	}else{
//	        		query.setGetFieldStatistics(rowA+"_solr_d");
//	        	}
//	        	if(rowTypeMap.get(rowA) != null){
//	        		rowTypeMap.put(rowA, rowTypeMap.get(rowA)+":"+rowB);
//	        	}else{
//	        		rowTypeMap.put(rowA, rowB);
//	        	}
//	        }
//	        //查询结果
//	        QueryResponse response = solrServer.query(query);
//	        Map<String, FieldStatsInfo> solrMap = response.getFieldStatsInfo();
//        	for (String key : solrMap.keySet()) {
//    			FieldStatsInfo fieldStatsInfo = solrMap.get(key);
//    			String rowName = fieldStatsInfo.getName().split("_solr_")[0];
//    			String rowType = "";
//    			if(!rowName.equals("rowkey")){
//    				rowType = fieldStatsInfo.getName().split("_solr_")[1];
//    			}
//    			if(rowType.equals("d")){
//    				String[] valueList = rowTypeMap.get(rowName).split(":");
//    				for(int i = 0; i < valueList.length;i++){
//    					String value = valueList[i];
//    					if(value.equals("max")){
//        					resultMap.put(rowName+":max", fieldStatsInfo.getMax());
//        				}else if(value.equals("min")){
//        					resultMap.put(rowName+":min", fieldStatsInfo.getMin());
//        				}else if(value.equals("sum")){
//        					resultMap.put(rowName+":sum", fieldStatsInfo.getSum());
//        				}else if(value.equals("avg")){
//        					resultMap.put(rowName+":avg", CommUtil.formatDouble(fieldStatsInfo.getMean(), 2));
//        				}
//    				}
//    			}
//    			resultMap.put(rowName+":con", fieldStatsInfo.getCount());
//    		}
//        } catch (SolrServerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return resultMap;
//    }
//
//    /**
//     * solr索引查询
//     * @param paramMap 	查询条件
//     * @param tableName 表名
//     * @param rows		返回行数
//     * @return
//     */
//    public static Result[] solrQueryPage(Map<String, List<?>> paramMap,String tableName,int rows){
//    	Get get = null;
//    	Result[] resultList = null;
//    	List<Get> getList = new ArrayList<Get>();
//		try {
//			SolrQuery query = new SolrQuery(getSolrQueryParam(paramMap));
//			query.setStart(0);
//			query.setRows(rows);
//			QueryResponse response = solrServer.query(query);
//			SolrDocumentList docs = response.getResults();
//
//			for (SolrDocument doc : docs) {
//				get = new Get(Bytes.toBytes(String.valueOf(doc.getFieldValue("rowkey")).split("_")[0]));
//				getList.add(get);
//			}
//			resultList = user_information.get(getList);
//		} catch (SolrServerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return resultList;
//    }
//
//    /**
//     * solr查询条件转换
//     * @param paramMap 查询条件
//     * @return
//     */
//    public static String getSolrQueryParam(Map<String, List<?>> paramMap){
//    	StringBuffer queryParam = new StringBuffer();
//    	int i = 0;
//    	for (String code : paramMap.keySet()) {
//        	if(i != 0){
//        		queryParam.append(" AND ");
//        	}
//            //相同属性or关系
//        	int ii = 0;
//            for (Object obj : paramMap.get(code)) {
//            	@SuppressWarnings("unchecked")
//                Map<String, Object> map = (Map<String, Object>) obj;
//            	String value1 = String.valueOf(map.get("value1"));
//            	String symbol = String.valueOf(map.get("symbol"));
//            	String type = String.valueOf(map.get("type"));
//            	if(code.equals("desmobile")){
//            		code = "rowkey";
//            		symbol = "include";
//            	}else{
//            		if (type.equals("intType")) {
//                		code+="_solr_d";
//                	}else{
//                		code+="_solr_s";
//                	}
//            	}
//            	if(ii != 0){
//            		queryParam.append(" OR ");
//            	}
//            	//如果比较符为包含
//                if (String.valueOf(map.get("symbol")).equals("include")) {
//                	queryParam.append(code + ":" + "*"+ value1 +"*");
//                }
//                //如果比较符为区间
//                else if (String.valueOf(map.get("symbol")).equals("between")) {
//                	String value2 = String.valueOf(map.get("value2"));
//                	queryParam.append(code + ":" + "[" + value1 + " TO " + value2 + "]");
//                }
//                //小于
//                else if (symbol.equals("less")) {
//                	queryParam.append(code + ":" + "[* TO " + value1 + "] AND !" + code + ":" + value1);
//                }
//                //小于等于
//                else if (symbol.equals("less_or_equal")) {
//                	queryParam.append(code + ":" + "[* TO " + value1 + "]");
//                }
//                //等于
//                else if (symbol.equals("equal")) {
//                    queryParam.append(code + ":" + value1);
//                }
//                //不等于
//                else if (symbol.equals("not_equal")) {
//                    queryParam.append("!" + code + ":" + value1);
//                }
//                //大于
//                else if (symbol.equals("greater")) {
//                	queryParam.append(code + ":" + "[" + value1 + " TO *] AND !" + code + ":" + value1);
//                }
//                //大于等于
//                else if (symbol.equals("greater_or_equal")) {
//                	queryParam.append(code + ":" + "[" + value1 + " TO *]");
//                }
//                ii++;
//            }
//            i++;
//        }
//
//    	return queryParam.toString();
//    }
//
//    /**
//     * hbase查询条件转换
//     *
//     * @param paramMap 查询条件
//     * @return
//     */
//    public static FilterList mapToFilterList(Map<String, List<?>> paramMap) {
//        FilterList finalFilters = new FilterList(Operator.MUST_PASS_ALL);
//        FilterList codeFilters = new FilterList();
//        SingleColumnValueFilter scvf = null;
//        //循环查询条件list转换为filter
//        for (String code : paramMap.keySet()) {
//            //相同属性or关系过滤器
//            for (Object obj : paramMap.get(code)) {
//                @SuppressWarnings("unchecked")
//                Map<String, Object> map = (Map<String, Object>) obj;
//                //如果比较符为包含
//                if (String.valueOf(map.get("symbol")).equals("include")) {
//                    codeFilters = new FilterList(Operator.MUST_PASS_ONE);
//                    SubstringComparator comp = new SubstringComparator(String.valueOf(map.get("value1")));
//                    scvf =  new SingleColumnValueFilter(Bytes.toBytes("info"),
//                            //列名
//                            Bytes.toBytes(code),
//                            //比较符
//                            CompareOp.EQUAL,
//                            //值
//                            comp
//                    );
//                    scvf.setFilterIfMissing(true);
//                    codeFilters.addFilter(scvf);
//                }
//                //如果比较符为区间
//                else if (String.valueOf(map.get("symbol")).equals("between")) {
//                    codeFilters = new FilterList(Operator.MUST_PASS_ALL);
//                    scvf =   new SingleColumnValueFilter(Bytes.toBytes("info"),
//                            //列名
//                            Bytes.toBytes(code),
//                            //比较符
//                            CompareOp.GREATER_OR_EQUAL,
//                            //值
//                            Bytes.toBytes(CommUtil.supplementZero(Double.valueOf(String.valueOf(map.get("value1")))))
//                    );
//                    scvf.setFilterIfMissing(true);
//                    codeFilters.addFilter(scvf);
//                    scvf =   new SingleColumnValueFilter(Bytes.toBytes("info"),
//                            //列名
//                            Bytes.toBytes(code),
//                            //比较符
//                            CompareOp.LESS_OR_EQUAL,
//                            //值
//                            Bytes.toBytes(CommUtil.supplementZero(Double.valueOf(String.valueOf(map.get("value2")))))
//                    );
//                    scvf.setFilterIfMissing(true);
//                    codeFilters.addFilter(scvf);
//                } else {
//                    codeFilters = new FilterList(Operator.MUST_PASS_ONE);
//                    //转换比较符
//                    CompareOp symbol = transformSymbol(String.valueOf(map.get("symbol")));
//                    //如果属性类型为数字型
//                    if (map.get("type").equals("intType")) {
//                    	scvf =   new SingleColumnValueFilter(Bytes.toBytes("info"),
//                                //列名
//                                Bytes.toBytes(code),
//                                //比较符
//                                symbol,
//                                //值
//                                Bytes.toBytes(CommUtil.supplementZero(Double.valueOf(String.valueOf(map.get("value1")))))
//                        );
//                        scvf.setFilterIfMissing(true);
//                        codeFilters.addFilter(scvf);
//                    } else {
//                    	scvf = 	new SingleColumnValueFilter(Bytes.toBytes("info"),
//                                //列名
//                                Bytes.toBytes(code),
//                                //比较符
//                                symbol,
//                                //值
//                                Bytes.toBytes(String.valueOf(map.get("value1")))
//                        );
//                        scvf.setFilterIfMissing(true);
//                        codeFilters.addFilter(scvf);
//                    }
//                }
//            }
//            finalFilters.addFilter(codeFilters);
//        }
//        return finalFilters;
//    }
//
//    /**
//     * 比较符转换
//     * @param symbol
//     * @return
//     */
//    public static CompareOp transformSymbol(String symbol) {
//        if(symbol.equals("less")){
//            return CompareOp.LESS;
//        }
//        if(symbol.equals("less_or_equal")){
//            return CompareOp.LESS_OR_EQUAL;
//        }
//        if(symbol.equals("equal")){
//            return CompareOp.EQUAL;
//        }
//        if(symbol.equals("not_equal")){
//            return CompareOp.NOT_EQUAL;
//        }
//        if(symbol.equals("greater")){
//            return CompareOp.GREATER;
//        }
//        if(symbol.equals("greater_or_equal")){
//            return CompareOp.GREATER_OR_EQUAL;
//        }
//
//        return CompareOp.NO_OP;
//    }
//
//}