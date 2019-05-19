package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.bean.TPersonaldynamicDAO;
import com.basic.dao.TPersonaldynamicAction;
import com.basic.dao.TPublicinfoAction;
import com.json.service.JsonService;
import com.json.tools.JsonTools;

public class WebGetDynamicInfo extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8452795700582600207L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/x-json");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String userjsonString;
		PrintWriter out = response.getWriter();
		
		userjsonString = JsonTools.createJsonString("dynamic",
				JsonService.getdynamicInfo(TPersonaldynamicAction.getPersonaldynamic()));
		String callback = request.getParameter("callback");
		out.println(callback + "(" + userjsonString + ")");
		//System.out.println("JSON:" + userjsonString);
		System.out.println(callback);
		out.flush();
		out.close();
	}

}
