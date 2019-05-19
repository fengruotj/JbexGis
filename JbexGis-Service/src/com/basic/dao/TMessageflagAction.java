package com.basic.dao;

import java.util.List;

import com.basic.bean.TMessageflag;
import com.basic.bean.TMessageflagDAO;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;

public class TMessageflagAction {
	public static void  addTMessageflag(String username){
		TUserDAO userdao=new TUserDAO();
		TMessageflagDAO messageflagdao=new TMessageflagDAO();
		org.hibernate.Transaction tran = messageflagdao.getSession()
				.beginTransaction();
		TMessageflag messageflag=new TMessageflag();
		TUser user = (TUser) userdao.findByEmail(username).get(0);
		messageflag.setFlag(false);
		messageflag.setTUser(user);
		messageflagdao.save(messageflag);
		
		tran.commit();
		messageflagdao.getSession().close();
		userdao.getSession().close();
	}
	
	@SuppressWarnings("unchecked")
	public static void setTMessageflag(String username,boolean flag){
		TUserDAO userdao=new TUserDAO();
		TMessageflagDAO messageflagdao=new TMessageflagDAO();
		org.hibernate.Transaction tran = messageflagdao.getSession()
				.beginTransaction();
		TUser user = (TUser) userdao.findByEmail(username).get(0);
		List<TMessageflag> list2= messageflagdao.getSession()
	    		.createQuery("from TMessageflag as messageflag where messageflag.TUser=:ownerUser").setEntity("ownerUser", user)
	    		.list();
		TMessageflag messageflag=list2.get(0);
		messageflag.setFlag(flag);
		messageflagdao.getSession().update(messageflag);
		tran.commit();
		messageflagdao.getSession().close();
	}
	
	
}
