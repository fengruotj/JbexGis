package com.basic.connectservice;

import java.util.HashMap;

import android.util.Log;

public class FriendService {
	public static final String setFriendsGroupURL = HttpUtil.BASE_URL+"servlet/SetFriendGroupServlet";
	public static final String addFriendsGroupURL = HttpUtil.BASE_URL+"servlet/FriendsGroupServlet";
	public static final String deleteFriendsGroupURL = HttpUtil.BASE_URL+"servlet/deleteFriendServlet";
	
	public static boolean setFriendsGroup(String owneruser,String frienduser,String GroupName){
		String url=setFriendsGroupURL;
		Log.d("url", url);
		String strFlag="";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("owneruser", owneruser);
		map.put("frienduser", frienduser);
		map.put("GroupName", GroupName);
		
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
	
	public static boolean addFriendGroup(String owneruser,String frienduser,String GroupName){
		String url=addFriendsGroupURL;
		Log.d("url", url);
		String strFlag="";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("owneruser", owneruser);
		map.put("frienduser", frienduser);
		map.put("style", "add");
		map.put("GroupName", GroupName);
		
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
	
	public static boolean deleteFriend(String owneruser,String frienduser){
		String url=deleteFriendsGroupURL;
		Log.d("url", url);
		String strFlag="";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("owneruser", owneruser);
		map.put("frienduser", frienduser);
		
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
