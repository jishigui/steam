package com.servlet;

import com.bean.GameTagBean;
import com.bean.GamesBean;
import com.bean.PublishBean;
import com.google.gson.Gson;
import com.service.GameTagService;
import com.service.GamesService;
import com.service.OrdersService;
import com.service.PublishService;
import com.service.impl.GameTagServiceImpl;
import com.service.impl.GamesServiceImpl;
import com.service.impl.OrdersServiceImpl;
import com.service.impl.PublishServiceImpl;
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

@WebServlet("/GamesServlet")
public class GamesServlet extends BaseServlet {

    GameTagService gameTagService = new GameTagServiceImpl();
    PublishService publishService = new PublishServiceImpl();
    GamesService gamesService = new GamesServiceImpl();

    /**
     * 添加类型信息
     * 添加发布信息
     * 添加游戏数据
     * @param
     * @return
     * @throws SQLException
     */
    public void  insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 往类型表插入的数据
        String[] gtId = req.getParameterValues("gtId[]");

        // 往发布表插入的数据
        String aId = req.getParameter("aId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String pDatetime = sdf.format(date);

        // 往游戏表插入的数据
        String gId = UUID.randomUUID().toString().replace("-", "");
        String gName = req.getParameter("gName");
        String gPrice = req.getParameter("gPrice");
        String gDiscount = req.getParameter("gDiscount");
        String gSize = req.getParameter("gSize");
        String gImage = req.getParameter("gImage");
        String gContent = req.getParameter("gContent");
        String[] gTag = req.getParameterValues("gTag[]");

        GameTagBean gameTagBean = new GameTagBean();
        gameTagBean.setGtId(Arrays.toString(gtId));
        gameTagBean.setgId(gId);

        PublishBean publishBean = new PublishBean();
        publishBean.setaId(aId);
        publishBean.setgId(gId);
        publishBean.setpDatetime(pDatetime);

        GamesBean gamesBean = new GamesBean();
        gamesBean.setgId(gId);
        gamesBean.setgName(gName);
        gamesBean.setgPrice(gPrice);
        gamesBean.setgDiscount(gDiscount);
        gamesBean.setgSize(gSize);
        gamesBean.setgImage(gImage);
        gamesBean.setgContent(gContent);
        gamesBean.setgTag(Arrays.toString(gTag));

        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        try {
            gamesService.insert(gamesBean);
            gameTagService.insert(gameTagBean);
            publishService.insert(publishBean);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("游戏添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("游戏添加失败，原因：" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }

    /**
     * 删除游戏数据
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String gId = req.getParameter("gId");

        GamesBean gamesBean = new GamesBean(gId);

        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        try {
            gamesService.delete(gamesBean);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("游戏删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("游戏删除失败，原因：" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }

    /**
     * 更新游戏数据
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String gId = req.getParameter("gId");
        String gPrice = req.getParameter("gPrice");
        String gDiscount = req.getParameter("gDiscount");
        String gSize = req.getParameter("gSize");
        String gImage = req.getParameter("gImage");
        String gContent = req.getParameter("gContent");
        String[] gTag = req.getParameterValues("gTag[]");

        GamesBean gamesBean = new GamesBean(gPrice, gDiscount, gSize, gImage, gContent, Arrays.toString(gTag), gId);
//        System.out.println("=============================================");
//
//        System.out.println(gPrice);
//        System.out.println("=============================================");
        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        try {
            gamesService.update(gamesBean);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("游戏更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("游戏更新失败，原因：" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }

    /**
     * 根据id查询单个游戏
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void queryGamesById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String gId = req.getParameter("gId");

        GamesBean gamesBean = new GamesBean();
        gamesBean.setgId(gId);

        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();
        List gamesList = new ArrayList();

        try {
            gamesList = gamesService.queryGames(gamesBean);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("成功");
            jsonUtil.setData(gamesList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("系统错误" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }

    /**
     * 分页查询
     * 按游戏名分页
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void queryGamesByNameOrAllPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String strPageSize  = req.getParameter("pageSize");
        String strCurrentPage  = req.getParameter("currentPage");
        String newName = req.getParameter("gName");

        GamesBean gamesBean = new GamesBean();
        gamesBean.setgName(newName);

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
            PageUtil pageUtil = gamesService.queryGamesPage(gamesBean, pageSize, currentPage);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("成功");
            jsonUtil.setData(pageUtil);
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("系统错误" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }

    /**
     * 根据游戏名分页模糊查询收益信息
     * @return
     * @throws Exception
     */
    public void queryOrderMoneyPage(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String strPageSize  = request.getParameter("pageSize");
        String strCurrentPage  = request.getParameter("currentPage");
        String newName = request.getParameter("gName");

        GamesBean gamesBean = new GamesBean();
        gamesBean.setgName(newName);

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
        PrintWriter out = response.getWriter();
        OrdersService ordersService=new OrdersServiceImpl();
        try {
            PageUtil pageUtil = ordersService.queryOrderMoneyPage(gamesBean, pageSize, currentPage);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("成功");
            jsonUtil.setData(pageUtil);
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("系统错误" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }

    /**
     * 查询用户已购买的游戏
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void queryGamesByuID(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        String uID = req.getParameter("uID");

        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();

        try {
            List gameList = gamesService.queryGamesByuID(uID);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("成功");
            jsonUtil.setData(gameList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("查询失败，原因：" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }
}
