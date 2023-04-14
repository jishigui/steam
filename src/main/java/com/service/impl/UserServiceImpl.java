package com.service.impl;

import com.bean.UserBean;
import com.bean.WishListBean;
import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.service.UserService;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    UserDao userDao=new UserDaoImpl();
    @Override
    public boolean userAccounExit(String uName) throws Exception {
        return userDao.userAccounExit(uName);
    }

    @Override
    public int save(UserBean userBean) throws Exception {
//        boolean flag =  userDao.userAccounExit(userBean.getuID());
//        if (flag==false){
//            throw new RuntimeException("帐号已存在");
//        }
//        //验证通过后，再调用save方法实现保存操作
//
//        //一个系统有可能要连接不同的数据库(mysql,sqlserver,Oracl)
//        //  每个数据库的自动生成的主键的方式是不同的，如mysql是 autoincrement ,sqlserver是identity,oracle是序列
//        //  万一我们做好的系统要改数据库。主键自动生成不合适,我们可以用Java 提供的UUID类生成永远不可能重复的36位长度的字符串
        return userDao.save(userBean);
    }

    @Override
    public UserBean userLoginByName(UserBean userbean) throws Exception {
        return userDao.userLoginByName(userbean);
    }

    @Override
    public UserBean userLoginByPhone(String tel) throws Exception {
        return userDao.userLoginByPhone(tel);
    }

    @Override
    public int userModifyPwd(UserBean userBean) throws Exception {
        return userDao.userModifyPwd(userBean);
    }

    @Override
    public List userViewWish(UserBean userBean) throws Exception {
        return userDao.userViewWish(userBean);
    }

    @Override
    public List userViewHome(UserBean userBean) throws Exception {
        return userDao.userViewHome(userBean);
    }

    @Override
    public int modifyMeg(UserBean userBean) throws Exception {
        return userDao.modifyMeg(userBean);
    }

    @Override
    public int deleteWish(UserBean userBean) throws Exception {
        return userDao.deleteWish(userBean);
    }

    @Override
    public int deleteWishOneByOne(WishListBean wishListBean) throws Exception {
        return userDao.deleteWishOneByOne(wishListBean);
    }

    @Override
    public int addWish(WishListBean wishListBean) throws Exception {
        return userDao.addWish(wishListBean);
    }

    @Override
    public List viewAll(UserBean userBean) throws Exception {
        return userDao.viewAll(userBean);
    }

    @Override
    public List viewAllByID(UserBean userBean) throws Exception {
        return userDao.viewAllByID(userBean);
    }

    public int queryBuyGames(UserBean userBean) throws Exception{
        return userDao.queryBuyGames(userBean);
    }

    @Override
    public List queryUserGames(UserBean userBean) throws Exception {
        return userDao.queryUserGames(userBean);
    }
    @Override
    public boolean wishExisit(WishListBean wishListBean) throws Exception{
        return userDao.wishExisit(wishListBean);
    }
}
