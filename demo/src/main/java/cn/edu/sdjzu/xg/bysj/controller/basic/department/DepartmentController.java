package cn.edu.sdjzu.xg.bysj.controller.basic.department;

import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

@RestController
public class DepartmentController  {
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
     *
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/department.ctl",method = RequestMethod.POST)
    @ResponseBody
    protected String doPost(HttpServletRequest request)
            throws ServletException, IOException {
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //根据request对象，获得代表参数的JSON字串
        String department_json = JSONUtil.getJSON(request);
        //将JSON字串解析为department对象
        Department departmentToAdd = JSON.parseObject(department_json, Department.class);
        try {
            //增加departmentToAdd对象
            boolean ifAdded = DepartmentService.getInstance().add(departmentToAdd);
            if(ifAdded){
                message.put("message", "数据库操作成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        return message.toString();
    }

    /**
     * delete http:106.54.75.83/bysj1842/department.ctl
     * 删除一个系对象：根据来自前端请求的id，删除数据库表中id的对应记录
     *
     *
     * @throws ServletException
     * @throws IOException
     */

    @RequestMapping(value = "/department.ctl",method = RequestMethod.DELETE)
    protected JSON doDelete(@RequestParam(value = "id",required = false) String id_str)
            throws ServletException, IOException {
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
        return message;
    }

    /**
     * put http:106.54.75.83/bysj1842/department.ctl
     * 修改一个系对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     *
     * @param request
     *
     * @throws ServletException
     * @throws IOException
     */

    @RequestMapping(value = "/department.ctl",method = RequestMethod.PUT)
    protected String doPut(HttpServletRequest request)
            throws ServletException, IOException {
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //从请求中获取JSON字串
        String department_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Department对象
        Department departmentToUpdate = JSON.parseObject(department_json, Department.class);
        try {
            //增加加Degree对象
            boolean ifAlter = DepartmentService.getInstance().update(departmentToUpdate);
            if(ifAlter) {
                message.put("message", "数据库操作正常");
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        return message.toString();
    }

    /**
     * get http:106.54.75.83/bysj1842/department.ctl
     * 响应一个或所有系对象
     *
     *
     *
     * @throws ServletException
     * @throws IOException
     */

    @RequestMapping(value = "/department.ctl",method = RequestMethod.GET)
    protected String doGet(@RequestParam(value="id",required = false)String id_str,@RequestParam(value="type",required = false)String type){
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
           if(type==null){
                if(id_str==null){
                    return responseDepartments();
                }
                else{
                    int id = Integer.parseInt(id_str);
                    return responseDepartment(id);
                }
           }
           else{
               if(type.contains("school")){
                   int school_id = Integer.parseInt(id_str);
                   return responseDepartmentsBySchool(school_id);
               }
           }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        return message.toString();
    }

    //响应对象
    private String responseDepartment(int id)
            throws ServletException, IOException, SQLException {
        //根据id查找学院
        Department department = DepartmentService.getInstance().find(id);
        //将department转换成department_json字串
        String department_json = JSON.toJSONString(department);
        return department_json;
    }

    //响应所有对象
    private String responseDepartments()
            throws SQLException {
        //获得所有学院
        Collection<Department> departments = DepartmentService.getInstance().getAll();
        String departments_json = JSON.toJSONString(departments);
        return departments_json;
    }

    private String responseDepartmentsBySchool(int school_id)
            throws SQLException {
        //获得所有学院
        Collection<Department> departments = DepartmentService.getInstance().findAllBySchool(school_id);
        String departments_json = JSON.toJSONString(departments);
        return departments_json;
    }

}
