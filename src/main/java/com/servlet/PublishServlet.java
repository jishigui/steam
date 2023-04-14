package com.servlet;

import com.bean.PublishBean;
import com.google.gson.Gson;
import com.service.PublishService;
import com.service.impl.PublishServiceImpl;
import com.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PublishServlet")
public class PublishServlet extends BaseServlet{

    PublishService publishService = new PublishServiceImpl();

    /**
     * 查询管理员发布信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void  insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String aId = req.getParameter("aId");

        PublishBean publishBean = new PublishBean();
        publishBean.setaId(aId);

        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();
        List adminList = new ArrayList();

        try {
            adminList = publishService.queryPublish(publishBean);
            jsonUtil.setCode(0);
            jsonUtil.setMsg("成功");
            jsonUtil.setData(adminList);
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("系统错误" + e.getMessage());
        }
        out.print(gson.toJson(jsonUtil));
    }
}
