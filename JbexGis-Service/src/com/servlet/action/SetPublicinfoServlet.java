package com.servlet.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.basic.bean.TPublicinfo;
import com.basic.dao.TAttentionAction;
import com.basic.dao.TPublicinfoAction;

public class SetPublicinfoServlet extends HttpServlet {

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
		String user_id=request.getParameter("user_id");
		String dotX=request.getParameter("dotX");
		String dotY=request.getParameter("dotY");
		String title=request.getParameter("title");
		String detail=request.getParameter("detail");
		String label=request.getParameter("label");
		String time=request.getParameter("time");
		String picture1=request.getParameter("picture1");
		String picture2=request.getParameter("picture2");
		
		TPublicinfo publicinfo=new TPublicinfo();
		publicinfo.setId(Long.valueOf(user_id));
		publicinfo.setDetail(detail);
		publicinfo.setDotX(Double.valueOf(dotX));
		publicinfo.setDotY(Double.valueOf(dotY));
		publicinfo.setLabel(label);
		publicinfo.setPicture1(picture1);
		publicinfo.setPicture2(picture2);
		
		java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Timestamp timestamp=Timestamp.valueOf(time);
		publicinfo.setTime(timestamp);
		publicinfo.setTitle(title);
		
		TPublicinfoAction.setPublicinfo(publicinfo);
		
		out.print(flag);
		out.flush();
		out.close();
	}


}
