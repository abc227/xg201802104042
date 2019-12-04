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
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/department.ctl")
public class DepartmentController extends HttpServlet {
    private School School;
    /**
     * 方法-功能
     * put 修改
     * post 添加
     * delete 删除
     * get 查找
     */

    /**
     * post http:106.54.75.83/bysj1842/department.ctl
     * 响应一个或所有系对象
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //根据request对象，获得代表参数的JSON字串
        String department_json = JSONUtil.getJSON(request);
        //将JSON字串解析为department对象
        Department departmentToAdd = JSON.parseObject(department_json, Department.class);
        try {
            //增加departmentToAdd对象
            DepartmentService.getInstance().add(departmentToAdd);
        } catch (SQLException e) {
            System.out.println(departmentToAdd);
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * delete http:106.54.75.83/bysj1842/department.ctl
     * 删除一个系对象：根据来自前端请求的id，删除数据库表中id的对应记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        //根据请求获得id值
        String id_str = request.getParameter("id");
        //将id_str转换为int型
        int id = Integer.parseInt(id_str);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //删除学院
            boolean ifDelete = DepartmentService.getInstance().delete(id);
            if(ifDelete){
                message.put("message", "数据库操作成功");
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
     * put http:106.54.75.83/bysj1842/department.ctl
     * 修改一个系对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
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
        //从请求中获取JSON字串
        String department_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Department对象
        Department departmentToUpdate = JSON.parseObject(department_json, Department.class);
        try {
            //增加加Degree对象
            DepartmentService.getInstance().update(departmentToUpdate);
            message.put("message", "数据库操作正常");
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
     * get http:106.54.75.83/bysj1842/department.ctl
     * 响应一个或所有系对象
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
        String type = request.getParameter("paraType");
        try {
           if(type==null){
                if(id_str==null){
                    responseDepartments(response);
                }
                else{
                    int id = Integer.parseInt(id_str);
                    responseDepartment(id,response);
                }
           }
           else{
               if(type.contains("school")){
                   int school_id = Integer.parseInt(id_str);
                   responseDepartmentsBySchool(school_id,response);
               }
           }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        }
    }

    //响应对象
    private void responseDepartment(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学院
        Department department = DepartmentService.getInstance().find(id);
        //将department转换成department_json字串
        String department_json = JSON.toJSONString(department);

        //响应message到前端
        response.getWriter().println(department_json);
    }

    //响应所有对象
    private void responseDepartments(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学院
        Collection<Department> departments = DepartmentService.getInstance().getAll();
        String departments_json = JSON.toJSONString(departments);

        //响应message到前端
        response.getWriter().println(departments_json);
    }

    private void responseDepartmentsBySchool(int school_id,HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学院
        Collection<Department> departments = DepartmentService.getInstance().findAllBySchool(school_id);
        String departments_json = JSON.toJSONString(departments);

        //响应message到前端
        response.getWriter().println(departments_json);
    }

}
