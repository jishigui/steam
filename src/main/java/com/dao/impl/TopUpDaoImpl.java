package com.dao.impl;

import com.bean.TopUpTableBean;
import com.bean.UserBean;
import com.dao.TopUp;
import com.util.JdbcUtil;

public class TopUpDaoImpl implements TopUp {
    @Override
    public int TopUp(TopUpTableBean top) throws Exception {
        String sql= "INSERT INTO `topup`(topMoney,topTime,topId,uID,out_trade_no) VALUES(?,NOW(),?,?,?)";
        return JdbcUtil.insertOrUpdateOrDelete(sql,top.getTopMoney(),top.getTopId(),top.getuID(),top.getOut_trade_no());
    }

    @Override
    public int TopUpTo(UserBean userBean) throws Exception {
        String sql="UPDATE `user` SET uBalance=? WHERE uID=?";
        return JdbcUtil.insertOrUpdateOrDelete(sql,userBean.getuBalance(),userBean.getuID());
    }
}
