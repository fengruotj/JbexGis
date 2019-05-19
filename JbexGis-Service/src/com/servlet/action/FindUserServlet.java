package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;
import com.basic.dao.TUserAction;
import com.json.service.JsonService;
import com.json.tools.JsonTools;

public class FindUserServlet extends HttpServlet {
	
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
		Boolean flag = true;
		TUser user = null;
		String userjsonString = "";
		List<TUser> list=new ArrayList<TUser>();
		
		String type=request.getParameter("type");
		String email=request.getParameter("email");
		
		list=TUserAction.findUser(email, type);
		
		if(list.size()!=0)
		{	
		user = list.get(0);
		//userdao.getSession().flush();// 先把数据更新到数据库
		//userdao.getSession().refresh(user);//把把对象更新为和数据库一致
		userjsonString = JsonTools.createJsonString("user",
				JsonService.getLoginUser(user, flag.toString()));
		out.println(userjsonString);
		}
		else{
		 out.println(userjsonString);
		}
		
		System.out.println("JSON:" + userjsonString);
		out.flush();
		out.close();
	}

}
