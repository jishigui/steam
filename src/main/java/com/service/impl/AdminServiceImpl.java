package com.service.impl;

import com.bean.AdminBean;
import com.bean.UserBean;
import com.dao.AdminDao;
import com.service.AdminService;
import com.dao.impl.AdminDaoImpl;
import com.util.PageUtil;

import java.sql.SQLException;

public class AdminServiceImpl implements AdminService {

    AdminDao adminDao=new AdminDaoImpl();
    @Override
    public boolean adminAccounExit(String aName) throws Exception {
        return adminDao.adminAccounExit(aName);
    }

    @Override
    public int save(AdminBean adminBean) throws SQLException {
        return adminDao.save(adminBean);
    }

    @Override
    public AdminBean adminLoginByName(AdminBean adminBean) throws Exception {
        return adminDao.adminLoginByName(adminBean);
    }

    @Override
    public AdminBean adminLoginByPhone(String tel) throws Exception {
        return adminDao.adminLoginByPhone(tel);
    }

    @Override
    public int adminModifyPwd(AdminBean adminBean) throws Exception {
        return  adminDao.adminModifyPwd(adminBean);
    }

    @Override
    public int deleteUser(String uName) throws Exception {
        return adminDao.deleteUser(uName);
    }

    @Override
    public int modifyUserStatus(UserBean userBean) throws Exception {
        return adminDao.modifyUser(userBean);
    }

    @Override
    public PageUtil queryAllUsers(UserBean userBean, int pageSize, int currentPage) throws Exception {
        return adminDao.queryAllComments(userBean,pageSize,currentPage);
    }

}
