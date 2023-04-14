package com.service;

import com.bean.OrderDetailsTableBean;

import java.util.List;

public interface OrderService {

//    public List orderView(OrderDetailsTableBean order) throws Exception;

    public boolean orderExits(OrderDetailsTableBean orderDetailsTableBean) throws Exception;

    public int orderInsert(OrderDetailsTableBean order) throws Exception;

    public int orderDelete(OrderDetailsTableBean orderDetailsTableBean) throws Exception;

}
