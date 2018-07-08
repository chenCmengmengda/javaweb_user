package cn.chenc.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.chenc.dao.UserDao;
import cn.chenc.po.User;
import cn.chenc.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	public User login(User user) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ?";
		User existUser;
		try {
			existUser = queryRunner.query(sql, new BeanHandler<User>(User.class), user.getUsername(),user.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("用户登录失败!");
		}
		return existUser;
	}

	@Override
	public boolean register(User user) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into user (username,password)values(?,?)";
		Object [] params = new Object[]{user.getUsername(),user.getPassword()};
		int existUser;
		try {
			existUser = queryRunner.update(sql,params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("用户注册失败!");
		}
		return true;
	}

	@Override
	public List<User> findAll(int pageStart,int pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user LIMIT "+pageStart+","+pageSize;
		List<User> list=null;
		try {
			list = (List) qr.query( sql, new BeanListHandler(User.class));
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException("用户查询失败!");
		}
		return list;
	}
	
	@Override
	public List<User> findAll(int pageStart,int pageSize,String search) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user WHERE id LIKE '%"+search+"%' or username LIKE "
						+ "'%"+search+"%' or password LIKE '%"+search+"%' or type LIKE "
						+ "'%"+search+"%' LIMIT "+pageStart+","+pageSize;
		List<User> list=null;
		try {
			list = (List) qr.query( sql, new BeanListHandler(User.class));
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException("用户查询失败!");
		}
		return list;
	}
	
	public int getNum() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user";
		List<User> list=null;
		try {
			list = (List) qr.query( sql, new BeanListHandler(User.class));
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException("用户查询失败!");
		}
		int num=list.size();
		return num;
	}
	
	
	public int getNum(String search) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user WHERE id LIKE '%"+search+"%' or username LIKE "
				+ "'%"+search+"%' or password LIKE '%"+search+"%' or type LIKE '%"+search+"%' ";
		List<User> list=null;
		try {
			list = (List) qr.query( sql, new BeanListHandler(User.class));
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException("用户查询失败!");
		}
		int num=list.size();
		return num;
	}
	
	public boolean edit(User user) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "UPDATE user SET username=? , password = ? where id=?";
		Object [] params = new Object[]{user.getUsername(),user.getPassword(),user.getId()};
		int existUser;
		try {
			existUser = queryRunner.update(sql,params);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public boolean delete(int id) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "DELETE FROM user WHERE id=?";
		Object [] params = new Object[]{id};
		try {
			queryRunner.update(sql,params);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
