package com.servlet;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.google.gson.Gson;
import com.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/AliPayServlet")
public class AliPayServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        PrintWriter out=response.getWriter();
        JsonUtil jsonUtil=new JsonUtil();
        Gson gson=new Gson();

        try{
            String  OutTradeNo = request.getParameter("OutTradeNo");
            String  TotalAmount = request.getParameter("TotalAmount");
            String  Subject = request.getParameter("Subject");

            response.setContentType("text/html;charset=utf-8");


            AlipayClient alipayClient = new DefaultAlipayClient(
                    "https://openapi.alipaydev.com/gateway.do","2021000118647580",
                    "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCZgpz/JzVzDABzD4Mvnyss8RrJEU51aXtgdmyGS+RX4rmx1ghdMQ4DbU8sVoVHQPR44g5GjGEcwJGsj8BtCCwRv/WoHHO+/0JfVcdfnt9KsPWN2uLzXnohlG0kmqCaTtRky89lK1doXvGwleHwBth3+HuJOPlQXZWBHyNdIwRjnnoiA21gDcEY4sNF8Mm/f3utYJWziaqdYamymxf6koM9ae/5UxxQJf1lBY8KURe5EbMU5arh0jjau3Bo8gJFeGGuHivJW5e6Fz/UQPyTPyh4WhxJo9skldNqXSMAVR7vuQ/3vecR+ILicJ2FK0OSUVuA8sZiFTKLJAzxjH0336jHAgMBAAECggEBAJOmUFEOKle0tH327qwQQRvh72xzi+5cfYGjhfCJ8GAjv43wGm1gMGVVbvBra5fEPyBB9Y8fmIxqH/NE9MUqNhEAfEysyRttMy61N6WeDpEtsZE2dlG0jm3b8Dpjy5N/vMQSUDGzxUerAnZhQ0Z1nyGNS20J8qq8yH7AL2h2tNMG4oMXcWz7DUsXbw9viD1fG5ZxJyGFgCwu1ceVfrJK8qxDgbM8EfptqYxnz9/1XaoyOp0xPLz6AJyA/F9EH1mvCt5+YVT0fUCJIT6vTtiYSyxGowSpPfEYbE/CQTJjM0NTk5lc+tLMFxr3lo7E/c/bqy+9HGsnLgw6kAtokJIWHhkCgYEA/pksvlihn8w8dig7oeyCQNUxWPwOfM24awoSZ5/Srb4CKDydXSep5NKzGUWBlMS89QWEj+/uwd2HSOfG9RCbZ5C/4uzNvf3Ck7J0rzoL7U/qGOg62RsmTt3RhCd8Kk3ygdWZh4lPRhav8ObusGdtj6NCSuvxCgEqSpx+lsyzO9UCgYEAmlr3lfL68TvuPL7oaerIVRopDLGljAh7WVCIsiRtfhhPaK1GLPKTzjnvm8EkbvqeRZkZuapMUcgzwb7GbYs6U4p8DNS/x002utOKNBBGqCvg0SAlI2DT7ynwuq0U/X3NRMu6k8U13LswJzBWXIIKwGU4QLf82U12/HgNMpOMLCsCgYEAriDnODx4NknCZHG571JJ97uYJ6sk4Xj2E8dstRDbT9cvtINYgXj6OrVcpwEh0jUIo3AlrShFnLQ9rcBQFBZKo3edtb+OK85eARRBuAK0W5eXnXdfoFNwTOprPL6grnJnVSNZARkJbf9gUBwxzda/WUkoswNR9Sy81xDXh53zTA0CgYAkd/GfH/ViIYK8H27R94K1eKTSNvaiCy75fymMzUaBUEUSuj2mSPna851E83EkN1pFn1KQp0e42tA/qk8/enGhY0rDzVAySm/v3CL/Aglvmk5XmnyryyjHjOUvdc1LxvlEk83VM/bx29gYqjb9TEw2ALQ1tkKrJL5EECkuFeY8WQKBgQDC+lTZS9Ky3ywT6/RO4rH8HZ4e+Gl9VJjvpizaDVVwJ5Yidv0/jAxrR4xf+024v0/bBCKfOQELGKSdnviTzQDw+7dXZALOwzL+839hYv0x3H+61WHzofdMpptnqulhOJGtvM5+6oNOC80f5GtOa6qcP1GZDDAK9Xc2oaXPI/qwBA==",
                    "json","utf-8",
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiWyHDlf25ss4hN6iY0aC4FOlA4vv2YJLaRsUDnX96ZWvlLRQOfQ2MbdF7fhu0S8XabGpG3tQSBh6XK9AMNgu7pCYGZHVAIB0+B26RRm2giDUtVXYVIi754lpUUiMmj80piUkFeaojNDkqWUc8rZeIsamLGE321oSyHQdhw8MuYsuedrGPvNy2j4ZRuay6ZcTCV7YRSQ4/svioVwian2+qUNeQ/Cs2RRWE05vgfDKz+1oWDdWgulzEki+p+DbXCKZRf2Nx6nvD57z+b9Y8gWdsIVx2H3eIcm5xovIlCSNODuKA1zVgmNbQc99gYqpjP3Jj/QUWBUJ9t/WmV1mMWUqmQIDAQAB","RSA2");

            AlipayTradePrecreateRequest req = new AlipayTradePrecreateRequest();
            req.setNotifyUrl("http://xingyi.gz2vip.91tunnel.com/AliPayCallback");
            req.setReturnUrl("http://xingyi.free.idcfengye.com/Alipay");
            AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
            req.setBizModel(model);
            model.setOutTradeNo(OutTradeNo);
            model.setTotalAmount(TotalAmount);
            model.setSubject(Subject);
            AlipayTradePrecreateResponse rsp = null;
            try {
                rsp = alipayClient.execute(req);
                System.out.print(rsp.getBody());
                System.out.print(rsp.getQrCode());
                out.print(rsp.getBody());
            } catch (AlipayApiException e) {
                e.printStackTrace();
                out.print("{\"alipay_trade_precreate_response\":{\"code\":\"99999\",\"msg\":\"系统错误:"+e.getMessage()+"\"}");
            }


        }catch (Exception e){
            jsonUtil.setMsg("错误"+e.getMessage());
            jsonUtil.setCode(1);
        }


        

    }
}
