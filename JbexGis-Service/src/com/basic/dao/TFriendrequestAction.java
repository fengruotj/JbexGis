package com.basic.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.basic.bean.TFriendrequest;
import com.basic.bean.TFriendrequestDAO;
import com.basic.bean.TGroupfriends;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;

public class TFriendrequestAction {
   public static boolean addFriendrequest(String owneruser,String frienduser,String requestGroup,Timestamp requestDate,String validationmessage){
	    TFriendrequestDAO friendrequestdao=new TFriendrequestDAO();
	    
	    TUserDAO userdao=new TUserDAO();
	    TUser friendUser = (TUser) userdao.findByEmail(frienduser).get(0);
		TUser ownerUser= (TUser) userdao.findByEmail(owneruser).get(0);
	    
		List result= friendrequestdao.getSession()
				.createQuery("from TFriendrequest as requset where requset.TUserByUserId=:ownerUser and requset.TUserByFriendId=:friendsUser")
				.setEntity("ownerUser", ownerUser)
				.setEntity("friendsUser",friendUser)
				.list();
	    if(result.size()!=0){
	    	friendrequestdao.getSession().close();
	 	    userdao.getSession().close();
	    	return false;
	    }
	    
		org.hibernate.Transaction tran = friendrequestdao.getSession()
				.beginTransaction();
		
		TFriendrequest friendrequest=new TFriendrequest();
	    friendrequest.setTUserByUserId(ownerUser);
	    friendrequest.setTUserByFriendId(friendUser);
	    friendrequest.setRequestgroup(requestGroup);
	    friendrequest.setRequestime(requestDate);
	    friendrequest.setValidationmessage(validationmessage);
	    friendrequestdao.save(friendrequest);
	    tran.commit();
	    
	    friendrequestdao.getSession().close();
	    userdao.getSession().close();
	    return true;
   }
   
   public static List<TFriendrequest> getFriendrequest(String frienduser){
	   
	   TFriendrequestDAO friendrequestdao=new TFriendrequestDAO();
	   org.hibernate.Transaction tran = friendrequestdao.getSession()
				.beginTransaction();
	   friendrequestdao.getSession().flush();
	   tran.commit();
	   
	   TUserDAO userdao=new TUserDAO();
	   
	   TUser friendUser = (TUser) userdao.findByEmail(frienduser).get(0);
	   List result= friendrequestdao.getSession()
			   .createQuery("from TFriendrequest as requset where requset.TUserByFriendId=:friendsUser")
			   .setEntity("friendsUser", friendUser)
			   .list();
	   List<TFriendrequest> list=new  ArrayList<TFriendrequest>();
	   for(int i=0;i<result.size();i++){
		   TFriendrequest friendrequest=(TFriendrequest) result.get(i);
		   list.add(friendrequest);
		  }
	   
	   friendrequestdao.getSession().close();
	   userdao.getSession().close();
	   return list;
   }
   
   public static boolean deleteFriendrequest(String owneruser, String frienduser){
	   TFriendrequestDAO friendrequestdao=new TFriendrequestDAO();
	   TUserDAO userdao=new TUserDAO();
	   TUser friendUser = (TUser) userdao.findByEmail(frienduser).get(0);
	   TUser ownerUser= (TUser) userdao.findByEmail(owneruser).get(0);
	   List result= friendrequestdao.getSession()
				.createQuery("from TFriendrequest as requset where requset.TUserByUserId=:ownerUser and requset.TUserByFriendId=:friendsUser")
				.setEntity("ownerUser", ownerUser)
				.setEntity("friendsUser",friendUser)
				.list();
		if(result.size()==0){
			friendrequestdao.getSession().close();
			userdao.getSession().close();
			return false;
		}
		
		org.hibernate.Transaction tran = friendrequestdao.getSession()
				.beginTransaction();
		friendrequestdao.delete((TFriendrequest) result.get(0));
		tran.commit();
		friendrequestdao.getSession().close();
		userdao.getSession().close();
		return true;
   }
}
