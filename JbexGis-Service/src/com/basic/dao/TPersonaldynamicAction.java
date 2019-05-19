package com.basic.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.basic.bean.TPersonaldynamic;
import com.basic.bean.TPersonaldynamicDAO;
import com.basic.bean.TPublicinfo;
import com.basic.bean.TPublicinfoDAO;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;

public class TPersonaldynamicAction {
	
     public static long addPersonaldynamic(String username, Double dotX, Double dotY,
			String detail, Timestamp time){
    	TUserDAO userdao=new TUserDAO();
 		TPersonaldynamicDAO personaldao = new TPersonaldynamicDAO();
 		org.hibernate.Transaction tran = personaldao.getSession()
 				.beginTransaction();
 		TPersonaldynamic personaldynamic=new TPersonaldynamic();
 		TUser user = (TUser) userdao.findByEmail(username).get(0);
 		personaldynamic.setTUser(user);
 		personaldynamic.setDotX(dotX);
 		personaldynamic.setDotY(dotY);
 		personaldynamic.setDetail(detail);
 		personaldynamic.setTime(time);
 		personaldao.save(personaldynamic);
 		tran.commit();
 		
 		personaldao.getSession().close();
 		userdao.getSession().close();
 		
 		return personaldynamic.getId();
     }
     
     @SuppressWarnings("unchecked")
	public static List<TPersonaldynamic> getFriendPersonaldynamic(String userid){
    	 
    	 TPersonaldynamicDAO personaldao = new TPersonaldynamicDAO();
    	 org.hibernate.Transaction tran = personaldao.getSession()
  				.beginTransaction();
    	 personaldao.getSession().flush();
    	 tran.commit();
    	 
    	 TUserDAO userdao=new TUserDAO();
    	 TUser owneruser=userdao.findById(Long.valueOf(userid));
    	 
    	 List<TUser> frienduserList=TFriendsAction.getUserFriends(owneruser.getEmail(), "好友");
    	 List<TUser> studentuserList=TFriendsAction.getUserFriends(owneruser.getEmail(), "同学");
    	 List<TUser> familyuserList=TFriendsAction.getUserFriends(owneruser.getEmail(), "家人");
    	 
    	 TUser frienduser=new TUser();
    	 List<TPersonaldynamic> friendPersonaldynamicList=new ArrayList<TPersonaldynamic>();
    	 
    	 for(int i=0;i<frienduserList.size();i++){
    		 frienduser=frienduserList.get(i);
    		 List<TPersonaldynamic> list= personaldao.getSession()
    	 	    		.createQuery("from TPersonaldynamic as personaldynamic where personaldynamic.TUser=:ownerUser").setEntity("ownerUser", frienduser)
    	 	    		.list();
    		 friendPersonaldynamicList.addAll(list);
    	 }
    	 
    	 for(int i=0;i<studentuserList.size();i++){
    		 frienduser=studentuserList.get(i);
    		 List<TPersonaldynamic> list= personaldao.getSession()
    	 	    		.createQuery("from TPersonaldynamic as personaldynamic where personaldynamic.TUser=:ownerUser").setEntity("ownerUser", frienduser)
    	 	    		.list();
    		 friendPersonaldynamicList.addAll(list);
    	 }
    	 
    	 for(int i=0;i<familyuserList.size();i++){
    		 frienduser=familyuserList.get(i);
    		 List<TPersonaldynamic> list= personaldao.getSession()
    	 	    		.createQuery("from TPersonaldynamic as personaldynamic where personaldynamic.TUser=:ownerUser").setEntity("ownerUser", frienduser)
    	 	    		.list();
    		 friendPersonaldynamicList.addAll(list);
    	 }
 		
    	personaldao.getSession().close();
    	userdao.getSession().close();
 		return friendPersonaldynamicList;
     }
     
     @SuppressWarnings("unchecked")
  	public static List<TPersonaldynamic> getAttentionPersonaldynamic(String userid){
      	 TPersonaldynamicDAO personaldao = new TPersonaldynamicDAO();
      	 org.hibernate.Transaction tran = personaldao.getSession()
   				.beginTransaction();
     	 personaldao.getSession().flush();
     	 tran.commit();
     	 
      	 TUserDAO userdao=new TUserDAO();
    	 TUser owneruser=userdao.findById(Long.valueOf(userid));
    	 
    	 TUser frienduser=new TUser();
    	 List<TPersonaldynamic> friendPersonaldynamicList=new ArrayList<TPersonaldynamic>();
    	 List<TUser> frienduserList=TAttentionAction.getAttentions(owneruser.getEmail());
    	 
    	 for(int i=0;i<frienduserList.size();i++){
    		 frienduser=frienduserList.get(i);
    		 List<TPersonaldynamic> list= personaldao.getSession()
    	   	    		.createQuery("from TPersonaldynamic as personaldynamic where personaldynamic.TUser=:ownerUser").setEntity("ownerUser", frienduser)
    	   	    		.list();
    		 friendPersonaldynamicList.addAll(list);
    	 }
      	
      	 personaldao.getSession().close();
      	userdao.getSession().close();
   		return friendPersonaldynamicList;
       }
     
