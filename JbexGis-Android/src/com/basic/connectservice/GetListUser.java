package com.basic.connectservice;

import java.util.ArrayList;
import java.util.List;

import com.basic.service.model.User;

import android.os.Handler;
import android.util.Log;

public class GetListUser {

	public static final String PATH_URL = HttpUtil.BASE_URL+"servlet/JsonUserServlet?email=";
	
	/**
	 * @param email 
	 * @param kind 有三种值，分别为：family、classmates、friends
	 * @return
	 */
	public static List<User> GetGroupList(String email, String kind) {

		String url = PATH_URL + email + "&kind=" + kind;
		Log.d("email", email);
		String jsonString = "";
		try {
			jsonString = HttpUtil.getRequest(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail");
		}
		List<User> familyList = new ArrayList<User>();
		List<User> classmateList = new ArrayList<User>();
		List<User> friendList = new ArrayList<User>();
		List<User> list=new ArrayList<User>();
		if (jsonString != null) {
			if (kind == "family") {
				familyList = GetUser.getMultiUser("family", jsonString);
				return familyList;
			} else if (kind == "classmates") {
				classmateList = GetUser.getMultiUser("classmates", jsonString);
				return classmateList;
			} else if (kind == "friends") {
				friendList = GetUser.getMultiUser("friends", jsonString);
				return friendList;
			} else {
				return list;
			}
		}else{
			return list;
		}
	}
}
