package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.bean.TFriendrequest;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;
import com.basic.dao.TFriendrequestAction;
import com.basic.dao.TFriendsAction;
import com.json.service.JsonService;
import com.json.tools.JsonTools;

public class JsonFriendRequestServlet extends HttpServlet {


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
		
		String frienduser=request.getParameter("frienduser");
		List<TFriendrequest> friendrequest=TFriendrequestAction.getFriendrequest(frienduser);
		
		String friendrequestJsonString=JsonTools.createJsonString("friendrequest",
				JsonService.getFriendRequest(friendrequest));
		System.out.println(friendrequestJsonString);
		
		out.print(friendrequestJsonString);
		
		out.flush();
		out.close();
	}
}
