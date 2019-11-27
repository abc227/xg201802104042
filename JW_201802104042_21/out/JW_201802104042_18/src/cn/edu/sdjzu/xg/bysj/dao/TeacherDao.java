package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.*;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
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
			Teacher teacher = new Teacher(resultSet.getInt("id"),resultSet.getString("name"),ProfTitleDao.getInstance().find(resultSet.getInt("profTitle_id")),DegreeDao.getInstance().find(resultSet.getInt("degree_id")),DepartmentDao.getInstance().find(resultSet.getInt("department_id")));
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
			ProfTitle profTitle = ProfTitleDao.getInstance().find(resultSet.getInt("id"));
			Degree degree = DegreeDao.getInstance().find(resultSet.getInt("id"));
			Department department = DepartmentDao.getInstance().find(resultSet.getInt("id"));
			//若结果集仍然有下一条记录，则执行循环体
			teacher = new Teacher(resultSet.getInt("id"),resultSet.getString("name"),profTitle,degree,department );
		}
		return teacher;
	}
	public boolean add(Teacher teacher) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String addTeacher_sql = "INSERT INTO teacher (name,proftitle_id,degree_id,department_id) VALUES"+" (?,?,?,?)";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(addTeacher_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,teacher.getName());
		preparedStatement.setInt(2,teacher.getTitle().getId());
		preparedStatement.setInt(3,teacher.getDegree().getId());
		preparedStatement.setInt(4,teacher.getDepartment().getId());
		//执行预编译语句，获取添加记录行数并赋值给affectedRowNum
		int affectedRowNum=preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRowNum>0;
	}

	//delete方法，根据degree的id值，删除数据库中对应的degree对象
	public boolean delete(int id) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String deleteTeacher_sql = "DELETE FROM teacher WHERE id=?";
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

	public boolean update(Teacher teacher) throws ClassNotFoundException,SQLException{
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
		preparedStatement.close();
		//关闭connection对象，因为建立了连接所以必须关闭
		connection.close();
		return affectedRows>0;
	}

	public static void main(String[] args) throws SQLException{
		/*ProfTitle profTitle = ProfTitleDao.getInstance().find(1);
		Degree degree = DegreeDao.getInstance().find(1);
		Department department = DepartmentDao.getInstance().find(1);
		Teacher teacher = new Teacher(1,"李月",profTitle,degree,department);
		TeacherDao.getInstance().add(teacher);

		 */
		//TeacherDao.getInstance().delete(1);
	}

}
