package com.service;

import com.bean.CommentFormBean;
import com.bean.GamesBean;
import com.util.PageUtil;

import java.sql.SQLException;
import java.util.List;

public interface CommentFormService {

    /**
     * 添加评论信息
     * @param
     * @return
     * @throws SQLException
     */
    public int insert(CommentFormBean commentFormBean) throws Exception;

    /**
     * 判断游戏是否购买
     * @param gId
     * @param uID
     * @return
     * @throws Exception
     */
    public boolean gameWhetherBuy(String gId, String uID) throws Exception;

    /**
     * 判断是否已经评论
     * @param gId
     * @param uID
     * @return
     * @throws Exception
     */
    public boolean commentIn(String gId, String uID) throws Exception;

    /**
     * 查询单个游戏评论
     * 查询一个用户所有的评论
     * @param commentFormBean
     * @return
     * @throws Exception
     */
    public List queryComment(CommentFormBean commentFormBean) throws Exception;


    public PageUtil queryAllComments(CommentFormBean commentFormBean, int pageSize, int currentPage) throws Exception;

    /**
     * 根据评论id删除自己的评论
     * @param commentFormBean
     * @return
     * @throws Exception
     */
    public int deleteByID(CommentFormBean commentFormBean) throws Exception;

    /**
     * 根据评论id修改自己的评论
     * @param commentFormBean
     * @return
     * @throws Exception
     */
    public int updateByID(CommentFormBean commentFormBean) throws Exception;

    public int update(CommentFormBean commentFormBean) throws Exception;
}
