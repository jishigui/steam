package com.service;

import com.bean.TopUpTableBean;
import com.bean.UserBean;

public interface TopUpService {
    public int TopUpInsert(TopUpTableBean top) throws Exception;

    public int TopUpTo(UserBean userBean) throws Exception;
}
