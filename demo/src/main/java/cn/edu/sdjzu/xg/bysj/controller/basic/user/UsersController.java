package cn.edu.sdjzu.xg.bysj.controller.basic.user;

import cn.edu.sdjzu.xg.bysj.domain.User;
import cn.edu.sdjzu.xg.bysj.service.UserService;
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
public class UsersController {

    @RequestMapping(value = "/user.ctl",method = RequestMethod.PUT)
    @ResponseBody
    protected String doPut(HttpServletRequest request) throws IOException {
        //创建JSON对象
        JSONObject message = new JSONObject();
        String user = JSONUtil.getJSON(request);
        User userToAdd = JSON.parseObject(user, User.class);
        try{
            boolean ifUpset = UserService.getInstance().changePassword(userToAdd);
            if (ifUpset) {
                message.put("message", "修改成功");
            }
        }catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        return message.toJSONString();
    }

    @RequestMapping(value = "/user.ctl",method = RequestMethod.POST)
    @ResponseBody
    public String doPost(@RequestParam(value="username")String username, @RequestParam (value="password")String password, HttpServletRequest request) throws IOException{
        //创建JSON对象
        JSONObject message = new JSONObject();
        try{
            User user=UserService.getInstance().login(username,password);
            String user_json = JSON.toJSONString(user);
            return user_json;
        }catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        return message.toJSONString();
    }

    @RequestMapping(value = "/user.ctl",method = RequestMethod.GET)
    @ResponseBody
    protected String doGet(@RequestParam(value="id",required = false)String id_str)
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
            message.put("message", "数据库异常");
            e.printStackTrace();
            return message.toJSONString();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
            return message.toJSONString();
        }
    }

    //响应一个老师对象
    private String responseTeacher(int id)
            throws ServletException, IOException,SQLException {
        //根据id查找学院
        User user = UserService.getInstance().getUser(id);
        String user_json = JSON.toJSONString(user);
        return user_json;
    }

    //响应所有对象
    private String responseTeachers()
            throws ServletException, IOException, SQLException {
        //获得所有学院
        Collection<User> users = UserService.getInstance().getUsers();
        String users_json = JSON.toJSONString(users);
        return users_json;
    }
}
