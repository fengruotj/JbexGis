package com.basic.dao;

import java.sql.Timestamp;
import java.util.List;

import com.basic.bean.TFriendrequest;
import com.basic.bean.TPublicinfo;
import com.basic.bean.TPublicinfoDAO;
import com.basic.bean.TUser;
import com.basic.bean.TUserDAO;
import com.json.service.JsonService;
import com.json.tools.JsonTools;

public class TPublicinfoAction {

	public static long  addPublicinfo(String username, Double dotX, Double dotY, String title,
			String detail, Timestamp time, String label){
		
		TUserDAO userdao=new TUserDAO();
		TPublicinfoDAO publicinfodao = new TPublicinfoDAO();
		org.hibernate.Transaction tran = publicinfodao.getSession()
				.beginTransaction();
		TPublicinfo publicinfo = new TPublicinfo();
		TUser user = (TUser) userdao.findByEmail(username).get(0);
		publicinfo.setTUser(user);
		publicinfo.setDotX(dotX);
		publicinfo.setDotY(dotY);
		publicinfo.setTitle(title);
		publicinfo.setDetail(detail);
		publicinfo.setTime(time);
		publicinfo.setLabel(label);
		publicinfodao.save(publicinfo);
		tran.commit();
		
		publicinfodao.getSession().close();
		userdao.getSession().close();
		
		return publicinfo.getId();
	}
	
	@SuppressWarnings("unchecked")
	public static List<TPublicinfo> getPublicinfo(){

		TPublicinfoDAO publicinfodao = new  TPublicinfoDAO();		

		List<TPublicinfo> list2= publicinfodao.getSession()
	    		.createQuery("from TPublicinfo")
	    		.list();
		
		publicinfodao.getSession().close();
		return list2;
	}
	
	@SuppressWarnings("unchecked")
	public static String getPublicinfoByusername(String username){
		TUserDAO userdao = new TUserDAO();
		List<TUser> list1 = userdao.findByEmail(username);
		if (list1.size() == 0) {
			userdao.getSession().close();
			return "";
		}
		TUser user = (TUser) list1.get(0);
		
		TPublicinfoDAO publicinfodao = new  TPublicinfoDAO();	
		publicinfodao.getSession().flush();
		org.hibernate.Transaction tran = publicinfodao.getSession()
				.beginTransaction();
		tran.commit();
		
		List<TPublicinfo> list2= publicinfodao.getSession()
	    		.createQuery("from TPublicinfo as publicinfo where publicinfo.TUser=:ownerUser").setEntity("ownerUser", user)
	    		.list();
		if (list2.size() == 0) {
			userdao.getSession().close();
			publicinfodao.getSession().close();
			return "";
		}
		String PublicInfoJsonString = JsonTools.createJsonString("publicinfo",
				JsonService.getPublicInfo(list2));
		
		userdao.getSession().close();
		publicinfodao.getSession().close();
		return PublicInfoJsonString;
	}
	
	@SuppressWarnings("unchecked")
	public static List<TPublicinfo>  getPublicinfoByuserid(String userid){
		TUserDAO userdao = new TUserDAO();
		TUser user = (TUser) userdao.findById(Long.valueOf(userid));
		
		TPublicinfoDAO publicinfodao = new  TPublicinfoDAO();	
		publicinfodao.getSession().flush();
		org.hibernate.Transaction tran = publicinfodao.getSession()
				.beginTransaction();
		tran.commit();
		
		List<TPublicinfo> list2= publicinfodao.getSession()
	    		.createQuery("from TPublicinfo as publicinfo where publicinfo.TUser=:ownerUser").setEntity("ownerUser", user)
	    		.list();
	   userdao.getSession().close();
		publicinfodao.getSession().close();
		return list2;
	}
	
	public static void setPublicinfo(TPublicinfo publicinfo){
		
		TPublicinfoDAO publicinfodao = new  TPublicinfoDAO();	
		org.hibernate.Transaction tran = publicinfodao.getSession()
				.beginTransaction();
		TPublicinfo newpublicinfo=publicinfodao.findById(publicinfo.getId());
		newpublicinfo.setDotX(publicinfo.getDotX());
		newpublicinfo.setDotY(publicinfo.getDotY());
		newpublicinfo.setDetail(publicinfo.getDetail());
		newpublicinfo.setLabel(publicinfo.getLabel());
		
		if(publicinfo.getPicture1()!="null")
		newpublicinfo.setPicture1(publicinfo.getPicture1());
		if(publicinfo.getPicture2()!="null")
		newpublicinfo.setPicture2(publicinfo.getPicture2());
		
		newpublicinfo.setTime(publicinfo.getTime());
		newpublicinfo.setTitle(publicinfo.getTitle());
		publicinfodao.getSession().update(newpublicinfo);
		
		tran.commit();
		publicinfodao.getSession().close();
	}
	
	public static Boolean deletePublicinfo(String publicinfoid){
		TPublicinfoDAO publicinfodao = new  TPublicinfoDAO();	
		org.hibernate.Transaction tran = publicinfodao.getSession()
				.beginTransaction();
		TPublicinfo newpublicinfo=publicinfodao.findById(Long.valueOf(publicinfoid));
		publicinfodao.getSession().delete(newpublicinfo);
		tran.commit();
		publicinfodao.getSession().close();
		return true;
	}
}
