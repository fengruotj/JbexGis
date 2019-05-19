package com.basic.service.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class DynamicInfo implements Serializable {
	private Long id;
	private User TUser;
	private Double dotX;
	private Double dotY;
	private String detail;
	private Timestamp time;
	private String picture1;
	private String picture2;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getTUser() {
		return TUser;
	}
	public void setTUser(User tUser) {
		TUser = tUser;
	}
	public Double getDotX() {
		return dotX;
	}
	public void setDotX(Double dotX) {
		this.dotX = dotX;
	}
	public Double getDotY() {
		return dotY;
	}
	public void setDotY(Double dotY) {
		this.dotY = dotY;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
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
	
	
}
