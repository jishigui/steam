package com.service.impl;

import com.bean.PublishBean;
import com.dao.PublishDao;
import com.dao.impl.PublishDaoImpl;
import com.service.PublishService;

import java.util.List;

public class PublishServiceImpl implements PublishService {

    PublishDao publishDao = new PublishDaoImpl();

    @Override
    public int insert(PublishBean publishBean) throws Exception {
        return publishDao.insert(publishBean);
    }

    @Override
    public List queryPublish(PublishBean publishBean) throws Exception {
        return publishDao.queryPublish(publishBean);
    }
}
