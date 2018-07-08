package cn.chenc.service.impl;

import java.sql.SQLException;
import java.util.List;

import cn.chenc.dao.UserDao;
import cn.chenc.dao.impl.UserDaoImpl;
import cn.chenc.po.User;
import cn.chenc.service.UserService;

public class UserServiceImpl implements UserService {

	UserDao dao=new UserDaoImpl();
	@Override
	public User login(User user) {
		return dao.login(user);
	}
	
	@Override
	public boolean register(User user) {
		return dao.register(user);
	}
	
	@Override
	public List<User> findAll(int pageIndex) throws SQLException{
		int pageSize=5;
		int pageStart=(pageIndex-1)*pageSize;
		List<User> list=dao.findAll(pageStart,pageSize);
		return list;
	}
	
	@Override
	public List<User> findAll(int pageIndex,String search) throws SQLException{
		int pageSize=5;
		int pageStart=(pageIndex-1)*pageSize;
		List<User> list=dao.findAll(pageStart,pageSize,search);
		return list;
	}
	
	@Override
	public int getPage() throws SQLException {
		return (int) Math.ceil((double)dao.getNum()/5);
	}
	
	@Override
	public int getPage(String search) throws SQLException {
		return (int) Math.ceil((double)dao.getNum(search)/5);
	}
	
	public boolean edit(User user) {
		return dao.edit(user);
	}
	
	public boolean delete(int id) {
		return dao.delete(id);
	}

}
