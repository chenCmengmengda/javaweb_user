package cn.chenc.service;

import java.sql.SQLException;
import java.util.List;

import cn.chenc.po.User;

public interface UserService {

	public User login(User user);

	public boolean register(User user);

	/**
	 * 查询所有数据
	 * @return
	 * @throws SQLException
	 */
	List<User> findAll(int pageIndex) throws SQLException;
	
	public List<User> findAll(int pageIndex,String search) throws SQLException;

	/**
	 * 获取分页数
	 * @return
	 * @throws SQLException
	 */
	int getPage() throws SQLException;

	/**
	 * 根据条件获取页数
	 * @param search
	 * @return
	 * @throws SQLException
	 */
	int getPage(String search) throws SQLException;

	public boolean edit(User user);
	
	public boolean delete(int id);
}