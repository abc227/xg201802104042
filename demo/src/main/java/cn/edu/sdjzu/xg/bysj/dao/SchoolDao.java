package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.School;
import org.springframework.stereotype.Repository;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public final class SchoolDao {
	private static SchoolDao schoolDao = new SchoolDao();
	private int id;
	private String no;
	private String description;
	private String remarks;

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
		JdbcHelper.close(preparedStatement,connection);
        return school;
    }

	//findall方法返回Collection集合
	public Collection<School> findAll() throws SQLException{
		Set<School> schools = new HashSet<School>();
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
		JdbcHelper.close(statement,connection);
		//返回集合
		return schools;
	}

	//delete方法，根据degree的id值，删除数据库中对应的degree对象
	public boolean delete(int id) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String deleteSchool_sql = "DELETE FROM school WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteSchool_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//关闭preparedStatement对象
		int affectedRows = preparedStatement.executeUpdate();
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}

	public boolean update(School school) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateSchool_sql = " update school set description=?,no=?,remarks=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateSchool_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,school.getDescription());
		preparedStatement.setString(2,school.getNo());
		preparedStatement.setString(3,school.getRemarks());
		preparedStatement.setInt(4,school.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
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

}
