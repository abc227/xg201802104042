package cn.edu.sdjzu.xg.bysj.controller.basic.teacher;

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
import java.util.Collection;

@WebServlet("/teacher.ctl")
public class TeacherController extends HttpServlet {
    /**
     * 方法-功能
     * put 修改
     * post 添加
     * delete 删除
     * get 查找
     */

    /**
     * post http:106.54.75.83/bysj1842/teacher.ctl
     * 增加一个老师对象：将来自前端请求的JSON对象，增加到数据库表中
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //创建JSON对象
        JSONObject message = new JSONObject();
        //根据request对象，获得代表参数的JSON字串
        String teacher_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        Teacher teacherToAdd = JSON.parseObject(teacher_json, Teacher.class);
        try {
            TeacherService.getInstance().add(teacherToAdd);
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * delete http:106.54.75.83/bysj1842/teacher.ctl
     * 删除一个老师对象：根据来自前端请求的id，删除数据库表中id的对应记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //创建JSON对象
        JSONObject message = new JSONObject();
        //
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        try {
            //删除学院
            boolean ifDelete = TeacherService.getInstance().delete(id);
            if (ifDelete) {
                message.put("message", "删除成功");
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * put http:106.54.75.83/bysj1842/teacher.ctl
     * 修改一个老师对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //从请求中获取json字串
        String teacher_json = JSONUtil.getJSON(request);
        //将JSON字串解析为teacher对象
        Teacher teacherToUpdate = JSON.parseObject(teacher_json, Teacher.class);
        try {
            //增加加Degree对象
            boolean ifUpdate = TeacherService.getInstance().update(teacherToUpdate);
            if (ifUpdate) {
                message.put("message", "数据库修改成功");
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * get http:106.54.75.83/bysj1842/teacher.ctl
     * 响应一个或所有老师对象
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //读取参数id
        String id_str = request.getParameter("id");
        try {
            //如果id = null, 表示响应所有老师对象，否则响应id指定的老师对象
            if (id_str == null) {
                responseTeachers(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseTeacher(id, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        }
        System.out.println(message);
    }

    //响应一个老师对象
    private void responseTeacher(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学院
        Teacher teacher = TeacherService.getInstance().find(id);
        String teacher_json = JSON.toJSONString(teacher);

        //响应message到前端
        response.getWriter().println(teacher_json);
    }

    //响应所有对象
    private void responseTeachers(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学院
        Collection<Teacher> teachers = TeacherService.getInstance().findAll();
        String teachers_json = JSON.toJSONString(teachers);

        //响应message到前端
        response.getWriter().println(teachers_json);
    }

}
