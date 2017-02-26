package com.blogadmin.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.blogadmin.core.model.BaseEntity;
import com.blogadmin.core.service.support.paging.PageHelper;
import com.blogadmin.core.service.support.paging.PageInfo;
import com.blogadmin.core.utils.response.ListJsonResult;

/**
 * MyBatis Dao的泛型基类.
 * 继承于Spring的SqlMapClientDaoSupport,提供分页函数和若干便捷查询方法，并对返回值作了泛型类型转换.
 */
@SuppressWarnings("unchecked")
public abstract class MybatisGenericDao {

    private static final Logger LOG                      = Logger.getLogger(MybatisGenericDao.class);

    public static final String  POSTFIX_INSERT           = "_insert";
    public static final String  POSTFIX_INSERT_BATCH	 = "_insert_batch";
    public static final String  POSTFIX_UPDATE           = "_update";
    public static final String  POSTFIX_UPDATE_SELECTIVE = "_updateSelective";
    public static final String  POSTFIX_LOGIC_DELETE     = "_logicDelete";
    public static final String  POSTFIX_DELETE           = "_delete";
    public static final String  POSTFIX_DELETE_BYKEY     = "_deleteByPrimaryKey";
    public static final String  POSTFIX_GET              = "_get";
    public static final String  POSTFIX_SELECT           = "_list";
    public static final String  POSTFIX_COUNT            = "_count";

    @Autowired
    private SqlSession          sqlSession;

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    // CRUD-------------------------------------------------------------------------
    /**
     * 根据ID获取对象.
     * 
     * @throws 如果对象不存在 ，则抛出ObjectRetrievalFailureException异常.
     */
    public <T> T get(Class<T> entityClass, Serializable id) {
        Assert.notNull(id);
        T o = (T) sqlSession.selectOne(entityClass.getSimpleName() + POSTFIX_GET, id);
        return o;
    }

    /**
     * 获取全部对象.
     */
    public <T> List<T> getAll(Class<T> entityClass) {
        return sqlSession.selectList(entityClass.getSimpleName() + POSTFIX_SELECT);
    }

    /**
     * 插入对象
     * 
     * @param o
     * @return
     */
    public Long save(BaseEntity o) {
        Assert.notNull(o);
        sqlSession.insert(o.getClass().getSimpleName() + POSTFIX_INSERT, o);
        return o.getId();
    }
    
    /**
     * 批量插入对象
     * @param <T>
     * 
     * @param List<T>
     * @return 
     * @return
     */
    public <T> Long saveBatch(List<T> l) {
        Assert.notNull(l);
        sqlSession.insert(l.get(0).getClass().getSimpleName() + POSTFIX_INSERT_BATCH, l);
        return null;
    }

    /**
     * 更新对象
     * 
     * @param o
     * @return
     */
    public Long update(BaseEntity o) {
        Assert.notNull(o);
        sqlSession.update(o.getClass().getSimpleName() + POSTFIX_UPDATE, o);
        return o.getId();

    }

    /**
     * 逻辑删除对象
     * 
     * @param o
     * @return
     */
    public <T> int logicRemove(Class<T> entityClass, Serializable id) {
        Assert.notNull(id);
        return sqlSession.update(entityClass.getSimpleName() + POSTFIX_LOGIC_DELETE, id);
    }

    /**
     * 删除对象.
     */
    public int remove(String statement, BaseEntity o) {
        Assert.notNull(o);
        return sqlSession.delete(statement, o);
    }

    /**
     * 删除对象.
     */
    public int remove(BaseEntity o) {
        Assert.notNull(o);
        return sqlSession.delete(o.getClass().getSimpleName() + POSTFIX_DELETE, o);
    }

    /**
     * 根据主键删除对象.
     */
    public <T> T removeById(Class<T> entityClass, Serializable id) {
        Assert.notNull(id);
        sqlSession.delete(entityClass.getSimpleName() + POSTFIX_DELETE_BYKEY, id);
        return null;
    }

    /**
     * 查询jQuery Datatables分页列表（指定的sqlId=类名_list）
     * 
     * @param paramMap
     * @return
     */
    public <T> ListJsonResult<T> queryDatatablesPage(Class<T> entityClass, Map<String, Object> paramMap) {
        return this.queryDatatablesPage(entityClass.getSimpleName() + POSTFIX_SELECT, paramMap);
    }

    /**
     * 查询jQuery Datatables分页列表
     * 
     * @param statement
     * @param paramMap
     * @return
     */
    public <T> ListJsonResult<T> queryDatatablesPage(String statement, Map<String, Object> paramMap) {
        String drawStr = String.valueOf(paramMap.get("draw"));
        int draw = StringUtils.isBlank(drawStr) ? 0 : Integer.parseInt(drawStr);
        Object indexStr = paramMap.get("order[0][column]");
        Integer index = indexStr == null ? null : Integer.parseInt(String.valueOf(indexStr));
        if (index != null) {
            String column = "columns[" + index + "][data]";
            paramMap.put("orderColumn", paramMap.get(column));
        }
        paramMap.put("orderBy", paramMap.get("order[0][dir]"));

        PageInfo<T> pageInfo = this.queryPaged(statement, paramMap);
        ListJsonResult<T> resList = new ListJsonResult<T>();
        if (pageInfo != null) {
            resList.setDraw(draw);
            resList.setRecordsTotal(pageInfo.getTotal());
            resList.setRecordsFiltered(pageInfo.getTotal());
            resList.setData(pageInfo.getRows());
        }
        return resList;
    }

