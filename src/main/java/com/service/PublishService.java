package com.service;

import com.bean.PublishBean;
import java.sql.SQLException;
import java.util.List;

public interface PublishService {

    /**
     * 添加发布信息
     * @param
     * @return
     * @throws SQLException
     */
    public int insert(PublishBean publishBean) throws Exception;

    /**
     * 查询管理员发布信息
     * @param publishBean
     * @return
     * @throws Exception
     */
    public List queryPublish(PublishBean publishBean) throws Exception;
}
