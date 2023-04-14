package com.dao;

import com.bean.OrderDetailsTableBean;
import com.bean.OrderListBean;

import java.util.List;

public interface OrderDao {

    public boolean orderExits(OrderDetailsTableBean orderDetailsTableBean) throws  Exception;

    public int orderInsert(OrderDetailsTableBean order) throws Exception;

    public int orderDelete(OrderDetailsTableBean orderDetailsTableBean) throws Exception ;


}
