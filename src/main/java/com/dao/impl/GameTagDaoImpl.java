package com.dao.impl;

import com.bean.GameTagBean;
import com.dao.GameTagDao;
import com.util.JdbcUtil;
import com.util.PageUtil;
import com.util.impl.GameTagBeanMapperImpl;
import java.util.ArrayList;
import java.util.List;

public class GameTagDaoImpl implements GameTagDao {

    @Override
    public PageUtil queryGameByTagPage(GameTagBean gameTagBean, int pageSize, int currentPage) throws Exception {

        String sql = "select * from game where gId in(select gId from gametag where gtName = ?)";
        return JdbcUtil.queryDataByPage(sql, pageSize, currentPage, gameTagBean.getGtName());
    }

    @Override
    public int insert(GameTagBean gameTagBean) throws Exception {

        int reg = 0;

        String sql = "insert into gametag (gId, gtId, gtName) values (?, ?, ?)";
        String querySql = "SELECT DISTINCT gtName FROM gametag WHERE gtId = ?";

        String[] gtIDStr = gameTagBean.getGtId().replace("[","").replace("]","").replace(" ","").split(",");

        String gId = gameTagBean.getgId();

        // 如果类型只有一种
        if(gtIDStr.length == 1){

            String gtId = gtIDStr[0];
            querySql += " gtId = ?";
            List gtNameList = JdbcUtil.queryData(querySql, GameTagBean.class, gtId);
            String gtName = (String) gtNameList.get(0);

            reg = JdbcUtil.insertOrUpdateOrDelete(sql, gameTagBean.getgId(), gtId, gtName);

        }

        // 如果类型数组长度大于1
        if(gtIDStr.length > 1){
            List gtNameList = new ArrayList();
            for(int i = 0; i < gtIDStr.length; i++){

                List nameList = JdbcUtil.queryData(querySql, new GameTagBeanMapperImpl(), gtIDStr[i]);
                gameTagBean = (GameTagBean) nameList.get(0);
                String gtName = gameTagBean.getGtName();
                gtNameList.add(gtName);
            }
            for(int j = 0; j < gtIDStr.length; j++){
                reg = JdbcUtil.insertOrUpdateOrDelete(sql, gId, gtIDStr[j], gtNameList.get(j));
            }
        }
        return reg;
    }
}
