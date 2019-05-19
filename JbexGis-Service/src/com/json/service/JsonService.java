package com.json.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basic.bean.TFriendrequest;
import com.basic.bean.TJbrxinfo;
import com.basic.bean.TPersonaldynamic;
import com.basic.bean.TPublicinfo;
import com.basic.bean.TUser;
import com.basic.dao.TJbexfriendAction;

public class JsonService {

	public static List<Map<String, Object>> getLoginUser(TUser user, String flag) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("user_id", user.getUserId());
		map.put("user_name", user.getUserName());
		map.put("password", user.getPassword());
		map.put("person_signature", user.getPersonSignature());
		map.put("sex", user.getSex());
		map.put("school", user.getSchool());
		map.put("academy", user.getAcademy());
		map.put("state", user.getState());
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		map.put("birthday", sdf.format(user.getBirthday()));
		map.put("telephone", user.getTelephone());
		map.put("picture", user.getPicture());
		map.put("user_nickname", user.getUserNickname());
		map.put("email", user.getEmail());
		map.put("SecurityControl", user.getSecurityControl());
		list.add(map);
		return list;
	}

	public static List<Map<String, Object>> getGroupUser(List<TUser> Group_List) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		TUser user = null;
		for (int i = 0; i < Group_List.size(); i++) {
			user = (TUser) Group_List.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flag", "");
			map.put("user_id", user.getUserId());
			map.put("user_name", user.getUserName());
			map.put("password", user.getPassword());
			map.put("person_signature", user.getPersonSignature());
			map.put("sex", user.getSex());
			map.put("school", user.getSchool());
			map.put("academy", user.getAcademy());
			map.put("state", user.getState());
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			map.put("birthday", sdf.format(user.getBirthday()));
			map.put("telephone", user.getTelephone());
			map.put("picture", user.getPicture());
			map.put("user_nickname", user.getUserNickname());
			map.put("email", user.getEmail());
			map.put("SecurityControl", user.getSecurityControl());
			list.add(map);
		}
		return list;
	}

	public static List<Map<String, Object>> getFriendRequest(
			List<TFriendrequest> FriendRequest_List) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		TFriendrequest friendrequest = null;
		TUser ownerUser = null;
		for (int i = 0; i < FriendRequest_List.size(); i++) {
			friendrequest = (TFriendrequest) FriendRequest_List.get(i);
			ownerUser = friendrequest.getTUserByUserId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("friendrequest_id", friendrequest.getId());
			map.put("flag", "");
			map.put("user_id", ownerUser.getUserId());
			map.put("user_name", ownerUser.getUserName());
			map.put("password", ownerUser.getPassword());
			map.put("person_signature", ownerUser.getPersonSignature());
			map.put("sex", ownerUser.getSex());
			map.put("school", ownerUser.getSchool());
			map.put("academy", ownerUser.getAcademy());
			map.put("state", ownerUser.getState());
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			map.put("birthday", sdf.format(ownerUser.getBirthday()));
			map.put("telephone", ownerUser.getTelephone());
			map.put("picture", ownerUser.getPicture());
			map.put("user_nickname", ownerUser.getUserNickname());
			map.put("email", ownerUser.getEmail());
			map.put("SecurityControl", ownerUser.getSecurityControl());
			map.put("requestgroup", friendrequest.getRequestgroup());
			map.put("validationmessage", friendrequest.getValidationmessage());
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			map.put("requestime",
					formatter.format(friendrequest.getRequestime()));
			list.add(map);
		}
		return list;
	}

	public static List<Map<String, Object>> getPublicInfo(
			List<TPublicinfo> publicinfo_list) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		TPublicinfo publicinfo = null;
		TUser ownerUser = null;
		for (int i = 0; i < publicinfo_list.size(); i++) {
			publicinfo = (TPublicinfo) publicinfo_list.get(i);
			ownerUser=publicinfo.getTUser();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flag", "");
			map.put("user_id", ownerUser.getUserId());
			map.put("user_name", ownerUser.getUserName());
			map.put("password", ownerUser.getPassword());
			map.put("person_signature", ownerUser.getPersonSignature());
			map.put("sex", ownerUser.getSex());
			map.put("school", ownerUser.getSchool());
			map.put("academy", ownerUser.getAcademy());
			map.put("state", ownerUser.getState());
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			map.put("birthday", sdf.format(ownerUser.getBirthday()));
			map.put("telephone", ownerUser.getTelephone());
			map.put("picture", ownerUser.getPicture());
			map.put("user_nickname", ownerUser.getUserNickname());
			map.put("email", ownerUser.getEmail());
			map.put("SecurityControl", ownerUser.getSecurityControl());
			map.put("id", String.valueOf(publicinfo.getId()));
			map.put("DotX", publicinfo.getDotX());
			map.put("DotY", publicinfo.getDotY());
			map.put("title", publicinfo.getTitle());
			map.put("detail", publicinfo.getDetail());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 定义格式，不显示毫秒
			String time = df.format(publicinfo.getTime());
			map.put("time", time);
			map.put("label", publicinfo.getLabel());
			map.put("picture1", publicinfo.getPicture1());
			map.put("picture2", publicinfo.getPicture2());
			list.add(map);
		}
		return list;
	}

	public static Object getJbexInfo(List<TJbrxinfo> list2) {
		// TODO 自动生成的方法存根
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		TJbrxinfo jbexinfo = null;
		TUser ownerUser = null;
		for (int i = 0; i < list2.size(); i++) {
			jbexinfo = (TJbrxinfo) list2.get(i);
			ownerUser=jbexinfo.getTUser();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flag", "");
			map.put("user_id", ownerUser.getUserId());
			map.put("user_name", ownerUser.getUserName());
			map.put("password", ownerUser.getPassword());
			map.put("person_signature", ownerUser.getPersonSignature());
			map.put("sex", ownerUser.getSex());
			map.put("school", ownerUser.getSchool());
			map.put("academy", ownerUser.getAcademy());
			map.put("state", ownerUser.getState());
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			map.put("birthday", sdf.format(ownerUser.getBirthday()));
			map.put("telephone", ownerUser.getTelephone());
			map.put("picture", ownerUser.getPicture());
			map.put("user_nickname", ownerUser.getUserNickname());
			map.put("email", ownerUser.getEmail());
			map.put("SecurityControl", ownerUser.getSecurityControl());
			map.put("id", String.valueOf(jbexinfo.getId()));
			map.put("DotX", jbexinfo.getDotX());
			map.put("DotY", jbexinfo.getDotY());
			map.put("title", jbexinfo.getTitle());
			map.put("detail", jbexinfo.getDetail());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 定义格式，不显示毫秒
			String time = df.format(jbexinfo.getTime());
			map.put("time", time);
			map.put("label", jbexinfo.getLabel());
			map.put("picture1", jbexinfo.getPicture1());
			map.put("picture2", jbexinfo.getPicture2());
			map.put("size", jbexinfo.getSize());
			list.add(map);
		}
		return list;
	}

	public static Object getJbexRequest(List<TJbrxinfo> list2) {
		// TODO 自动生成的方法存根
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		TJbrxinfo jbexinfo = null;
		TUser ownerUser = null;
		TUser User = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		
		for (int i = 0; i < list2.size(); i++) {
			jbexinfo = (TJbrxinfo) list2.get(i);
			ownerUser=jbexinfo.getTUser();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flag", "");
			map.put("user_id", ownerUser.getUserId());
			map.put("user_name", ownerUser.getUserName());
			map.put("password", ownerUser.getPassword());
			map.put("person_signature", ownerUser.getPersonSignature());
			map.put("sex", ownerUser.getSex());
			map.put("school", ownerUser.getSchool());
			map.put("academy", ownerUser.getAcademy());
			map.put("state", ownerUser.getState());
			
			map.put("birthday", sdf.format(ownerUser.getBirthday()));
			map.put("telephone", ownerUser.getTelephone());
			map.put("picture", ownerUser.getPicture());
			map.put("user_nickname", ownerUser.getUserNickname());
			map.put("email", ownerUser.getEmail());
			map.put("SecurityControl", ownerUser.getSecurityControl());
			map.put("id", String.valueOf(jbexinfo.getId()));
			map.put("DotX", jbexinfo.getDotX());
			map.put("DotY", jbexinfo.getDotY());
			map.put("title", jbexinfo.getTitle());
			map.put("detail", jbexinfo.getDetail());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 定义格式，不显示毫秒
			String time = df.format(jbexinfo.getTime());
			map.put("time", time);
			map.put("label", jbexinfo.getLabel());
			map.put("picture1", jbexinfo.getPicture1());
			map.put("picture2", jbexinfo.getPicture2());
			map.put("size", jbexinfo.getSize());
			List<TUser> userList=TJbexfriendAction.getJbexRequestFriend(ownerUser.getEmail(), jbexinfo.getId());
			
			map.put("userListSize", userList.size());
			for(int j=0;j<userList.size();j++){
				User=userList.get(j);
				map.put(j+"flag", "");
				map.put(j+"user_id", User.getUserId());
				map.put(j+"user_name", User.getUserName());
				map.put(j+"password", User.getPassword());
				map.put(j+"person_signature", User.getPersonSignature());
				map.put(j+"sex", User.getSex());
				map.put(j+"school", User.getSchool());
				map.put(j+"academy", User.getAcademy());
				map.put(j+"state", User.getState());
				map.put(j+"birthday", sdf.format(User.getBirthday()));
				map.put(j+"telephone", User.getTelephone());
				map.put(j+"picture", User.getPicture());
				map.put(j+"user_nickname", User.getUserNickname());
				map.put(j+"email", User.getEmail());
				map.put(j+"SecurityControl", User.getSecurityControl());
			}
					
			list.add(map);
		}
		return list;
	}

	public static List<Map<String, Object>> getdynamicInfo(List<TPersonaldynamic> list2) {
		// TODO 自动生成的方法存根
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		TPersonaldynamic dynamicinfo = null;
		TUser ownerUser = null;
		for (int i = 0; i < list2.size(); i++) {
			dynamicinfo = (TPersonaldynamic) list2.get(i);
			ownerUser=dynamicinfo.getTUser();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flag", "");
			map.put("user_id", ownerUser.getUserId());
			map.put("user_name", ownerUser.getUserName());
			map.put("password", ownerUser.getPassword());
			map.put("person_signature", ownerUser.getPersonSignature());
			map.put("sex", ownerUser.getSex());
			map.put("school", ownerUser.getSchool());
			map.put("academy", ownerUser.getAcademy());
			map.put("state", ownerUser.getState());
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			map.put("birthday", sdf.format(ownerUser.getBirthday()));
			map.put("telephone", ownerUser.getTelephone());
			map.put("picture", ownerUser.getPicture());
			map.put("user_nickname", ownerUser.getUserNickname());
			map.put("email", ownerUser.getEmail());
			map.put("SecurityControl", ownerUser.getSecurityControl());
			map.put("id", String.valueOf(dynamicinfo.getId()));
			map.put("DotX", dynamicinfo.getDotX());
			map.put("DotY", dynamicinfo.getDotY());
			map.put("detail", dynamicinfo.getDetail());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 定义格式，不显示毫秒
			String time = df.format(dynamicinfo.getTime());
			map.put("time", time);
			map.put("picture1", dynamicinfo.getPicture1());
			map.put("picture2", dynamicinfo.getPicture2());
			list.add(map);
		}
		return list;
	}
	
}
