package com.basic.connectservice;

import java.util.HashMap;

import android.util.Log;

import com.basic.service.model.User;

public class UserService {
	
	public static final String PATH_URL = HttpUtil.BASE_URL+"servlet/FindUserServlet?email=";
	public static final String PATH_URLIsfriend=HttpUtil.BASE_URL+"servlet/IsFriendServlet";
    public static User getUser(String email,String type){
    	 String url=PATH_URL+email+"&type="+type;
    	 Log.d("url", url);
    	 String jsonString = "";
    	 User user=new User();
    	 try {
 			jsonString = HttpUtil.getRequest(url);
 			 Log.d("jsonFriendUser", jsonString);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			Log.d("获取jsonString失败", "fail");
 		}
    	 if(jsonString.equals("")!=true){
    	  user= GetUser.getSimpleUser("user", jsonString);
    	  return user;
    	 }
    	 else 
    		 return null;
    }
    
    public static boolean registerUser(String user_nickname,String email ,String password){
    	 String strFlag = "";
 		// 使用Map封装请求参数
 		HashMap<String, String> map = new HashMap<String, String>();
 		map.put("email", email);
 		map.put("password", password);
 		map.put("user_nickname", user_nickname);
 		// 定义发送请求的URL
 		String url = HttpUtil.BASE_URL + "servlet/RegisterServlet"; // POST方式
 		try {
 			// 发送请求
 			strFlag = HttpUtil.postRequest(url, map); // POST方式
 		} catch (Exception e) {
 			e.printStackTrace();
 			Log.d("http请求结果", "失败！");
 		}

 		if (strFlag.trim().equals("true")) {
 			return true;
 		} else {
 			return false;
 		}
    }
    public static boolean LoginUser(String username,String password){

    	String jsonString = "";
		String flag;
		jsonString = doLogin(username, password);
		Log.d("登录结果", jsonString);
		if (jsonString.equals("flase")) {
			flag = "flase";
		} else {
			User user = GetUser.getSimpleUser("user", jsonString);
			flag = user.getFlag();
		}
		
		if (flag.trim().equals("true")) {
			return true;
		} else {
			return false;
		}
    }
    
    public static boolean Isfriend(String owneruser,String frienduser){
    	String url=PATH_URLIsfriend;
    	String strFlag="";
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("owneruser", owneruser);
    	map.put("frienduser",frienduser);
    	
    	try {
			strFlag = HttpUtil.postRequest(url, map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			Log.d("http请求结果", "失败！");
		} // POST方式
    	
    	if (strFlag.trim().equals("true")) {
			return true;
		} else {
			return false;
		}
    }
    
 // 定义发送请求的方法
 	private static String doLogin(String username, String password) {
 		String jsonString = "";
 		// 使用Map封装请求参数
 		HashMap<String, String> map = new HashMap<String, String>();
 		map.put("username", username);
 		map.put("password", password);
 		// 定义发送请求的URL
 		// String url = HttpUtil.BASE_URL + "servlet/LoginServlet?username=" +
 		// username
 		// + "&password=" + password; // GET方式
 		String url = HttpUtil.BASE_URL + "servlet/LoginServlet"; // POST方式
 		Log.d("url", url);
 		Log.d("username", username);
 		Log.d("password", password);
 		try {
 			// 发送请求
 			jsonString = HttpUtil.postRequest(url, map); // POST方式
 			// strFlag = HttpUtil.getRequest(url); // GET方式
 			Log.d("服务器返回json值", jsonString);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}

 		return jsonString;

 	}
}
