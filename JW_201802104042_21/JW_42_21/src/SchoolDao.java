

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
	public Collection<School> findAll() throws ClassNotFoundException,SQLException{
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

	public boolean update(int id) throws ClassNotFoundException,SQLException{
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
	public School addWithStoredProcedure(School school) throws SQLException,ClassNotFoundException{
		//创建连接
		Connection connection = JdbcHelper.getConn();
		//创建CallableStatement对象，根据连接对象准备可调用的语句对象，sp为存储过程名称，后面为4哥参数
		CallableStatement callableStatement = connection.prepareCall("{CALL sp_addSchool(?,?,?,?)}");
		//将第四个参数设置为输出参数，类型为bigint
		callableStatement.registerOutParameter(4, Types.BIGINT);
		callableStatement.setString(1,school.getNo());
		callableStatement.setString(2,school.getDescription());
		callableStatement.setString(3,school.getRemarks());
		//执行callableStatement
		callableStatement.execute();
		//获得第四个参数的值，数据为该记录自动生成的id
		int id = callableStatement.getInt(4);
		//为school的id赋值
		school.setId((id));
		//关闭
		callableStatement.close();
		connection.close();
		return  school;
	}

	//创建main方法，查询数据库中的对象，并输出
	public static void main(String[] args) throws ClassNotFoundException,SQLException{
		//删
		schoolDao.delete(2);
		//增
		School addschool = new School("02","博士","");
		SchoolDao schoolDao = new SchoolDao();
		School school= schoolDao.addWithStoredProcedure(addschool);
		System.out.println(school);
		//改
		schoolDao.update(2);
		//查
		schoolDao.findAll();
	}
}
