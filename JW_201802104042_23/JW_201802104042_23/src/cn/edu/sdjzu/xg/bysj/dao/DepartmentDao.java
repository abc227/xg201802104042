package cn.edu.sdjzu.xg.bysj.dao;

import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.School;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public final class DepartmentDao {
	private static DepartmentDao departmentDao=new DepartmentDao();
	private DepartmentDao(){}

	public static DepartmentDao getInstance(){
		return departmentDao;
	}


	public Collection<Department> findAll() throws SQLException {
		Collection<Department> departments = new HashSet<Department>();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		Statement statement = connection.createStatement();
		//执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
		ResultSet resultSet = statement.executeQuery("select * from department");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()){
			//创建Degree对象，根据遍历结果中的id,description,no,remarks值
			Department department = new Department(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"), SchoolDao.getInstance().find(resultSet.getInt("school_id")));
			//向degrees集合中添加Degree对象
			departments.add(department);
		}
		//关闭资源
		JdbcHelper.close(resultSet,statement,connection);
		return departments;
	}

	public Department find(Integer id) throws SQLException{
		Department department = null;
		Connection connection = JdbcHelper.getConn();
		String findDepartment_sql = "select * FROM department WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(findDepartment_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			School school = SchoolDao.getInstance().find(resultSet.getInt("school_id"));
		//若结果集仍然有下一条记录，则执行循环体
			department = new Department(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getString("no"), resultSet.getString("remarks"), school);
		}
		JdbcHelper.close(preparedStatement,connection);
		return department;
	}

	public Collection<Department> findAllBySchool(Integer school_id) throws SQLException{
		Collection<Department> departments = new HashSet<Department>();
		Connection connection = JdbcHelper.getConn();
		String findDepartment_sql = "select * FROM department WHERE school_id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(findDepartment_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,school_id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			School school = SchoolDao.getInstance().find(resultSet.getInt("school_id"));
			//若结果集仍然有下一条记录，则执行循环体
			Department department = new Department(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getString("no"), resultSet.getString("remarks"), school);
			departments.add(department);
		}
		JdbcHelper.close(preparedStatement,connection);
		return departments;
	}


	public boolean add(Department department) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql = "INSERT INTO department (description,no,remarks,school_id) VALUES"+" (?,?,?,?)";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(addDegree_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,department.getDescription());
		preparedStatement.setString(2,department.getNo());
		preparedStatement.setString(3,department.getRemarks());
		preparedStatement.setInt(4,department.getSchool().getId());
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
		String deleteDepartment_sql = "DELETE FROM department WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteDepartment_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//关闭preparedStatement对象
		int affectedRows = preparedStatement.executeUpdate();
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}

	public boolean update(Department department) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateDepartment_sql = " update department set description=?,no=?,remarks=?,school_id=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateDepartment_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,department.getDescription());
		preparedStatement.setString(2,department.getNo());
		preparedStatement.setString(3,department.getRemarks());
		preparedStatement.setInt(4,department.getSchool().getId());
		preparedStatement.setInt(5,department.getId());
		//关闭preparedStatement对象
		int affectedRows = preparedStatement.executeUpdate();
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}

	//创建main方法，查询数据库中的对象，并输出
	public static void main(String[] args) throws ClassNotFoundException,SQLException{
		//删
		//DepartmentDao.getInstance().delete(1);
		/*School school = SchoolDao.getInstance().find(1);
		Department department = new Department("信管","01","",school);
		DepartmentDao.getInstance().add(department);

		 */

		/*Department department1 = departmentDao.getInstance().find(3);
		System.out.println(department1);
		department1.setDescription("房管");
		DepartmentDao.getInstance().update(department1);
		Department department2 = DepartmentDao.getInstance().find(3);
		System.out.println(department2.getDescription());

		 */

		System.out.println(DepartmentDao.getInstance().findAll());

	}
}

