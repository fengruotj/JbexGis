package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.bean.TPersonaldynamic;
import com.basic.dao.TPersonaldynamicAction;
import com.json.service.JsonService;
import com.json.tools.JsonTools;

public class GetFriendynamicinfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2782307314875557815L;

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
		
		String userid=request.getParameter("userid");
		List<TPersonaldynamic> dynamicinfo=TPersonaldynamicAction.getFriendPersonaldynamic(userid);
		
		String dynamicInfoJsonString = JsonTools.createJsonString("dynamicinfo",
				JsonService.getdynamicInfo(dynamicinfo));
		
		out.print(dynamicInfoJsonString);
		out.flush();
		out.close();
	}
}
