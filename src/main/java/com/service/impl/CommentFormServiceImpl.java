package com.service.impl;

import com.bean.CommentFormBean;
import com.dao.CommentFormDao;
import com.dao.impl.CommentFormDaoImpl;
import com.service.CommentFormService;
import com.util.PageUtil;

import java.util.List;

public class CommentFormServiceImpl implements CommentFormService {

    CommentFormDao commentFormDao = new CommentFormDaoImpl();

    @Override
    public int insert(CommentFormBean commentFormBean) throws Exception {

        boolean flag = commentFormDao.gameWhetherBuy(commentFormBean.getgId(), commentFormBean.getuID());
        boolean flag1 = commentFormDao.commentIn(commentFormBean.getgId(), commentFormBean.getuID());
        if(flag != true){
            throw new RuntimeException("游戏尚未购买");
        } else if(flag1 != true){
            throw new RuntimeException("您已经评论过该游戏");
        }
        // 验证通过后，再评论
        return commentFormDao.insert(commentFormBean);
    }

    @Override
    public boolean gameWhetherBuy(String gId, String uID) throws Exception {
        return commentFormDao.gameWhetherBuy(gId, uID);
    }

    @Override
    public boolean commentIn(String gId, String uID) throws Exception {
        return commentFormDao.commentIn(gId, uID);
    }

    @Override
    public List queryComment(CommentFormBean commentFormBean) throws Exception {
        return commentFormDao.queryComment(commentFormBean);
    }

    @Override
    public PageUtil queryAllComments(CommentFormBean commentFormBean, int pageSize, int currentPage) throws Exception {
        return commentFormDao.queryAllComments(commentFormBean,pageSize,currentPage);
    }


    @Override
    public int deleteByID(CommentFormBean commentFormBean) throws Exception {
        return commentFormDao.deleteByID(commentFormBean);
    }

    @Override
    public int updateByID(CommentFormBean commentFormBean) throws Exception {
        return commentFormDao.updateByID(commentFormBean);
    }

    @Override
    public int update(CommentFormBean commentFormBean) throws Exception {
        return commentFormDao.update(commentFormBean);
    }

}
