package cn.edu.sdjzu.xg.bysj.controller.basic.department;

import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
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

@WebServlet("/department/add.ctl")
public class AddDepartmentControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            //根据request对象，获得代表参数的JSON字串
            String department_json = JSONUtil.getJSON(request);

            //根据JSON字串获得JSON对象
            JSONObject jsonObject = JSON.parseObject(department_json);
            //将JSON字串解析为department对象
            Department departmentToAdd = JSON.parseObject(department_json, Department.class);
            //用大于4的随机数给schoolToAdd的id赋值
            departmentToAdd.setId(4 + (int) (1000 * Math.random()));
            //根据json对象获得的school_id值，获得一个School对象
            int schoolId = Integer.parseInt(jsonObject.getString("school_id"));
            School addSchool = SchoolService.getInstance().find(schoolId);
            //向departmentToAdd发送setSchool消息，传入addSchool参数
            departmentToAdd.setSchool(addSchool);
            //增加departmentToAdd对象
            DepartmentService.getInstance().add(departmentToAdd);

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
