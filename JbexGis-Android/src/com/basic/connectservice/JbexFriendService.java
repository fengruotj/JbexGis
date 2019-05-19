package com.basic.connectservice;

import java.util.HashMap;

import android.util.Log;

public class JbexFriendService {
	public static final String PATH_URL_AddJbexFriend = HttpUtil.BASE_URL+"servlet/JBexFriendServlet";
	
	public static boolean addJBexFriend(String owneruser,String frienduser,Long jbexinfoId){
		String url=PATH_URL_AddJbexFriend;
		
		HashMap<String, String> map = new HashMap<String, String>();
		String strFlag="";
		map.put("owneruser", owneruser);
		map.put("frienduser", frienduser);
		map.put("jbexinfoId", String.valueOf(jbexinfoId));
		map.put("style", "add");
		
		try {
			strFlag =HttpUtil.postRequest(url, map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			Log.d("http请求结果", "失败！");
		}
		
		if (strFlag.trim().equals("true")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean deleteJBexFriend(String owneruser,String frienduser,Long jbexinfoId){
		String url=PATH_URL_AddJbexFriend;
		
		HashMap<String, String> map = new HashMap<String, String>();
		String strFlag="";
		map.put("owneruser", owneruser);
		map.put("frienduser", frienduser);
		map.put("jbexinfoId", String.valueOf(jbexinfoId));
		map.put("style", "delete");
		
		try {
			strFlag =HttpUtil.postRequest(url, map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			Log.d("http请求结果", "失败！");
		}
		
		if (strFlag.trim().equals("true")) {
			return true;
		} else {
			return false;
		}
	}
}
