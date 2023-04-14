package com.service.impl;

import com.bean.GameTagBean;
import com.dao.GameTagDao;
import com.dao.impl.GameTagDaoImpl;
import com.service.GameTagService;
import com.util.PageUtil;

public class GameTagServiceImpl implements GameTagService {

    GameTagDao gameTagDao = new GameTagDaoImpl();

    @Override
    public PageUtil queryGameByTagPage(GameTagBean gameTagBean, int pageSize, int currentPage) throws Exception {
        return gameTagDao.queryGameByTagPage(gameTagBean, pageSize, currentPage);
    }

    @Override
    public int insert(GameTagBean gameTagBean) throws Exception {
        return gameTagDao.insert(gameTagBean);
    }
}
