package com.util.impl;

import com.bean.GamesBean;
import com.util.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GamesBeanMapperImpl implements RowMapper<GamesBean> {

    @Override
    public GamesBean rowMap(ResultSet rs) throws SQLException {

        /**
         * 把结果集rs对象的数据取出来放到CommentForm对象中
         */
        GamesBean gamesBean = new GamesBean();
        gamesBean.setgId(rs.getString("gId"));
        gamesBean.setgName(rs.getString("gName"));
        gamesBean.setgPrice(rs.getString("gPrice"));
        gamesBean.setgDiscount(rs.getString("gDiscount"));
        gamesBean.setgContent(rs.getString("gContent"));
        gamesBean.setgImage(rs.getString("gImage"));
        gamesBean.setgSize(rs.getString("gSize"));
        gamesBean.setgTag(rs.getString("gTag"));

        return gamesBean;
    }
}
