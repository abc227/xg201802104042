package cn.edu.sdjzu.xg.bysj.controller.basic.degree;

import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.service.DegreeService;
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

@WebServlet("/degree/add.ctl")
public class AddDegreeControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //处理异常
        try {
            //根据request对象，获得代表参数的JSON字串
            String degree_json = JSONUtil.getJSON(request);
            //将JSON字串解析为School对象
            Degree degreeToAdd = JSON.parseObject(degree_json,Degree.class);
            //用大于4的随机数给schoolToAdd的id赋值
            //degreeToAdd.setId(4 + (int) (1000 * Math.random()));
            //增加加School对象
            DegreeService.getInstance().add(degreeToAdd);
            //创建JSON对象
            JSONObject resp = new JSONObject();
            //加入数据信息
            resp.put("MSG", "OK");
            //响应
            response.getWriter().println(resp);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
