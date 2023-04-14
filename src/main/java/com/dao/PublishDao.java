package com.dao;

import com.bean.PublishBean;
import java.sql.SQLException;
import java.util.List;

public interface PublishDao {

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
