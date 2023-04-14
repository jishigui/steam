package filter;

import com.google.gson.Gson;
import com.util.JsonUtil;
import com.util.JwtToken;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;


//http://localhost/PersonnelServlet?command=queryPersonnnel

//@WebFilter("/*")   //表示拦截所有的请求
//@WebFilter("/PersonnelServlet") //表示拦截url中包含/PersonnelServlet的请求
//@WebFilter("*.html")  //拦截所有的请求url中以.html结尾地址，

//假设要拦截某些必需在用户登录后才能访问的Servlet，可以在配置WebServlet时给Servlet请求地址加一个后缀  .do
@WebFilter("*.do")   //拦截.do的请求
public class LoginValidateFilter  implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    /**
     * ServletRequest servletRequest   传递request对象
     *  ServletResponse servletResponse 传递reponse对象
     *    FilterChain filterChain 过滤器链 ，此对象的  filterChain.doFilter();方法被调用时，目标Servlet才会被调用
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {


        //把servletRequest强制转换为HttpServletRequest;

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        System.out.println("拦截到客户端的请求，客户端的请求地址为:"+request.getRequestURL());

        JsonUtil jsonUtil = new JsonUtil();
        //验证用户是否已登录
        servletResponse.setContentType("text/html;charset=utf-8");

        PrintWriter out = servletResponse.getWriter();
        Gson gson = new Gson();
        try{


            JwtToken jwtToken = new JwtToken();
            String jwtData =  jwtToken.getTokenData(request);
            System.out.println("得到的JWT解密后的数据为:"+jwtData);

            if (jwtData==null){ //当getTokenData为null时，表示用户未登录
                jsonUtil.setCode(777777);
                jsonUtil.setData("您还未登录，请先登录");
                //用resonse对象的输出流往客户端输出结果
            }else{
                //此方法被 调用 ，目标Servlet就会被访问
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
        }catch (Exception e){

            jsonUtil.setCode(777777);
            jsonUtil.setData("您还未登录，请先登录");
        }

        out.print(gson.toJson(jsonUtil));
    }

    @Override
    public void destroy() {

    }
}
