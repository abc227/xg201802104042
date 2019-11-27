package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.School;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public final class SchoolDao {
	private static SchoolDao schoolDao = new SchoolDao();
	private int id;
	private String no;
	private String description;
	private String remarks;
	Set<School> schools = new HashSet<School>();

	//private SchoolDao(){}
	
	public static SchoolDao getInstance(){
		return schoolDao;
	}

    public School find(Integer id) throws SQLException{
	    School school=null;
        Connection connection = JdbcHelper.getConn();
        String findSchool_sql = "select * FROM school WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(findSchool_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            school=new School(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"));
        }
        return school;
    }

	//findall方法返回Collection集合
	public Collection<School> findAll() throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//创建语句盒子
		Statement statement = connection.createStatement();
		//查询
		ResultSet resultSet = statement.executeQuery("select * from school");
		//如果resultSet仍有下一行，将他们依次加入到degrees集合中
		while(resultSet.next()) {
			School school = new School(resultSet.getInt("id"),resultSet.getString("no"),
					resultSet.getString("description"), resultSet.getString("remarks"));
			schools.add(school);
		}
		//返回集合
		return schools;
	}

	//delete方法，根据degree的id值，删除数据库中对应的degree对象
	public boolean delete(int id) throws ClassNotFoundException,SQLException{
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String deleteTeacher_sql = "DELETE FROM degree WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteTeacher_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//关闭preparedStatement对象
		int affectedRows = preparedStatement.executeUpdate();
		preparedStatement.close();
		//关闭connection对象，因为建立了连接所以必须关闭
		connection.close();
		return affectedRows>0;
	}

	public boolean update(int id) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateSchool_sql = "update degree set no = 2 WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateSchool_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//关闭preparedStatement对象
		int affectedRows = preparedStatement.executeUpdate();
		preparedStatement.close();
		//关闭connection对象，因为建立了连接所以必须关闭
		connection.close();
		return affectedRows>0;
	}
	public boolean add(School school) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String addSchool_sql = "INSERT INTO school (description,no,remarks) VALUES"+" (?,?,?)";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(addSchool_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,school.getDescription());
		preparedStatement.setString(2,school.getNo());
		preparedStatement.setString(3,school.getRemarks());
		//执行预编译语句，获取添加记录行数并赋值给affectedRowNum
		int affectedRowNum=preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRowNum>0;
	}

	//创建main方法，查询数据库中的对象，并输出
	public static void main(String[] args) throws ClassNotFoundException,SQLException{
		//删
		//schoolDao.delete(2);
		//增
		School addschool = new School("01","博士","");
		SchoolDao.getInstance().add(addschool);
		//改
		//schoolDao.update(2);
		//查
		//schoolDao.findAll();
	}
}
