package com.dao.impl;

import com.bean.OrderDetailsTableBean;
import com.bean.OrderListBean;
import com.dao.OrderDao;
import com.util.JdbcUtil;

import java.util.List;

public class OrderDaoImpl implements OrderDao {


    @Override
    public boolean orderExits(OrderDetailsTableBean orderDetailsTableBean) throws Exception {
        String sql = "SELECT osId FROM orders WHERE osId IN (SELECT osId FROM `order` WHERE oName = ? and uID = ?)";


        List list  = JdbcUtil.queryData(sql,orderDetailsTableBean.getoName(),orderDetailsTableBean.getuID());
        System.out.println(list);
        if(list.size() != 0 ){
            return true;
        }else {
            return false;
        }


    }

    @Override
    public int orderInsert(OrderDetailsTableBean order) throws Exception {
        String sql="INSERT INTO `order` (oId,oDiscount,oPrice,oName,osID,uID) VALUES(?,?,?,?,?,?)";


        return JdbcUtil.insertOrUpdateOrDelete(sql,order.getoId(),order.getoDiscount(),order.getoPrice(),order.getoName(),order.getOsId(),order.getuID());

    }

    @Override
    public int orderDelete(OrderDetailsTableBean orderDetailsTableBean) throws Exception {

        String sql="DELETE from `order` WHERE osId=?";
        return JdbcUtil.insertOrUpdateOrDelete(sql,orderDetailsTableBean.getOsId());

    }


}
