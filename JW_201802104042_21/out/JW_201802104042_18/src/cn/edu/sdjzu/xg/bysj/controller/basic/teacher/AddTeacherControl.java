package cn.edu.sdjzu.xg.bysj.controller.basic.teacher;

import cn.edu.sdjzu.xg.bysj.dao.DepartmentDao;
import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import cn.edu.sdjzu.xg.bysj.service.DegreeService;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import cn.edu.sdjzu.xg.bysj.service.ProfTitleService;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/teacher/add.ctl")
public class AddTeacherControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        try {
            //根据request对象，获得代表参数的JSON字串
            String teacher_json = JSONUtil.getJSON(request);
            JSONObject jsonObject = JSONObject.parseObject(teacher_json);
            //将JSON字串解析为School对象
            Teacher teacherToAdd = JSON.parseObject(teacher_json, Teacher.class);
            //用大于4的随机数给schoolToAdd的id赋值
            teacherToAdd.setId(4 + (int) (1000 * Math.random()));
            //增加加School对象
            int degreeId= Integer.parseInt(jsonObject.getString("degree_id"));
            int departmentId= Integer.parseInt(jsonObject.getString("department_id"));
            int profTitleId= Integer.parseInt(jsonObject.getString("profTitle_id"));
            Degree degree = DegreeService.getInstance().find(degreeId);
            Department department = DepartmentService.getInstance().find(departmentId);
            ProfTitle profTitle = ProfTitleService.getInstance().find(profTitleId);
            teacherToAdd.setDegree(degree);
            teacherToAdd.setDepartment(department);
            teacherToAdd.setTitle(profTitle);
            TeacherService.getInstance().add(teacherToAdd);
            //创建JSON对象
            JSONObject resp = new JSONObject();
            //加入数据信息
            resp.put("MSG", "OK");
            //响应
            response.getWriter().println(resp);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
