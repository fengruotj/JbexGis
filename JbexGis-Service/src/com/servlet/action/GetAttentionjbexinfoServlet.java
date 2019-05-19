package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.bean.TJbrxinfo;
import com.basic.dao.TJbexinfoAction;
import com.json.service.JsonService;
import com.json.tools.JsonTools;

public class GetAttentionjbexinfoServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 34600670665572531L;

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
		
		String NowTime=request.getParameter("NowTime");
		String userid=request.getParameter("userid");
		
		List<TJbrxinfo> jbexinfo=TJbexinfoAction.getAttentionJbexInfo(userid, NowTime);
		
		String PublicInfoJsonString = JsonTools.createJsonString("jbexinfo",
				JsonService.getJbexInfo(jbexinfo));
		
		out.print(PublicInfoJsonString);
		out.flush();
		out.close();
	}

}