    /**
     * 查询jQuery Datatables分页列表（指定的sqlId=类名_list）
     * 
     * @param entityClass
     * @param paramMap
     * @return
     */
    public <T> ListJsonResult<T> queryDTList(Class<T> entityClass, Map<String, Object> paramMap) {
        return this.queryDTList(entityClass.getSimpleName() + POSTFIX_SELECT, paramMap);
    }

    /**
     * 查询jQuery Datatables分页列表
     * 
     * @param statement
     * @param paramMap
     * @return
     */
    public <T> ListJsonResult<T> queryDTList(String statement, Map<String, Object> paramMap) {
        String drawStr = String.valueOf(paramMap.get("draw"));
        int draw = StringUtils.isBlank(drawStr) ? 0 : Integer.parseInt(drawStr);
        Object indexStr = paramMap.get("order[0][column]");
        Integer index = indexStr == null ? null : Integer.parseInt(String.valueOf(indexStr));
        if (index != null) {
            String column = "columns[" + index + "][data]";
            paramMap.put("orderColumn", paramMap.get(column));
        }
        paramMap.put("orderBy", paramMap.get("order[0][dir]"));

        List<T> list = query(statement, paramMap);
        ListJsonResult<T> resList = new ListJsonResult<T>();
        if (list != null) {
            resList.setDraw(draw);
            resList.setRecordsTotal(list.size());
            resList.setRecordsFiltered(list.size());
            resList.setData(list);
        }
        return resList;
    }

    /**
     * 查询列表（指定sqlId=类名_list）
     * 
     * @param parameterMap 包含各种属性的查询
     */
    public <T> List<T> query(Class<T> entityClass, Map parameterMap) {
        Assert.notNull(parameterMap);
        return this.sqlSession.selectList(entityClass.getSimpleName() + POSTFIX_SELECT, parameterMap);
    }

    /**
     * 查询List列表
     * 
     * @param statement sqlId
     * @param parameterMap 包含各种属性的查询
     * @return
     */
    public <T> List<T> query(String statement, Map parameterMap) {
        Assert.notNull(statement);
        Assert.notNull(parameterMap);
        return this.sqlSession.selectList(statement, parameterMap);
    }

    /**
     * 查询分页列表（指定的sqlId=类名_list）
     * 
     * @param entityClass
     * @param parameterMap
     * @return
     */
    public <T> PageInfo<T> queryPaged(Class<T> entityClass, Map<String, Object> parameterMap) {
        Assert.notNull(parameterMap);
        return this.queryPaged(entityClass.getSimpleName() + POSTFIX_SELECT, parameterMap);
    }

    /**
     * 查询分页列表
     * 
     * @param statement sqlId
     * @param parameterMap 包含各种属性的查询
     * @return
     */
    public <T> PageInfo<T> queryPaged(String statement, Map<String, Object> parameterMap) {
        Assert.notNull(statement);
        Assert.notNull(parameterMap);
        String rowsStr = parameterMap.get("length") == null ? null : (String) parameterMap.get("length");
        String startStr = parameterMap.get("start") == null ? null : (String) parameterMap.get("start");
        // 第几页
        int page = 1;
        // 每页显示数量
        int rows = 1;
        try {
            rows = rowsStr != null ? Integer.valueOf(rowsStr) : rows;
            page = startStr == null || "0".equals(startStr) ? page : (Integer.valueOf(startStr) + rows) / rows;
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageHelper.startPage(page, rows);
        List<T> list = query(statement, parameterMap);
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        return pageInfo;
    }

    /**
     * 得到符合查询条件的记录数（指定的sqlId=类名_count）
     * 
     * @param entityClass
     * @param parameterMap
     * @return
     */
    public <T> Integer getCount(Class<T> entityClass, Map<String, Object> parameterMap) {
        Integer totalCount = (Integer) this.sqlSession.selectOne(entityClass.getSimpleName() + POSTFIX_COUNT,
                parameterMap);
        return totalCount;
    }

    /**
     * 查询唯一值（指定的sqlId=类名_list）
     * 
     * @param map 包含各种属性的查询
     */
    public <T> T queryUniquely(Class<T> entityClass, Map map) {
        Assert.notNull(map);
        return (T) sqlSession.selectOne(entityClass.getSimpleName() + POSTFIX_SELECT, map);
    }

    /**
     * map查询唯一值
     * 
     * @param map 包含各种属性的查询
     */
    public <T> T queryUniquely(String statement, Map map) {
        Assert.notNull(statement);
        Assert.notNull(map);
        map.put("findBy", "True");
        return (T) sqlSession.selectOne(statement, map);
    }

    // CRUD-------------------------------------------------------------------------

}
