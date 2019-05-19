package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.dao.TUserAction;
import com.json.service.JsonService;
import com.json.tools.JsonTools;

public class SetUserSecurityControl extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4925293517279180146L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");

		PrintWriter out = response.getWriter();
		
		String user_id = request.getParameter("user_id");
		String SecurityControl = request.getParameter("SecurityControl");
		
		TUserAction.setUserSecurityControl(user_id, SecurityControl);
		System.out.println("SecurityControl");
		out.print("true");
		out.flush();
		out.close();
	}


}
