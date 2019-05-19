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
import com.basic.dao.TUserAction;

public class UserInfoServlet extends HttpServlet {

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
	
		boolean flag = true;
        TUser user = new TUser();
		user.setEmail(request.getParameter("email"));
		user.setSex(Integer.parseInt(request.getParameter("sex")));
		user.setPersonSignature(request.getParameter("person_signature"));
		user.setTelephone(request.getParameter("telephone"));
		user.setSchool(request.getParameter("school"));
		user.setPicture(request.getParameter("picture"));
		user.setAcademy(request.getParameter("academy"));
		String bir = request.getParameter("birthday");
		java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		try {
			java.util.Date birthday = timeformat.parse(bir);
			user.setBirthday(birthday);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			System.out.println("String--->>Date:失败");
		}	
		TUserAction.setUser(user);
		
		out.print(flag);
		out.flush();
		out.close();
	}

}
