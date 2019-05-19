package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.dao.TJbexinfoAction;
import com.basic.dao.TPersonaldynamicAction;

public class DeletedynamicinfoServlet extends HttpServlet {
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
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		PrintWriter out = response.getWriter();
		Boolean flag =true;
		String dynamicinfoid=request.getParameter("dynamicinfoid");
		TPersonaldynamicAction.deletePersonaldynamic(dynamicinfoid);
		
		out.print(flag);
		out.flush();
		out.close();
	}
}
