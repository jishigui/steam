package com.dao.impl;

import com.bean.GamesBean;
import com.dao.GamesDao;
import com.util.JdbcUtil;
import com.util.PageUtil;
import com.util.impl.GamesBeanMapperImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class GamesDaoImpl implements GamesDao {

    @Override
    public int insert(GamesBean gamesBean) throws Exception {

        String sql = "insert into game (gId, gName, gPrice, gDiscount, gSize, gImage, gContent, gTag) values (?, ?, ?, ?, ?, ?, ?, ?)";
        return JdbcUtil.insertOrUpdateOrDelete(sql, gamesBean.getgId(), gamesBean.getgName(), gamesBean.getgPrice(), gamesBean.getgDiscount(), gamesBean.getgSize(), gamesBean.getgImage(), gamesBean.getgContent(), gamesBean.getgTag());
    }

    @Override
    public boolean gameExists(String gName) throws Exception {

        String sql = "select count(*) cnt from game where gName = ?";

        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,gName);

            // 返回查询到的值
            // cnt是count(*)的别名
            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()){
                count = rs.getInt("cnt");
            }
            // 根据查询结果返回 true或false
            return count == 0 ? false : true;
        } catch (Exception e){
            throw e;
        } finally {
            JdbcUtil.close(conn, rs, ps);
        }
    }

    @Override
    public int delete(GamesBean gamesBean) throws Exception {

        String sql = "delete from game where gId = ?";
        return JdbcUtil.insertOrUpdateOrDelete(sql, gamesBean.getgId());
    }

    @Override
    public int update(GamesBean gamesBean) throws Exception {

        String sql = "update game set gPrice = ?, gDiscount = ?, gSize = ?, gImage = ?, gContent = ?, gTag = ? where gId = ?";
        return JdbcUtil.insertOrUpdateOrDelete(sql, gamesBean.getgPrice(), gamesBean.getgDiscount(), gamesBean.getgSize(), gamesBean.getgImage(), gamesBean.getgContent(), gamesBean.getgTag(), gamesBean.getgId());
    }

    @Override
    public List <GamesBean> queryGames(GamesBean gamesBean) throws Exception {

        String sql = "select * from game where gId = ?";

        return JdbcUtil.queryData(sql, new GamesBeanMapperImpl(), gamesBean.getgId());
    }

    @Override
    public PageUtil queryGamesPage(GamesBean gamesBean, int pageSize, int currentPage) throws Exception {

        String sql = "select * from game where 1 = 1";

        PageUtil pageUtil = new PageUtil();

        // 判断查询条件是否为空
        if(gamesBean != null){
            // 判断游戏名是否为空以及不为空格
            if(gamesBean.getgName() != null && !gamesBean.getgName().trim().equals("")){
                // 按游戏名模糊查
                sql += " and gName like ?";
                String newName = "%" + gamesBean.getgName() + "%";
                pageUtil = JdbcUtil.queryDataByPage(sql, pageSize, currentPage, newName);
            } else {
                pageUtil = JdbcUtil.queryDataByPage(sql, pageSize, currentPage);
            }
        }
        return pageUtil;
    }


    /**
     * 如果游戏库中不存在此游戏，则返回true
     * @param oName
     * @param uID
     * @return
     * @throws Exception
     */
    @Override
    public boolean GameExist(String oName, String uID) throws Exception {
        String sql="SELECT COUNT(*) c FROM `order` WHERE oName=? AND uID=?";

        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,oName);
            ps.setString(2,uID);

            // 返回查询到的值
            // cnt是count(*)的别名
            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()){
                count = rs.getInt("c");
            }
            // 根据查询结果返回 true或false
            return count == 0 ? true:false;
        } catch (Exception e){
            throw e;
        } finally {
            JdbcUtil.close(conn, rs, ps);
        }
    }

    @Override
    public List queryGamesByuID(String uID) throws Exception {

        String sql = "SELECT gId, gName, gPrice, gDiscount, gSize, gImage, gContent, gTag\n" +
                "FROM game\n" +
                "LEFT JOIN(\n" +
                "SELECT oName,uID FROM \n" +
                "`order`\n" +
                "JOIN `orders`\n" +
                "ON(`orders`.osId=`order`.osId )\n" +
                "WHERE osPaystatus=1\n" +
                ") t\n" +
                "ON(game.gName=t.oName)\n" +
                "WHERE t.uID = ?\n";
        return JdbcUtil.queryData(sql, GamesBean.class, uID);
    }
}
