package cn.edu.sdjzu.xg.bysj.controller.basic.profTitle;

import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import cn.edu.sdjzu.xg.bysj.service.ProfTitleService;
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
public class ProfTitleController {
    /**
     * 方法-功能
     * put 修改
     * post 添加
     * delete 删除
     * get 查找
     */

    /**
     * post http:106.54.75.83/bysj1842/profTitle.ctl
     * 响应一个或所有教师头衔对象
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @RequestMapping(value = "/profTitle.ctl",method = RequestMethod.POST)
    @ResponseBody
    protected String doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //根据request对象，获得代表参数的JSON字串
        String profTitle_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        ProfTitle profTitleToAdd = JSON.parseObject(profTitle_json, ProfTitle.class);
        try {
            //增加对象
            boolean ifAdded = ProfTitleService.getInstance().add(profTitleToAdd);
            if(ifAdded){
                message.put("message","数据库操作成功");
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }return  message.toJSONString();

    }

    /**
     * delete http:106.54.75.83/bysj1842/profTitle.ctl
     * 删除一个教师头衔对象：根据来自前端请求的id，删除数据库表中id的对应记录
     *
     *
     * @throws ServletException
     * @throws IOException
     */

    @RequestMapping(value = "/profTitle.ctl",method = RequestMethod.DELETE)
    @ResponseBody
    protected String doDelete(@RequestParam(value = "id",required = false) String id_str)
            throws ServletException, IOException {
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //将id_str转换为int型
        int id = Integer.parseInt(id_str);
        try {
            //删除ProfTitle
            boolean ifDelete = ProfTitleService.getInstance().delete(id);
            if(ifDelete) {
                message.put("message", "数据库操作成功");
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

    /**
     * put http:106.54.75.83/bysj1842/profTitle.ctl
     * 修改一个教师头衔对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     *
     * @param request
     *
     * @throws ServletException
     * @throws IOException
     */

    @RequestMapping(value = "/profTitle.ctl",method = RequestMethod.PUT)
    @ResponseBody
    protected String doPut(HttpServletRequest request)
            throws ServletException, IOException {
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        String profTitle_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Degree对象
        ProfTitle profTitleToAdd = JSON.parseObject(profTitle_json, ProfTitle.class);
        try {
            //增加加Degree对象
            boolean ifAlter = ProfTitleService.getInstance().update(profTitleToAdd);
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
        return message.toJSONString();
    }

    /**
     * get http:106.54.75.83/bysj1842/ProfTitle.ctl
     * 响应一个或所有教师头衔对象
     *
     *
     * @throws ServletException
     * @throws IOException
     */

    @RequestMapping(value = "/profTitle.ctl",method = RequestMethod.GET)
    @ResponseBody
    protected String doGet(@RequestParam(value="id",required = false)String id_str) {
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有教师头衔对象，否则响应id指定的教师头衔对象
            if (id_str == null) {
                return responseProfTitles();
            } else {
                int id = Integer.parseInt(id_str);
                return responseProfTitle(id);
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

    //响应对象
    private String responseProfTitle(int id)
            throws SQLException {
        //根据id查找学院
        ProfTitle profTitle = ProfTitleService.getInstance().find(id);
        String profTitle_json = JSON.toJSONString(profTitle);
        return profTitle_json;
    }

    //响应所有对象
    private String responseProfTitles()
            throws ServletException, IOException, SQLException {
        //获得所有学院
        Collection<ProfTitle> profTitles = ProfTitleService.getInstance().getAll();
        String profTitles_json = JSON.toJSONString(profTitles);
        return profTitles_json;
    }

}
