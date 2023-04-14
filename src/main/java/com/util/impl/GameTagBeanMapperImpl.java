package com.util.impl;

import com.bean.GameTagBean;
import com.bean.GamesBean;
import com.util.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameTagBeanMapperImpl implements RowMapper<GameTagBean> {


    @Override
    public GameTagBean rowMap(ResultSet rs) throws SQLException {
        /**
         * 把结果集rs对象的数据取出来放到CommentForm对象中
         */
        GameTagBean gameTagBean = new GameTagBean();

        gameTagBean.setgId(rs.getString("gId"));
        gameTagBean.setGtId(rs.getString("gtId"));
        gameTagBean.setGtName(rs.getString("gtName"));

        return gameTagBean;
    }
}
