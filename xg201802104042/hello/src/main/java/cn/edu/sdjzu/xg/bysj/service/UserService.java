package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.UserDao;
import cn.edu.sdjzu.xg.bysj.domain.User;

import java.sql.SQLException;
import java.util.Collection;

public final class UserService {
	private UserDao userDao = UserDao.getInstance();
	private static UserService userService = new UserService();
	
	public UserService() {
	}
	
	public static UserService getInstance(){
		return UserService.userService;
	}

	public Collection<User> getUsers() throws SQLException{
		return userDao.findAll();
	}
	
	public User getUser(Integer id) throws SQLException {
		return userDao.find(id);
	}

//	public User login2(String username, String password) throws SQLException{
//		Collection<User> users = this.getUsers();
//		User desiredUser = null;
//		for(User user:users){
//			if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
//				desiredUser = user;
//			}
//		}
//		return desiredUser;
//	}
	public User login(String username, String password) throws SQLException{
		User user = UserDao.getInstance().loign(username,password);
		return user;
	}

	public boolean changePassword(User user) throws SQLException{
		return UserDao.getInstance().changePassword(user);
	}
}
