package cn.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@Order(30)
@Repository
@WebFilter(filterName="Filter 30",urlPatterns = {"/*"})//所有资源均可以过滤
public class LoginFilter implements Filter{
    public void destroy(){}
    public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter 30 - begins");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        String url = request.getRequestURI();
        if(url.contains("/login.ctl")||url.contains("/mySchool")||url.equals("/")||url.contains("/cookies")){
            //执行其他过滤器，若过滤器已经执行完毕，则执行原请求
            chain.doFilter(req, res);
        }else {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("currentUser") == null) {
                response.getWriter().println("您没有登陆，请登录或者重新登录");
                return;
            }
            //执行其他过滤器，若过滤器已经执行完毕，则执行原请求
            chain.doFilter(req, res);
            return;
        }
        System.out.println("Filter 30 - ends");
    }
    public void init(FilterConfig config) throws ServletException{}
}
