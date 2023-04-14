package com.service.impl;

import com.bean.TopUpTableBean;
import com.bean.UserBean;
import com.dao.TopUp;
import com.dao.impl.TopUpDaoImpl;
import com.service.TopUpService;

public class TopUpServiceImpl implements TopUpService {
    TopUp topUp=new TopUpDaoImpl();


    @Override
    public int TopUpInsert(TopUpTableBean top) throws Exception {
        return topUp.TopUp(top);
    }

    @Override
    public int TopUpTo(UserBean userBean) throws Exception {
        return topUp.TopUpTo(userBean);
    }
}
