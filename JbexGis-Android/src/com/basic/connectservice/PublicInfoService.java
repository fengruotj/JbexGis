package com.basic.connectservice;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.basic.service.model.DynamicInfo;
import com.basic.service.model.FriendRequest;
import com.basic.service.model.PublicInfo;
import com.basic.service.model.User;

public class PublicInfoService {

	public static final String PATH_URL_AddPublicInfo = HttpUtil.BASE_URL
			+ "servlet/AddPublicinfoServlet";
	public static final String PATH_URL_GetPublicInfo = HttpUtil.BASE_URL
			+ "servlet/GetPublicinfoServlet";
    public static final String PATH_URL_SetPublicInfo=
    		HttpUtil.BASE_URL+"servlet/SetPublicinfoServlet";
    public static final String PATH_URL_DeletePublicInfo=
    		HttpUtil.BASE_URL+"servlet/DeletepublicinfoServlet";
    public static final String PATH_URL_GetPublicInfoByUserid = HttpUtil.BASE_URL
			+ "servlet/GetpublicinfoByUseridServlet?userid=";
    
	public static String addPublicinfo(User user, Double dotX, Double dotY,
			String title, String detail, Date time, String label
			) {
		String flag = "";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", user.getUser_name());
		map.put("dotX", Double.toString(dotX));
		map.put("dotY", Double.toString(dotY));
		map.put("title", title);
		map.put("detail", detail);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		map.put("time", sdf.format(time));
		map.put("label", label);
		
		try {
			// 发送请求
			flag = HttpUtil.postRequest(PATH_URL_AddPublicInfo, map); // POST方式
			Log.d("addPublicInfo---flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public static boolean setPublicInfo(PublicInfo publicinfo){
		String flag = "";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_id", String.valueOf(publicinfo.getId()));
		map.put("dotX", String.valueOf(publicinfo.getDotX()));
		map.put("dotY", String.valueOf(publicinfo.getDotY()));
		map.put("title", String.valueOf(publicinfo.getTitle()));
		map.put("detail", String.valueOf(publicinfo.getDetail()));
		map.put("label",String.valueOf(publicinfo.getLabel()));
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		map.put("time", sdf.format(publicinfo.getTime()));
		map.put("picture1", String.valueOf(publicinfo.getPicture1()));
		map.put("picture2", String.valueOf(publicinfo.getPicture2()));
		
		try {
			// 发送请求
			flag = HttpUtil.postRequest(PATH_URL_SetPublicInfo, map); // POST方式
			Log.d("addPublicInfo---flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(flag.equals("true"))
		return true;
		else 
			return false;
	}
	
	public static List<PublicInfo> getPublicInfoList(){
		String jsonString="";
		try {
			// 发送请求
			jsonString = HttpUtil.getRequest(PATH_URL_GetPublicInfo);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail");
		}
		List<PublicInfo> publicinfoList=getPublicInfo("publicinfo",jsonString);
		return publicinfoList;
	}
	
	public static String deletePublicinfo(String publicinfoid
			) {
		String flag = "";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("publicinfoid", publicinfoid);
		try {
			// 发送请求
			flag = HttpUtil.postRequest(PATH_URL_DeletePublicInfo, map); // POST方式
			Log.d("deletedynamicinfo---flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public static List<PublicInfo> getPublicInfoListByUserid(String userid){
		String jsonString="";
		try {
			// 发送请求
			jsonString = HttpUtil.getRequest(PATH_URL_GetPublicInfoByUserid+userid);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail");
		}
		List<PublicInfo> publicinfoList=getPublicInfo("publicinfo",jsonString);
		return publicinfoList;
	}
	
	public static List<PublicInfo> getPublicInfo(String key, String jsonString){
		List<PublicInfo> publicinfoList=new ArrayList<PublicInfo>();
		List<Map<String, Object>> list = GetUser.listKeyMaps(key, jsonString);
		for (int i = 0; i < list.size(); i++) {
			PublicInfo publicinfo=new PublicInfo();
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
			
			publicinfo.setId(Long.valueOf((String) map.get("id")));
			publicinfo.setDetail((String) map.get("detail"));
			publicinfo.setDotX((Double) map.get("DotX"));
			publicinfo.setDotY((Double) map.get("DotY"));
			publicinfo.setLabel((String) map.get("label"));
			publicinfo.setPicture1((String) map.get("picture1"));
			publicinfo.setPicture2((String) map.get("picture2"));
			publicinfo.setTitle((String) map.get("title"));
			publicinfo.setTime(Timestamp.valueOf((String) map.get("time")));
			publicinfo.setTUser(owneruser);
			publicinfoList.add(publicinfo);
		}
		Log.d("解析publicInfotList为publicinfo", publicinfoList.toString());
		return publicinfoList;
	}
}
