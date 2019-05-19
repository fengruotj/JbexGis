package com.basic.connectservice;

import java.util.HashMap;

import android.util.Log;

import com.basic.service.model.User;

public class SettingUserInfo {

	public static boolean settingUserInfo(User user) {
		String flag = "false";
		flag = getServiceReturn(user);
		if(flag.equals("true")){
			return true;
		}else{
			return false;
		}
	}

	public static String getServiceReturn(User user) {
		String flag = "false";
		// 使用Map封装请求参数
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", user.getEmail());
		map.put("sex", String.valueOf(user.getSex()));
		map.put("person_signature", user.getPerson_signature());
		map.put("telephone", user.getTelephone());
		map.put("school", user.getSchool());
		map.put("academy", user.getAcademy());
		map.put("picture", user.getPicture());
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		map.put("birthday", sdf.format(user.getBirthday()));

		String url = HttpUtil.BASE_URL + "servlet/UserInfoServlet"; // POST方式
		Log.d("保存设置的user", user.toString());
		try {
			// 发送请求
			flag = HttpUtil.postRequest(url, map); // POST方式
			Log.d("保存返回的flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("保存设置请求服务器失败", "false");
		}

		return flag;
	}

}
