package cn.edu.sdjzu.xg.bysj.controller.basic.profTitle;

import cn.edu.sdjzu.xg.bysj.service.ProfTitleService;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profTitle/delete.ctl")
public class DeleteProfTitleControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置UTF8编码
        response.setContentType("application/json;charset=UTF-8");
        try {
            //根据请求获得id值
            String id_str = request.getParameter("id");
            //将id_str转换为int型
            int id = Integer.parseInt(id_str);
            //删除ProfTitle
            ProfTitleService.getInstance().delete(id);
            System.out.println("delete doGet");
            //将请求重定向查询ProfTitle
            response.sendRedirect("/html/basic/profTitle.html");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
