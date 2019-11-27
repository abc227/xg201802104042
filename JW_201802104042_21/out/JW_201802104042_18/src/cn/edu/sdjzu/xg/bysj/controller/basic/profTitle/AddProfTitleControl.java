package cn.edu.sdjzu.xg.bysj.controller.basic.profTitle;

import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.ProfTitleService;
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
import java.sql.SQLException;

@WebServlet("/profTitle/add.ctl")
public class AddProfTitleControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        try {
            //根据request对象，获得代表参数的JSON字串
            String profTitle_json = JSONUtil.getJSON(request);

            //将JSON字串解析为School对象
            ProfTitle profTitleToAdd = JSON.parseObject(profTitle_json, ProfTitle.class);
            //用大于4的随机数给schoolToAdd的id赋值
            profTitleToAdd.setId(4 + (int) (1000 * Math.random()));
            //增加加School对象
            ProfTitleService.getInstance().add(profTitleToAdd);

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
