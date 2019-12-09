package cn.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Order(0)
@Repository
@WebFilter(filterName="Filter 0",urlPatterns = {"/*"})//所有资源均可以过滤
public class EncodingFilter implements Filter {
    public void destroy(){}
    public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter 0 - encoding begins");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        String url = request.getRequestURI();
        if(url.contains("/mySchool")){
            chain.doFilter(req,res);
        }else {
            if (request.getMethod() == "post" || request.getMethod() == "put") {
                request.setCharacterEncoding("UTF-8");
            }
            response.setContentType("text/html;charset=UTF-8");
            //执行其他过滤器，若过滤器已经执行完毕，则执行原请求
            chain.doFilter(req, res);
        }
        System.out.println("Filter 0 - encoding ends");
    }
    public void init(FilterConfig config) throws ServletException{}
}
