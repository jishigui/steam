package com.servlet;


import com.bean.AdminBean;
import com.bean.UserBean;
import com.google.gson.Gson;
import com.service.AdminService;
import com.service.UserService;
import com.service.impl.AdminServiceImpl;
import com.service.impl.UserServiceImpl;
import com.util.JsonUtil;
import com.util.JwtToken;
import com.util.SendMsg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends BaseServlet{
    UserService userService=new UserServiceImpl();

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

        PrintWriter out=response.getWriter();

        Gson gson=new Gson();
        JsonUtil jsonUtil=new JsonUtil();
        JwtToken jwtToken=new JwtToken();

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
                jsonUtil.setData(serviceBean);
            }else {
                jsonUtil.setCode(1);
                jsonUtil.setMsg("用户名或密码错误");
            }
        }else {
            jsonUtil.setCode(1);
            jsonUtil.setMsg("账号不存在");
        }

        out.print(gson.toJson(jsonUtil));
    }

    public void userRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String uName=request.getParameter("uName");
//获取从前端传回的各个字段
        String uPwd=request.getParameter("uPwd");
        String uNick=request.getParameter("uNick");
        String uTel=request.getParameter("uTel");
        PrintWriter out=response.getWriter();

        JsonUtil jsonUtil=new JsonUtil();
        Gson gson=new Gson();


        try {
            if(userService.userAccounExit(uName)){

                UserBean userBean=new UserBean();


                //将数组传入userBean,便于使用sava方法保存进入数据库
                userBean.setuID(UUID.randomUUID().toString());
                userBean.setuName(uName);
                userBean.setuPwd(uPwd);
                userBean.setuNick(uNick);
                userBean.setuTel(uTel);
//                userBean.setuStatus(1);
//                userBean.setuBalance(0);

                UserService userService1=new UserServiceImpl();

                try {
                    int a=userService.save(userBean);
                    if(a>0){
                        jsonUtil.setCode(0);
                        jsonUtil.setMsg("注册成功");
                    }

                } catch (SQLException e) {
                    System.out.println("系统错误"+e.getMessage());
                }

            }else if(userService.userAccounExit(uName)==false){
                jsonUtil.setMsg("已存在此用户");
                jsonUtil.setCode(1);
            }
//            }

        }catch (Exception e){
            e.printStackTrace();;
            jsonUtil.setCode(1);
            jsonUtil.setMsg("注册失败:"+e.getMessage());
        }
