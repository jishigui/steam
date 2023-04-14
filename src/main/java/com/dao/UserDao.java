package com.dao;

import com.bean.AdminBean;
import com.bean.UserBean;
import com.bean.WishListBean;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserDao {

    public boolean userAccounExit(String ID) throws Exception;

    public int save(UserBean userBean) throws SQLException;

    public UserBean userLoginByName(UserBean userbean) throws Exception;

    public UserBean userLoginByPhone(String uTel) throws Exception;

    public int userModifyPwd(UserBean userBean) throws Exception;

    public boolean userAccounExitByTel(String uTel) throws Exception;

    public List userViewWish(UserBean userBean) throws Exception;

    public List userViewHome(UserBean userBean) throws Exception;

    public int modifyMeg(UserBean userBean) throws Exception;

    public int deleteWish(UserBean userBean) throws  Exception;

    public int deleteWishOneByOne(WishListBean wishListBean) throws Exception;

    public int addWish(WishListBean wishListBean) throws  Exception;

    public List viewAll(UserBean userBean) throws  Exception;

    public List<Map<String,Object>> viewAllByID(UserBean userBean) throws Exception;

    /**
     * 查询用户拥有几款游戏
     * @param userBean
     * @return
     * @throws Exception
     */
    public int queryBuyGames(UserBean userBean) throws Exception;

    /**
     * 查询用户拥有的游戏
     * @param userBean
     * @return
     * @throws Exception
     */
    public List queryUserGames(UserBean userBean) throws Exception;

    public boolean wishExisit(WishListBean wishListBean) throws Exception;
}
