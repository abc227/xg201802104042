package cn.edu.sdjzu.xg.bysj.controller.basic.school;

import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
public class SchoolController {
    @RequestMapping(value = "/school.ctl",method = RequestMethod.POST)
    @ResponseBody
    protected String doPost(HttpServletRequest request) throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String school_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Degree对象
        School schoolToAdd = JSON.parseObject(school_json, School.class);
        //创建JSON对象
        JSONObject message = new JSONObject();
        try {
            //增加School对象
            boolean ifAdded = SchoolService.getInstance().add(schoolToAdd);
            if(ifAdded) {
                message.put("message", "增加成功");
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

    @RequestMapping(value = "/school.ctl",method = RequestMethod.DELETE)
    @ResponseBody
    protected String doDelete(@RequestParam(value = "id",required = false)String id_str) throws IOException {
        int id = Integer.parseInt(id_str);
        //创建JSON对象
        JSONObject message = new JSONObject();
        try {
            //删除学院
            boolean ifDelete = SchoolService.getInstance().delete(id);
            //加入数据信息
            if(ifDelete) {
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

    @RequestMapping(value = "/school.ctl",method = RequestMethod.PUT)
    @ResponseBody
    protected String doPut(HttpServletRequest request) throws IOException {
        //根据request对象，获得代表参数的JSON字串
        String school_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        School schoolToAdd = JSON.parseObject(school_json, School.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //到数据库表修改School对象对应的记录
            boolean ifAlter = SchoolService.getInstance().update(schoolToAdd);
            if(ifAlter) {
                //加入数据信息
                message.put("message", "更新成功");
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

    @RequestMapping(value = "/school.ctl",method = RequestMethod.GET)
    @ResponseBody
    protected String doGet(@RequestParam(value="id",required = false)String id_str) throws IOException {
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学位对象，否则响应id指定的学位对象
            if (id_str == null) {
                //调用返回所有school的方法来响应
                return responseSchools();
            } else {
                //转化int
                int id = Integer.parseInt(id_str);
                //调用返回指定ID的school的方法来响应
                return responseSchool(id);
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            return message.toJSONString();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
            return message.toJSONString();
        }
    }

    private String responseSchool(int id)
            throws SQLException, IOException, ClassNotFoundException {
        //根据id查找学院
        School school = SchoolService.getInstance().find(id);
        //把json类型转化为字符串
        String school_json = JSON.toJSONString(school);
        return school_json;
    }

    //响应所有学位对象
    private String responseSchools()
            throws SQLException, IOException, ClassNotFoundException {
        //获得所有学院
        Collection<School> schools = SchoolService.getInstance().findAll();
        //将对象转化为json格式
        String schools_json = JSON.toJSONString(schools, SerializerFeature.DisableCircularReferenceDetect);
        return schools_json;
    }
}