     @SuppressWarnings("unchecked")
 	public static List<TPersonaldynamic> getPersonaldynamic(){
     	 TPersonaldynamicDAO personaldao = new TPersonaldynamicDAO();
     	 org.hibernate.Transaction tran = personaldao.getSession()
   				.beginTransaction();
     	 personaldao.getSession().flush();
     	 tran.commit();
     	 
     	 List<TPersonaldynamic> list2= personaldao.getSession()
  	    		.createQuery("from TPersonaldynamic")
  	    		.list();
  		
     	 personaldao.getSession().close();
  		return list2;
      }
     
     @SuppressWarnings("unchecked")
	public static List<TPersonaldynamic> getPersonaldynamicByusername(String username){
    	 TUserDAO userdao = new TUserDAO();
    	 List<TUser> list1 = userdao.findByEmail(username);
    	 TUser user = (TUser) list1.get(0);
    	 
    	 TPersonaldynamicDAO personaldao = new TPersonaldynamicDAO();
    	 org.hibernate.Transaction tran = personaldao.getSession()
   				.beginTransaction();
     	 personaldao.getSession().flush();
     	 tran.commit();
     	 
  		List<TPersonaldynamic> list2= personaldao.getSession()
	    		.createQuery("from TPersonaldynamic as personaldynamic where personaldynamic.TUser=:ownerUser").setEntity("ownerUser", user)
	    		.list();
  		
  		personaldao.getSession().close();
  		userdao.getSession().close();
  		return list2;
     }
     
     @SuppressWarnings("unchecked")
 	public static List<TPersonaldynamic> getPersonaldynamicByuserid(String userid){
     	 TUserDAO userdao = new TUserDAO();
     	 TUser user = (TUser)userdao.findById(Long.valueOf(userid));
     	 
     	 TPersonaldynamicDAO personaldao = new TPersonaldynamicDAO();
     	 org.hibernate.Transaction tran = personaldao.getSession()
   				.beginTransaction();
     	 personaldao.getSession().flush();
     	 tran.commit();
     	 
   		List<TPersonaldynamic> list2= personaldao.getSession()
 	    		.createQuery("from TPersonaldynamic as personaldynamic where personaldynamic.TUser=:ownerUser").setEntity("ownerUser", user)
 	    		.list();
   		
   		personaldao.getSession().close();
   		userdao.getSession().close();
   		return list2;
      }
     
     public static void setPersonaldynamic(TPersonaldynamic personaldynamic){
    	TPersonaldynamicDAO personaldao = new TPersonaldynamicDAO();
  		org.hibernate.Transaction tran = personaldao.getSession()
  				.beginTransaction();
  		TPersonaldynamic newpersonaldynamic=personaldao.findById(personaldynamic.getId());
  		newpersonaldynamic.setDetail(personaldynamic.getDetail());
  		newpersonaldynamic.setDotX(personaldynamic.getDotX());
  		newpersonaldynamic.setDotY(personaldynamic.getDotY());
  		
  		if(personaldynamic.getPicture1()!="null")
  		newpersonaldynamic.setPicture1(personaldynamic.getPicture1());
  		if(personaldynamic.getPicture2()!="null")
  	    newpersonaldynamic.setPicture2(personaldynamic.getPicture2());
  		
  		newpersonaldynamic.setTime(personaldynamic.getTime());
  		personaldao.getSession().update(newpersonaldynamic);
  		
  		tran.commit();
  		personaldao.getSession().close();
     }
     
     @SuppressWarnings("unused")
	public static boolean deletePersonaldynamic(String dynamicid){
    	TPersonaldynamicDAO personaldao = new TPersonaldynamicDAO();
   		org.hibernate.Transaction tran = personaldao.getSession()
   				.beginTransaction();
   		TPersonaldynamic newpersonaldynamic=personaldao.findById(Long.valueOf(dynamicid));
   		personaldao.getSession().delete(newpersonaldynamic);
   		
   		tran.commit();
  		personaldao.getSession().close();
  		return true;
     }
}
