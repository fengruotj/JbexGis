package com.basic.connectservice;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.basic.service.model.JbexInfo;
import com.basic.service.model.User;

public class JbexInfoService {
	public static final String PATH_URL_AddJbexInfo = HttpUtil.BASE_URL
			+ "servlet/AddJbexinfoServlet";
	public static final String PATH_URL_GetJbexInfo = HttpUtil.BASE_URL
			+ "servlet/GetJbexinfoServlet";
	public static final String PATH_URL_GetFriendJbexInfo = HttpUtil.BASE_URL
			+ "servlet/GetFriendjbexinfoServlet";
	public static final String PATH_URL_GetAttentionJbexInfo = HttpUtil.BASE_URL
			+ "servlet/GetAttentionjbexinfoServlet";
    public static final String PATH_URL_SetJbexInfo=
    		HttpUtil.BASE_URL+"servlet/SetJbexinfoServlet";
    public static final String PATH_URL_DeleteJbexInfo=
    		HttpUtil.BASE_URL+"servlet/DeleteJbexinfoServlet";
    
    public static String addJbexinfo(User user, Double dotX, Double dotY,
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
			flag = HttpUtil.postRequest(PATH_URL_AddJbexInfo, map); // POST方式
			Log.d("addPublicInfo---flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
    
    public static String deleteJbexinfo(String jbexinfoid
			) {
		String flag = "";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("jbexinfoid", jbexinfoid);
		try {
			// 发送请求
			flag = HttpUtil.postRequest(PATH_URL_DeleteJbexInfo, map); // POST方式
			Log.d("deltePublicInfo---flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
    
    public static boolean setJbexInfo(JbexInfo jbexinfo){
		String flag = "";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_id", String.valueOf(jbexinfo.getId()));
		map.put("dotX", String.valueOf(jbexinfo.getDotX()));
		map.put("dotY", String.valueOf(jbexinfo.getDotY()));
		map.put("title", String.valueOf(jbexinfo.getTitle()));
		map.put("detail", String.valueOf(jbexinfo.getDetail()));
		map.put("label",String.valueOf(jbexinfo.getLabel()));
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		map.put("time", sdf.format(jbexinfo.getTime()));
		map.put("picture1", String.valueOf(jbexinfo.getPicture1()));
		map.put("picture2", String.valueOf(jbexinfo.getPicture2()));
		
		try {
			// 发送请求
			flag = HttpUtil.postRequest(PATH_URL_SetJbexInfo, map); // POST方式
			Log.d("addPublicInfo---flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(flag.equals("true"))
		return true;
		else 
			return false;
	}
	
	public static List<JbexInfo> getJbexInfoList(String NowTime){
		String url = PATH_URL_GetJbexInfo ;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("NowTime", NowTime);
		String jsonString="";
		try {
			// 发送请求
			jsonString = HttpUtil.postRequest(url, map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail");
		}
		List<JbexInfo> jbexinfoList=getJbexInfo("jbexinfo",jsonString);
		return jbexinfoList;
	}
	
	public static List<JbexInfo> getAttentionjbexInfoListByUserid(String userid,String NowTime){
		String url = PATH_URL_GetAttentionJbexInfo ;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("NowTime", NowTime);
		map.put("userid", userid);
		String jsonString="";
		try {
			// 发送请求
			jsonString = HttpUtil.postRequest(url, map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail"); 
		}
		List<JbexInfo> jbexinfoList=getJbexInfo("jbexinfo",jsonString);
		return jbexinfoList;
	}
	
	public static List<JbexInfo> getFrienJbexInfoListByUserid(String userid,String NowTime){
		String url = PATH_URL_GetFriendJbexInfo ;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("NowTime", NowTime);
		map.put("userid", userid);
		String jsonString="";
		try {
			// 发送请求
			jsonString = HttpUtil.postRequest(url, map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail");
		}
		List<JbexInfo> jbexinfoList=getJbexInfo("jbexinfo",jsonString);
		return jbexinfoList;
	}
	
	public static List<JbexInfo> getJbexInfo(String key, String jsonString){
		List<JbexInfo> jbexinfoList=new ArrayList<JbexInfo>();
		List<Map<String, Object>> list = GetUser.listKeyMaps(key, jsonString);
		for (int i = 0; i < list.size(); i++) {
			JbexInfo jbexinfo=new JbexInfo();
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
			
			jbexinfo.setId(Long.valueOf((String) map.get("id")));
			jbexinfo.setDetail((String) map.get("detail"));
			jbexinfo.setDotX((Double) map.get("DotX"));
			jbexinfo.setDotY((Double) map.get("DotY"));
			jbexinfo.setLabel((String) map.get("label"));
			jbexinfo.setPicture1((String) map.get("picture1"));
			jbexinfo.setPicture2((String) map.get("picture2"));
			jbexinfo.setTitle((String) map.get("title"));
			jbexinfo.setTime(Timestamp.valueOf((String) map.get("time")));
			jbexinfo.setSize( (Integer) map.get("size"));
			jbexinfo.setTUser(owneruser);
			jbexinfoList.add(jbexinfo);
		}
		Log.d("解析publicInfotList为publicinfo", jbexinfoList.toString());
		return jbexinfoList;
	}
}
