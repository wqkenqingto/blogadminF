package com.blogadmin.blog.dao.impl;

import com.blogadmin.blog.dao.OthertidingDao;
import com.blogadmin.blog.model.Othertidings;
import com.blogadmin.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by wqkenqing on 2017/3/17.
 */
@Repository
public class OthertidingsDaoImpl extends BaseDao<Othertidings> implements OthertidingDao {
    public Integer delete(Othertidings article) {
        return null;
    }
}
