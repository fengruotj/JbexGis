package com.basic.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.basic.bean.TMessagebean;
import com.basic.bean.TMessagebeanDAO;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;

public class TMessagebeanAction {
	public static void addTMessagebean(String senduser,String receiveruser,Timestamp time,String msg, Integer type){
		TMessagebeanDAO messagedao=new TMessagebeanDAO();
		TUserDAO userdao=new TUserDAO();
		TMessagebean messagebean=new TMessagebean();
		org.hibernate.Transaction tran = messagedao.getSession()
				.beginTransaction();
		TUser sendUser=(TUser) userdao.findByEmail(senduser).get(0);
		TUser receiverUser=(TUser) userdao.findByEmail(receiveruser).get(0);
		messagebean.setMsg(msg);
		messagebean.setTime(time);
		messagebean.setTUserByReceiverId(receiverUser);
		messagebean.setTUserBySendId(sendUser);
		messagebean.setType(type);
		messagedao.save(messagebean);
		
		tran.commit();
		
		TMessageflagAction.setTMessageflag(receiveruser, true);
		messagedao.getSession().close();
		userdao.getSession().close();
	}
	
	
	public static void addTMessagebeanByID(String senduser,String receiveruser,Timestamp time,String msg, int type){
		
		TUserDAO userdao=new TUserDAO();
		TMessagebeanDAO messagedao=new TMessagebeanDAO();
		TMessagebean messagebean=new TMessagebean();
		org.hibernate.Transaction tran = messagedao.getSession()
				.beginTransaction();
		TUser sendUser=(TUser) userdao.findById(Long.valueOf(senduser));
		TUser receiverUser=(TUser) userdao.findById(Long.valueOf(receiveruser));
		messagebean.setMsg(msg);
		messagebean.setTime(time);
		messagebean.setTUserByReceiverId(receiverUser);
		messagebean.setTUserBySendId(sendUser);
		messagebean.setType(type);
		messagedao.save(messagebean);
		
		tran.commit();
		
		messagedao.getSession().close();
		userdao.getSession().close();
	}
	
	@SuppressWarnings("unchecked")
	public static List<TMessagebean> getMessageBeanByid(String userid){
		TUserDAO userdao = new TUserDAO();
		TUser user= userdao.findById(Long.valueOf(userid));
		List<TMessagebean> result=new ArrayList<TMessagebean>();
		if(user==null){
			userdao.getSession().close();
			return result;
		}
		TMessagebeanDAO messagedao=new TMessagebeanDAO();
		
		   result= messagedao.getSession()
				.createQuery("from TMessagebean as message where message.TUserByReceiverId=:user")
				.setEntity("user", user)
				.list();
	
		userdao.getSession().close();
		messagedao.getSession().close();
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	public static void  deleteMessageBeanByid(String userid){
		TUserDAO userdao = new TUserDAO();
		TUser user= userdao.findById(Long.valueOf(userid));
		TMessagebeanDAO messagedao=new TMessagebeanDAO();
		org.hibernate.Transaction tran = messagedao.getSession()
				.beginTransaction();
		List<TMessagebean> result= messagedao.getSession()
				.createQuery("from TMessagebean as message where message.TUserByReceiverId=:user")
				.setEntity("user", user)
				.list();
		
		for(int i=0;i<result.size();i++){
			TMessagebean message=result.get(i);
			messagedao.delete(message);
		}
		
		tran.commit();
		userdao.getSession().close();
		messagedao.getSession().close();
	}
}
