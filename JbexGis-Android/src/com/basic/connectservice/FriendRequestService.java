package com.basic.connectservice;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basic.service.model.FriendRequest;
import com.basic.service.model.User;

import android.R.integer;
import android.annotation.SuppressLint;
import android.util.Log;

public class FriendRequestService {
	public static final String addFriendRequestURL = HttpUtil.BASE_URL+"servlet/AddFriendRequestServlet";
	public static final String getFriendRequestListURL = HttpUtil.BASE_URL+"servlet/JsonFriendRequestServlet?frienduser=";
	public static final String deleteFriendRequestURL = HttpUtil.BASE_URL+"servlet/deleteFriendRequestServlet";
	
	@SuppressLint("SimpleDateFormat")
	public static boolean addFriendRequest(String owneruser,String frienduser,String requestGroup,Date requestDate,String validationmessage){
		String flag ="";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("owneruser", owneruser);
		map.put("frienduser", frienduser);
		map.put("requestGroup", requestGroup);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		map.put("requestDate", sdf.format(requestDate));
		map.put("validationmessage", validationmessage);
		String url=addFriendRequestURL;
		
		try {
			// 发送请求
			flag = HttpUtil.postRequest(url, map); // POST方式
			Log.d("保存返回的flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("保存设置请求服务器失败", "false");
		}
		if(flag.equals("true")){
			return true;
		}
		else return false;
	}
	
	public static boolean deleteFriendReuqest(String owneruser, String frienduser){
		String flag ="";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("owneruser", owneruser);
		map.put("frienduser", frienduser);
		String url=deleteFriendRequestURL;
		
		try {
			// 发送请求
			flag = HttpUtil.postRequest(url, map); // POST方式
			Log.d("保存返回的flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("保存设置请求服务器失败", "false");
		}
		if(flag.equals("true")){
			return true;
		}
		else return false;
		
	}
	
	public static List<FriendRequest> getFriendRequestList(String frienduser){
		String url=getFriendRequestListURL+frienduser;
		Log.d("requesturl", url);
		String jsonString="";
		try {
			// 发送请求
			jsonString = HttpUtil.getRequest(url);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail");
		}
		List<FriendRequest> friendrequest=getFriendRequest("friendrequest",jsonString);
		return friendrequest;
	}
	
	public static List<FriendRequest> getFriendRequest(String key, String jsonString){
		List<FriendRequest> friendrequestList=new ArrayList<FriendRequest>();
		List<Map<String, Object>> list = GetUser.listKeyMaps(key, jsonString);
		
		for (int i = 0; i < list.size(); i++) {
			FriendRequest request=new FriendRequest();
			User owneruser = new User();
			Map<String, Object> map = new HashMap<String, Object>();
			map = list.get(i);
			owneruser.setFlag((String) map.get("flag"));
			owneruser.setUser_id((Integer) map.get("user_id"));
			owneruser.setUser_name((String) map.get("user_name"));
			owneruser.setPassword((String) map.get("password"));
			owneruser.setPerson_signature((String) map.get("person_signature"));
			owneruser.setSex((Integer) map.get("sex"));
			owneruser.setSchool((String) map.get("school"));
			owneruser.setAcademy((String) map.get("academy"));
			owneruser.setState((Integer) map.get("state"));

			String bir = (String) map.get("birthday");
			java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			try {
				java.util.Date birthday = timeformat.parse(bir);
				owneruser.setBirthday(birthday);
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("String--->>Date", "失败");
			}

			owneruser.setTelephone((String) map.get("telephone"));
			owneruser.setPicture((String) map.get("picture"));
			owneruser.setUser_nickname((String) map.get("user_nickname"));
			owneruser.setEmail((String) map.get("email"));
			owneruser.setSecurityControl((Integer) map.get("SecurityControl"));
			
			request.setId((Integer)map.get("friendrequest_id"));
			request.setOwneruser(owneruser);
			request.setRequestgroup((String) map.get("requestgroup"));
			request.setValidationmessage((String) map.get("validationmessage"));
			
			Timestamp time=Timestamp.valueOf((String) map.get("requestime"));
			request.setRequestime(time);
			friendrequestList.add(request);
		}
		Log.d("解析friendrequestList为request", friendrequestList.toString());
		return friendrequestList;
	}
}
