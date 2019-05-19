package com.basic.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.basic.bean.TJbrxfriend;
import com.basic.bean.TJbrxfriendDAO;
import com.basic.bean.TJbrxfriendId;
import com.basic.bean.TJbrxinfo;
import com.basic.bean.TJbrxinfoDAO;
import com.basic.bean.TMyattention;
import com.basic.bean.TPublicinfo;
import com.basic.bean.TPublicinfoDAO;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;
import com.json.service.JsonService;
import com.json.tools.JsonTools;

public class TJbexfriendAction {

	public static boolean addJbexfriend(String owneruser, String frienduser,long jbexinfoId) {
		// TODO 自动生成的方法存根
		TJbrxfriendDAO jbrxfriendao=new TJbrxfriendDAO();
		TUserDAO userdao=new TUserDAO();
		TJbrxinfoDAO jbrxinfodao=new TJbrxinfoDAO();
		 if (userdao.findByEmail(owneruser).size() != 1||userdao.findByEmail(frienduser).size() != 1) {
			 userdao.getSession().close();
			 jbrxfriendao.getSession().close();
			 jbrxinfodao.getSession().close();
				return false;
			}
		 TUser friendsUser = (TUser) userdao.findByEmail(frienduser).get(0);
		 TUser ownerUser= (TUser) userdao.findByEmail(owneruser).get(0);
		 TJbrxinfo jbrxinfo=jbrxinfodao.findById(jbexinfoId);
		 
		 List jbexfriendList=jbrxfriendao.getSession().
				 createQuery("from TJbrxfriend as jbrxfriend where jbrxfriend.TUserByUserId=:ownerUser and jbrxfriend.TUserByJbrxfriendId=:friendUser and jbrxfriend.TJbrxinfo=:jbrxinfo")
				   .setEntity("ownerUser", ownerUser)
				   .setEntity("friendUser", friendsUser)
				   .setEntity("jbrxinfo", jbrxinfo)
				   .list();
		 
		 if(jbexfriendList.size()!=0){
			 
		 userdao.getSession().close();
		 jbrxfriendao.getSession().close();
		 jbrxinfodao.getSession().close();
	     return false;
		 }
		 org.hibernate.Transaction tran = jbrxfriendao.getSession()
					.beginTransaction();
		 TJbrxfriend jbrxfriend=new TJbrxfriend();
		 jbrxfriend.setTJbrxinfo(jbrxinfo);
		 jbrxfriend.setId(new TJbrxfriendId(ownerUser.getUserId(), friendsUser.getUserId(), jbrxinfo.getId()));
		 jbrxfriend.setTUserByJbrxfriendId(friendsUser);
		 jbrxfriend.setTUserByUserId(ownerUser);
		 
		 jbrxfriendao.save(jbrxfriend);
		 tran.commit();
		 
		 userdao.getSession().close();
		 jbrxfriendao.getSession().close();
		 jbrxinfodao.getSession().close();
		 
		 return true;
	}

	public static boolean deleteJbexfriend(String owneruser, String frienduser,long jbexinfoId) {
		// TODO 自动生成的方法存根
		TJbrxfriendDAO jbrxfriendao=new TJbrxfriendDAO();
		TUserDAO userdao=new TUserDAO();
		TJbrxinfoDAO jbrxinfodao=new TJbrxinfoDAO();
		 if (userdao.findByEmail(owneruser).size() != 1||userdao.findByEmail(frienduser).size() != 1) {
			 userdao.getSession().close();
			 jbrxfriendao.getSession().close();
			 jbrxinfodao.getSession().close();
			 
				return false;
			}
		 TUser ownerUser = (TUser) userdao.findByEmail(frienduser).get(0);
		 TUser friendsUser= (TUser) userdao.findByEmail(owneruser).get(0);
		 TJbrxinfo jbrxinfo=jbrxinfodao.findById(jbexinfoId);
		 
		 List jbexfriendList=jbrxinfodao.getSession().
				 createQuery("from TJbrxfriend as jbrxfriend where jbrxfriend.TUserByUserId=:ownerUser and jbrxfriend.TUserByJbrxfriendId=:friendUser and jbrxfriend.TJbrxinfo=:jbrxinfo")
				   .setEntity("ownerUser", ownerUser)
				   .setEntity("friendUser", friendsUser)
				   .setEntity("jbrxinfo", jbrxinfo)
				   .list();
		 
		 if(jbexfriendList.size()!=0){
			   org.hibernate.Transaction tran1 = jbrxfriendao.getSession().beginTransaction();
			   jbrxfriendao.delete((TJbrxfriend) jbexfriendList.get(0));
	 		    tran1.commit();
		   }
		 
		 userdao.getSession().close();
		 jbrxfriendao.getSession().close();
		 jbrxinfodao.getSession().close();
		 
		 return true;
	}
	
   @SuppressWarnings("unchecked")
public static List<TUser> getJbexRequestFriend(String frienduser,long jbrxinfoID){
	    List <TUser> userList=new ArrayList<TUser>();
	    
	    TJbrxfriendDAO jbrxfriendao=new TJbrxfriendDAO();
	    org.hibernate.Transaction tran = jbrxfriendao.getSession()
				.beginTransaction();
	    jbrxfriendao.getSession().flush();
	    tran.commit();
	    
		TUserDAO userdao=new TUserDAO();
		TJbrxinfoDAO jbrxinfodao=new TJbrxinfoDAO();
		
		 if (userdao.findByEmail(frienduser).size() != 1) {
			 jbrxfriendao.getSession().close();
			 userdao.getSession().close();
			 jbrxinfodao.getSession().close();
			 
				return userList;
			}
		 
		 TUser friendsUser = (TUser) userdao.findByEmail(frienduser).get(0);
		 TJbrxinfo jbrxinfo=jbrxinfodao.findById(jbrxinfoID);
		 
		 List<TJbrxfriend> jbexfriendList=jbrxfriendao.getSession().
				 createQuery("from TJbrxfriend as jbrxfriend where  jbrxfriend.TUserByJbrxfriendId=:friendUser and jbrxfriend.TJbrxinfo=:jbrxinfo")
				   .setEntity("friendUser", friendsUser)
				   .setEntity("jbrxinfo", jbrxinfo)
				   .list();
		 
		 for(int i=0;i<jbexfriendList.size();i++){
			 TJbrxfriend jbrxfriend=jbexfriendList.get(i);
			 userList.add(jbrxfriend.getTUserByUserId());
		 }
		 
		 jbrxfriendao.getSession().close();
		 userdao.getSession().close();
		 jbrxinfodao.getSession().close();
		 
		 return userList;
   }
   
}
