package com.dao.impl;

import com.bean.GamesBean;
import com.bean.UserBean;
import com.bean.WishListBean;
import com.dao.UserDao;
import com.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {


    @Override
    public boolean userAccounExit(String uName) throws Exception {
        String sql="SELECT COUNT(`uName`) AS coun FROM `user` WHERE uName=?";
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            conn= JdbcUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,uName);
            rs=ps.executeQuery();

            int coun=0;
            while (rs.next()){
                coun=rs.getInt("coun");
            }
            return coun==0?true:false;
        } catch (Exception e) {
            throw e;
        }finally {
            JdbcUtil.close(conn,rs,ps);
        }

    }

    @Override
    public int save(UserBean userBean) throws SQLException {
        String sql="INSERT INTO `user`(uID,uName,uPwd,uNick,uTel,uRegTime,uStatus,uBalance) VALUES(?,?,MD5(?),?,?,NOW(),1,0)";
        return JdbcUtil.insertOrUpdateOrDelete(sql,userBean.getuID(),userBean.getuName(),userBean.getuPwd(),userBean.getuNick(),userBean.getuTel());
    }


    @Override
    public UserBean userLoginByName(UserBean userBean) throws SQLException {
        String sql = "SELECT  uID,uName,uPwd,uNick,uTel,uRegTime,uStatus,uBalance  from `user` where uName=? and uPwd=MD5(?)";
        try {
            List<UserBean> userBeanList =  JdbcUtil.queryData(sql,UserBean.class,userBean.getuName(),userBean.getuPwd());
            //取出userBeanList集合中的第一个元素做为方法的返回值
            if(userBeanList.size()==0){
                System.out.println(userBean.getuName()+"   "+userBean.getuPwd());
                return null;
            }else{
                return userBeanList.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserBean userLoginByPhone(String tel) throws Exception {
        String sql = "SELECT  uID,uName,uPwd,uNick,uTel,uMail,uRegTime,uStatus,uIden,uBalance  from `user` where uTel=? ";
        List<UserBean> managerBeanList =  JdbcUtil.queryData(sql,UserBean.class,tel);
        if (managerBeanList.size()>0){
            return managerBeanList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int userModifyPwd(UserBean userBean) throws Exception {

        if(this.userAccounExitByTel(userBean.getuTel())){
            System.out.println(userBean.getuTel());
            System.out.println("此账户不存在");
            return 0;
        }else {
            String sql="UPDATE `user` SET uPwd=? where uTel=?";
            return JdbcUtil.insertOrUpdateOrDelete(sql,userBean.getuPwd(),userBean.getuTel());
        }
    }

    @Override
    public boolean userAccounExitByTel(String aTel) throws Exception {
        String sql="SELECT COUNT(uName) coun FROM `user` WHERE uTel=?";
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            conn= JdbcUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,aTel);
            rs=ps.executeQuery();

            int coun=0;
            while (rs.next()){
                coun=rs.getInt("coun");
            }
            return coun==0?true:false;
        } catch (Exception e) {
            throw e;
        }finally {
            JdbcUtil.close(conn,rs,ps);
        }
    }

    @Override
    public List userViewWish(UserBean userBean) throws Exception {
        String sql="SELECT uID,uName,uPwd,uNick,uTel,uMail,uRegTime,uStatus,uIden,uBalance,gName\n" +
                "                FROM (\n" +
                "                SELECT u.uID,uName,uPwd,uNick,uTel,uMail,uRegTime,uStatus,uIden,uBalance,gId\n" +
                "                FROM `user` u\n" +
                "                LEFT JOIN `wishlist` w\n" +
                "                ON(u.uID=w.uID)\n" +
                "                ) t\n" +
                "                LEFT JOIN `game` g \n" +
                "                ON(g.gId=t.gId) \n" +
                "                WHERE uName=?\n" +
                "                ";
        return JdbcUtil.queryData(sql,userBean.getuName());
    }

    @Override
    public List userViewHome(UserBean userBean) throws Exception {
//        String sql="SELECT u.uID,uName,uPwd,uNick,uTel,uMail,uRegTime,uStatus,uIden,uBalance,t.oName\n" +
//                "FROM `user` u\n" +
//                "LEFT JOIN (\n" +
//                "SELECT oName,`order`.oId,orders.uID\n" +
//                "FROM `order`\n" +
//                "LEFT JOIN `orders`\n" +
//                "ON(`order`.oId=`orders`.oId)\n" +
//                "WHERE osPaystatus!=0\n" +
//                ") t\n" +
//                "ON(u.uID=t.uID)\n" +
//                "WHERE uName=?\n";

        String sql="SELECT oName\n" +
                "FROM `order`\n" +
                "WHERE oId \n" +
                "IN(SELECT oId FROM `orders` WHERE uName=? AND osPaystatus=1)";
        return JdbcUtil.queryData(sql,userBean.getuName());
    }

    @Override
    public int modifyMeg(UserBean userBean) throws Exception {

        String sql="UPDATE `user` SET uNick=?,uTel=?,uMail=?,uIden=? WHERE uName=?";
        return JdbcUtil.insertOrUpdateOrDelete(sql,userBean.getuNick(),userBean.getuTel(),userBean.getuMail(),userBean.getuIden(),userBean.getuName());
    }

    @Override
    public int deleteWish(UserBean userBean) throws Exception {
        String sql="\n" +
                "DELETE FROM `wishlist` \n" +
                "WHERE uID=(\n" +
                "SELECT uID FROM `user`\n" +
                "WHERE uName=?\n" +
                ")\n";
        return JdbcUtil.insertOrUpdateOrDelete(sql,userBean.getuName());
    }

    @Override
    public int deleteWishOneByOne(WishListBean wishListBean) throws Exception {
        String sql="DELETE FROM wishlist WHERE gId=? AND uID=?";
        return JdbcUtil.insertOrUpdateOrDelete(sql,wishListBean.getgId(),wishListBean.getuID());
    }

    @Override
    public int addWish(WishListBean wishListBean) throws Exception {
        String sql="INSERT INTO `wishlist` (gID,uID) VALUES(?,?)";
        return JdbcUtil.insertOrUpdateOrDelete(sql,wishListBean.getgId(),wishListBean.getuID());
    }

    @Override
    public List viewAll(UserBean userBean) throws Exception {
        String sql="SELECT uID,uName,uPwd,uNick,uMail,uRegTime,uStatus,uIden,uBalance,uTel FROM `user` WHERE uName=?";
        return JdbcUtil.queryData(sql,userBean.getuName());
    }

    @Override
    public List<Map<String,Object>> viewAllByID(UserBean userBean) throws Exception {
        String sql="SELECT uID,uName,uPwd,uNick,uMail,uRegTime,uStatus,uIden,uBalance FROM `user` WHERE uID=?";
        return JdbcUtil.queryData(sql,userBean.getuID());
    }

    public int queryBuyGames(UserBean userBean) throws Exception{

        String sql = "SELECT COUNT(*) cnt \n" +
                "FROM `orders` os\n" +
                "LEFT JOIN\n" +
                "`order` o\n" +
                "ON(o.osId=os.osId)\n" +
                "WHERE uID=? AND osPaystatus=1";

        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, userBean.getuID());

            // 返回查询到的值
            // cnt是count(*)的别名
            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()){
                count = rs.getInt("cnt");
            }
            return count;
        } catch (Exception e){
            throw e;
        } finally {
            JdbcUtil.close(conn, rs, ps);
        }
    }

    @Override
    public List queryUserGames(UserBean userBean) throws Exception {

        String sql = "select * from game where gName in (select oName from order where oId in (SELECT oId FROM orders WHERE uID = ? AND osPaystatus = 1))";
        return JdbcUtil.queryData(sql, GamesBean.class, userBean.getuID());
    }

    @Override
    public boolean wishExisit(WishListBean wishListBean) throws Exception {
        String sql = "SELECT COUNT(*) cnt FROM wishlist WHERE uID=?\n" +
                "AND gId=?";

        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, wishListBean.getuID());
            ps.setString(2,wishListBean.getgId());

            // 返回查询到的值
            // cnt是count(*)的别名
            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()){
                count = rs.getInt("cnt");
            }
            return count==0?true:false;
        } catch (Exception e){
            throw e;
        } finally {
            JdbcUtil.close(conn, rs, ps);
        }

    }


}
