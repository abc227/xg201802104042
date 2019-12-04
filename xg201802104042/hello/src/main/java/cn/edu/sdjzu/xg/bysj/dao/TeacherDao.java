package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public final class TeacherDao {
	private static TeacherDao teacherDao=new TeacherDao();
	private TeacherDao(){}
	public static TeacherDao getInstance(){
		return teacherDao;
	}

	public Collection<Teacher> findAll() throws SQLException {
		Collection<Teacher> teachers = new HashSet<Teacher>();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		Statement statement = connection.createStatement();
		//执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
		ResultSet resultSet = statement.executeQuery("select * from teacher");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()){
			//创建Degree对象，根据遍历结果中的id,description,no,remarks值
			Teacher teacher = new Teacher(resultSet.getInt("id"),resultSet.getString("no"),resultSet.getString("name"),ProfTitleDao.getInstance().find(resultSet.getInt("profTitle_id")), DegreeDao.getInstance().find(resultSet.getInt("degree_id")),DepartmentDao.getInstance().find(resultSet.getInt("department_id")));
			//向degrees集合中添加Degree对象
			teachers.add(teacher);
		}
		//关闭资源
		JdbcHelper.close(resultSet,statement,connection);
		return teachers;
	}
	public Teacher find(Integer id) throws SQLException{
		Teacher teacher = null;
		Connection connection = JdbcHelper.getConn();
		String findTeacher_sql = "select * FROM teacher WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(findTeacher_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			ProfTitle profTitle = ProfTitleDao.getInstance().find(resultSet.getInt("profTitle_id"));
			Degree degree = DegreeDao.getInstance().find(resultSet.getInt("degree_id"));
			Department department = DepartmentDao.getInstance().find(resultSet.getInt("department_id"));
			//若结果集仍然有下一条记录，则执行循环体
			teacher = new Teacher(resultSet.getInt("id"),resultSet.getString("no"),resultSet.getString("name"),profTitle,degree,department );
		}
		JdbcHelper.close(preparedStatement,connection);
		return teacher;
	}

	public boolean add(Teacher teacher) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		PreparedStatement preparedStatement = null;
		Date date = new Date();
		int affectedRowNum = 0;
		int teacher_id=0;
		try {
			//关闭连接的自动提交，事务开始
			connection.setAutoCommit(false);
			String addTeacher_sql = "INSERT INTO teacher (no,name,profTitle_id,degree_id,department_id) VALUES" + " (?,?,?,?,?)";
			//在该连接上创建预编译语句对象
			preparedStatement = connection.prepareStatement(addTeacher_sql);
			//为预编译参数赋值
			preparedStatement.setString(1, teacher.getNo());
			preparedStatement.setString(2, teacher.getName());
			preparedStatement.setInt(3, teacher.getTitle().getId());
			preparedStatement.setInt(4, teacher.getDegree().getId());
			preparedStatement.setInt(5, teacher.getDepartment().getId());
			//执行预编译语句，获取添加记录行数并赋值给affectedRowNum
			affectedRowNum = preparedStatement.executeUpdate();
			String findTeacher_sql = "select * FROM teacher WHERE name=? and no=? ";
			//在该连接上创建预编译语句对象
			preparedStatement = connection.prepareStatement(findTeacher_sql);
			//为预编译参数赋值
			preparedStatement.setString(1,teacher.getName());
			preparedStatement.setString(2,teacher.getNo());
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				teacher_id = resultSet.getInt("id");
			}
			String addUser_sql = "INSERT INTO user(username,password,loginTime,teacher_id) VALUES" + "(?,?,?,?)";
			//在该连接上创建预编译语句对象
			preparedStatement = connection.prepareStatement(addUser_sql);
			//为预编译参数赋值
			preparedStatement.setString(1, teacher.getNo());
			preparedStatement.setString(2, teacher.getNo());
			preparedStatement.setDate(3, new java.sql.Date(date.getTime()));
			preparedStatement.setInt(4,teacher_id );
			affectedRowNum = preparedStatement.executeUpdate();
			//手动提交申请，事务结束
			connection.commit();
		} catch (SQLException e) {
			//若发生异常输出出错信息和错误码
			System.out.println(e.getMessage() + "\nErrorCode:" + e.getErrorCode());
			try {
				//如果连接不为空，则回滚到insert之前的状态
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException d) {
				d.printStackTrace();
			}
		} finally {//最终执行
			try {
				//如果连接不为空，重新开启自动提交
				if (connection != null) {
					connection.setAutoCommit(true);
				}
			} catch (SQLException f) {
				f.printStackTrace();
			}
			//关闭资源
			JdbcHelper.close(preparedStatement, connection);
			return affectedRowNum > 0;
		}
	}

	//delete方法，根据degree的id值，删除数据库中对应的degree对象
	public boolean delete(int id) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		PreparedStatement preparedStatement = null;
		int affectedRows =0;
		try {
			//关闭连接的自动提交，事务开始
			connection.setAutoCommit(false);
			//写sql语句
			String deleteUser_sql = "DELETE FROM user WHERE teacher_id=?";
			//在该连接上创建预编译语句对象
			preparedStatement = connection.prepareStatement(deleteUser_sql);
			//为预编译参数赋值
			preparedStatement.setInt(1, id);
			//关闭preparedStatement对象
			affectedRows = preparedStatement.executeUpdate();
			//写sql语句
			String deleteTeacher_sql = "DELETE FROM teacher WHERE id=?";
			//在该连接上创建预编译语句对象
			preparedStatement = connection.prepareStatement(deleteTeacher_sql);
			//为预编译参数赋值
			preparedStatement.setInt(1, id);
			//关闭preparedStatement对象
			affectedRows = preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			//若发生异常输出出错信息和错误码
			System.out.println(e.getMessage() + "\nErrorCode:" + e.getErrorCode());
			e.printStackTrace();
			try {
				//如果连接不为空，则回滚到insert之前的状态
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException d) {
				d.printStackTrace();
			}
		} finally {//最终执行
			try {
				//如果连接不为空，重新开启自动提交
				if (connection != null) {
					connection.setAutoCommit(true);
				}
			} catch (SQLException f) {
				f.printStackTrace();
			}
			//关闭资源
			JdbcHelper.close(preparedStatement, connection);
			return affectedRows > 0;
		}
	}

	public boolean update(Teacher teacher) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateDepartment_sql = " update teacher set name=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateDepartment_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,teacher.getName());
		preparedStatement.setInt(2,teacher.getId());
		//关闭preparedStatement对象
		int affectedRows = preparedStatement.executeUpdate();
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}

}
