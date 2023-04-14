package com.servlet;

import com.alipay.api.java_websocket.WebSocket;
import com.google.gson.Gson;
import com.server.ServerDemo;
import com.util.JsonUtil;
import org.apache.commons.logging.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/AliPayCallback")
public class AliPayCallback extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        String orderID = "";
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();

                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                /* valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");*/
                params.put(name, valueStr);

            }
            System.out.println(params);
            //获取流水号
            String  out_trade_no=params.get("out_trade_no");
            System.out.println("out_trade_no==="+out_trade_no);

            String trade_status=params.get("trade_status");

            String subject=params.get("subject");
            System.out.println(trade_status);
//            System.out.println(ServerDemo.map);
            WebSocket webSocket=ServerDemo.map.get(out_trade_no);
//            System.out.println("=========="+webSocket);


            Map<String,String> map=new HashMap<String,String>();
            map.put("TRADE",trade_status);
            map.put("out_trade_no",out_trade_no);
            map.put("Subject",subject);


            Gson gson=new Gson();
            webSocket.send(gson.toJson(map));
//            webSocket.send(map);

        }catch (Exception e){

        }
    }
}
