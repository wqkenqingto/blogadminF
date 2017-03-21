package com.blogadmin.blog.service.impl;

import com.blogadmin.blog.dao.BlogContentDao;
import com.blogadmin.blog.dao.OthertidingDao;
import com.blogadmin.blog.model.Article;
import com.blogadmin.blog.model.Othertidings;
import com.blogadmin.blog.service.BlogContentService;
import com.blogadmin.blog.service.OthertidingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wqkenqing on 2017/3/10.
 */
@Component
public class OthertidingsServiceImpl implements OthertidingsService {
    @Autowired
    private OthertidingDao othertidingDao;

    @Override
    public Long add(Othertidings othertidings) {
        return othertidingDao.saveEntity(othertidings);
    }


    @Override
    public Othertidings getOthertidings(Long id) {
        return othertidingDao.get(id);
    }

    @Override
    public List<Othertidings> ListOthertidings() {
        return null;
    }

    @Override
    public Long updateOthertidings(Othertidings othertidings) {
        return othertidingDao.updateEntity(othertidings);
    }

    @Override
    public Long removeOthertidings(Long id) {
        return Long.valueOf(othertidingDao.logicRemove(id));
    }
}
