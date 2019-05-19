package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.bean.TFriendrequestDAO;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;
import com.basic.dao.TFriendrequestAction;
import com.basic.dao.TFriendsAction;

public class AddFriendRequestServlet extends HttpServlet {


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
		String GroupName=request.getParameter("requestGroup");
		String validationmessage=request.getParameter("validationmessage");
		String bir = request.getParameter("requestDate");
		java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date requestDate=null;
		try {
			requestDate = timeformat.parse(bir);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		Timestamp time=Timestamp.valueOf(bir);
		if(validationmessage.equals("")){
			validationmessage="请求添加为好友";
		}
		flag=TFriendrequestAction.addFriendrequest(owneruser, frienduser, GroupName, time, validationmessage);
		out.print(flag);
		out.flush();
		out.close();
	}

}
