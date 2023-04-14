package com.service;

import com.bean.GamesBean;
import com.util.PageUtil;

import java.sql.SQLException;
import java.util.List;

public interface GamesService {

    /**
     * 添加游戏信息
     * @param
     * @return
     * @throws SQLException
     */
    public int insert(GamesBean gamesBean) throws Exception;

    /**
     * 检查是否有同名游戏
     * @param gName
     * @return
     * @throws Exception
     */
    public boolean gameExists(String gName) throws Exception;

    /**
     * 删除游戏数据
     * @param gamesBean
     * @return
     */
    public int delete(GamesBean gamesBean) throws Exception;

    /**
     * 更新游戏数据
     * @param gamesBean
     * @return
     * @throws Exception
     */
    public int update(GamesBean gamesBean) throws Exception;

    /**
     * 根据ID查询单个游戏
     * @param gamesBean
     * @return
     * @throws Exception
     */
    public List queryGames(GamesBean gamesBean) throws Exception;

    /**
     * 分页查询
     * 按游戏名分页
     * @param gamesBean
     * @param pageSize
     * @param currentPage
     * @return
     * @throws Exception
     */
    public PageUtil queryGamesPage(GamesBean gamesBean, int pageSize, int currentPage) throws Exception;

    public boolean GameExist(String oName, String uID) throws Exception;

    /**
     * 查询用户已购买的游戏
     * @param uID
     * @return
     * @throws Exception
     */
    public List queryGamesByuID(String uID) throws Exception;
}
