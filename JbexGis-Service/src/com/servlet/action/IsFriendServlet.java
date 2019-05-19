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
import com.json.service.JsonService;
import com.json.tools.JsonTools;

public class IsFriendServlet extends HttpServlet {


	private static final long serialVersionUID = 1403306667488537868L;

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
		
		PrintWriter out = response.getWriter();
		Boolean flag ;
		String owneruser=request.getParameter("owneruser");
		String frienduser=request.getParameter("frienduser");
		
		flag=TFriendsAction.IsFriends(owneruser, frienduser);
		
		out.print(flag);
		out.flush();
		out.close();
	}

}