//        System.out.println(uName);
        //判断数据库是否存在此账号

        out.print(gson.toJson(jsonUtil));
    }


    public void userLoginByName(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取前端输入的用户名和密码
        String uName=request.getParameter("uName");
        String uPwd=request.getParameter("uPwd");

        UserService userService=new UserServiceImpl();
        JwtToken jwtToken=new JwtToken();
//        String data=jwtToken.getTokenData(request);
//


        PrintWriter out=response.getWriter();
        Gson gson=new Gson();
        JsonUtil jsonUtil=new JsonUtil();

        UserBean userBean=new UserBean();
        userBean.setuName(uName);
        userBean.setuPwd(uPwd);


        System.out.println("servlet-userbean:"+userBean.toString());


//        String[] jwtDataArray  =   data.split(",");
//        if (!jwtDataArray[0].equals(uName)){
//            jsonUtil.setCode(1);
//            jsonUtil.setMsg("用户名不正确");
//        }else if (!jwtDataArray[1].equals(uPwd)){
//            jsonUtil.setCode(1);
//            jsonUtil.setMsg("密码输入错误");
//        }else {
        try{
            if(!userService.userAccounExit(uName)){

                UserBean serviceBean=userService.userLoginByName(userBean);
                if(serviceBean.getuStatus()!=0){
                    if(serviceBean!=null && !"".equals(uName) && !"".equals(uPwd)){

                        jsonUtil.setCode(0);
                        jsonUtil.setMsg("登录成功");
                        String jwtStr = jwtToken.generateToken( uName+","+uPwd);
                        List list=new ArrayList();
                        list.add(jwtStr);
                        jsonUtil.setData(list);
                        jsonUtil.setData(jwtStr);//把jwtStr传递给前端
                    }else {

                        jsonUtil.setCode(1);
                        jsonUtil.setMsg("用户名或密码错误");
                    }
                }else {
                    jsonUtil.setCode(1);
                    jsonUtil.setMsg("您的账号已被冻结");
                }

            }else {

                jsonUtil.setCode(1);
                jsonUtil.setMsg("账号不存在");
            }

        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("登录失败,原因为:"+e.getMessage());
        }
//        }

        //对输入的账号进行判断，若不存在，则发送信息，存在则进行下一步


        //把jsonUtil转为json对象传到前端
        out.print(gson.toJson(jsonUtil));

    }

    public void userLoginByPhone(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String phone = request.getParameter("uTel");//获得手机号
        String userInputCode = request.getParameter("code");//获得用户在文本框中输入的验证码
        //得到JWT的解密串
        JwtToken jwtToken = new JwtToken();
        String data = jwtToken.getTokenData(request);
        System.out.println("得到的解密串为:"+data);

        System.out.println("手机号为:"+phone +"验证码为:"+userInputCode);

        //拆分出jwt解密后的手机号和验证码，与用户输入的做比较


        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        JsonUtil jsonUtil = new JsonUtil();


//        try {
//            String[] jwtDataArray  =   data.split(",");
//            if (!jwtDataArray[0].equals(phone)){
//                jsonUtil.setCode(1);
//                jsonUtil.setMsg("接收短信的手机号不正确");
//            }else if (!jwtDataArray[1].equals(userInputCode)){
//                jsonUtil.setCode(1);
//                jsonUtil.setMsg("验证码输入错误");
//            }else{
//                //用手机号查询数据库，得到手机号对应的用户信息
//                UserBean userBean=userService.userLoginByPhone(phone);
//
//                if (userBean!=null){
//                    if(userBean.getuStatus()!=0){
//                        //表示登录成功
//                        jsonUtil.setCode(0);
//                        jsonUtil.setMsg("登录成功");
//                        String jwtStr =   jwtToken.generateToken( phone+","+userInputCode);
//                        jsonUtil.setData(jwtStr);//把jwtStr传递给前端
//                    }else {
//                        jsonUtil.setCode(1);
//                        jsonUtil.setMsg("您的账号已被冻结");
//                    }
//
//                }else{
//                    //表示手机号不存在
//                    jsonUtil.setCode(1);
//                    jsonUtil.setMsg("手机号在系统中不存在");
//                }
//
//            }
//        }catch (Exception e){
//            jsonUtil.setCode(1);
//            jsonUtil.setMsg("短信验证码发送失败,原因:"+e.getMessage());
//        }
        UserBean userBean=userService.userLoginByPhone(phone);

        if (userBean!=null){
            if(userBean.getuStatus()!=0){
                //表示登录成功
                jsonUtil.setCode(0);
                jsonUtil.setMsg("登录成功");
                String jwtStr =   jwtToken.generateToken( phone+","+userInputCode);
                jsonUtil.setData(jwtStr);//把jwtStr传递给前端
            }else {
                jsonUtil.setCode(1);
                jsonUtil.setMsg("您的账号已被冻结");
            }

        }else{
            //表示手机号不存在
            jsonUtil.setCode(1);
            jsonUtil.setMsg("手机号在系统中不存在");
        }
        out.print(gson.toJson(jsonUtil));
    }

    public void getMobilePhoneCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone = request.getParameter("uTel");//得到手机号

        System.out.println("getCode"+phone);
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        JsonUtil jsonUtil = new JsonUtil();

        try {
            int code = SendMsg.getCode(phone);//返回999999表示短信验证码发送失败
            if (code == 999999) {
                jsonUtil.setCode(1);
                jsonUtil.setMsg("短信验证码发送失败");
            } else {
                jsonUtil.setCode(0);
                jsonUtil.setMsg("成功");
                jsonUtil.setData(code);


                //request.getSession().setAttribute(18007328601, 123456);
                //以手机号为key,短信验证码为值存储数据到session中  Redis
                // request.getSession().setAttribute(phone, code);

                //换成用JWT 加密验证码和手机号
//                JwtToken jwtToken = new JwtToken();
//                String jwtStr =   jwtToken.generateToken( phone+","+code);
//                jsonUtil.setData(jwtStr);//把jwtStr传递给前端

            }
        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("短信验证码发送失败,原因:"+e.getMessage());
        }



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
