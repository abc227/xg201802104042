package test;

import cn.edu.sdjzu.xg.bysj.dao.SchoolDao;
import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;

import java.sql.SQLException;

public class SchoolTest {
    public static void main(String[] args)throws SQLException,ClassNotFoundException {
        //创建school对象
        School schoolToAdd = new School("信息管理","02","the best");
        //创建schoolDao对象
        SchoolDao.getInstance().add(schoolToAdd);
        System.out.println("成功");
    }
}
