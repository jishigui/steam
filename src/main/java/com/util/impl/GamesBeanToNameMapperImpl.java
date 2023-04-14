package com.util.impl;

import com.bean.GamesBean;
import com.util.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GamesBeanToNameMapperImpl implements RowMapper<GamesBean> {

    @Override
    public GamesBean rowMap(ResultSet rs) throws SQLException {

        /**
         * 把结果集rs对象的数据取出来放到GamesBean对象中
         */
        GamesBean gamesBean = new GamesBean();
        gamesBean.setgName(rs.getString("gName"));

        return gamesBean;
    }
}
