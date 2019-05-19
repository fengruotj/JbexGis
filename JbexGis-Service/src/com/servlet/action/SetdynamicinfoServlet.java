package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.bean.TJbrxinfo;
import com.basic.bean.TPersonaldynamic;
import com.basic.dao.TJbexinfoAction;
import com.basic.dao.TPersonaldynamicAction;

public class SetdynamicinfoServlet extends HttpServlet {
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
		Boolean flag=true;
		String user_id=request.getParameter("id");
		String dotX=request.getParameter("dotX");
		String dotY=request.getParameter("dotY");
		String detail=request.getParameter("detail");
		String str_time=request.getParameter("time");
		String picture1=request.getParameter("picture1");
		String picture2=request.getParameter("picture2");
		
		TPersonaldynamic dynamicinfo=new TPersonaldynamic();
		dynamicinfo.setId(Long.valueOf(user_id));
		dynamicinfo.setDetail(detail);
		dynamicinfo.setDotX(Double.valueOf(dotX));
		dynamicinfo.setDotY(Double.valueOf(dotY));
		dynamicinfo.setPicture1(picture1);
		dynamicinfo.setPicture2(picture2);
		
		java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Timestamp timestamp=Timestamp.valueOf(str_time);
		dynamicinfo.setTime(timestamp);
		
		TPersonaldynamicAction.setPersonaldynamic(dynamicinfo);
		
		out.print(flag);
		out.flush();
		out.close();
	}

}
