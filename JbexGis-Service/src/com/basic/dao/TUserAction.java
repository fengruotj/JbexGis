package com.basic.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.basic.bean.TGroupfamily;
import com.basic.bean.TGroupfamilyDAO;
import com.basic.bean.TGroupfamilyId;
import com.basic.bean.TGroupfriends;
import com.basic.bean.TGroupfriendsDAO;
import com.basic.bean.TGroupfriendsId;
import com.basic.bean.TGroupstudent;
import com.basic.bean.TGroupstudentDAO;
import com.basic.bean.TGroupstudentId;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;

public class TUserAction {

	public static boolean LoginUser(String email, String password) {
		TUserDAO userdao = new TUserDAO();
		org.hibernate.Transaction tran = userdao.getSession()
				.beginTransaction();
		userdao.getSession().flush();
		tran.commit();
		List<TUser> list = userdao.findByEmail(email);
		if (list.size() == 0) {
			return false;
		}
		TUser user = (TUser) list.get(0);
		userdao.getSession().close();
		if (user.getPassword().endsWith(password)) {
			return true;
		} else
			return false;
	}

	public static boolean RegisterUser(String email, String password, String user_nickname) {
		TUserDAO userdao = new TUserDAO();
		List list = userdao.findByEmail(email);
		List list2 = userdao.findByUserNickname(user_nickname);

		if (list.size() == 1 || list2.size() == 1) {
			userdao.getSession().close();
			return false; // 数据库中有相同数据
		}

		org.hibernate.Transaction tran = userdao.getSession()
				.beginTransaction();

		TUser user = new TUser();
		user.setEmail(email);
		user.setUserName(email);
		user.setUserNickname(user_nickname);
		user.setPassword(password);
		user.setBirthday(new Date());
		user.setAcademy("未绑定");
		user.setSchool("中国地质大学");
		user.setSecurityControl(0);
		user.setSex(1);
		user.setPicture("default.png");
		user.setPersonSignature("未绑定");
		user.setState(1);
		user.setTelephone("未绑定");
		userdao.save(user);

		tran.commit();
		userdao.getSession().close();
		return true;
	}

	public static void setUser(TUser setuser) {
		TUserDAO userdao = new TUserDAO();
		org.hibernate.Transaction tran = userdao.getSession()
				.beginTransaction();
		List<TUser> list = userdao.findByEmail(setuser.getEmail());
		list.get(0).setAcademy(setuser.getAcademy());
		list.get(0).setBirthday(setuser.getBirthday());
		list.get(0).setPersonSignature(setuser.getPersonSignature());
		list.get(0).setPicture(setuser.getPicture());
		list.get(0).setSchool(setuser.getSchool());
		//list.get(0).setSecurityControl(setuser.getSecurityControl());
		list.get(0).setSex(setuser.getSex());
		list.get(0).setTelephone(setuser.getTelephone());
		userdao.getSession().update(list.get(0));
		tran.commit();
		userdao.getSession().close();
	}

	public static boolean addFriends(String owneruser, String friends, String GroupName) {
		TGroupfriendsDAO friendsdao = new TGroupfriendsDAO();
		TGroupstudentDAO studentdao = new TGroupstudentDAO();
		TGroupfamilyDAO familydao = new TGroupfamilyDAO();
        TUserDAO userdao=new TUserDAO();
		if (userdao.findByEmail(friends).size() != 1) {
			userdao.getSession().close();
			friendsdao.getSession().close();
			familydao.getSession().close();
			studentdao.getSession().close();
			return false;
		}
		TUser friendsUser = (TUser) new TUserDAO().findByEmail(friends).get(0);
		TUser ownerUser = (TUser) userdao.findByEmail(owneruser).get(0);
		if (GroupName.endsWith("好友")) {
			org.hibernate.Transaction tran = friendsdao.getSession()
					.beginTransaction();

			TGroupfriends groupfriends = new TGroupfriends();
			groupfriends.setTUserByUserId(ownerUser);
			groupfriends.setTUserByFriendsId(friendsUser);
			groupfriends.setId(new TGroupfriendsId(ownerUser.getUserId(),
					friendsUser.getUserId()));
			friendsdao.save(groupfriends);
			tran.commit();
		}

		else if (GroupName.endsWith("同学")) {
			org.hibernate.Transaction tran2 = studentdao.getSession()
					.beginTransaction();
			if (userdao.findByEmail(friends).size() != 1) {
				userdao.getSession().close();
				friendsdao.getSession().close();
				familydao.getSession().close();
				studentdao.getSession().close();
				return false;
			}
			TGroupstudent groupstudnents = new TGroupstudent();

			groupstudnents.setTUserByUserId(ownerUser);
			groupstudnents.setTUserByFriendsId(friendsUser);
			groupstudnents.setId(new TGroupstudentId(ownerUser.getUserId(),
					friendsUser.getUserId()));

			studentdao.save(groupstudnents);
			tran2.commit();
		}

		else if (GroupName.endsWith("家人")) {
			org.hibernate.Transaction tran3 = familydao.getSession()
					.beginTransaction();
			if (userdao.findByEmail(friends).size() != 1) {
				userdao.getSession().close();
				friendsdao.getSession().close();
				familydao.getSession().close();
				studentdao.getSession().close();
				return false;
			}
			TGroupfamily groupfamilys = new TGroupfamily();

			groupfamilys.setTUserByUserId(ownerUser);
			groupfamilys.setTUserByFriendsId(friendsUser);
			groupfamilys.setId(new TGroupfamilyId(ownerUser.getUserId(),
					friendsUser.getUserId()));

			familydao.save(groupfamilys);
			tran3.commit();
		}
		userdao.getSession().close();
		friendsdao.getSession().close();
		familydao.getSession().close();
		studentdao.getSession().close();
		return true;
	}
      public static  List<TUser> findUser(String owneruser,String type){
    	  TUserDAO userdao = new TUserDAO();
    	  org.hibernate.Transaction tran = userdao.getSession()
    			  .beginTransaction();
    	  userdao.getSession().flush();
    	  tran.commit();
    	  List<TUser> list=new ArrayList<TUser>();
    	  if(type.equals("email")){
    			String email=owneruser;
    			list= userdao.findByEmail(email);
    		 	}else if(type.equals("id")){
    		 	String id=owneruser;
    		 	list.add(userdao.findById(Long.valueOf(id)));
    		 	}
    	  userdao.getSession().close();
    	  return list;
      }
      
      @SuppressWarnings("unchecked")
	public static  List<TUser> GetUserList(){
    	  TUserDAO userdao = new TUserDAO();
    	  org.hibernate.Transaction tran = userdao.getSession()
    			  .beginTransaction();
    	  userdao.getSession().flush();
    	  tran.commit();
    	  List<TUser> list=new ArrayList<TUser>();
    	  list=userdao.getSession()
  	    		.createQuery("from TUser")
  	    		.list();
    	  userdao.getSession().close();
    	  return list;
      }
      public static void setUserSecurityControl(String userid,String SecurityControl){
   	   TUserDAO userdao = new TUserDAO();
   	   TUser owneruser=userdao.findById(Long.valueOf(userid));
   	   
   	   org.hibernate.Transaction tran = userdao.getSession()
    			  .beginTransaction();
   	   owneruser.setSecurityControl(Integer.valueOf(SecurityControl));
   	   
   	   userdao.getSession().update(owneruser);
   	   tran.commit();
   	   userdao.getSession().close();
      }
}
