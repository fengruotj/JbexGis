package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.bean.TPublicinfo;
import com.basic.bean.TPublicinfoDAO;
import com.basic.dao.TPublicinfoAction;
import com.json.service.JsonService;
import com.json.tools.JsonTools;

@SuppressWarnings("serial")
public class GetPublicinfoServlet extends HttpServlet {

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
		
		List<TPublicinfo> publicinfo=TPublicinfoAction.getPublicinfo();
		
		String PublicInfoJsonString = JsonTools.createJsonString("publicinfo",
				JsonService.getPublicInfo(publicinfo));
		
		out.print(PublicInfoJsonString);
		out.flush();
		out.close();
	}

}
