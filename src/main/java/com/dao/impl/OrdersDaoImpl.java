package com.dao.impl;

import com.bean.GamesBean;
import com.bean.OrderDetailsTableBean;
import com.bean.OrderListBean;
import com.dao.OrdersDao;
import com.util.JdbcUtil;
import com.util.PageUtil;

import java.util.List;

public class OrdersDaoImpl implements OrdersDao {
    @Override
    public List orderListView(OrderDetailsTableBean order) throws Exception {
        String sql="SELECT `orders`.osId,oId,uID,osPaytime,osPayment,osTotalPrice,osCreatetime,osPaystatus,oDiscount,oPrice,oName\n" +
                "FROM `orders`\n" +
                "JOIN `order`\n" +
                "ON(`orders`.osID=`order`.osID)\n" +
                "WHERE `order`.uID=?\n" +
                "\t";
        return JdbcUtil.queryData(sql,order.getuID());
    }

    @Override
    public int OrderListInsert(OrderListBean orderListBean) throws Exception {

        String sql="INSERT INTO `orders` (osId,osPayment,osTotalPrice,osCreatetime,osPaystatus) VALUES(?,?,?,NOW(),0)";

        return JdbcUtil.insertOrUpdateOrDelete(sql,orderListBean.getOsId(),orderListBean.getOsPayment(),orderListBean.getOsTotalPrice());
    }

    @Override
    public int OrderListComplete(OrderListBean orderListBean) throws Exception {

        String sql="UPDATE `orders`\n" +
                "SET osPaystatus=1,\n" +
                "osPaytime=NOW(),\n" +
                "Sernumber=?\n" +
                "WHERE osId=?";
        return JdbcUtil.insertOrUpdateOrDelete(sql,orderListBean.getSernumber(),orderListBean.getOsId());
    }

    @Override
    public int OrderListDelete(OrderDetailsTableBean orderDetailsTableBean) throws Exception {
        String sql="DELETE FROM `orders` WHERE osId=?";
        return JdbcUtil.insertOrUpdateOrDelete(sql,orderDetailsTableBean.getOsId());
    }

    @Override
    public List orderListViewAll(OrderDetailsTableBean order) throws Exception {
        String sql="";
        return null;
    }

    @Override
    public PageUtil queryOrderMoneyPage(GamesBean gamesBean, int pageSize, int currentPage) throws Exception {
        String nameStr = "";

        String sql="SELECT oId, oName, oPrice, oDiscount, osTotalPrice, osId, uName, osPaytime\n" +
                "FROM `user` u\n" +
                "JOIN \n" +
                "(\n" +
                "\t\tSELECT oId, uID, oPrice, oDiscount, osTotalPrice, oName, os.osId, osPaytime\n" +
                "\t\tFROM `order` o JOIN orders os\n" +
                "\t\tON(o.osid=os.osid AND os.osPaystatus=1)\n" +
                "\t\tWHERE 1 = 1" + nameStr +
                ") t1 \n" +
                "ON(t1.uID = u.uID)\n";
        PageUtil pageUtil = new PageUtil();

        // 判断查询条件是否为空
        if(gamesBean != null){
            // 判断游戏名是否为空以及不为空格
            if(gamesBean.getgName() != null && !gamesBean.getgName().trim().equals("")){
                // 按游戏名模糊查
                nameStr = " and oName like ?";
                sql="SELECT oId, oName, oPrice, oDiscount, osTotalPrice, osId, uName, osPaytime\n" +
                        "FROM `user` u\n" +
                        "JOIN \n" +
                        "(\n" +
                        "\t\tSELECT oId, uID, oPrice, oDiscount, osTotalPrice, oName, os.osId, osPaytime\n" +
                        "\t\tFROM `order` o JOIN orders os\n" +
                        "\t\tON(o.osid=os.osid AND os.osPaystatus=1)\n" +
                        "\t WHERE 1 = 1" + nameStr +
                        ") t1 \n" +
                        "ON(t1.uID = u.uID)\n";
                String newName = "%" + gamesBean.getgName() + "%";
                pageUtil = JdbcUtil.queryDataByPage(sql, pageSize, currentPage, newName);
            } else {
                pageUtil = JdbcUtil.queryDataByPage(sql, pageSize, currentPage);
            }
        }
        return pageUtil;
    }


}
