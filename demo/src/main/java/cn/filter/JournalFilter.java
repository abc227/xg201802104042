package cn.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Order(20)
@Repository
@WebFilter(filterName="Filter 20",urlPatterns = {"/*"})//所有资源均可以过滤
public class JournalFilter implements Filter{
    public void destroy(){}
    public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter 20 begins");
        HttpServletRequest request = (HttpServletRequest) req;
        String url = request.getRequestURI();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(url+"@"+df.format(date));
        //执行其他过滤器，若过滤器已经执行完毕，则执行原请求
        chain.doFilter(req, res);
        System.out.println("Filter 20 ends");
    }
    public void init(FilterConfig config) throws ServletException{}
}
