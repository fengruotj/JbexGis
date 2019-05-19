package com.basic.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.basic.bean.TJbrxinfo;
import com.basic.bean.TJbrxinfoDAO;
import com.basic.bean.TPersonaldynamic;
import com.basic.bean.TPersonaldynamicDAO;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;
import com.json.service.JsonService;
import com.json.tools.JsonTools;

public class TJbexinfoAction {
	public static long  addJbexinfo(String username, Double dotX, Double dotY, String title,
			String detail, Timestamp time, String label){
	    TUserDAO userdao=new TUserDAO();
	    TJbrxinfoDAO jbexinfodao=new TJbrxinfoDAO();
	    org.hibernate.Transaction tran = jbexinfodao.getSession()
		.beginTransaction();
	    TJbrxinfo jbexinfo=new TJbrxinfo();
	    TUser user = (TUser) userdao.findByEmail(username).get(0);
	    jbexinfo.setTUser(user);
	    jbexinfo.setTUser(user);
	    jbexinfo.setDotX(dotX);
	    jbexinfo.setDotY(dotY);
		jbexinfo.setTitle(title);
		jbexinfo.setDetail(detail);
		jbexinfo.setTime(time);
		jbexinfo.setLabel(label);
		jbexinfo.setSize(0);
		jbexinfodao.save(jbexinfo);
		tran.commit();
		
		jbexinfodao.getSession().close();
		userdao.getSession().close();
		
		return jbexinfo.getId();
   }
   
   @SuppressWarnings("unchecked")
	public static List<TJbrxinfo> getJbexinfo(String NowTime){

	    TJbrxinfoDAO jbexinfodao=new TJbrxinfoDAO();
	    org.hibernate.Transaction tran = jbexinfodao.getSession()
				.beginTransaction();
	    jbexinfodao.getSession().flush();
	    tran.commit();
	    
	    Timestamp nowtime = Timestamp.valueOf(NowTime);
		List<TJbrxinfo> list2= jbexinfodao.getSession()
	    		.createQuery("from TJbrxinfo as jbexinfo where jbexinfo.time >=:nowTime").setTimestamp("nowTime", nowtime)
	    		.list();
		
		jbexinfodao.getSession().close();
		return list2;
	}
	
   @SuppressWarnings("unchecked")
	public static List<TJbrxinfo> getAllJbexinfo(){
	   
	    TJbrxinfoDAO jbexinfodao=new TJbrxinfoDAO();
	    org.hibernate.Transaction tran = jbexinfodao.getSession()
				.beginTransaction();
	    jbexinfodao.getSession().flush();
	    tran.commit();
	    
		List<TJbrxinfo> list2= jbexinfodao.getSession()
	    		.createQuery("from TJbrxinfo as jbexinfo ")
	    		.list();
		
		jbexinfodao.getSession().close();
		return list2;
	}
   
	@SuppressWarnings("unchecked")
	public static List<TJbrxinfo> getJbexinfoByusername(String username){
		TUserDAO userdao = new TUserDAO();

		List<TUser> list1 = userdao.findByEmail(username);

		TUser user = (TUser) list1.get(0);
		
		TJbrxinfoDAO jbexinfodao=new TJbrxinfoDAO();	
		org.hibernate.Transaction tran = jbexinfodao.getSession()
				.beginTransaction();
		jbexinfodao.getSession().flush();
		tran.commit();
		
		List<TJbrxinfo> list2= jbexinfodao.getSession()
	    		.createQuery("from TJbrxinfo as jbexinfo where jbexinfo.TUser=:ownerUser").setEntity("ownerUser", user)
	    		.list();

		userdao.getSession().close();
		jbexinfodao.getSession().close();
		return list2;
	}
	
	public static void setJbexinfo(TJbrxinfo jbexinfo){
		
		TJbrxinfoDAO jbexinfodao=new TJbrxinfoDAO();		
		org.hibernate.Transaction tran = jbexinfodao.getSession()
				.beginTransaction();
		TJbrxinfo newpublicinfo=jbexinfodao.findById(jbexinfo.getId());
		newpublicinfo.setDotX(jbexinfo.getDotX());
		newpublicinfo.setDotY(jbexinfo.getDotY());
		newpublicinfo.setDetail(jbexinfo.getDetail());
		newpublicinfo.setLabel(jbexinfo.getLabel());
		
		if(jbexinfo.getPicture1()!="null")
		newpublicinfo.setPicture1(jbexinfo.getPicture1());
		if(jbexinfo.getPicture2()!="null")
		newpublicinfo.setPicture2(jbexinfo.getPicture2());
		
		newpublicinfo.setTime(jbexinfo.getTime());
		newpublicinfo.setTitle(jbexinfo.getTitle());
		jbexinfodao.getSession().update(newpublicinfo);
		
		tran.commit();
		jbexinfodao.getSession().close();
	}
	
