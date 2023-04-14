package com.servlet;


import com.alipay.api.domain.BigCardData;
import com.bean.TopUpTableBean;
import com.bean.UserBean;
import com.service.TopUpService;
import com.service.UserService;
import com.service.impl.TopUpServiceImpl;
import com.service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/TopUpServlet.do")
public class TopUpServlet extends BaseServlet{

    public void userTopUp(HttpServletRequest request,HttpServletResponse response) throws Exception{

        String topMoney=request.getParameter("topMoney");
        String topId= UUID.randomUUID().toString();
        String uID=request.getParameter("uID");
        String out_trade_no=request.getParameter("out_trade_no");

        TopUpTableBean top=new TopUpTableBean();
        top.setTopId(topId);
        System.out.println(topMoney);
        top.setTopMoney((int)Double.parseDouble(topMoney));
        top.setOut_trade_no(out_trade_no);
        top.setuID(uID);

        TopUpService topUpService=new TopUpServiceImpl();
        topUpService.TopUpInsert(top);

        UserService userService=new UserServiceImpl();
        UserBean userBean=new UserBean();
        userBean.setuID(uID);


        List<Map<String,Object>> list=userService.viewAllByID(userBean);

        Object uBalance=  list.get(0).get("uBalance");
        BigDecimal bigDecimal= (BigDecimal) uBalance;
        double to=Double.parseDouble(topMoney);
        userBean.setuBalance(to+bigDecimal.doubleValue());
        topUpService.TopUpTo(userBean);
    }
}
