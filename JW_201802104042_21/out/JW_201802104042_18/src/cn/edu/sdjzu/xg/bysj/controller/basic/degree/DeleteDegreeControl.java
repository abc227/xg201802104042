package cn.edu.sdjzu.xg.bysj.controller.basic.degree;

import cn.edu.sdjzu.xg.bysj.service.DegreeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/degree/delete.ctl")
public class DeleteDegreeControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置UTF8编码
        response.setContentType("application/json;charset=UTF-8");
        try {
            //从请求中获得id值，并赋值给id_str
            String id_str = request.getParameter("id");
            //将String类型的id_str转换为int类型
            int id = Integer.parseInt(id_str);
            //删除学院
            DegreeService.getInstance().delete(id);
            System.out.println("delete doGet");
            //将请求重定向查询学院，以刷新学院信息
            response.sendRedirect("/html/basic/degree.html");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
