package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.bean.TUser;
import com.basic.dao.TPublicinfoAction;

public class AddPublicinfoServlet extends HttpServlet {

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

		String username = request.getParameter("username");
		Double dotX = Double.parseDouble(request.getParameter("dotX"));
		Double dotY = Double.parseDouble(request.getParameter("dotY"));
		String title = request.getParameter("title");
		String detail = request.getParameter("detail");
		String s_time = request.getParameter("time");
		Timestamp time = Timestamp.valueOf(s_time);
		String label = request.getParameter("label");

		long flag = TPublicinfoAction.addPublicinfo(username, dotX, dotY, title, detail,
				time, label);

		out.print(String.valueOf(flag));
		out.flush();
		out.close();
	}

}
