package com.basic.dao;

import java.util.ArrayList;
import java.util.List;

import com.basic.bean.TGroupfamily;
import com.basic.bean.TGroupfamilyDAO;
import com.basic.bean.TGroupfriends;
import com.basic.bean.TGroupfriendsDAO;
import com.basic.bean.TGroupstudent;
import com.basic.bean.TGroupstudentDAO;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;

public class TFriendsAction {
	
      public static List<TUser> getUserFriends(String owneruser,String GroupName){
    	 TGroupfriendsDAO friendsdao=new TGroupfriendsDAO();
    	 org.hibernate.Transaction tran1 = friendsdao.getSession().beginTransaction();
    	 friendsdao.getSession().flush();
    	 tran1.commit();
    	 
    	 TGroupstudentDAO studentdao=new TGroupstudentDAO();
    	 org.hibernate.Transaction tran2 = studentdao.getSession().beginTransaction();
    	 friendsdao.getSession().flush();
    	 tran2.commit();
    	 
    	 TGroupfamilyDAO familydao=new TGroupfamilyDAO();
    	 org.hibernate.Transaction tran3 = familydao.getSession().beginTransaction();
    	 friendsdao.getSession().flush();
    	 tran3.commit();
   	     TUserDAO userdao=new TUserDAO();
   	     List<TUser> list = userdao.findByEmail(owneruser);
	     TUser ownerUser = (TUser) list.get(0);
	     
    	  if(GroupName.endsWith("好友")){
    		  List result= friendsdao.getSession()
    				.createQuery("from TGroupfriends as group where group.TUserByUserId=:ownerUser").setEntity("ownerUser", ownerUser)
    				.list();
    		  List<TUser> user=new ArrayList<TUser>();
    		  for(int i=0;i<result.size();i++){
    			  TGroupfriends friendsuser=(TGroupfriends) result.get(i);
    			  user.add(friendsuser.getTUserByFriendsId());
    		  }
    		  
    		  friendsdao.getSession().close();
        	  studentdao.getSession().close();
        	  familydao.getSession().close();
        	  userdao.getSession().close();
        	  
    		  return user;
    	  }
    	  else if(GroupName.equals("同学")){
    		  List result= studentdao.getSession()
      				.createQuery("from TGroupstudent as group where group.TUserByUserId=:ownerUser").setEntity("ownerUser", ownerUser)
      				.list();
    		  List<TUser> student=new ArrayList<TUser>();
    		  for(int i=0;i<result.size();i++){
    			  TGroupstudent friendsuser=(TGroupstudent) result.get(i);
    			  student.add(friendsuser.getTUserByFriendsId());
    		  }
    		  
    		  friendsdao.getSession().close();
        	  studentdao.getSession().close();
        	  familydao.getSession().close();
        	  userdao.getSession().close();
        	  
      		  return student;
    	   }else if(GroupName.endsWith("家人")){
    		   List result= familydao.getSession()
         				.createQuery("from TGroupfamily as group where group.TUserByUserId=:ownerUser").setEntity("ownerUser", ownerUser)
         				.list();
    		   List<TUser> family=new ArrayList<TUser>();
     		  for(int i=0;i<result.size();i++){
     			 TGroupfamily friendsuser=(TGroupfamily) result.get(i);
     			 family.add(friendsuser.getTUserByFriendsId());
     		  }
     		  
     	  friendsdao.getSession().close();
       	  studentdao.getSession().close();
       	  familydao.getSession().close();
       	  userdao.getSession().close();
       	  
         		  return family;
    	   }
    	  return null;
      }
      public static boolean IsFriends(String owneruser,String friends){
    	  TGroupfriendsDAO friendsdao=new TGroupfriendsDAO();
    	  TGroupstudentDAO studentdao=new TGroupstudentDAO();
    	  TGroupfamilyDAO familydao=new TGroupfamilyDAO();
          
    	  TUserDAO userdao=new TUserDAO();
    	  TUser user = (TUser) userdao.findByEmail(friends).get(0);
    	  TUser ownerUser = (TUser) userdao.findByEmail(owneruser).get(0);
    	  
    	  List friend= friendsdao.getSession()
    			  .createQuery("from TGroupfriends as group where group.TUserByUserId=:ownerUser and group.TUserByFriendsId=:friendsUser")
    			  .setEntity("ownerUser", ownerUser)
    			  .setEntity("friendsUser", user)
    			  .list();

    	  List student= studentdao.getSession()
    			  .createQuery("from TGroupstudent as group where group.TUserByUserId=:ownerUser and group.TUserByFriendsId=:friendsUser")
    			  .setEntity("ownerUser", ownerUser)
    			  .setEntity("friendsUser", user)
    			  .list();

    	  List family= familydao.getSession()
    			  .createQuery("from TGroupfamily as group where group.TUserByUserId=:ownerUser and group.TUserByFriendsId=:friendsUser")
    			  .setEntity("ownerUser", ownerUser)
    			  .setEntity("friendsUser", user)
    			  .list();
    
    	  friendsdao.getSession().close();
    	  studentdao.getSession().close();
    	  familydao.getSession().close();
    	  userdao.getSession().close();
    	  
    	  if(friend.size()==0&&student.size()==0&&family.size()==0){
    		  return false;
    	  }
    	  else 
    		  return true;
      }
      
