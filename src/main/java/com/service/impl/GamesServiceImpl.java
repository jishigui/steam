package com.service.impl;

import com.bean.GamesBean;
import com.dao.GamesDao;
import com.dao.impl.GamesDaoImpl;
import com.service.GamesService;
import com.util.PageUtil;
import java.util.List;

public class GamesServiceImpl implements GamesService {

    GamesDao gamesDao = new GamesDaoImpl();

    @Override
    public int insert(GamesBean gamesBean) throws Exception {

        boolean flag = gamesDao.gameExists(gamesBean.getgName());

        if(flag == true){
            throw new RuntimeException("游戏名已存在");
        }
        // 验证通过后，再保存数据
        return gamesDao.insert(gamesBean);
    }

    @Override
    public boolean gameExists(String gName) throws Exception {
        return gamesDao.gameExists(gName);
    }

    @Override
    public int delete(GamesBean gamesBean) throws Exception {
        return gamesDao.delete(gamesBean);
    }

    @Override
    public int update(GamesBean gamesBean) throws Exception {
        return gamesDao.update(gamesBean);
    }

    @Override
    public List queryGames(GamesBean gamesBean) throws Exception {
        return gamesDao.queryGames(gamesBean);
    }

    @Override
    public PageUtil queryGamesPage(GamesBean gamesBean, int pageSize, int currentPage) throws Exception {
        return gamesDao.queryGamesPage(gamesBean, pageSize, currentPage);
    }

    @Override
    public boolean GameExist(String oName, String uID) throws Exception {
        return gamesDao.GameExist(oName,uID);
    }

    @Override
    public List queryGamesByuID(String uID) throws Exception {
        return gamesDao.queryGamesByuID(uID);
    }
}
