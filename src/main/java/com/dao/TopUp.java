package com.dao;

import com.bean.TopUpTableBean;
import com.bean.UserBean;

public interface TopUp {
    public int TopUp(TopUpTableBean top) throws Exception;

    public int TopUpTo(UserBean userBean) throws Exception;
}
