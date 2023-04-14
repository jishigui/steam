package com.service;

import com.bean.AdminBean;
import com.bean.UserBean;
import com.util.PageUtil;

import java.sql.SQLException;

public interface AdminService {
    public boolean adminAccounExit(String ID) throws Exception;

    public int save(AdminBean adminBean) throws SQLException;

    public AdminBean adminLoginByName(AdminBean adminBean) throws Exception;

    public AdminBean adminLoginByPhone(String tel) throws Exception;

    public int adminModifyPwd(AdminBean adminBean) throws Exception;

    public int deleteUser(String uName) throws Exception;

    public int modifyUserStatus(UserBean userBean) throws Exception;

    public PageUtil queryAllUsers(UserBean userBean, int pageSize, int currentPage) throws Exception;
}
