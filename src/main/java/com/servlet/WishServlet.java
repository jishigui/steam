package com.servlet;

import com.bean.UserBean;
import com.bean.WishListBean;
import com.google.gson.Gson;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import com.util.JsonUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/WishServlet.do")
public class WishServlet extends BaseServlet{

    public void viewWish(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uName=request.getParameter("uName");

        UserBean userBean=new UserBean();
        UserService userService=new UserServiceImpl();
        userBean.setuName(uName);

        PrintWriter out=response.getWriter();
        Gson gson=new Gson();
        JsonUtil jsonUtil=new JsonUtil();
        List userWishList=new ArrayList();

        try{
            userWishList=userService.userViewWish(userBean);

            System.out.println("=========wishServlet"+userWishList);
            jsonUtil.setCode(0);
            jsonUtil.setData(userWishList);

        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("异常"+e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }

    public void viewHome(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String uName=request.getParameter("uName");

        UserBean userBean=new UserBean();
        userBean.setuName(uName);
        UserService userService=new UserServiceImpl();

        PrintWriter out=response.getWriter();
        Gson gson=new Gson();
        JsonUtil jsonUtil=new JsonUtil();
        List userHomeList=new ArrayList();

        try{
            userHomeList=userService.userViewHome(userBean);
            jsonUtil.setCode(0);
            jsonUtil.setData(userHomeList);
        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("异常"+e.getMessage());
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

    public void deleteWish(HttpServletRequest request,HttpServletResponse response) throws IOException {
        PrintWriter out=response.getWriter();
        Gson gson=new Gson();
        JsonUtil jsonUtil=new JsonUtil();


        try {

            String uID=request.getParameter("uID");

            UserBean userBean=new UserBean();
            userBean.setuID(uID);
            UserService userService=new UserServiceImpl();

            int a=userService.deleteWish(userBean);
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

    public void deleteWishOneByOne(HttpServletRequest request,HttpServletResponse response) throws IOException {
        PrintWriter out=response.getWriter();
        Gson gson=new Gson();
        JsonUtil jsonUtil=new JsonUtil();


        try {

            String uID=request.getParameter("uID");
            String gId=request.getParameter("gId");

            WishListBean wish=new WishListBean();
            wish.setgId(gId);
            wish.setuID(uID);
            UserService userService=new UserServiceImpl();

            int a=userService.deleteWishOneByOne(wish);
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

    public void addWish(HttpServletRequest request,HttpServletResponse response) throws IOException {
        PrintWriter out=response.getWriter();
        Gson gson=new Gson();
        JsonUtil jsonUtil=new JsonUtil();

        try {

            String uID=request.getParameter("uID");
            String gId=request.getParameter("gId");
            WishListBean wishListBean=new WishListBean();
            wishListBean.setuID(uID);
            wishListBean.setgId(gId);

            UserService userService=new UserServiceImpl();

            if(userService.wishExisit(wishListBean)){
                int a=userService.addWish(wishListBean);
                if(a>0){
                    jsonUtil.setCode(0);
                    jsonUtil.setMsg("插入成功");
                }
            }else {
                jsonUtil.setCode(1);
                jsonUtil.setMsg("该愿望已存在");
            }

        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("错误"+e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }
}
