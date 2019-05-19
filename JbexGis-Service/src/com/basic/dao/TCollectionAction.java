package com.basic.dao;

import java.util.ArrayList;
import java.util.List;

import com.basic.bean.TJbrxinfo;
import com.basic.bean.TJbrxinfoDAO;
import com.basic.bean.TMycollections;
import com.basic.bean.TMycollectionsDAO;
import com.basic.bean.TMycollectionsId;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;

public class TCollectionAction {
	public static List<TJbrxinfo> getCollectiosbyUserID(String userid){
		TMycollectionsDAO collectionsdao=new TMycollectionsDAO();
		org.hibernate.Transaction tran = collectionsdao.getSession()
				.beginTransaction();
		collectionsdao.getSession().flush();
		tran.commit();
		
		TUserDAO userdao=new TUserDAO();
		TUser owneruser = userdao.findById(Long.valueOf(userid));
		List result= collectionsdao.getSession()
				.createQuery("from TMycollections as collections where collections.TUser=:ownerUser").setEntity("ownerUser", owneruser)
				.list();
		List<TJbrxinfo> collectionsJbrx=new ArrayList<TJbrxinfo>();
		for(int i=0;i<result.size();i++){
			TMycollections mycollection=(TMycollections) result.get(i);
			collectionsJbrx.add(mycollection.getTJbrxinfo());
		}
		collectionsdao.getSession().close(); 
		userdao.getSession().close();
		return collectionsJbrx;
	}
    
	 public static boolean addCollections(String userid,String collectionjbex){
		   TMycollectionsDAO collectionsdao=new TMycollectionsDAO();
		   TUserDAO userdao=new TUserDAO();
		   TJbrxinfoDAO jbrxinfodao=new TJbrxinfoDAO();
		  
		   
		   TUser owneruser = userdao.findById(Long.valueOf(userid));
		   TJbrxinfo jbrxinfo=jbrxinfodao.findById(Long.valueOf(collectionjbex));
		   
		   List jbexinfoList=jbrxinfodao.getSession().
					 createQuery("from TMycollections as collections where collections.TUser=:ownerUser and collections.TJbrxinfo=:jbrxInfo")
					   .setEntity("ownerUser", owneruser)
					   .setEntity("jbrxInfo", jbrxinfo)
					   .list();
			 
			 if(jbexinfoList.size()!=0){
				 
			 userdao.getSession().close();
			 jbrxinfodao.getSession().close();
		     return false;
			 }
			 
		   org.hibernate.Transaction tran = collectionsdao.getSession()
					.beginTransaction();
		   
		   TMycollections collections=new TMycollections();
		   collections.setId(new TMycollectionsId(Long.valueOf(userid), Long.valueOf(collectionjbex)));
		   collections.setTUser(owneruser);
		   collections.setTJbrxinfo(jbrxinfo);
		   
		   collectionsdao.save(collections);
		   tran.commit();
		   
		   collectionsdao.getSession().close();
		   userdao.getSession().close();
		   jbrxinfodao.getSession().close();
		   return true;
	   }
	 
	 public static boolean deleteCollectionss(String userid,String collectionjbex){
		  TMycollectionsDAO collectionsdao=new TMycollectionsDAO();
		   TUserDAO userdao=new TUserDAO();
		   TJbrxinfoDAO jbrxinfodao=new TJbrxinfoDAO();
		   
		   TUser owneruser = userdao.findById(Long.valueOf(userid));
		   TJbrxinfo jbrxinfo=jbrxinfodao.findById(Long.valueOf(collectionjbex));
		   org.hibernate.Transaction tran = collectionsdao.getSession()
					.beginTransaction();
		   
		   List collections=collectionsdao.getSession()
				   .createQuery("from TMycollections as collections where collections.TUser=:ownerUser and collections.TJbrxinfo=:jbrxInfo")
				   .setEntity("ownerUser", owneruser)
				   .setEntity("jbrxInfo", jbrxinfo)
				   .list();
		   
		   if(collections.size()==0){
			   collectionsdao.getSession().close();
			   userdao.getSession().close();	
			   jbrxinfodao.getSession().close();
			   return false;
		   }
		   
		   collectionsdao.delete((TMycollections) collections.get(0));
		   tran.commit();
		   collectionsdao.getSession().close();
		   userdao.getSession().close();	
		   jbrxinfodao.getSession().close();
		   
		   return true;
	   }
}
