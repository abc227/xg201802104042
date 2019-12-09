package cn.edu.sdjzu.xg.bysj.controller.basic.cookies;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cookies.ctl")
public class Cookies extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] allCookies = request.getCookies();
        if(allCookies==null){
            response.getWriter().println("no cookies");
        }
        else{
            for(Cookie cookies:allCookies){
                response.getWriter().println("name: "+cookies.getName()+"\n value: "+cookies.getValue());
            }
        }
    }
}
