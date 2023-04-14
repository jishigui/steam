package com.dao.impl;

import com.bean.PublishBean;
import com.dao.PublishDao;
import com.util.JdbcUtil;

import java.util.List;

public class PublishDaoImpl implements PublishDao {

    @Override
    public int insert(PublishBean publishBean) throws Exception {

        String sql = "insert into publish (aId, gId, pDatetime) values (?, ?, ?)";
        return JdbcUtil.insertOrUpdateOrDelete(sql, publishBean.getaId(), publishBean.getgId(), publishBean.getpDatetime());
    }

    @Override
    public List queryPublish(PublishBean publishBean) throws Exception {

        String sql = "select * from publish where aId = ?";
        return JdbcUtil.queryData(sql, PublishBean.class, publishBean.getaId());
    }
}