	public static void addJbexSize(String jbexinfoid){
		TJbrxinfoDAO jbexinfodao=new TJbrxinfoDAO();	
		jbexinfodao.getSession().flush();
		org.hibernate.Transaction tran1 = jbexinfodao.getSession()
				.beginTransaction();
		/*
		 * 通过查阅Hibernate的API可以知道flush方法的主要作用就是清理缓存，强制数据库
与Hibernate缓存同步，以保证数据的一致性。它的主要动作就是向数据库发送一系列的sql语句，并执行这些sql语句，但是不会向数据库提交。
而commit方法则会首先调用flush方法，然后提交事务。这就是为什么我们仅仅调用flush的时候记录并未插入到数据库中的原因，因为只有提交了事务，
对数据库所做的更新才会被保存下来。
		 */
		tran1.commit();
		TJbrxinfo publicinfo=jbexinfodao.findById(Long.valueOf(jbexinfoid));
		int size=publicinfo.getSize();
		size=size+1;
		publicinfo.setSize(size);
		jbexinfodao.getSession().update(publicinfo);
		
		org.hibernate.Transaction tran2 = jbexinfodao.getSession()
				.beginTransaction();
		
		tran2.commit();
		jbexinfodao.getSession().close();
	}
	
	public static boolean deleteJbexinfo(String jbexinfoid){
		TJbrxinfoDAO jbexinfodao=new TJbrxinfoDAO();		
		org.hibernate.Transaction tran = jbexinfodao.getSession()
				.beginTransaction();
		TJbrxinfo newpublicinfo=jbexinfodao.findById(Long.valueOf(jbexinfoid));
		jbexinfodao.getSession().delete(newpublicinfo);
		tran.commit();
		jbexinfodao.getSession().close();
		return true;
	}

	@SuppressWarnings("unchecked")
	public static List<TJbrxinfo> getFriendJbexinfo(String nowTime,
			String userid) {
	TJbrxinfoDAO jbexinfodao=new TJbrxinfoDAO();	
	org.hibernate.Transaction tran = jbexinfodao.getSession()
				.beginTransaction();
	jbexinfodao.getSession().flush();
	tran.commit();
	    
	TUserDAO userdao=new TUserDAO();
   	TUser owneruser=userdao.findById(Long.valueOf(userid));
    Timestamp nowtime = Timestamp.valueOf(nowTime);
    
    List<TUser> frienduserList=TFriendsAction.getUserFriends(owneruser.getEmail(), "好友");
    List<TUser> studentuserList=TFriendsAction.getUserFriends(owneruser.getEmail(), "同学");
	List<TUser> familyuserList=TFriendsAction.getUserFriends(owneruser.getEmail(), "家人");
	 
	 TUser frienduser=new TUser();
	 List<TJbrxinfo> friendJbexInfoList=new ArrayList<TJbrxinfo>();
	 
	 for(int i=0;i<frienduserList.size();i++){
		 frienduser=frienduserList.get(i);
		 List<TJbrxinfo> list= jbexinfodao.getSession()
	 	    		.createQuery("from TJbrxinfo as jbexinfo where jbexinfo.TUser=:ownerUser and jbexinfo.time >=:nowTime").setEntity("ownerUser", frienduser)
	 	    		.setTimestamp("nowTime", nowtime)
	 	    		.list();
		 friendJbexInfoList.addAll(list);
	 }

	 for(int i=0;i<studentuserList.size();i++){
		 frienduser=frienduserList.get(i);
		 List<TJbrxinfo> list= jbexinfodao.getSession()
	 	    		.createQuery("from TJbrxinfo as jbexinfo where jbexinfo.TUser=:ownerUser and jbexinfo.time >=:nowTime").setEntity("ownerUser", frienduser)
	 	    		.setTimestamp("nowTime", nowtime)
	 	    		.list();
		 friendJbexInfoList.addAll(list);
	 }
	 
	 for(int i=0;i<familyuserList.size();i++){
		 frienduser=frienduserList.get(i);
		 List<TJbrxinfo> list= jbexinfodao.getSession()
	 	    		.createQuery("from TJbrxinfo as jbexinfo where jbexinfo.TUser=:ownerUser and jbexinfo.time >=:nowTime").setEntity("ownerUser", frienduser)
	 	    		.setTimestamp("nowTime", nowtime)
	 	    		.list();
		 friendJbexInfoList.addAll(list);
	 }
	 
   userdao.getSession().close();
   jbexinfodao.getSession().close();
	 return friendJbexInfoList;
	}
	
	@SuppressWarnings("unchecked")
	public static List<TJbrxinfo> getAttentionJbexInfo(String userid,String nowTime){
	TJbrxinfoDAO jbexinfodao=new TJbrxinfoDAO();	
	TUserDAO userdao=new TUserDAO();
	TUser owneruser=userdao.findById(Long.valueOf(userid));
	 Timestamp nowtime = Timestamp.valueOf(nowTime);
   	 
   	 TUser frienduser=new TUser();
   	 List<TJbrxinfo> AttentionJbexInfoList=new ArrayList<TJbrxinfo>();
   	 List<TUser> frienduserList=TAttentionAction.getAttentions(owneruser.getEmail());
   	 
   	 for(int i=0;i<frienduserList.size();i++){
   		 frienduser=frienduserList.get(i);
   		List<TJbrxinfo> list= jbexinfodao.getSession()
 	    		.createQuery("from TJbrxinfo as jbexinfo where jbexinfo.TUser=:ownerUser and jbexinfo.time >=:nowTime").setEntity("ownerUser", frienduser)
 	    		.setTimestamp("nowTime", nowtime)
 	    		.list();
   		AttentionJbexInfoList.addAll(list);
   	 }
     	
   	jbexinfodao.getSession().close();
    userdao.getSession().close();
  	return AttentionJbexInfoList;
      }
}
