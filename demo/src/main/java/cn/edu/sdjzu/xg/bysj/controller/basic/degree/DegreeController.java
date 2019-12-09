package cn.edu.sdjzu.xg.bysj.controller.basic.degree;

import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.service.DegreeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mysql.cj.xdevapi.JsonString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 将所有方法组织在一个Controller(Servlet)中
 */
@RestController
public class DegreeController {
    @RequestMapping(value = "/degree.ctl",method = RequestMethod.GET)
    public String  get(@RequestParam(value="id",required = false)String id_str){
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学位对象，否则响应id指定的学位对象
            if (id_str == null) {
                return responseDegrees();
            } else {
                int id = Integer.parseInt(id_str);
                return responseDegree(id);
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            //响应message到前端
            return message.toJSONString();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
            //响应message到前端
            return message.toJSONString();
        }
    }

    //响应一个学位对象
    private String   responseDegree(int id)
            throws SQLException {
        //根据id查找学院
        Degree degree = DegreeService.getInstance().find(id);
        String degree_json = JSON.toJSONString(degree);
        return degree_json;
    }

    //响应所有学位对象
    private String  responseDegrees()
            throws SQLException {
        //获得所有学院
        Collection<Degree> degrees = DegreeService.getInstance().findAll();
        String degrees_json = JSON.toJSONString(degrees, SerializerFeature.DisableCircularReferenceDetect);
        return degrees_json;
    }
    @RequestMapping(value = "/degree.ctl",method = RequestMethod.POST)
    protected JSON post(HttpServletRequest request)
            throws IOException {
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //根据request对象，获得代表参数的JSON字串
        String degree_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Degree对象
        Degree degreeToAdd = JSON.parseObject(degree_json, Degree.class);
        try {
            //增加加Degree对象
            boolean ifAdded = DegreeService.getInstance().add(degreeToAdd);
            if(ifAdded){
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
    @RequestMapping(value = "/degree.ctl",method = RequestMethod.DELETE)
    protected JSON delete(@RequestParam(value = "id",required = false) String id_str) {
        int id = Integer.parseInt(id_str);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //到数据库表中删除对应的学院
            boolean ifDeleted = DegreeService.getInstance().delete(id);
            if(ifDeleted){
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
    @RequestMapping(value = "/degree.ctl",method = RequestMethod.PUT)
    protected JSON doPut(HttpServletRequest request)
            throws IOException {
        String degree_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Degree对象
        Degree degreeToAdd = JSON.parseObject(degree_json, Degree.class);
        JSONObject message = new JSONObject();
        //到数据库表修改School对象对应的记录
        try {
            //增加加Degree对象
            boolean ifAlter = DegreeService.getInstance().update(degreeToAdd);
            if(ifAlter){
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


}
