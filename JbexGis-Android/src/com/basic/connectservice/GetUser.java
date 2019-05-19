package com.basic.connectservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.basic.service.model.User;

import android.util.Log;

public class GetUser {

	public static User getSimpleUser(String key, String jsonString) {
		List<Map<String, Object>> list = listKeyMaps(key, jsonString);
		User user = new User();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map = list.get(i);
			user.setFlag((String) map.get("flag"));
			user.setUser_id((Integer) map.get("user_id"));
			user.setUser_name((String) map.get("user_name"));
			user.setPassword((String) map.get("password"));
			user.setPerson_signature((String) map.get("person_signature"));
			user.setSex((Integer) map.get("sex"));
			user.setSchool((String) map.get("school"));
			user.setAcademy((String) map.get("academy"));
			user.setState((Integer) map.get("state"));

			String bir = (String) map.get("birthday");
			java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			try {
				java.util.Date birthday = timeformat.parse(bir);
				user.setBirthday(birthday);
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("String--->>Date", "失败");
			}

			user.setTelephone((String) map.get("telephone"));
			user.setPicture((String) map.get("picture"));
			user.setUser_nickname((String) map.get("user_nickname"));
			user.setEmail((String) map.get("email"));
			user.setSecurityControl((Integer) map.get("SecurityControl"));
		}
		Log.d("解析list成的user", user.toString());
		return user;
	}
	
	public static List<User> getMultiUser(String key, String jsonString) {
		List<User> listUser = new ArrayList<User>();
		List<Map<String, Object>> list = listKeyMaps(key, jsonString);
		for (int i = 0; i < list.size(); i++) {
			User user = new User();
			Map<String, Object> map = new HashMap<String, Object>();
			map = list.get(i);
			user.setFlag((String) map.get("flag"));
			user.setUser_id((Integer) map.get("user_id"));
			user.setUser_name((String) map.get("user_name"));
			user.setPassword((String) map.get("password"));
			user.setPerson_signature((String) map.get("person_signature"));
			user.setSex((Integer) map.get("sex"));
			user.setSchool((String) map.get("school"));
			user.setAcademy((String) map.get("academy"));
			user.setState((Integer) map.get("state"));

			String bir = (String) map.get("birthday");
			java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			try {
				java.util.Date birthday = timeformat.parse(bir);
				user.setBirthday(birthday);
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("String--->>Date", "失败");
			}

			user.setTelephone((String) map.get("telephone"));
			user.setPicture((String) map.get("picture"));
			user.setUser_nickname((String) map.get("user_nickname"));
			user.setEmail((String) map.get("email"));
			user.setSecurityControl((Integer) map.get("SecurityControl"));
			
			listUser.add(user);
		}
		Log.d("解析listMap为listUser", listUser.toString());
		return listUser;
	}

	public static List<Map<String, Object>> listKeyMaps(String key,
			String jsonString) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				Iterator<String> iterator = jsonObject2.keys();
				while (iterator.hasNext()) {
					String json_key = iterator.next();
					Object json_value = jsonObject2.get(json_key);
					if (json_value == null) {
						json_value = "";
					}
					map.put(json_key, json_value);
				}
				list.add(map);
			}
		} catch (Exception e) {
			Log.d("JSON", "解析json数据出现问题！！");
		}
		Log.d("解析json成的list", list.toString());
		return list;
	}

}
