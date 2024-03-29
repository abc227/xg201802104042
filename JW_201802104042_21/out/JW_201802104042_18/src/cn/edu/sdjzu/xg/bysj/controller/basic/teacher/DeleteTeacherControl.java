package cn.edu.sdjzu.xg.bysj.controller.basic.teacher;

import cn.edu.sdjzu.xg.bysj.service.TeacherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/teacher/delete.ctl")
public class DeleteTeacherControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String id_str = request.getParameter("id");
            int id = Integer.parseInt(id_str);
            //删除学院
            TeacherService.getInstance().delete(id);
            System.out.println("delete doGet");
            //将请求重定向查询学院，以刷新学院信息
            response.sendRedirect("/html/basic/teacher.html");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
