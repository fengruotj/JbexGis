package com.basic.connectservice;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.basic.service.model.JbexInfo;
import com.basic.service.model.MyJbexRequest;
import com.basic.service.model.User;

public class MyJbexRequestService {
	public static final String PATH_URL_GET_JBEX_Requst = HttpUtil.BASE_URL+"servlet/GetJbexRequestServlet?owneruser=";
	
	public static List<MyJbexRequest> getMyJbexRequestList(String owneruser){
		String url = PATH_URL_GET_JBEX_Requst + owneruser ;
		String jsonString="";
		try {
			// 发送请求
			jsonString = HttpUtil.getRequest(url);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("获取jsonString失败", "fail");
		}
		
		List <MyJbexRequest> MyJbexRequestList=getJbexReqeustList("Jbexrequest",jsonString);
		
		return MyJbexRequestList;
	}

	private static List<MyJbexRequest> getJbexReqeustList(String key, String jsonString) {
		// TODO 自动生成的方法存根
		List <MyJbexRequest> MyJbexRequestList=new ArrayList<MyJbexRequest>();
	
		List<Map<String, Object>> list = GetUser.listKeyMaps(key, jsonString);
		for (int i = 0; i < list.size(); i++) {
			JbexInfo jbexinfo=new JbexInfo();
			User owneruser=new User();
			User user=new User();
			List <User> userList=new ArrayList<User>();
			MyJbexRequest myJbexRequest=new MyJbexRequest();
			
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
			jbexinfo.setSize((Integer)map.get("size"));
			jbexinfo.setTime(Timestamp.valueOf((String) map.get("time")));
			jbexinfo.setTUser(owneruser);
			
			int userListSize=(Integer) map.get("userListSize");
			
			for(int j=0;j<userListSize;j++){
				user=new User();
				user.setFlag((String) map.get(j+"flag"));
				user.setUser_id((Integer) map.get(j+"user_id"));
				user.setUser_name((String) map.get(j+"user_name"));
				user.setPassword((String) map.get(j+"password"));
				user.setPerson_signature((String) map.get(j+"person_signature"));
				user.setSex((Integer) map.get(j+"sex"));
				user.setSchool((String) map.get(j+"school"));
				user.setAcademy((String) map.get(j+"academy"));
				user.setState((Integer) map.get(j+"state"));

				String bir_user = (String) map.get(j+"birthday");
				try {
					java.util.Date birthday = timeformat.parse(bir_user);
					user.setBirthday(birthday);
				} catch (Exception e) {
					e.printStackTrace();
					Log.d("String--->>Date", "失败");
				}

				user.setTelephone((String) map.get(j+"telephone"));
				user.setPicture((String) map.get(j+"picture"));
				user.setUser_nickname((String) map.get(j+"user_nickname"));
				user.setEmail((String) map.get(j+"email"));
				user.setSecurityControl((Integer) map.get(j+"SecurityControl"));
				userList.add(user);
				
			}
			myJbexRequest.setJbexInfo(jbexinfo);
			myJbexRequest.setJbexuserList(userList);
			MyJbexRequestList.add(myJbexRequest);
		}
		
		return MyJbexRequestList;
	}
	}

