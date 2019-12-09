package cn.edu.sdjzu.xg.bysj.controller.basic.logout;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class LogoutController{
    @RequestMapping(value = "/lgout.ctl",method = RequestMethod.GET)
    @ResponseBody
    public void doGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
