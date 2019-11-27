package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import util.JdbcHelper;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

public final class ProfTitleDao {
	private static ProfTitleDao profTitleDao=new ProfTitleDao();
	private ProfTitleDao(){}
	public static ProfTitleDao getInstance(){
		return profTitleDao;
	}

	//findall方法返回Collection集合
	public Set<ProfTitle> findAll() throws SQLException{
		Set<ProfTitle> profTitles = new TreeSet<ProfTitle>();
		Connection connection = JdbcHelper.getConn();
		//创建语句盒子
		Statement statement = connection.createStatement();
		//查询
		ResultSet resultSet = statement.executeQuery("select * from profTitle");
		//如果resultSet仍有下一行，将他们依次加入到degrees集合中
		while(resultSet.next()) {
			ProfTitle profTitle = new ProfTitle(resultSet.getInt("id"),resultSet.getString("description"),
					resultSet.getString("no"), resultSet.getString("remarks"));
			profTitles.add(profTitle);
		}
		return profTitles;
	}

	public ProfTitle find(Integer id) throws SQLException{
		ProfTitle profTitle = null;
		Connection connection = JdbcHelper.getConn();
		String findProfTitle_sql = "SELECT * FROM proftitle WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(findProfTitle_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		ResultSet resultSet = preparedStatement.executeQuery();
		//由于id不能取重复值，故结果集中最多有一条记录
		//若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Degree对象
		//若结果集中没有记录，则本方法返回null
		if (resultSet.next()){
			profTitle = new ProfTitle(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"));
		}
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		return profTitle;
	}


	public boolean add(ProfTitle profTitle) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String addProfTitle_sql = "INSERT INTO profTitle (description,no,remarks) VALUES"+" (?,?,?)";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(addProfTitle_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,profTitle.getDescription());
		preparedStatement.setString(2,profTitle.getNo());
		preparedStatement.setString(3,profTitle.getRemarks());
		//执行预编译语句，获取添加记录行数并赋值给affectedRowNum
		int affectedRowNum=preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRowNum>0;
	}


	public boolean update(ProfTitle profTitle) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateProfTitle_sql = "  update profTitle set description=?,no=?,remarks=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateProfTitle_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,profTitle.getDescription());
		preparedStatement.setString(2,profTitle.getNo());
		preparedStatement.setString(3,profTitle.getRemarks());
		preparedStatement.setInt(4,profTitle.getId());
		//关闭preparedStatement对象
		int affectedRows = preparedStatement.executeUpdate();
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}

	//delete方法，根据degree的id值，删除数据库中对应的degree对象
	public boolean delete(int id) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String deleteProTitle_sql = "DELETE FROM profTitle WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteProTitle_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//关闭preparedStatement对象
		int affectedRows = preparedStatement.executeUpdate();
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}
	//创建main方法，查询数据库中的对象，并输出
	public static void main(String[] args) throws ClassNotFoundException,SQLException{
		//删
		//profTitleDao.delete(3);
		//增

		ProfTitle profTitle = new ProfTitle(1,"数据库教授","01","");
		ProfTitleDao.getInstance().add(profTitle);
		//改
		/*ProfTitle profTitle = ProfTitleDao.getInstance().find(4);
		System.out.println(profTitle);
		profTitle.setDescription("博士");
		ProfTitleDao.getInstance().update(profTitle);
		ProfTitle profTitle2 = ProfTitleDao.getInstance().find(4);
		System.out.println(profTitle2.getDescription());

		 */
	}
}

