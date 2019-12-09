package cn.edu.sdjzu.xg.bysj.controller.basic.teacher;

import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@Controller
public class TeacherController {
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
     *
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/teacher.ctl",method = RequestMethod.POST)
    @ResponseBody
    protected String doPost(HttpServletRequest request)
            throws  IOException {
        //创建JSON对象
        JSONObject message = new JSONObject();
        //根据request对象，获得代表参数的JSON字串
        String teacher_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        Teacher teacherToAdd = JSON.parseObject(teacher_json, Teacher.class);
        try {
            boolean ifadd = TeacherService.getInstance().add(teacherToAdd);
            if(ifadd){
                message.put("message","数据库操作正常");
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        return message.toJSONString();
    }

    /**
     * delete http:106.54.75.83/bysj1842/teacher.ctl
     * 删除一个老师对象：根据来自前端请求的id，删除数据库表中id的对应记录
     *
     *
     * @throws ServletException
     * @throws IOException
     */

    @RequestMapping(value = "/teacher.ctl",method = RequestMethod.DELETE)
    @ResponseBody
    protected String doDelete(@RequestParam(value = "id",required = false)String id_str)
            throws ServletException, IOException {
        //创建JSON对象
        JSONObject message = new JSONObject();
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
        return message.toJSONString();
    }

    /**
     * put http:106.54.75.83/bysj1842/teacher.ctl
     * 修改一个老师对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     *
     * @param request
     *
     * @throws ServletException
     * @throws IOException
     */

    @RequestMapping(value = "/teacher.ctl",method = RequestMethod.PUT)
    @ResponseBody
    protected String doPut(HttpServletRequest request)
            throws ServletException, IOException {
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
        return message.toJSONString();
    }

    /**
     * get http:106.54.75.83/bysj1842/teacher.ctl
     * 响应一个或所有老师对象
     *
     *
     * @throws ServletException
     * @throws IOException
     */

    @RequestMapping(value = "/teacher.ctl",method = RequestMethod.GET)
    @ResponseBody
    protected String  doGet(@RequestParam(value="id",required = false)String id_str)
            throws ServletException, IOException {
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有老师对象，否则响应id指定的老师对象
            if (id_str == null) {
                return responseTeachers();
            } else {
                int id = Integer.parseInt(id_str);
                return responseTeacher(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        return message.toJSONString();
    }

    //响应一个老师对象
    private String responseTeacher(int id)
            throws ServletException, IOException, SQLException {
        //根据id查找学院
        Teacher teacher = TeacherService.getInstance().find(id);
        String teacher_json = JSON.toJSONString(teacher);
        return teacher_json;
    }

    //响应所有对象
    private String responseTeachers()
            throws ServletException, IOException, SQLException {
        //获得所有学院
        Collection<Teacher> teachers = TeacherService.getInstance().findAll();
        String teachers_json = JSON.toJSONString(teachers);
        return teachers_json;
    }

}
