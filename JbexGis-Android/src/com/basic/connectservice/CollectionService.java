package com.basic.connectservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.basic.service.model.JbexInfo;

import android.util.Log;

public class CollectionService {
	public static final String PATH_URL_GetCollections = HttpUtil.BASE_URL+"servlet/JsonCollectionsServlet";
	public static final String PATH_URL_AddCollections = HttpUtil.BASE_URL+"servlet/AddCollections";
	
	public static boolean addCollections(String userid,String jbexinfoid){
		String url=PATH_URL_AddCollections;
		
		HashMap<String, String> map = new HashMap<String, String>();
		String strFlag="";
		map.put("userid", userid);
		map.put("jbexinfoid", jbexinfoid);
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
	
	public static boolean deleteCollections(String userid,String jbexinfoid){
		String url=PATH_URL_AddCollections;
		
		HashMap<String, String> map = new HashMap<String, String>();
		String strFlag="";
		map.put("userid", userid);
		map.put("jbexinfoid", jbexinfoid);
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
	
	public static List<JbexInfo> GetCollectionsList(String userid) {
		String url=PATH_URL_GetCollections;
		Log.d("PATH_URL_GetCollections", url);
		String jsonString = "";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		
		try {
			jsonString =HttpUtil.postRequest(url, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail");
		}
		
		List<JbexInfo> jbexinfoList = new ArrayList<JbexInfo>();
		if (jsonString != null) {
			jbexinfoList= JbexInfoService.getJbexInfo("collectionjbexinfo",jsonString);
			return jbexinfoList;
		}
		else return jbexinfoList;
	}
}
