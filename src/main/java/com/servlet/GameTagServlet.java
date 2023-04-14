package com.servlet;

import com.bean.GameTagBean;
import com.google.gson.Gson;
import com.service.GameTagService;
import com.service.impl.GameTagServiceImpl;
import com.util.JsonUtil;
import com.util.PageUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/GameTagServlet")
public class GameTagServlet extends BaseServlet {

    GameTagService gameTagService = new GameTagServiceImpl();

    /**
     * 类型分页查询
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void queryGamesByTagPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String strPageSize  = req.getParameter("pageSize");
        String strCurrentPage  = req.getParameter("currentPage");
        String tName = req.getParameter("gtName");

        GameTagBean gameTagBean = new GameTagBean();
        gameTagBean.setGtName(tName);

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
            PageUtil pageUtil = gameTagService.queryGameByTagPage(gameTagBean, pageSize, currentPage);
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
}
