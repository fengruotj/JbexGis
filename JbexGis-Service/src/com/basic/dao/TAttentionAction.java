package com.basic.dao;

import java.util.ArrayList;
import java.util.List;

import com.basic.bean.TGroupfriends;
import com.basic.bean.TMyattention;
import com.basic.bean.TMyattentionDAO;
import com.basic.bean.TMyattentionId;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;

public class TAttentionAction {
	
   public static List<TUser> getAttentions(String email){
	    TMyattentionDAO attentiondao=new TMyattentionDAO();
	    org.hibernate.Transaction tran1 = attentiondao.getSession().beginTransaction();
	    attentiondao.getSession().flush();
	    tran1.commit();
	    
	    TUserDAO userdao=new TUserDAO();
	    List<TUser> list = userdao.findByEmail(email);
	    TUser owneruser = (TUser) list.get(0);
	    List result= attentiondao.getSession()
	    		.createQuery("from TMyattention as attention where attention.TUserByUserId=:ownerUser").setEntity("ownerUser", owneruser)
	    		.list();
	    List<TUser> attentionuser=new ArrayList<TUser>();
	    for(int i=0;i<result.size();i++){
			  TMyattention attention=(TMyattention) result.get(i);
			  attentionuser.add(attention.getTUserByAttentionId());
		  }	
	    userdao.getSession().close();
	    attentiondao.getSession().close();
	    return attentionuser;
   }
   
   public static boolean addAttentions(String owneruser,String frienduser){
	   TMyattentionDAO attentiondao=new TMyattentionDAO();
	   TUserDAO userdao=new TUserDAO();
	   if (userdao.findByEmail(owneruser).size() != 1||userdao.findByEmail(frienduser).size() != 1) {
		   attentiondao.getSession().close();
		   userdao.getSession().close();
			return false;
		}
	   TUser friendsUser = (TUser) userdao.findByEmail(frienduser).get(0);
	   TUser ownerUser= (TUser) userdao.findByEmail(owneruser).get(0);
	   org.hibernate.Transaction tran = attentiondao.getSession()
				.beginTransaction();
	   TMyattention myattention=new TMyattention();
	   myattention.setTUserByUserId(ownerUser);
	   myattention.setTUserByAttentionId(friendsUser);
	   myattention.setId(new TMyattentionId(ownerUser.getUserId(),friendsUser.getUserId()));
	   attentiondao.save(myattention);
	   tran.commit();
	   
	   attentiondao.getSession().close();
	   userdao.getSession().close();
	   return true;
   }
   
   public static void deleteAttentions(String owneruser,String frienduser){
	   TMyattentionDAO attentiondao=new TMyattentionDAO();
	   TUserDAO userdao=new TUserDAO();
	   TUser friendsUser = (TUser) new TUserDAO().findByEmail(frienduser).get(0);
	   TUser ownerUser= (TUser) new TUserDAO().findByEmail(owneruser).get(0);
	   
	   List attentions=attentiondao.getSession()
			   .createQuery("from TMyattention as attention where attention.TUserByUserId=:ownerUser and attention.TUserByAttentionId=:friendUser")
			   .setEntity("ownerUser", ownerUser)
			   .setEntity("friendUser", friendsUser)
			   .list();
	   
	   if(attentions.size()!=0){
		   org.hibernate.Transaction tran1 = attentiondao.getSession().beginTransaction();
		   attentiondao.delete((TMyattention) attentions.get(0));
 		    tran1.commit();
	   }
	   attentiondao.getSession().close();
	   userdao.getSession().close();	   
   }
}
