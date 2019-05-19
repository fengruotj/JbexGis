package com.mapgis.model;
import java.io.Serializable;
import java.sql.Timestamp;

import com.zondy.mapgis.geometry.Dot;

public class PublicUserIfo implements Serializable{

	private String favicon;                           //用户的头像
	private Dot position;                             //结伴的地点
	private String title;                                //结伴的主题
	private String detail;                             //结伴详细信息
	private String time;                               //结伴的时间 
	private String type1;                              //用于筛选用的 例如官方的  活动
	private String username;                     //用户的名字
	private String label;                             //用于区分用的    例如  吃喝玩乐
	private String picture1;
	private String picture2;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getPicture1() {
		return picture1;
	}
	public void setPicture1(String picture1) {
		this.picture1 = picture1;
	}
	public String getPicture2() {
		return picture2;
	}
	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}
	public String getFavicon() {
		return favicon;
	}
	public void setFavicon(String favicon) {
		this.favicon = favicon;
	}
	public Dot getPosition() {
		return position;
	}
	public void setPosition(Dot position) {
		this.position = position;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "PublicUserIfo [favicon=" + favicon + ", position=" + position
				+ ", title=" + title + ", detail=" + detail + ", time=" + time
				+ ", type1=" + type1 + ", username=" + username +  ", type2=" + label +  "]";
	}

	
}
