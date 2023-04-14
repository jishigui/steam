package filter;

import com.server.ServerDemo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.net.UnknownHostException;


@WebFilter("*.xxxxx")
public class WebSocketFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            new ServerDemo(8890).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }




}
