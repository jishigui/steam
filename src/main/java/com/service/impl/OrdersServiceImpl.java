package com.service.impl;

import com.bean.GamesBean;
import com.bean.OrderDetailsTableBean;
import com.bean.OrderListBean;
import com.dao.OrdersDao;
import com.dao.impl.OrdersDaoImpl;
import com.service.OrdersService;
import com.util.PageUtil;

import java.util.List;

public class OrdersServiceImpl implements OrdersService {
    OrdersDao ordersDao=  new OrdersDaoImpl();


    @Override
    public List orderListView(OrderDetailsTableBean order) throws Exception {
        return ordersDao.orderListView(order);
    }

    @Override
    public int OrderListInsert(OrderListBean orderListBean) throws Exception {
        return ordersDao.OrderListInsert(orderListBean);
    }

    @Override
    public int OrderListComplete(OrderListBean orderListBean) throws Exception {
        return ordersDao.OrderListComplete(orderListBean);
    }

    @Override
    public int orderListDelete(OrderDetailsTableBean orderDetailsTableBean) throws Exception {
        return ordersDao.OrderListDelete(orderDetailsTableBean);
    }

    @Override
    public PageUtil queryOrderMoneyPage(GamesBean game, int pageSize, int currentPage) throws Exception {
        return ordersDao.queryOrderMoneyPage(game,pageSize,currentPage);
    }

}
