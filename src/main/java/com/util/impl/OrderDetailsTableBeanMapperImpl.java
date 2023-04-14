package com.util.impl;

import com.bean.OrderDetailsTableBean;
import com.util.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailsTableBeanMapperImpl implements RowMapper<OrderDetailsTableBean> {

    @Override
    public OrderDetailsTableBean rowMap(ResultSet rs) throws SQLException {

        /**
         * 把结果集rs对象的数据取出来放到OrderDetailsTableBean对象中
         */
        OrderDetailsTableBean orderDetailsTableBean = new OrderDetailsTableBean();

        orderDetailsTableBean.setoName(rs.getString("oName"));

        return orderDetailsTableBean;
    }
}
