package com.servlet;

import com.bean.CommentFormBean;
import com.bean.GamesBean;
import com.bean.UserBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.service.AdminService;
import com.service.CommentFormService;
import com.service.impl.AdminServiceImpl;
import com.service.impl.CommentFormServiceImpl;
import com.util.JsonUtil;
import com.util.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/CommentFormServlet")
public class CommentFormServlet extends BaseServlet {

    CommentFormService commentFormService = new CommentFormServiceImpl();

    /**
     * 添加评论数据
     * @param
     * @return
     * @throws SQLException
     */
    public void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 往游戏表插入的数据
        String gId = req.getParameter("gId");
        String uID = req.getParameter("uID");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String cDatetime = sdf.format(date);

        String cContent = req.getParameter("cContent");
        Integer cRank = Integer.parseInt(req.getParameter("cRank"));
        Integer cHiddenStr = Integer.parseInt(req.getParameter("cHidden"));
        // 判断是否匿名有没有被选中
        // 默认为1
        Integer cHidden = 0;
        if(cHiddenStr != null){
            cHidden = cHiddenStr;
        }

        String cId =  UUID.randomUUID().toString().replace("-", "");

        CommentFormBean commentFormBean = new CommentFormBean();
        commentFormBean.setgId(gId);
        commentFormBean.setcContent(cContent);
        commentFormBean.setcDatetime(cDatetime);
        commentFormBean.setcHidden(cHidden);
        commentFormBean.setcId(cId);
        commentFormBean.setuID(uID);
        commentFormBean.setcRank(cRank);

        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        try {
            commentFormService.insert(commentFormBean);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("评论添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("评论添加失败，原因：" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }

    /**
     * 查询一个游戏里的所有评论
     * 查询一个用户所有的评论
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void queryCommentByGameOrUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String gId = req.getParameter("gId");
        String uID = req.getParameter("uID");

        CommentFormBean commentFormBean = new CommentFormBean();
        commentFormBean.setgId(gId);
        commentFormBean.setuID(uID);

        JsonUtil jsonUtil = new JsonUtil();

        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();

        PrintWriter out = resp.getWriter();
        List commentList = new ArrayList();

        try {
            commentList = commentFormService.queryComment(commentFormBean);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("成功");
            jsonUtil.setData(commentList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("系统错误" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }

    public void queryAllComments(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String strPageSize  = req.getParameter("pageSize");
        String strCurrentPage  = req.getParameter("currentPage");
        String newName = req.getParameter("cId");

        CommentFormBean commentFormBean = new CommentFormBean();
        commentFormBean.setcId(newName);

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
            PageUtil pageUtil = commentFormService.queryAllComments(commentFormBean, pageSize, currentPage);
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

    /**
     * 根据评论id删除自己的评论
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void deleteByID(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        String cId = req.getParameter("cId");
        CommentFormBean commentFormBean = new CommentFormBean();
        commentFormBean.setcId(cId);
        System.out.println("xxxxxxxxxxx");
        System.out.println(cId);

        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        try {
            commentFormService.deleteByID(commentFormBean);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("评论删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("评论删除错误，原因：" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }

    /**
     * 根据id修改自己的评论
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void updateByID(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String cDatetime = sdf.format(date);

        String cContent = req.getParameter("cContent");
        String cId = req.getParameter("cId");
        Integer cRank = Integer.parseInt(req.getParameter("cRank"));
        Integer cHiddenStr = Integer.parseInt(req.getParameter("cHidden"));
        // 判断是否匿名有没有被选中
        // 默认为0
        Integer cHidden = 0;
        if(cHiddenStr != null){
            cHidden = cHiddenStr;
        }

        CommentFormBean commentFormBean = new CommentFormBean();
        commentFormBean.setcId(cId);
        commentFormBean.setcDatetime(cDatetime);
        commentFormBean.setcContent(cContent);
        commentFormBean.setcRank(cRank);
        commentFormBean.setcHidden(cHidden);

        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        try {
            commentFormService.updateByID(commentFormBean);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("评论修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("评论修改错误，原因：" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String cDatetime = sdf.format(date);

        String cContent = req.getParameter("cContent");
        String cId = req.getParameter("cId");


        CommentFormBean commentFormBean = new CommentFormBean();
        commentFormBean.setcId(cId);
        commentFormBean.setcDatetime(cDatetime);
        commentFormBean.setcContent(cContent);


        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        try {
            commentFormService.update(commentFormBean);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("评论修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("评论修改错误，原因：" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
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
        AdminService adminService = new AdminServiceImpl();
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
}