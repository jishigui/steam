package com.service.impl;

import com.bean.OrderDetailsTableBean;
import com.dao.OrderDao;
import com.dao.impl.OrderDaoImpl;
import com.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao=new OrderDaoImpl();

//    @Override
//    public List orderView(OrderDetailsTableBean order) throws Exception {
//        return orderDao.orderView(order);
//    }

    @Override
    public boolean orderExits(OrderDetailsTableBean orderDetailsTableBean) throws Exception {

        return orderDao.orderExits(orderDetailsTableBean);
    }

    @Override
    public int orderInsert(OrderDetailsTableBean order) throws Exception {
        return orderDao.orderInsert(order);
    }

    @Override
    public int orderDelete(OrderDetailsTableBean orderDetailsTableBean) throws Exception {
        return orderDao.orderDelete(orderDetailsTableBean);
    }


}
