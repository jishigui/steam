package com.servlet;

import com.bean.AdminBean;
import com.bean.CommentFormBean;
import com.bean.UserBean;
import com.google.gson.Gson;
import com.service.AdminService;
import com.service.UserService;
import com.service.impl.AdminServiceImpl;
import com.service.impl.UserServiceImpl;
import com.util.JdbcUtil;
import com.util.JsonUtil;
import com.util.JwtToken;
import com.util.PageUtil;

import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

@WebServlet("/AdminServlet.do")
public class AdminServlet extends BaseServlet{
    AdminService adminService=new AdminServiceImpl();

    public void adminRegister(HttpServletRequest request, HttpServletResponse response) throws Exception{


        String aName=request.getParameter("aName");
        String aPwd=request.getParameter("aPwd");
        String aTel=request.getParameter("aTel");

        PrintWriter out=response.getWriter();

        JsonUtil jsonUtil=new JsonUtil();
        Gson gson=new Gson();

        //判断是否存在此管理员,ture代表不存在
        if(adminService.adminAccounExit(aName)){

            AdminBean adminBean=new AdminBean();

            adminBean.setaId(UUID.randomUUID().toString());
            adminBean.setaName(aName);
            adminBean.setaPwd(aPwd);
            adminBean.setaTel(aTel);

            AdminService adminService=new AdminServiceImpl();

            try{
                adminService.save(adminBean);
                jsonUtil.setCode(0);
                jsonUtil.setMsg("注册成功");
            }catch (Exception e){
                System.out.println("系统错误"+e.getMessage());
            }

        }else if(adminService.adminAccounExit(aName)==false){
            jsonUtil.setMsg("已存在此用户");
            jsonUtil.setCode(1);
        }
        out.print(gson.toJson(jsonUtil));
    }

    public void adminLoginByName(HttpServletRequest request,HttpServletResponse response) throws Exception {

        //从前端获取输入的用户名和密码
        String aName=request.getParameter("aName");
        String aPwd=request.getParameter("aPwd");


        JwtToken jwtToken=new JwtToken();
        PrintWriter out=response.getWriter();

        Gson gson=new Gson();
        JsonUtil jsonUtil=new JsonUtil();

        AdminBean adminBean=new AdminBean();
        adminBean.setaName(aName);
        adminBean.setaPwd(aPwd);

        if(!adminService.adminAccounExit(aName)){

            AdminBean serviceBean=adminService.adminLoginByName(adminBean);
            if(serviceBean!=null && !"".equals(aName) &&!"".equals(aPwd)){
                String jwtStr = jwtToken.generateToken( aName+","+aPwd);
                jsonUtil.setData(jwtStr);//把jwtStr传递给前端
                jsonUtil.setCode(0);
                jsonUtil.setMsg("登录成功");
            }else {
                jsonUtil.setCode(1);
                jsonUtil.setMsg("用户名或密码错误");
            }
        }else {
            jsonUtil.setCode(0);
            jsonUtil.setMsg("账号不存在");
        }

        out.print(gson.toJson(jsonUtil));
    }




    public void modifyPwd(HttpServletRequest request,HttpServletResponse responser) throws IOException {

        String aTel=request.getParameter("aTel");
        String code=request.getParameter("code");
        String aPwd=request.getParameter("aPwd");

        JwtToken jwtToken=new JwtToken();
        String data=jwtToken.getTokenData(request);

        System.out.println("得到的解密串为:"+data);
        System.out.println("手机号为:"+aTel+"验证码为:"+code);

        PrintWriter out=responser.getWriter();
        Gson gson=new Gson();
        JsonUtil jsonUtil=new JsonUtil();

        AdminBean adminBean=new AdminBean();
        adminBean.setaTel(aTel);
        adminBean.setaTel(aPwd);
        try {
            String[] jwtDataArray  =   data.split(",");
            if (!jwtDataArray[0].equals(aTel)){
                jsonUtil.setCode(1);
                jsonUtil.setMsg("接收短信的手机号不正确");
            }else if (!jwtDataArray[1].equals(code)){
                jsonUtil.setCode(1);
                jsonUtil.setMsg("验证码输入错误");
            }else{
                //调用方法，查询是否存在此账号
                int co=adminService.adminModifyPwd(adminBean);
                if (co>0){
                    jsonUtil.setCode(0);
                    jsonUtil.setMsg("密码修改成功");
                }else {
                    jsonUtil.setCode(1);
                    jsonUtil.setMsg("此号码不存在");
                }
            }
        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("短信验证码发送失败,原因:"+e.getMessage());
        }

        out.print(gson.toJson(jsonUtil));

    }

