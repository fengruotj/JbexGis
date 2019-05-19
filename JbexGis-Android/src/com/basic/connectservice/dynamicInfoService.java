package com.basic.connectservice;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.basic.service.model.DynamicInfo;
import com.basic.service.model.JbexInfo;
import com.basic.service.model.User;

public class dynamicInfoService {
	public static final String PATH_URL_AddynamicInfo = HttpUtil.BASE_URL
			+ "servlet/AddynamicinfoServlet";
	public static final String PATH_URL_GetFriendynamicInfo = HttpUtil.BASE_URL
			+ "servlet/GetFriendynamicinfoServlet?userid=";
	public static final String PATH_URL_GetAttentiondynamicInfo = HttpUtil.BASE_URL
			+ "servlet/GetAttentiondynamicinfoServlet?userid=";
	public static final String PATH_URL_GetdynamicInfo = HttpUtil.BASE_URL
			+ "servlet/GetdynamicinfoServlet";
	public static final String PATH_URL_GetdynamicInfoByUserid = HttpUtil.BASE_URL
			+ "servlet/GetdynamicinfoByUseridServlet?userid=";
    public static final String PATH_URL_SetdynamicInfo=
    		HttpUtil.BASE_URL+"servlet/SetdynamicinfoServlet";
    public static final String PATH_URL_DeletedynamicInfo=
    		HttpUtil.BASE_URL+"servlet/DeletedynamicinfoServlet";
    
    public static String addynamicinfo(User user, Double dotX, Double dotY,
			 String detail, Date time
			) {
		String flag = "";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", user.getUser_name());
		map.put("dotX", Double.toString(dotX));
		map.put("dotY", Double.toString(dotY));
		map.put("detail", detail);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		map.put("time", sdf.format(time));
		
		try {
			// 发送请求
			flag = HttpUtil.postRequest(PATH_URL_AddynamicInfo, map); // POST方式
			Log.d("AddynamicInfo---flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
    
    public static String deletedynamicinfo(String dynamicinfoid
			) {
		String flag = "";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dynamicinfoid", dynamicinfoid);
		try {
			// 发送请求
			flag = HttpUtil.postRequest(PATH_URL_DeletedynamicInfo, map); // POST方式
			Log.d("deletedynamicinfo---flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
    
    public static boolean setdynamicInfo(DynamicInfo dynamicinfo){
		String flag = "";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(dynamicinfo.getId()));
		map.put("dotX", String.valueOf(dynamicinfo.getDotX()));
		map.put("dotY", String.valueOf(dynamicinfo.getDotY()));
		map.put("detail", String.valueOf(dynamicinfo.getDetail()));
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		map.put("time", sdf.format(dynamicinfo.getTime()));
		map.put("picture1", String.valueOf(dynamicinfo.getPicture1()));
		map.put("picture2", String.valueOf(dynamicinfo.getPicture2()));
		
		try {
			// 发送请求
			flag = HttpUtil.postRequest(PATH_URL_SetdynamicInfo, map); // POST方式
			Log.d("setdynamicInfo---flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(flag.equals("true"))
		return true;
		else 
			return false;
	}
	
	public static List<DynamicInfo> getdynamicInfoList(){
		String jsonString="";
		try {
			// 发送请求
			jsonString = HttpUtil.getRequest(PATH_URL_GetdynamicInfo);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail");
		}
		List<DynamicInfo> dynamicinfoList=getdynamicInfo("dynamicinfo",jsonString);
		return dynamicinfoList;
	}
	
	public static List<DynamicInfo> getdynamicInfoListByUserid(String userid){
		String jsonString="";
		try {
			// 发送请求
			jsonString = HttpUtil.getRequest(PATH_URL_GetdynamicInfoByUserid+userid);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail");
		}
		List<DynamicInfo> dynamicinfoList=getdynamicInfo("dynamicinfo",jsonString);
		return dynamicinfoList;
	}
	
	public static List<DynamicInfo> getFriendynamicInfoList(String userid){
		String jsonString="";
		try {
			// 发送请求
			jsonString = HttpUtil.getRequest(PATH_URL_GetFriendynamicInfo+userid);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail");
		}
		List<DynamicInfo> dynamicFriendinfoList=getdynamicInfo("dynamicinfo",jsonString);
		return dynamicFriendinfoList;
	}
	
	public static List<DynamicInfo> getAttentionynamicInfoList(String userid){
		String jsonString="";
		try {
			// 发送请求
			jsonString = HttpUtil.getRequest(PATH_URL_GetAttentiondynamicInfo+userid);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail");
		}
		List<DynamicInfo> dynamicFriendinfoList=getdynamicInfo("dynamicinfo",jsonString);
		return dynamicFriendinfoList;
	}
	
	public static List<DynamicInfo> getdynamicInfo(String key, String jsonString){
		List<DynamicInfo> dynamicinfoList=new ArrayList<DynamicInfo>();
		List<Map<String, Object>> list = GetUser.listKeyMaps(key, jsonString);
		for (int i = 0; i < list.size(); i++) {
			DynamicInfo dynamicinfo=new DynamicInfo();
			User owneruser=new User();
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
			
			dynamicinfo.setId(Long.valueOf((String) map.get("id")));
			dynamicinfo.setDetail((String) map.get("detail"));
			dynamicinfo.setDotX((Double) map.get("DotX"));
			dynamicinfo.setDotY((Double) map.get("DotY"));
			dynamicinfo.setPicture1((String) map.get("picture1"));
			dynamicinfo.setPicture2((String) map.get("picture2"));
			dynamicinfo.setTime(Timestamp.valueOf((String) map.get("time")));
			dynamicinfo.setTUser(owneruser);
			dynamicinfoList.add(dynamicinfo);
		}
		Log.d("解析publicInfotList为publicinfo", dynamicinfoList.toString());
		return dynamicinfoList;
	}
}
