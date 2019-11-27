
import java.sql.*;
import java.util.Collection;

public final class DepartmentDao {
	private static Collection<Department> departments;
	private static DepartmentDao departmentDao=new DepartmentDao();
	private DepartmentDao(){}

	public static DepartmentDao getInstance(){
		return departmentDao;
	}


	public Collection<Department> findAll() throws SQLException {
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		Statement statement = connection.createStatement();
		//执行statement，将影响行数赋值给resultSet
		ResultSet resultSet = statement.executeQuery("select * from department");
		//根据id值创建School对象
		School school = SchoolDao.getInstance().find(resultSet.getInt("id"));
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()){
			//创建Department对象，根据遍历结果中的id,description,no,remarks，school值
			Department department = new Department(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"),school);
			//向departments集合中添加Department对象
			departments.add(department);
		}
		//关闭资源
		JdbcHelper.close(resultSet,statement,connection);
		return departments;
	}

	//创建find方法
	public Department find(Integer id) throws SQLException{
		//声明find类型的变量
		Department department = null;
		//创建连接
		Connection connection = JdbcHelper.getConn();
		//创建sql语句
		String findDepartment_sql = "select * FROM department WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(findDepartment_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//执行预编译语句
		ResultSet resultSet = preparedStatement.executeQuery();
		//由于id不能取重复值，故结果集中最多有一条记录
		//若结果集有一条记录，则以当前记录中的id,description,no,remarks，school值为参数，创建Department对象
		//若结果集中没有记录，则本方法返回null
		if(resultSet.next()) {
			//根据id创建school对象
			School school = SchoolDao.getInstance().find(resultSet.getInt("id"));
			//创建Department对象，将其赋值给department
			department = new Department(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getString("no"), resultSet.getString("remarks"), school);
		}
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		//返回department
		return department;
	}

	//创建add方法
	public boolean add(Department department) throws SQLException,ClassNotFoundException{
		//创建连接
		Connection connection = JdbcHelper.getConn();
		//创建sql语句
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
	public boolean delete(int id) throws ClassNotFoundException,SQLException{
		//创建连接
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String deleteDepartment_sql = "DELETE FROM department WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteDepartment_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//执行预编译语句，获取删除记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}

	//创建更新方法
	public boolean update(Department department) throws ClassNotFoundException,SQLException{
		//创建连接
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
		//执行预编译语句，获取更改记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}

	//创建main方法，查询数据库中的对象，并输出
	public static void main(String[] args) throws ClassNotFoundException,SQLException{
		//删
		//degreeDao.delete(2);
		//增
		/*School school = SchoolDao.getInstance().find(3);
		Department department = new Department("信管","03","",school);
		DepartmentDao.getInstance().add(department);
		 */
		//获得一个Department对象，并输出
		Department department1 = departmentDao.getInstance().find(3);
		System.out.println(department1);
		//修改该对象属性
		department1.setDescription("工管");
		//执行departmentDao的update方法，修改表中数据
		DepartmentDao.getInstance().update(department1);
		//再次获得Department对象，并输出其改动的属性值
		Department department2 = DepartmentDao.getInstance().find(3);
		System.out.println(department2.getDescription());
	}
}