      public static boolean deleteFriends(String owneruser,String email){
    	  if(IsFriends(owneruser, email)!=true)
    		  return false;
    	  
    	  TGroupfriendsDAO friendsdao=new TGroupfriendsDAO();
    	  TGroupstudentDAO studentdao=new TGroupstudentDAO();
    	  TGroupfamilyDAO familydao=new TGroupfamilyDAO();
    	  
    	  TUserDAO userdao=new TUserDAO();
    	  TUser user = (TUser) userdao.findByEmail(email).get(0);
    	  TUser ownerUser = (TUser) userdao.findByEmail(owneruser).get(0);
    	  
    	  List friend= friendsdao.getSession()
    			  .createQuery("from TGroupfriends as group where group.TUserByUserId=:ownerUser and group.TUserByFriendsId=:friendsUser")
    			  .setEntity("ownerUser", ownerUser)
    			  .setEntity("friendsUser", user)
    			  .list();

    	  List student= studentdao.getSession()
    			  .createQuery("from TGroupstudent as group where group.TUserByUserId=:ownerUser and group.TUserByFriendsId=:friendsUser")
    			  .setEntity("ownerUser", ownerUser)
    			  .setEntity("friendsUser", user)
    			  .list();

    	  List family= familydao.getSession()
    			  .createQuery("from TGroupfamily as group where group.TUserByUserId=:ownerUser and group.TUserByFriendsId=:friendsUser")
    			  .setEntity("ownerUser", ownerUser)
    			  .setEntity("friendsUser",user)
    			  .list();
    	  
    	  if(friend.size()!=0){
    		  org.hibernate.Transaction tran1 = friendsdao.getSession().beginTransaction();
    		  friendsdao.delete((TGroupfriends) friend.get(0));
    		  tran1.commit();
    	  }
    	  else if(student.size()!=0){
    		  org.hibernate.Transaction tran2 = studentdao.getSession().beginTransaction();
    		  studentdao.delete((TGroupstudent) student.get(0));
    		  tran2.commit();
    	  }
    	  else if(family.size()!=0){

        	  org.hibernate.Transaction tran3 = familydao.getSession().beginTransaction();
    		  familydao.delete((TGroupfamily) family.get(0));
    		  tran3.commit();
    	  }
    	  userdao.getSession().close();
    	  friendsdao.getSession().close();
    	  studentdao.getSession().close();
    	  familydao.getSession().close();
    	  return true;
    	  
      }
   
   /**
 * @param ownuser
 * @param email
 * @param GroupName
 */
@SuppressWarnings({ "unchecked", "static-access" })
//移至分组GroupName
public static void setFriendGroup(String ownuser,String email,String GroupName){

	   deleteFriends(ownuser,email);
	   new TUserAction().addFriends(ownuser, email, GroupName);
   }
}
