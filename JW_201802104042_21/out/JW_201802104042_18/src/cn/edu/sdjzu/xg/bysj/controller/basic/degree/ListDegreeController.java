package cn.edu.sdjzu.xg.bysj.controller.basic.degree;

import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.service.DegreeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/degree/list.ctl")
public class ListDegreeController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置UTF8编码
        response.setContentType("text/html;charset=UTF-8");
        try {
            //像DegreeService的getInstance发送findAll消息，得到degrees集合
            Collection<Degree> degrees = DegreeService.getInstance().findAll();
            //将degrees集合转变成String类型
            String degrees_str = JSON.toJSONString(degrees, SerializerFeature.DisableCircularReferenceDetect);
            System.out.println(degrees_str);
            //将degrees_str发送给客户端
            response.getWriter().println(degrees_str);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
