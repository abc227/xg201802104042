package cn.edu.sdjzu.xg.bysj.dao;

import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import cn.edu.sdjzu.xg.bysj.domain.User;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;


public final class UserDao {
	private static UserDao userDao = new UserDao();

	private UserDao() {
	}

	public static UserDao getInstance() {
		return userDao;
	}

	private static Collection<User> users;

	public Collection<User> findAll() throws SQLException{
		Collection<User> users = new TreeSet<User>();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		Statement statement = connection.createStatement();
		//执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
		ResultSet resultSet = statement.executeQuery("select * from user");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()){
			//创建Degree对象，根据遍历结果中的id,description,no,remarks值
			User user = new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getDate("loginTime"), TeacherDao.getInstance().find(resultSet.getInt("teacher_id")));
			//向degrees集合中添加Degree对象
			users.add(user);
		}
		//关闭资源
		JdbcHelper.close(resultSet,statement,connection);
		return users;
	}

	public User find(Integer id) throws SQLException{
		User user = null;
		Connection connection = JdbcHelper.getConn();
		String findUser_sql = "SELECT * FROM user WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(findUser_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()){
			Teacher teacher = TeacherDao.getInstance().find(resultSet.getInt("teacher_id"));
			user = new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getDate("loginTime"),teacher);
		}
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		return user;
	}
	public User loign(String  username,String password) throws SQLException{
		User user = null;
		Connection connection = JdbcHelper.getConn();
		String findUser_sql = "SELECT * FROM user WHERE username=? and password=? ";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(findUser_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,username);
		preparedStatement.setString(2,password);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()){
			Teacher teacher = TeacherDao.getInstance().find(resultSet.getInt("teacher_id"));
			user = new User(resultSet.getInt("id"),
					resultSet.getString("username"),
					resultSet.getString("password"),
					resultSet.getDate("loginTime"),
					teacher);
		}
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		System.out.println(user);
		return user;
	}

	public User findByUsername(String  username) throws SQLException{
		User user = null;
		Connection connection = JdbcHelper.getConn();
		String findUser_sql = "SELECT * FROM user WHERE username=? ";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(findUser_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,username);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()){
			Teacher teacher = TeacherDao.getInstance().find(resultSet.getInt("teacher_id"));
			user = new User(resultSet.getInt("id"),
					resultSet.getString("username"),
					resultSet.getString("password"),
					resultSet.getDate("loginTime"),
					teacher);
		}
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		System.out.println(user);
		return user;
	}

	public boolean changePassword(User user) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateUser_sql = " update user set password =? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateUser_sql);
		//为预编译参数赋值
		preparedStatement.setString(1, user.getPassword());
		preparedStatement.setInt(2, user.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement, connection);
		return affectedRows > 0;
	}
}
