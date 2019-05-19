package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;
import com.basic.dao.TFriendsAction;

public class SetFriendGroupServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3859149641690677341L;


	/**
	 * doGet()里面调用doPost()
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}


	/**
	 * doPost()方法实现
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		Boolean flag=true;
		PrintWriter out = response.getWriter();
		String owneruser=request.getParameter("owneruser");
		String frienduser=request.getParameter("frienduser");
		String GroupName=request.getParameter("GroupName");
		
		if(GroupName.equals("结伴的好友"))
			GroupName="家人";
		
		TFriendsAction.setFriendGroup(owneruser, frienduser, GroupName);
		
		out.print(flag);
		out.flush();
		out.close();
	}

}
