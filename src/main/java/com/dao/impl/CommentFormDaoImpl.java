package com.dao.impl;

import com.bean.CommentFormBean;
import com.bean.GamesBean;
import com.dao.CommentFormDao;
import com.util.JdbcUtil;
import com.util.PageUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommentFormDaoImpl implements CommentFormDao {

    @Override
    public int insert(CommentFormBean commentFormBean) throws Exception {

        String sql = "insert into comments (gId, uID, cDatetime, cContent, cRank, cHidden, cId) values (?, ?, ?, ?, ?, ?, ?)";
        return JdbcUtil.insertOrUpdateOrDelete(sql, commentFormBean.getgId(), commentFormBean.getuID(), commentFormBean.getcDatetime(), commentFormBean.getcContent(), commentFormBean.getcRank(), commentFormBean.getcHidden(), commentFormBean.getcId());

    }

    @Override
    public boolean gameWhetherBuy(String gId, String uID) throws Exception {

        // 状态osPaystatus  1 表示已购买  0  表示未购买
        String sql = "select count(*) cnt from game where gId = ? and gName in (SELECT oName FROM `order` WHERE oId in (SELECT oId FROM orders WHERE uID = ? AND osPaystatus = 1))";

        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, gId);
            ps.setString(2, uID);

            // 返回查询到的值
            // cnt是count(*)的别名
            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()){
                count = rs.getInt("cnt");
            }
            // 根据查询结果返回 true或false
            return count == 0 ? false : true;
        } catch (Exception e){
            throw e;
        } finally {
            JdbcUtil.close(conn, rs, ps);
        }
    }

    @Override
    public boolean commentIn(String gId, String uID) throws Exception {

        String sql = "select count(*) cnt from comments where gId = ? and uID = ?";

        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, gId);
            ps.setString(2, uID);

            // 返回查询到的值
            // cnt是count(*)的别名
            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()){
                count = rs.getInt("cnt");
            }
            // 根据查询结果返回 true或false
            return count == 0 ? true : false;
        } catch (Exception e){
            throw e;
        } finally {
            JdbcUtil.close(conn, rs, ps);
        }
    }

    @Override
    public List queryComment(CommentFormBean commentFormBean) throws Exception {

        List commentList = new ArrayList();

        // 判断gId是否为空
        if(commentFormBean.getgId() != null){
            String sql = "SELECT gId, uId, DATE_FORMAT(cDatetime,'%Y-%c-%d %h:%i:%s') cDatetime, cContent, cRank, cHidden, cId FROM comments where gId = ?";
            commentList = JdbcUtil.queryData(sql, CommentFormBean.class, commentFormBean.getgId());
        } else if(commentFormBean.getuID() != null){
            // 判断uID是否为空
            String sql = "SELECT gId, uId, DATE_FORMAT(cDatetime,'%Y-%c-%d %h:%i:%s') cDatetime, cContent, cRank, cHidden, cId FROM comments where uID = ?";
            commentList = JdbcUtil.queryData(sql, CommentFormBean.class, commentFormBean.getuID());
        }
        return commentList;
    }

    @Override
    public int deleteByID(CommentFormBean commentFormBean) throws Exception {

        String sql = "delete from comments where cId = ?";
        return JdbcUtil.insertOrUpdateOrDelete(sql, commentFormBean.getcId());
    }

    @Override
    public int updateByID(CommentFormBean commentFormBean) throws Exception {

        String sql = "update comments set cId = ?, cDatetime = ?, cContent = ?, cRank = ?, cHidden = ?";
        return JdbcUtil.insertOrUpdateOrDelete(sql, commentFormBean.getcId(), commentFormBean.getcDatetime(), commentFormBean.getcContent(), commentFormBean.getcRank(), commentFormBean.getcHidden());
    }

    @Override
    public PageUtil queryAllComments(CommentFormBean commentFormBean, int pageSize, int currentPage) throws Exception {
        String sql = "select * from comments where 1 = 1";

        PageUtil pageUtil = new PageUtil();

        // 判断查询条件是否为空
        if(commentFormBean != null){
            // 判断游戏名是否为空以及不为空格
            if(commentFormBean.getcId() != null && !commentFormBean.getcId().trim().equals("")){
                // 按游戏名模糊查
//                sql += " and cId = ?";
                String newName = commentFormBean.getcId();
                pageUtil = JdbcUtil.queryDataByPage(sql, pageSize, currentPage);
            } else {
                pageUtil = JdbcUtil.queryDataByPage(sql, pageSize, currentPage);
            }
        }
        return pageUtil;
    }

    @Override
    public int update(CommentFormBean commentFormBean) throws Exception {
        String sql = "UPDATE comments SET cContent=? WHERE cId=?";
        return JdbcUtil.insertOrUpdateOrDelete(sql,commentFormBean.getcContent(),commentFormBean.getcId());
    }

    @Override
    public int delete(CommentFormBean commentFormBean) throws Exception {
        return 0;
    }


}
