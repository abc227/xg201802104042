package cn.edu.sdjzu.xg.bysj.controller.basic.login;

import cn.edu.sdjzu.xg.bysj.domain.User;
import cn.edu.sdjzu.xg.bysj.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class LoginController {

    @RequestMapping(value = "/login.ctl",method = RequestMethod.POST)
    @ResponseBody
    public JSON doPost(@RequestParam (value="username")String username, @RequestParam (value="password")String password, HttpServletRequest request){
        //创建JSON对象
        JSONObject message = new JSONObject();
        try{
            //获取登陆后的user对象
            User loginUser= UserService.getInstance().login(username,password);
           // String user_json = JSON.toJSONString(loginUser);
            if(loginUser!=null){
                //创建session对象
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(10*60);
                //设置属性
                session.setAttribute("currentUser",loginUser);
            }
            else{
                message.put("message","登陆失败请重试");
            }
        }catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        return message;
    }
}
