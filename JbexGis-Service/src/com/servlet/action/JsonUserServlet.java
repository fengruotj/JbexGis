package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;
import com.basic.dao.TFriendsAction;
import com.json.service.JsonService;
import com.json.tools.JsonTools;

public class JsonUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		PrintWriter out = response.getWriter();

		// 从客户端获得username字段，用于向数据库查询和返回字段
		String username = request.getParameter("email");
		String kind = request.getParameter("kind");

		List<TUser> userFamilyList = TFriendsAction.getUserFriends(username,
				"家人");
		List<TUser> userClassmateList = TFriendsAction.getUserFriends(
				username, "同学");
		List<TUser> userFriendList = TFriendsAction.getUserFriends(username,
				"好友");
		
		String familyjsonString = JsonTools.createJsonString("family",
				JsonService.getGroupUser(userFamilyList));
		String classmatesjsonString = JsonTools.createJsonString("classmates",
				JsonService.getGroupUser(userClassmateList));
		String friendsjsonString = JsonTools.createJsonString("friends",
				JsonService.getGroupUser(userFriendList));

		System.out.println("JSON:" + familyjsonString + classmatesjsonString
				+ friendsjsonString);

		if(kind.equals("family")){
			out.print(familyjsonString);
		}else if(kind.equals("classmates")){
			out.print(classmatesjsonString);
		}else if(kind.equals("friends")){
			out.print(friendsjsonString);
		}else{
			out.print("");
		}

		out.flush();
		out.close();
	}
}
