package com.blogadmin.sys.service.impl;

import com.blogadmin.sys.dao.MenuDao;
import com.blogadmin.sys.model.Menu;
import com.blogadmin.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuDao menuDao;

    @Override
    public Long addMenu(Menu menu) {
        return menuDao.saveEntity(menu);
    }

    @Override
    public Menu getMenu(Long id) {
        return menuDao.get(id);
    }

    @Override
    public List<Menu> ListMenu() {
        return null;
    }

    @Override
    public Long updateBlog(Menu menu) {
        return menuDao.updateEntity(menu);
    }

    @Override
    public int removeMenu(Long id) {
        return menuDao.logicRemove(id);
    }
}
