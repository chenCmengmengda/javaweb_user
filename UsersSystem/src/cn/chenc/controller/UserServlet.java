package cn.chenc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.chenc.po.User;
import cn.chenc.service.UserService;
import cn.chenc.service.impl.UserServiceImpl;
import cn.chenc.utils.BaseServlet;

@WebServlet("/user")
public class UserServlet extends BaseServlet {
	
	UserService us = new UserServiceImpl();
	/**
	 * 登录的功能
	 * @throws IOException 
	 */
	public String login(HttpServletRequest request,HttpServletResponse response) 
			throws IOException{
		request.setCharacterEncoding("UTF-8");
		//接收数据
		Map<String, String[]> map = request.getParameterMap();
		User user=new User();
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		// 封装数据
		try {
			BeanUtils.populate(user, map);
			// 调用Service层处理数据 
			
			User existUser = us.login(user);
			if(existUser==null) {
				//登录失败
				String s="{\"message\":false}";
				JsonObject jsonObj = new JsonParser().parse(s).getAsJsonObject();
				out.print(jsonObj);
				out.flush();
			} else {
				String s="{\"message\":true}";
				JsonObject jsonObj = new JsonParser().parse(s).getAsJsonObject();
				out.print(jsonObj);
				out.flush();
			}
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 注册的功能
	 * @throws IOException 
	 */
	public String register(HttpServletRequest request,HttpServletResponse response) 
			throws IOException{
		request.setCharacterEncoding("UTF-8");
		// 接收数据
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		PrintWriter out = response.getWriter();
		// 封装数据
		try {
			BeanUtils.populate(user, map);
			// 调用Service层处理数据 
			
			boolean existUser = us.register(user);
			if (existUser!=true) {
				//用户已存在
				String s="{\"message\":false}";
				JsonObject jsonObj = new JsonParser().parse(s).getAsJsonObject();
				out.print(jsonObj);
				out.flush();
			} else {
				String s="{\"message\":true}";
				JsonObject jsonObj = new JsonParser().parse(s).getAsJsonObject();
				out.print(jsonObj);
				out.flush();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 查询所用用户
	 * @throws SQLException 
	 */
	public void findAll(HttpServletRequest request,HttpServletResponse response) 
			throws IOException{
		String pageString=request.getParameter("pageIndex");
		int pageIndex=Integer.parseInt(pageString);
		List list=null;
		PrintWriter out = response.getWriter();
		try {
			list=us.findAll(pageIndex);
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException("用户查询失败!");
		}
		
		Gson gson=new Gson();
		String s=gson.toJson(list);
		out.print(s);
		out.flush();
		out.close();
		return;
	}
	
	public void getPage(HttpServletRequest request,HttpServletResponse response) 
			throws SQLException, IOException {
		
		int pageNum=us.getPage();
		PrintWriter out = response.getWriter();
		String s="{\"pageNum\":"+pageNum+"}";
		JsonObject jsonObj = new JsonParser().parse(s).getAsJsonObject();
		out.print(jsonObj);
		out.flush();
		out.close();
	}
	
	public void getSearchPage(HttpServletRequest request,HttpServletResponse response) 
			throws SQLException, IOException {
		String search=request.getParameter("search");
		int pageNum=us.getPage(search);
		PrintWriter out = response.getWriter();
		String s="{\"pageNum\":"+pageNum+"}";
		JsonObject jsonObj = new JsonParser().parse(s).getAsJsonObject();
		out.print(jsonObj);
		out.flush();
		out.close();
	}
	
	//条件查询用户
	public void findSearchAll(HttpServletRequest request,HttpServletResponse response) 
			throws IOException{
		String search=request.getParameter("search");
		String pageString=request.getParameter("pageIndex");
		int pageIndex=Integer.parseInt(pageString);
		List list=null;
		PrintWriter out = response.getWriter();
		try {
			list=us.findAll(pageIndex,search);
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException("用户查询失败!");
		}
		
		Gson gson=new Gson();
		String s=gson.toJson(list);
		out.print(s);
		out.flush();
		out.close();
		return;
	}
	
	//修改用户
	public void edit(HttpServletRequest request,HttpServletResponse response) 
			throws IOException{
		request.setCharacterEncoding("UTF-8");
		//接收数据
		Map<String, String[]> map = request.getParameterMap();
		User user=new User();
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		// 封装数据
		try {
			BeanUtils.populate(user, map);
			// 调用Service层处理数据 
			
			boolean existUser = us.edit(user);
			if(!existUser) {
				//修改失败
				String s="{\"message\":false}";
				JsonObject jsonObj = new JsonParser().parse(s).getAsJsonObject();
				out.print(jsonObj);
				out.flush();
			} else {
				String s="{\"message\":true}";
				JsonObject jsonObj = new JsonParser().parse(s).getAsJsonObject();
				out.print(jsonObj);
				out.flush();
			}
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void delete(HttpServletRequest request,HttpServletResponse response) 
			throws IOException{
		String sid=request.getParameter("id");
		int id=Integer.parseInt(sid);
		PrintWriter out = response.getWriter();
		boolean b=us.delete(id);
		if(!b) {
			//删除失败
			String s="{\"message\":false}";
			JsonObject jsonObj = new JsonParser().parse(s).getAsJsonObject();
			out.print(jsonObj);
			out.flush();
		} else {
			String s="{\"message\":true}";
			JsonObject jsonObj = new JsonParser().parse(s).getAsJsonObject();
			out.print(jsonObj);
			out.flush();
		}
		out.close();
	}
	
	public void setCookie(HttpServletRequest request,HttpServletResponse response) {
		String str=request.getParameter("str");
		String val=request.getParameter("val");
		Cookie cookie = new Cookie(str,val);
		cookie.setMaxAge(3600);
		response.addCookie(cookie);
	}
	
	public void getCookie(HttpServletRequest request,HttpServletResponse response) 
			throws IOException {
		String str=request.getParameter("str");
		Cookie[] cookies=request.getCookies();
		PrintWriter out = response.getWriter();
		for(int i=0;cookies!=null && i<cookies.length;i++) {
			System.out.println(cookies.length);
			if(cookies[i].getName().equals(str)) {
				String val=cookies[i].getValue();
				String s="{\"val\":"+val+"}";
				JsonObject jsonObj = new JsonParser().parse(s).getAsJsonObject();
				out.print(jsonObj);
				out.flush();
				out.close();
				return;
			}
		}
	}
}
