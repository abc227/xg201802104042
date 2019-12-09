package cn.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
@Order(10)
@Repository
@WebFilter(filterName="Filter 10",urlPatterns = {"/*"})//所有资源均可以过滤
public class Filter10 implements Filter {
    public void destroy(){}
    public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter 10  begins");
        //执行其他过滤器，若过滤器已经执行完毕，则执行原请求
        chain.doFilter(req, res);
        System.out.println("Filter 10 ends");
    }
    public void init(FilterConfig config) throws ServletException{}
}
