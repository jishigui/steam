package com.dao;

import com.bean.GamesBean;
import com.bean.OrderDetailsTableBean;
import com.bean.OrderListBean;
import com.util.PageUtil;

import java.util.List;

public interface OrdersDao {
    public List orderListView(OrderDetailsTableBean order) throws Exception;

    public int OrderListInsert(OrderListBean orderListBean) throws Exception;

    public int OrderListComplete(OrderListBean orderListBean) throws Exception;

    public int OrderListDelete(OrderDetailsTableBean orderDetailsTableBean) throws Exception;

    public List orderListViewAll(OrderDetailsTableBean order) throws Exception;
//    public int GameExist();
    /**
     * 课根据游戏名分页模糊查询收益信息
     * @param game
     * @param pageSize
     * @param currentPage
     * @return
     * @throws Exception
     */
    public PageUtil queryOrderMoneyPage(GamesBean game, int pageSize, int currentPage) throws Exception;


}
