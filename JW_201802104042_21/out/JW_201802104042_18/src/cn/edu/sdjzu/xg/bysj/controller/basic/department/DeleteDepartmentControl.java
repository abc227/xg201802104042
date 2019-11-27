package cn.edu.sdjzu.xg.bysj.controller.basic.department;

import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/department/delete.ctl")
public class DeleteDepartmentControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            //根据请求获得id值
            String id_str = request.getParameter("id");
            //将id_str转换为int型
            int id = Integer.parseInt(id_str);
            //删除学院
            DepartmentService.getInstance().delete(id);
            System.out.println("delete doGet");
            //将请求重定向查询学院，以刷新学院信息
            //response.sendRedirect("/html/basic/department.html");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
