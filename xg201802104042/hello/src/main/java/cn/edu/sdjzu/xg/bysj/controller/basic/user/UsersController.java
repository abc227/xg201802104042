package cn.edu.sdjzu.xg.bysj.controller.basic.user;

import cn.edu.sdjzu.xg.bysj.domain.User;
import cn.edu.sdjzu.xg.bysj.service.UserService;
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

@WebServlet("/user.ctl")
public class UsersController extends HttpServlet {

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
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
        //响应message到前端
        response.getWriter().println(message);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //创建JSON对象
        JSONObject message = new JSONObject();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try{
            User user=UserService.getInstance().login(username,password);
            String user_json = JSON.toJSONString(user);
            //响应message到前端
            response.getWriter().println(user_json);
        }catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }
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
            message.put("message", "数据库异常");
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
            throws ServletException, IOException,SQLException {
        //根据id查找学院
        User user = UserService.getInstance().getUser(id);
        String user_json = JSON.toJSONString(user);

        //响应message到前端
        response.getWriter().println(user_json);
    }

    //响应所有对象
    private void responseTeachers(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学院
        Collection<User> users = UserService.getInstance().getUsers();
        String users_json = JSON.toJSONString(users);

        //响应message到前端
        response.getWriter().println(users_json);
    }
}
