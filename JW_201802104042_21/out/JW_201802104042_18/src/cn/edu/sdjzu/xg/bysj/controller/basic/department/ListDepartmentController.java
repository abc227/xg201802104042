package cn.edu.sdjzu.xg.bysj.controller.basic.department;

import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/department/list.ctl")
public class ListDepartmentController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置utf8代码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            //创建departments集合
            Collection<Department> departments = DepartmentService.getInstance().getAll();
            //将departments转换成字串
            String departments_str = JSON.toJSONString(departments, SerializerFeature.DisableCircularReferenceDetect);
            System.out.println(departments_str);
            //向客户端发送departments_str字串
            response.getWriter().println(departments_str);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
