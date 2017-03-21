package com.blogadmin.blog.service;

import com.blogadmin.blog.model.Message;
import com.blogadmin.blog.model.Othertidings;

import java.util.List;

/**
 * @className:MessageIOService
 * @author:wqkenqing
 * @describe:message交流接口
 * @date:2017/3/10
 **/
public interface OthertidingsService {
    public Long add(Othertidings othertidings);

    public Othertidings getOthertidings(Long id);

    public List<Othertidings> ListOthertidings();

    public Long updateOthertidings(Othertidings othertidings);

    public Long removeOthertidings(Long id);
}
