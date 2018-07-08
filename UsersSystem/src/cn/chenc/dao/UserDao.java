package cn.chenc.dao;

import java.sql.SQLException;
import java.util.List;

import cn.chenc.po.User;

public interface UserDao {
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User login(User user);
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public boolean register(User user);
	
	/**
	 * 查询所有用户数据,分页
	 * @return
	 * @throws SQLException
	 */
	public List<User> findAll(int pageStart,int pageSize) throws SQLException;
	
	/**
	 * 条件查询所有用户数据,分页
	 * @return
	 * @throws SQLException
	 */
	public List<User> findAll(int pageStart,int pageSize,String search) throws SQLException;
	
	/**
	 * 搜索查询,分页
	 * @param where
	 * @return
	 * @throws SQLException
	 */
	public int getNum(String search) throws SQLException;
	
	/**
	 * 查询数据个数
	 * @return
	 * @throws SQLException
	 */
	public int getNum() throws SQLException;
	
	public boolean edit(User user);
	
	public boolean delete(int id);
}
