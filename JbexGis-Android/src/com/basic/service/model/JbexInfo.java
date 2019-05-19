package com.basic.service.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class JbexInfo implements Serializable{
	
	private Long id;
	private User TUser;
	private Double dotX;
	private Double dotY;
	private String title;
	private String detail;
	private Timestamp time;
	private String label;
	private String picture1;
	private String picture2;
	private int size;
	
	
	public JbexInfo() {
		super();
	}
	public Long getId() {
		return id;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
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
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
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
	public JbexInfo(Long id, User tUser, Double dotX, Double dotY,
			String title, String detail, Timestamp time, String label,
			String picture1, String picture2) {
		super();
		this.id = id;
		TUser = tUser;
		this.dotX = dotX;
		this.dotY = dotY;
		this.title = title;
		this.detail = detail;
		this.time = time;
		this.label = label;
		this.picture1 = picture1;
		this.picture2 = picture2;
	}
	
	
}
