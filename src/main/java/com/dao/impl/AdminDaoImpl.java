package com.dao.impl;

import com.bean.AdminBean;
import com.bean.CommentFormBean;
import com.bean.UserBean;
import com.dao.AdminDao;
import com.dao.UserDao;
import com.util.JdbcUtil;
import com.util.PageUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao {

    @Override
    public boolean adminAccounExit(String aName) throws Exception {
        String sql="SELECT COUNT(aName) coun FROM `admin` WHERE aName=?";
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            conn= JdbcUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,aName);
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
    public boolean adminAccounExitByTel(String aTel) throws Exception {
        String sql="SELECT COUNT(aName) coun FROM `admin` WHERE aTel=?";
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
    public int save(AdminBean adminBean) throws SQLException {
        String sql="INSERT INTO `admin`(aId,aName,aPwd,aTel) VALUES(?,?,MD5(?),?)";
        return JdbcUtil.insertOrUpdateOrDelete(sql,adminBean.getaId(),adminBean.getaName(),adminBean.getaPwd(),adminBean.getaTel());
    }

    @Override
    public AdminBean adminLoginByPhone(String tel) throws Exception {
        String sql = "select aId,aName,aPwd,aTel from `admin` where aTel=? ";
        List<AdminBean> managerBeanList =  JdbcUtil.queryData(sql,AdminBean.class,tel);
        if (managerBeanList.size()>0){
            return managerBeanList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int adminModifyPwd(AdminBean adminBean) throws Exception {

        if(this.adminAccounExitByTel(adminBean.getaTel())){
            System.out.println(adminBean.getaTel());
            System.out.println("此账户不存在");
           return 0;
        }else {
            String sql="UPDATE `admin` SET aPwd=? where aTel=?";
            return JdbcUtil.insertOrUpdateOrDelete(sql,adminBean.getaPwd(),adminBean.getaTel());
        }
    }



    @Override
    public AdminBean adminLoginByName(AdminBean adminBean) throws SQLException {
        String sql = "SELECT  aId,aName,aPwd,aTel from `admin` where aName=? and aPwd=md5(?)";
        try {
            List<AdminBean> userBeanList =  JdbcUtil.queryData(sql,AdminBean.class,adminBean.getaName(),adminBean.getaPwd());
            //取出userBeanList集合中的第一个元素做为方法的返回值
            if(userBeanList.size()==0){
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
    public int deleteUser(String uName) throws Exception {
        String sql="DELETE FROM `user` WHERE uName=?";
        return JdbcUtil.insertOrUpdateOrDelete(sql,uName);
    }

    @Override
    public int modifyUser(UserBean userBean) throws Exception {
        String sql="UPDATE `user` SET uStatus=? WHERE uName=? " ;
        return JdbcUtil.insertOrUpdateOrDelete(sql,userBean.getuStatus(),userBean.getuName());
    }

    @Override
    public PageUtil queryAllComments(UserBean userBean, int pageSize, int currentPage) throws Exception {
        String sql = "select * from `user` where 1 = 1";

        PageUtil pageUtil = new PageUtil();

        // 判断查询条件是否为空
        if(userBean != null){
            // 判断游戏名是否为空以及不为空格
            if(userBean.getuID() != null && !userBean.getuID().trim().equals("")){
                // 按游戏名模糊查
//                sql += " and cId = ?";
                String newName = userBean.getuID();
                pageUtil = JdbcUtil.queryDataByPage(sql, pageSize, currentPage);
            } else {
                pageUtil = JdbcUtil.queryDataByPage(sql, pageSize, currentPage);
            }
        }
        return pageUtil;
    }


}

