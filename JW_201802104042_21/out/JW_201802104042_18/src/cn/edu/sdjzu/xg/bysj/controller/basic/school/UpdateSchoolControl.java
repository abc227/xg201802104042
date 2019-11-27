package cn.edu.sdjzu.xg.bysj.controller.basic.school;

import cn.edu.sdjzu.xg.bysj.domain.School;
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

@WebServlet("/school/update.ctl")
public class UpdateSchoolControl extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String school_json = JSONUtil.getJSON(request);
            //将JSON字串解析为School对象
            School schoolToAdd = JSON.parseObject(school_json, School.class);

            System.out.println("schoolToAdd=" + schoolToAdd);
            //增加加School对象
            SchoolService.getInstance().update(schoolToAdd);

            //创建JSON对象
            JSONObject resp = new JSONObject();
            //加入数据信息
            resp.put("MSG", "OK");
            //响应
            response.getWriter().println(resp);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            System.out.println("update doGet");
            //读取参数id
            String id_str = request.getParameter("id");
            int id = Integer.parseInt(id_str);

            System.out.println("id=" + id);
            ;
            //删除学院
            School school = SchoolService.getInstance().find(id);

            String schoolsToUpdate_str = JSON.toJSONString(school);
            System.out.println("schoolsToUpdate_str=" + schoolsToUpdate_str);
            //响应
            response.getWriter().println(schoolsToUpdate_str);
//        response.sendRedirect("/html/basic/updateSchool.html");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
