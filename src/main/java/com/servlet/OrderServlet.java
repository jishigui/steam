package com.servlet;


import com.bean.GamesBean;
import com.bean.OrderDetailsTableBean;
import com.bean.OrderListBean;
import com.dao.GamesDao;
import com.dao.impl.GamesDaoImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.service.OrderService;
import com.service.OrdersService;
import com.service.impl.OrderServiceImpl;
import com.service.impl.OrdersServiceImpl;
import com.util.JdbcUtil;
import com.util.JsonUtil;
import com.util.PageUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@WebServlet("/OrderServlet.do")
public class OrderServlet extends BaseServlet {

    OrdersService ordersService=new OrdersServiceImpl();
    OrderService orderService=new OrderServiceImpl();

    public void OrderListInsert(HttpServletRequest request,HttpServletResponse response) throws IOException {

        PrintWriter out=response.getWriter();
        JsonUtil jsonUtil=new JsonUtil();
        Gson gson=new Gson();

        try{
           String osId=UUID.randomUUID().toString();
           String oId=UUID.randomUUID().toString();

           String uID=request.getParameter("uID");
           String osPayment=request.getParameter("osPayment");
           String osTotalPrice=request.getParameter("osTotalPrice");
           String oDiscount=request.getParameter("oDiscount");
           String oPrice=request.getParameter("oPrice");
           String oName=request.getParameter("oName");

            GamesDao gamesDao=new GamesDaoImpl();
            boolean flag=gamesDao.GameExist(oName,uID);
            if(flag=true){
                OrderDetailsTableBean order=new OrderDetailsTableBean();
                OrderListBean orders=new OrderListBean();

                order.setuID(uID);
                order.setoId(oId);
                order.setOsId(osId);
                order.setoDiscount(Double.parseDouble(oDiscount));
                order.setoPrice(Double.parseDouble(oPrice));
                order.setoName(oName);

                System.out.println(order);



                orders.setOsId(osId);
                orders.setOsPayment(osPayment);
                orders.setOsTotalPrice(Double.parseDouble(osTotalPrice));

                System.out.println("++++++++orders"+orders);
                if(!orderService.orderExits(order)){
                    int a = ordersService.OrderListInsert(orders);
                    int b = orderService.orderInsert(order);


                    if (a > 0) {
                        jsonUtil.setCode(0);
                        jsonUtil.setMsg("订单创建成功");
                    }
                }else {
                    jsonUtil.setCode(1);
                    jsonUtil.setMsg("订单已经存在");
                }

            }else{
                jsonUtil.setCode(1);
                jsonUtil.setMsg("您已经购买此游戏");
            }



        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("错误"+e.getMessage());
        }

        out.print(gson.toJson(jsonUtil));
    }


    public void OrderListComplete(HttpServletRequest request,HttpServletResponse response) throws IOException {
        PrintWriter out=response.getWriter();
        JsonUtil jsonUtil=new JsonUtil();
        Gson gson=new Gson();

        try{
            String osId=request.getParameter("osId");
            String Sernumber=request.getParameter("out_trade_no");

            OrdersService ordersService=new OrdersServiceImpl();

            int a=0;
            OrderDetailsTableBean order=new OrderDetailsTableBean();
            OrderListBean orders=new OrderListBean();
            String os[]=osId.replace("[","").replace("]","").replace("\"","").split(",");
            orders.setSernumber(Sernumber);
            for(int i=0;i<os.length;i++){
                orders.setOsId(os[i]);

                int b=ordersService.OrderListComplete(orders);
                a=a+b;
            }
            if(a==os.length){
                jsonUtil.setCode(0);
                jsonUtil.setMsg("修改成功");
            }
        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("错误"+e.getMessage());
        }

        out.print(gson.toJson(jsonUtil));
    }
    public void OrderListDelete(HttpServletRequest request,HttpServletResponse response) throws IOException {
        PrintWriter out=response.getWriter();
        JsonUtil jsonUtil=new JsonUtil();
        Gson gson=new Gson();

        try{
            String osId=request.getParameter("osId");
            String uID=request.getParameter("uID");

            OrderService orderService=new OrderServiceImpl();
            OrdersService ordersService=new OrdersServiceImpl();

            OrderDetailsTableBean order=new OrderDetailsTableBean();
            order.setuID(uID);

            String os[]=osId.replace("[","").replace("]","").replace("\"","").split(",");
            for(int i=0;i<os.length;i++){
                System.out.println(os[i]);
                order.setOsId(os[i]);
                int b=orderService.orderDelete(order);

                ordersService.orderListDelete(order);

            }

            jsonUtil.setCode(0);
            jsonUtil.setMsg("删除成功");

        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("错误"+e.getMessage());
        }

        out.print(gson.toJson(jsonUtil));

    }

    public void OrderView(HttpServletRequest request,HttpServletResponse response) throws IOException {
        PrintWriter out=response.getWriter();
        JsonUtil jsonUtil=new JsonUtil();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson=gsonBuilder.create();

        try{
            String uID=request.getParameter("uID");

            OrderDetailsTableBean order=new OrderDetailsTableBean();
            order.setuID(uID);

            OrdersService ordersService=new OrdersServiceImpl();
            List list=ordersService.orderListView(order);
            if(list!=null){
                jsonUtil.setCode(0);
                jsonUtil.setData(list);
            }
        }catch (Exception e){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("错误"+e.getMessage());
        }

        out.print(gson.toJson(jsonUtil));

    }




}
