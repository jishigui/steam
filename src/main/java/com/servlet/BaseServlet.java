package com.servlet;

import com.google.gson.Gson;
import com.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {

     //张三做用户注册  调用register方法  out=  PrintWiter Hashcode=110 在张三做用户注册时，不未执行out输出，切换到李四线程，out就指向了为李四分配的PrinterWiter 对象了
                                  //  out.println();往张三客户端输出,out被后续的线程覆盖了 最终的输出结果就给到了李四
    //李四在同一时间 也做用户注册，调用register方法   out=  PrintWiter Hashcode=120
    // Servlet实例只有一个，在多线程的环境中调用方法

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 允许跨域的主机地址 */
        //Access-Control-Allow-Origin
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        response.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        response.setHeader("Access-Control-Max-Age", "4200");
        /* 允许跨域的请求头 */
        response.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        response.setHeader("Access-Control-Allow-Credentials", "true");

        HttpSession session =  request.getSession();
        String sessionId =   session.getId();
        System.out.println("为客户端分配的sessionID值为:"+sessionId);


        //System.out.println("BaseServlet中的doGet方法被调用");
         String command = request.getParameter("command");
         response.setContentType("text/html;charset=utf-8");
         PrintWriter out=response.getWriter();

        JsonUtil jsonUtil = new JsonUtil();
        Gson gson = new Gson();
        if (command==null || command.equals("")){
            jsonUtil.setCode(1);
            jsonUtil.setMsg("您的请求参数中未传递command,无法调用servlet相关方法"+command);
            out.print(gson.toJson(jsonUtil));

            return ;
        }

        //反射:得到Class
        //得到Class有三种方式   1 Class.forName  2 类名.class  3 对象名.getClass();

        Class cls =   this.getClass();
        //通过class得到类中的方法
        try {
   //com.servlet.UserReflectServlet.queryAjax(org.apache.catalina.connector.RequestFacade, org.apache.catalina.connector.ResponseFacade)
        //    public void queryAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            //System.out.println("request对象的实际类型为:"+ request.getClass().getName());;
            //request.getClass()是得到实参的类型
            //HttpServletRequest.class 得到的HttpServletRequest类的class类型
           Method method =  cls.getDeclaredMethod(command, HttpServletRequest.class, HttpServletResponse.class);

          /*
          class Person{
              info()
              info(int i)
          }

         Person p1 =  new Perosn();
          p1.info(1);


            Person p2 =  new Perosn();
            p2.info();

            Method m = Persn.info();
            m.invoke(p1)
             Method m1 = Persn.info(int.class);
            m1.invoke(p1,1)
            */
          //通过反射调用方法 就是调用Method对象的inovke方法
            method.invoke(this,request,response);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();

            jsonUtil.setCode(1);
            jsonUtil.setMsg("根据传递的command参数值"+command+"未找到与之匹配方法");
            out.print(gson.toJson(jsonUtil));

        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setCode(1);
            jsonUtil.setMsg("根据传递的command参数值"+command+"调用对应方法时出现异常"+e.getMessage());
             out.print(gson.toJson(jsonUtil));
        }

        //  cls.getMethod(command,request.getClass(),response.getClass());表示根据comamnd得到与commadn相匹配的方法
        //如command的值为login 就要获得  public void login(HttpServletRequest request, HttpServletResponse response) 方法

        //cls.getMethods();//得所有的
        //cls.getDeclaredMethods()


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doGet(req,resp);
    }
}