    public void deleteUserByName(HttpServletRequest request,HttpServletResponse response ) throws Exception{

        PrintWriter out=response.getWriter();
        Gson gson=new Gson();
        JsonUtil jsonUtil=new JsonUtil();
        try{
            String uName=request.getParameter("uName");

            AdminService adminService=new AdminServiceImpl();
            int a=adminService.deleteUser(uName);
            if(a>0){
                jsonUtil.setCode(0);
                jsonUtil.setMsg("删除成功");
            }

        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("错误"+e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }

    public void modifyStatus(HttpServletRequest request,HttpServletResponse response) throws IOException {


        PrintWriter out=response.getWriter();
        Gson gson=new Gson();
        JsonUtil jsonUtil=new JsonUtil();
        try{
            String uStatus=request.getParameter("uStatus");
            UserBean userBean=new UserBean();
            userBean.setuStatus(Integer.parseInt(uStatus));

            AdminService adminService=new AdminServiceImpl();
            int a=adminService.modifyUserStatus(userBean);
            if(a>0){
                jsonUtil.setCode(0);
                jsonUtil.setMsg("修改成功");
            }

        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("错误"+e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));


    }

    /**
     * 用于计算总收益
     * @param request
     * @param response
     * @throws Exception
     */
    public void sumMoney(HttpServletRequest request,HttpServletResponse response) throws Exception {

        JsonUtil jsonUtil=new JsonUtil();
        PrintWriter out =response.getWriter();
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Gson gson=new Gson();

        String sql="\n" +
                "SELECT SUM(osTotalPrice) sum\n" +
                "FROM `order` o\n" +
                "JOIN `orders` os\n" +
                "ON(o.osId=os.osId)\n" +
                "WHERE osPaystatus=1";
        try {
            ps = conn.prepareStatement(sql);
            // 返回查询到的值
            // cnt是count(*)的别名
            rs = ps.executeQuery();
            int sum = 0;
            while (rs.next()){
                sum = rs.getInt("sum");
            }
            jsonUtil.setData(sum);
            jsonUtil.setCode(0);

            out.print(gson.toJson(jsonUtil));

        } catch (Exception e){
            throw e;
        } finally {
            JdbcUtil.close(conn, rs, ps);
        }
    }

    public void queryAllUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String strPageSize  = req.getParameter("pageSize");
        String strCurrentPage  = req.getParameter("currentPage");
        String newName = req.getParameter("uID");

        UserBean commentFormBean = new UserBean();
        commentFormBean.setuID(newName);

        // 默认每页显示行数
        int pageSize = 5;
        // 判断显示行数是否为数值型
        if(strPageSize == null || !strPageSize.matches("[0-9]+")){
            System.out.println("请求参数未传递pageSize或currentPage的值是非数字类型");
        } else {
            // 把String类型参数转换为Int类型
            pageSize = Integer.parseInt(strPageSize);
        }
        // 默认页数
        int currentPage = 1;
        // 判断页数是否为数值型
        if(strCurrentPage == null || !strCurrentPage.matches("[0-9]+")){
            System.out.println("请求参数未传递pageSize或currentPage的值是非数字类型");
        } else {
            // 把String类型参数转换为Int类型
            currentPage = Integer.parseInt(strCurrentPage);
        }

        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        try {
                PageUtil pageUtil = adminService.queryAllUsers(commentFormBean,pageSize,currentPage);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("成功");
            jsonUtil.setData(pageUtil);
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("系统错误" + e.getMessage());
        }
        System.out.println(jsonUtil.getData());
        out.print(gson.toJson(jsonUtil));
    }

    public void modifyUserStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uStatus  = req.getParameter("uStatus");
        String uName  = req.getParameter("uName");

        UserBean commentFormBean = new UserBean();
        commentFormBean.setuStatus(Integer.parseInt(uStatus));

        AdminService userService = new AdminServiceImpl();


        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        try {
            userService.modifyUserStatus(commentFormBean);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("系统错误" + e.getMessage());
        }
        System.out.println(jsonUtil.getData());
        out.print(gson.toJson(jsonUtil));
    }

    public void userModifyMeg(HttpServletRequest request,HttpServletResponse response) throws IOException {

        PrintWriter out=response.getWriter();
        Gson gson=new Gson();
        JsonUtil jsonUtil=new JsonUtil();

        try{
            String uName=request.getParameter("uName");
            String uNick=request.getParameter("uNick");
            String uTel=request.getParameter("uTel");
            String uMail=request.getParameter("uMail");
            String uIden=request.getParameter("uIden");

            UserBean userBean=new UserBean();

            userBean.setuName(uName);
            userBean.setuNick(uNick);
            userBean.setuTel(uTel);
            userBean.setuMail(uMail);
            userBean.setuIden(uIden);

            try {
                UserService userService=new UserServiceImpl();
                int a=userService.modifyMeg(userBean);
                if(a>0){
                    jsonUtil.setCode(0);
                    jsonUtil.setMsg("修改成功");
                }
            }catch (Exception e){
                jsonUtil.setMsg("错误"+e.getMessage());
                jsonUtil.setCode(0);
            }
        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("返回值为空");

        }

        out.print(gson.toJson(jsonUtil));

    }
}


































































































































































